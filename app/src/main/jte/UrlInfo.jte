@import hexlet.code.dto.UrlPage
@import hexlet.code.util.NamedRoutes
@import java.text.SimpleDateFormat

@param UrlPage page

@template.layout.page(
    page = page,
content = @`
    <section>
    <div class="container-lg mt-5">
        <h1>Сайт ${page.getUrl().getName()}</h1>
        <table class="table table-bordered">
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${new SimpleDateFormat("dd/MM/yyyy HH:mm").format(page.getUrl().getCreatedAt())}</td>
            </tr>
        </table>
        <form method="post" action="${NamedRoutes.urlCheckPath(String.valueOf(page.getUrl().getId()))}">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>
        <h1 text="Проверки"></h1>
        <table class="table table-bordered table-hover mt-3">
            <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th class="col-1">Код ответа</th>
                    <th>Title</th>
                    <th>H1</th>
                    <th>Description</th>
                    <th class="col-2">Дата проверки</th>
                </tr>
            </thead>
            <tbody>
                @for(var urlCheck : page.getUrlCheckList())
                    <tr>
                        <td>${urlCheck.getId()}</td>
                        <td>${urlCheck.getStatusCode()}</td>
                        <td>${urlCheck.getTitle()}</td>
                        <td>${urlCheck.getH1()}</td>
                        <td>${urlCheck.getDescription()}</td>
                        <td>${new SimpleDateFormat("dd/MM/yyyy HH:mm").format(urlCheck.getCreatedAt())}</td>
                    </tr>
                @endfor
            </tbody>
        </table>
    </div>
</section>`)