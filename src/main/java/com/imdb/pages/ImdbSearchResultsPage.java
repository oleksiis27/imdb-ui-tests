package com.imdb.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
@Component
public class ImdbSearchResultsPage extends BasePage {

    private final SelenideElement resultsSection = $("section[data-testid='find-results-section-title']");

    @Step("Check if results contain: {text}")
    public boolean containsResult(String text) {
        resultsSection.shouldBe(visible);
        String sectionText = resultsSection.getText();
        boolean contains = sectionText.toLowerCase().contains(text.toLowerCase());
        log.info("Results contain '{}': {}", text, contains);
        return contains;
    }
}