package hexlet.code.controllers;

import hexlet.code.dto.ListOfUrlsPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UrlController {
    public static void createUrl(Context context) throws SQLException {
        String name = context.formParam("url");
        String correctedName;

        try {
            URI uri = new URI(name);
            URL url = uri.toURL();
            correctedName = url.getProtocol() + "://" + url.getAuthority();
        } catch (Exception e) {
            context.sessionAttribute("flash", "Некорректный URL");
            context.sessionAttribute("flashType", "danger");
            context.redirect(NamedRoutes.rootPath());
            return;
        }

        if (UrlsRepository.findByName(correctedName).isEmpty()) {
            UrlsRepository.save(new Url(correctedName));
            context.sessionAttribute("flash", "Страница успешно добавлена");
            context.sessionAttribute("flashType", "success");
            context.redirect(NamedRoutes.urlsPath());
        } else {
            context.sessionAttribute("flash", "Страница уже существует");
            context.sessionAttribute("flashType", "info");
            context.redirect(NamedRoutes.rootPath());
        }
    }

    public static void listUrls(Context context) throws SQLException {
        List<Url> urlsList = UrlsRepository.getEntities();
        var page = new ListOfUrlsPage(urlsList);
        page.setFlash(context.consumeSessionAttribute("flash"));
        page.setFlashType(context.consumeSessionAttribute("flashType"));
        page.setUrlsList(urlsList);
        context.render("ListOfUrls.jte", Collections.singletonMap("page", page));
    }

    public static void showUrl(Context context) throws SQLException {
        long id = context.pathParamAsClass("id", Long.class).getOrDefault(null);
        Url url = UrlsRepository.find(id).orElseThrow(NotFoundResponse::new);
        List<UrlCheck> urlChecks = UrlCheckRepository.getEntitiesByUrlId(id);

        var page = new UrlPage(url, urlChecks);

        page.setFlash(context.consumeSessionAttribute("flash"));
        page.setFlashType(context.consumeSessionAttribute("flashType"));
        page.setUrlCheckList(urlChecks);

        context.render("UrlInfo.jte", Collections.singletonMap("page", page));
    }

    public static void checkUrl(Context context) throws SQLException {
        long id = context.pathParamAsClass("id", Long.class).getOrDefault(null);
        Url url = UrlsRepository.find(id).orElseThrow(NotFoundResponse::new);

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document page = Jsoup.parse(response.getBody());

            int statusCode = response.getStatus();
            String title = page.title();
            String h1 = page.selectFirst("h1") == null ? ""
                    : Objects.requireNonNull(page.selectFirst("h1")).text();
            String description = page.select("meta[name=description]").get(0).attr("content");

            UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description);
            urlCheck.setUrlId(url.getId());
            UrlCheckRepository.save(urlCheck);
            context.sessionAttribute("flash", "Страница успешно проверена");
            context.sessionAttribute("flashType", "success");
        } catch (Exception exception) {
            context.sessionAttribute("flash", "Некорректный адрес");
            context.sessionAttribute("flashType", "danger");
        }

        context.redirect(NamedRoutes.urlPath(id));
    }

//    private static String correctUrlFormat(String name) throws MalformedURLException {
//        URL url = null;
//        try {
//            url = new URI(name).toURL();
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        String port = url.getPort() == -1 ? "" : ":" + url.getPort();
//        return url.getProtocol() + "://" + url.getHost() + port;
//    }
}
