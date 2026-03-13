package com.imdb.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
@Component
public class SearchComponent {

    private final SelenideElement searchInput = $("input[name='q']");
    private final ElementsCollection suggestionResults = $$("li[role='option'] a[data-testid='search-result--const']");
    private static final String SUGGESTION_TITLE_SELECTOR = "[class*='searchResult__constTitle']";

    @Step("Type query: {text}")
    public SearchComponent typeQuery(String text) {
        log.info("Typing search query: {}", text);
        searchInput.shouldBe(visible).setValue(text);
        return this;
    }

    @Step("Submit search: {text}")
    public void submitSearch(String text) {
        log.info("Submitting search for: {}", text);
        searchInput.shouldBe(visible).setValue(text).pressEnter();
    }

    @Step("Wait for autocomplete suggestions to appear")
    public SearchComponent waitForSuggestions() {
        log.debug("Waiting for autocomplete suggestions");
        suggestionResults.shouldHave(sizeGreaterThan(0));
        log.info("Autocomplete suggestions appeared, count: {}", suggestionResults.size());
        return this;
    }

    @Step("Get first suggestion title")
    public String getFirstSuggestionTitle() {
        suggestionResults.shouldHave(sizeGreaterThan(0));
        SelenideElement first = suggestionResults.first();
        String title = first.$(SUGGESTION_TITLE_SELECTOR).getText();
        log.info("First suggestion title: {}", title);
        return title;
    }

    @Step("Click first suggestion")
    public void clickFirstSuggestion() {
        suggestionResults.shouldHave(sizeGreaterThan(0));
        SelenideElement first = suggestionResults.first();
        log.info("Clicking first suggestion");
        first.shouldBe(visible).click();
    }
}