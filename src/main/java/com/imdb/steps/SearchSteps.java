package com.imdb.steps;

import com.imdb.components.SearchComponent;
import com.imdb.pages.ImdbHomePage;
import com.imdb.pages.ImdbMoviePage;
import com.imdb.pages.ImdbSearchResultsPage;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchSteps {

    private final ImdbHomePage homePage;
    private final ImdbMoviePage moviePage;
    private final ImdbSearchResultsPage searchResultsPage;
    private final SearchComponent searchComponent;

    @Step("Open IMDb home page")
    public void openHomePage() {
        homePage.openPage();
    }

    @Step("Type '{query}' in search and wait for suggestions")
    public void typeQueryAndWaitForSuggestions(String query) {
        searchComponent.typeQuery(query);
        searchComponent.waitForSuggestions();
    }

    @Step("Click first suggestion and open movie page")
    public void clickFirstSuggestion() {
        searchComponent.clickFirstSuggestion();
    }

    @Step("Submit search for '{query}'")
    public void submitSearch(String query) {
        searchComponent.submitSearch(query);
    }

    @Step("Verify first suggestion contains '{expectedText}'")
    public void verifyFirstSuggestionContains(String expectedText) {
        String firstTitle = searchComponent.getFirstSuggestionTitle();
        assertThat(firstTitle)
                .as("First suggestion title should contain '%s'", expectedText)
                .containsIgnoringCase(expectedText);
    }

    @Step("Verify movie title is '{expectedTitle}'")
    public void verifyMovieTitle(String expectedTitle) {
        assertThat(moviePage.getTitle())
                .as("Movie page title should be '%s'", expectedTitle)
                .contains(expectedTitle);
    }

    @Step("Verify movie rating is above {threshold}")
    public void verifyRatingAbove(double threshold) {
        assertThat(moviePage.isRatingAbove(threshold))
                .as("Movie rating should be above %.1f", threshold)
                .isTrue();
    }

    @Step("Verify search results contain '{text}'")
    public void verifyResultsContain(String text) {
        assertThat(searchResultsPage.containsResult(text))
                .as("Search results should contain '%s'", text)
                .isTrue();
    }
}