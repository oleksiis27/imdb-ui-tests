package com.imdb.tests;

import com.imdb.steps.SearchSteps;
import io.qameta.allure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("IMDb Search")
@Feature("Autocomplete Search")
public class AutocompleteSearchTest extends BaseTest {

    @Autowired
    private SearchSteps searchSteps;

    private static final String PARTIAL_NAME = "The Shawshank";
    private static final String FULL_NAME = "The Shawshank Redemption";
    private static final double RATE = 9.0;

    @Test(description = "Autocomplete suggestion is correct")
    @Severity(CRITICAL)
    @Story("Movie autocomplete")
    public void testAutocompleteSuggestion() {
        searchSteps.openHomePage();
        searchSteps.typeQueryAndWaitForSuggestions(PARTIAL_NAME);
        searchSteps.verifyFirstSuggestionContains(FULL_NAME);
        searchSteps.clickFirstSuggestion();
        searchSteps.verifyMovieTitle(FULL_NAME);
        searchSteps.verifyRatingAbove(RATE);
    }

    @Test(description = "Search returns results for exact movie name")
    @Severity(NORMAL)
    @Story("Full name search")
    public void testFullNameSearch() {
        searchSteps.openHomePage();
        searchSteps.submitSearch(FULL_NAME);
        searchSteps.verifyResultsContain(FULL_NAME);
    }
}