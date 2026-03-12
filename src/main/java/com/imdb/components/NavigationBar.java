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

    @Step("Click IMDb logo to navigate home")
    public void clickLogo() {
        log.info("Clicking IMDb logo to navigate to home page");
        imdbLogo.shouldBe(visible).click();
    }
}