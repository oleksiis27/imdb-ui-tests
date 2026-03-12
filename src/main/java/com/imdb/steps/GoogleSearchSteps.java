package com.imdb.steps;

import com.imdb.pages.GooglePage;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleSearchSteps {

    private final GooglePage googlePage;

    @Step("Open Google search page")
    public void openGooglePage() {
        googlePage.openPage();
    }

    @Step("Search for '{query}' on Google")
    public void searchOnGoogle(String query) {
        googlePage.search(query);
    }

    @Step("Click IMDb link in search results")
    public void clickImdbResult() {
        googlePage.clickResultByUrl("imdb.com");
    }

    @Step("Verify IMDb is in top {topN} Google results")
    public void verifyImdbInTopResults(int topN) {
        boolean present = googlePage.isResultPresentInTop("imdb.com", topN);
        assertThat(present)
                .as("IMDb should be in top %d Google results", topN)
                .isTrue();
    }
}