@import hexlet.code.dto.ListOfUrlsPage
@import hexlet.code.util.NamedRoutes
@import java.text.SimpleDateFormat

@param ListOfUrlsPage page

@template.layout.page(
    page = page,
content = @`<head>
    <meta charset="UTF-8">
    <title>Urls</title>
</head>
    <body>
    <section>
        <section>
            <div class="container-lg mt-5">
                <h1>Сайты</h1>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th class="col-3">Имя</th>
                        <th>Последняя проверка</th>
                        <th class="col-2">Код ответа</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(var url : page.getUrlsList())
                        <tr>
                            <td>${url.getId()}</td>
                            <td>
                                <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                            </td>
                            <td>${page.getLastUrlCheck(url.getId()).isEmpty() ? "" :
                                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(page.getLastUrlCheck(url.getId()).get().getCreatedAt())}</td>
                            <td>${page.getLastUrlCheck(url.getId()).isEmpty() ? "" :
                                String.valueOf(page.getLastUrlCheck(url.getId()).get().getStatusCode())}</td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        </section>
    </section>
    </body>`)