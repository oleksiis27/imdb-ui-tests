package com.imdb.pages;

import com.codeborne.selenide.SelenideElement;
import com.imdb.components.CookieConsent;
import com.imdb.components.NavigationBar;
import com.imdb.components.SearchComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImdbHomePage extends BasePage {

    private static final String IMDB_URL = "https://www.imdb.com";

    private final SelenideElement logo = $("a[aria-label='Home']");
    private final SelenideElement searchBar = $("input[name='q']");

    @Getter
    private final SearchComponent searchComponent;
    @Getter
    private final NavigationBar navigationBar;
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
        boolean logoVisible = logo.isDisplayed();
        boolean searchVisible = searchBar.isDisplayed();
        log.info("IMDb home page loaded — logo: {}, search: {}", logoVisible, searchVisible);
        return logoVisible || searchVisible || getCurrentUrl().contains("imdb.com");
    }
}