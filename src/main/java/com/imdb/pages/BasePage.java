package com.imdb.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.executeJavaScript;


@Slf4j
public abstract class BasePage {

    @Step("Wait for page to fully load")
    public void waitForPageLoad() {
        log.debug("Waiting for page to fully load");
        Selenide.Wait().until(driver ->
                "complete".equals(executeJavaScript("return document.readyState"))
        );
        log.debug("Page fully loaded");
    }

    @Step("Scroll to element")
    public void scrollToElement(SelenideElement element) {
        log.debug("Scrolling to element: {}", element.getSearchCriteria());
        executeJavaScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    @Step("Get current URL")
    public String getCurrentUrl() {
        String url = Selenide.webdriver().driver().url();
        log.info("Current URL: {}", url);
        return url;
    }
}