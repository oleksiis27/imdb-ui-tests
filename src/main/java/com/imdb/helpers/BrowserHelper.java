package com.imdb.helpers;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;


import static com.codeborne.selenide.Selenide.executeJavaScript;

@Slf4j
public final class BrowserHelper {
    @Step("Scroll to top of the page")
    public static void scrollToTop() {
        log.debug("Scrolling to top of the page");
        executeJavaScript("window.scrollTo({top: 0, behavior: 'instant'});");
    }
}