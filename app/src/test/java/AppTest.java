import hexlet.code.App;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {

    Javalin app;
    MockWebServer mockServer;

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
//                assertThat(response.body().string()).contains("Страница успешно добавлена");
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
                assertThat(client.post("/urls", requestBody).body().string()).contains("Бесплатно проверяйте сайты на SEO пригодность");
            });
        }
    }
}
