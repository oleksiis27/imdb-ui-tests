package com.imdb.pages;

import com.codeborne.selenide.SelenideElement;
import com.imdb.components.CookieConsent;
import com.imdb.config.AppConfig;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImdbHomePage extends BasePage {

    private static final String IMDB_URL = AppConfig.getInstance().baseUrl();

    private final SelenideElement searchBar = $("input[name='q']");

    private final CookieConsent cookieConsent;

    @Step("Open IMDb home page")
    public ImdbHomePage openPage() {
        log.info("Opening IMDb home page");
        open(IMDB_URL);
        cookieConsent.acceptIfPresent();
        return this;
    }

    @Step("Verify IMDb home page is loaded")
    public boolean isLoaded() {
        boolean searchVisible = searchBar.isDisplayed();
        boolean urlMatch = getCurrentUrl().contains("imdb.com");
        log.info("IMDb home page loaded — search: {}, url: {}", searchVisible, urlMatch);
        return searchVisible || urlMatch;
    }
}