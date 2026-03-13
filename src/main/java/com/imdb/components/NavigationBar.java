package com.imdb.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
@Component
public class NavigationBar {

    private final SelenideElement imdbLogo = $("a[aria-label='Home']");
    private final SelenideElement menuButton = $("#imdbHeader-navDrawerOpen");
    private final SelenideElement oscarsLink = $("a[href*='/oscars/']");

    @Step("Click IMDb logo to navigate home")
    public void clickLogo() {
        log.info("Clicking IMDb logo to navigate to home page");
        imdbLogo.shouldBe(visible).click();
    }

    @Step("Navigate to Oscars page via menu")
    public void navigateToOscars() {
        log.info("Opening navigation menu");
        menuButton.shouldBe(visible).click();
        log.info("Clicking Oscars link");
        oscarsLink.shouldBe(visible).click();
    }
}