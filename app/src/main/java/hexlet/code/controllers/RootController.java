package hexlet.code.controllers;

import hexlet.code.dto.MainPage;
import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void welcome(Context context) {
        var page = new MainPage();
        page.setFlash(context.consumeSessionAttribute("flash"));
        page.setFlashType(context.consumeSessionAttribute("flashType"));
        context.render("MainPage.jte", Collections.singletonMap("page", page));
    }
}
