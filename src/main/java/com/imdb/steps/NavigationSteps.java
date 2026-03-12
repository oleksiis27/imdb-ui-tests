package com.imdb.steps;

import com.imdb.components.NavigationBar;
import com.imdb.helpers.BrowserHelper;
import com.imdb.pages.ImdbHomePage;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Component
@RequiredArgsConstructor
public class NavigationSteps {

    private final ImdbHomePage homePage;
    private final NavigationBar navigationBar;

    @Step("Navigate to home page via IMDb logo")
    public void clickLogo() {
        BrowserHelper.scrollToTop();
        navigationBar.clickLogo();
    }

    @Step("Verify navigated to IMDb home page")
    public void verifyOnHomePage() {
        assertThat(homePage.getCurrentUrl())
                .as("Current URL should contain imdb.com")
                .contains("imdb.com");
        assertThat(homePage.isLoaded())
                .as("IMDb home page should be loaded")
                .isTrue();
    }
}