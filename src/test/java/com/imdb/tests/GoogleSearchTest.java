package com.imdb.tests;

import com.imdb.steps.GoogleSearchSteps;
import com.imdb.steps.NavigationSteps;
import io.qameta.allure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("IMDb Search")
@Feature("Google Search Integration")
public class GoogleSearchTest extends BaseTest {

    @Autowired
    private GoogleSearchSteps googleSearchSteps;
    @Autowired
    private NavigationSteps navigationSteps;

    private static final String IMDB_QUERY = "IMDb";

    @Test(description = "User can find IMDb through Google search")
    @Severity(CRITICAL)
    @Story("Google to IMDb navigation")
    public void testNavigateToImdbFromGoogle() {
        googleSearchSteps.openGooglePage();
        googleSearchSteps.searchOnGoogle(IMDB_QUERY);
        googleSearchSteps.clickImdbResult();
        navigationSteps.verifyOnHomePage();
    }

    @Test(description = "Google search results contain IMDb as top result")
    @Severity(NORMAL)
    @Story("Google search ranking")
    public void testImdbInTopGoogleResults() {
        googleSearchSteps.openGooglePage();
        googleSearchSteps.searchOnGoogle(IMDB_QUERY);
        googleSearchSteps.verifyImdbInTopResults(3);
    }
}