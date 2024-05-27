package hexlet.code;

//import hexlet.code.model.Url;
//import hexlet.code.model.UrlCheck;
//import hexlet.code.repository.UrlCheckRepository;
//import hexlet.code.repository.UrlsRepository;
//import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
//import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {

    private static Javalin app;
    private static MockWebServer mockServer;
    private static String mockUrl;

    @BeforeAll
    public static void beforeAll() {
        mockServer = new MockWebServer();
        mockUrl = mockServer.url("/").toString();
    }

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Nested
    class TestCreateUrl {

        @Test
        public void testCreateCorrectUrl() {
            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=http://yandex.ru";
                var response = client.post("/urls", requestBody);
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body().string()).contains("yandex.ru");
            });
        }

        @Test
        public void testCreateIncorrectUrl() {
            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=asdfbgh";
                var response = client.post("/urls", requestBody);
                assertThat(response.body().string()).doesNotContain("asdfbgh");
            });
        }

        @Test
        public void testCreateDuplicateUrl() {
            JavalinTest.test(app, (server, client) -> {
                var requestBody = "url=http://yandex.ru";
                var response = client.post("/urls", requestBody);
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body().string()).contains("Сайты");
                assertThat(client.post("/urls", requestBody).body().string())
                        .contains("Бесплатно проверяйте сайты на SEO пригодность");
            });
        }
    }

    @Test
    public void testListUrls() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/urls", "url=http://yandex.ru");
            client.post("/urls", "url=http://hexlet.io");
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("hexlet.io", "yandex.ru");
        });
    }

    @Test
    public void testShowUrl() {
        JavalinTest.test(app, (server, client) -> {
            client.post("/urls", "url=http://yandex.ru");
            var response = client.get("/urls/1");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("yandex.ru", "Проверки");
        });
    }

//    @Test
//    public void testCheckUrl() throws IOException {
//        String mockResponse = Files.readString(Paths.get("src/test/resources/mockTest.html"));
//        mockServer.enqueue(new MockResponse()
//                .setBody(mockResponse));
//        JavalinTest.test(app, (server, client) -> {
//            Url url = new Url(mockUrl);
//            UrlsRepository.save(url);
//            var response = client.post(NamedRoutes.urlCheckPath(url.getId()));
//            assertThat(response.code()).isEqualTo(200);
//
//            UrlCheck urlCheck = UrlCheckRepository.getLast(url.getId()).get();
//
//            var title = urlCheck.getTitle();
//            var h1 = urlCheck.getH1();
//
//            assertThat(title).isEqualTo("Test");
//            assertThat(h1).isEqualTo("Test is successful");
//            mockServer.shutdown();
//        });
//    }
}
