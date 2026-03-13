package com.imdb.steps;

import com.imdb.components.NavigationBar;
import com.imdb.pages.ImdbOscarsNomineesPage;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Component
@RequiredArgsConstructor
public class OscarsSteps {

    private final ImdbOscarsNomineesPage nomineesPage;
    private final NavigationBar navigationBar;

    @Step("Navigate to Oscars page via menu")
    public void navigateToOscarsPage() {
        navigationBar.navigateToOscars();
    }

    @Step("Navigate to Oscars nominees for year {year}")
    public void navigateToOscarsYear(int year) {
        nomineesPage.navigateToYear(year);
    }

    @Step("Verify winner in '{category}' is '{expectedWinner}'")
    public void verifyWinner(String category, String expectedWinner) {
        assertThat(nomineesPage.isWinner(category, expectedWinner))
                .as("Winner for '%s' should be '%s'", category, expectedWinner)
                .isTrue();
    }

    @Step("Verify all winners for year {year}")
    public void verifyWinners(int year, Map<String, String> winners) {
        SoftAssertions softly = new SoftAssertions();
        winners.forEach((category, expectedWinner) ->
                softly.assertThat(nomineesPage.isWinner(category, expectedWinner))
                        .as("Winner for '%s' in %d should be '%s'", category, year, expectedWinner)
                        .isTrue()
        );
        softly.assertAll();
    }
}