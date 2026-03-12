package com.imdb.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.steps.NavigationSteps;
import com.imdb.steps.OscarsSteps;
import io.qameta.allure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("IMDb Awards")
@Feature("Oscars")
public class OscarsWinnerTest extends BaseTest {

    private static final int YEAR = 1998;
    private static final String CATEGORY = "BestCinematography";
    private static final String EXPECTED_WINNER = "Titanic";

    @Autowired
    private OscarsSteps oscarsSteps;
    @Autowired
    private NavigationSteps navigationSteps;

    @DataProvider(name = "oscarsWinners")
    public Object[][] oscarsWinners() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream("testdata/oscars-winners.json");
        List<Map<String, Object>> data = mapper.readValue(is, new TypeReference<>() {});

        return data.stream()
                .map(entry -> new Object[]{
                        entry.get("year"),
                        entry.get("winners")
                })
                .toArray(Object[][]::new);
    }

    @Test(description = "Smoke: verify a single known Oscar winner")
    @Severity(CRITICAL)
    @Story("Single category winner smoke")
    public void testSingleCategoryWinner() {
        oscarsSteps.navigateToOscarsYear(YEAR);
        oscarsSteps.verifyWinner(CATEGORY, EXPECTED_WINNER);
        navigationSteps.clickLogo();
        navigationSteps.verifyOnHomePage();
    }

    @Test(description = "Verify Oscar winners for year {0}",
            dataProvider = "oscarsWinners")
    @Severity(NORMAL)
    @Story("Oscars winners by year")
    public void testOscarWinners(int year, Map<String, String> winners) {
        oscarsSteps.navigateToOscarsYear(year);
        oscarsSteps.verifyWinners(year, winners);
    }
}