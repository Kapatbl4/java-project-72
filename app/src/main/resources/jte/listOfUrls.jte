@import hexlet.code.dto.ListOfUrlsPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter

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
                        !{var urlCheck = page.getChecks().get(url.getId());}
                        <tr>
                            <td>${url.getId()}</td>
                            <td>
                                <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                            </td>
                            <td>${urlCheck == null ? "" : DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                                    .format(urlCheck.getCreatedAt())}</td>
                            <td>${urlCheck == null ? "" : String.valueOf(urlCheck.getStatusCode())}</td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        </section>
    </section>
    </body>`)