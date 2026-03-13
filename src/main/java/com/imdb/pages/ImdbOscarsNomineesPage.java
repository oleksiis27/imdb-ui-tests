package com.imdb.pages;

import com.codeborne.selenide.SelenideElement;
import com.imdb.components.CookieConsent;
import com.imdb.config.AppConfig;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImdbOscarsNomineesPage extends BasePage {

    private static final String OSCARS_URL = AppConfig.getInstance().baseUrl() + "/oscars/";
    private static final String OSCARS_EVENT_ID = "ev0000003";
    private static final String DECADE_XPATH = "//*[contains(text(),'%s')]";
    private static final String YEAR_XPATH = "//a[contains(@href, '/event/" + OSCARS_EVENT_ID + "/%d/')]";
    private static final String CATEGORY_SELECTOR = "section[data-testid='%s']";

    private final SelenideElement nomineesLink = $("a[href*='" + OSCARS_EVENT_ID + "']");

    private final CookieConsent cookieConsent;

    @Step("Navigate to Oscars nominees for year {year}")
    public ImdbOscarsNomineesPage navigateToYear(int year) {
        log.info("Navigating to Oscars nominees for year: {}", year);
        openOscarsPage();
        clickNominees();
        selectDecade(year);
        selectYear(year);
        log.info("Navigated to Oscars {} nominees page", year);
        return this;
    }

    @Step("Open Oscars page")
    private void openOscarsPage() {
        open(OSCARS_URL);
        cookieConsent.acceptIfPresent();
    }

    @Step("Click Nominees link")
    private void clickNominees() {
        log.info("Clicking Nominees link");
        nomineesLink.shouldBe(visible).click();
    }

    @Step("Select decade for year {year}")
    private void selectDecade(int year) {
        String decade = (year / 10 * 10) + "s";
        log.info("Clicking decade: {}", decade);
        SelenideElement decadeLink = $x(String.format(DECADE_XPATH, decade));
        scrollToElement(decadeLink.shouldBe(visible));
        decadeLink.click();
    }

    @Step("Select year {year}")
    private void selectYear(int year) {
        log.info("Clicking year: {}", year);
        SelenideElement yearLink = $x(String.format(YEAR_XPATH, year));
        scrollToElement(yearLink.shouldBe(visible));
        yearLink.click();
    }

    @Step("Get winner in category: {categoryTestId}")
    public String getWinner(String categoryTestId) {
        log.info("Getting winner for category: {}", categoryTestId);
        SelenideElement categorySection = $(String.format(CATEGORY_SELECTOR, categoryTestId));
        categorySection.shouldBe(visible);
        scrollToElement(categorySection);

        String sectionText = categorySection.getText();
        log.debug("Category section text: {}", sectionText.substring(0, Math.min(200, sectionText.length())));

        String winner = extractWinnerFromSection(sectionText);
        log.info("Winner for '{}': {}", categoryTestId, winner);
        return winner;
    }

    @Step("Verify winner in category '{categoryTestId}' is '{expectedWinner}'")
    public boolean isWinner(String categoryTestId, String expectedWinner) {
        String actual = getWinner(categoryTestId);
        boolean match = actual.toLowerCase().contains(expectedWinner.toLowerCase());
        log.info("Expected '{}' in '{}', actual: '{}', match: {}", expectedWinner, categoryTestId, actual, match);
        return match;
    }

    private String extractWinnerFromSection(String sectionText) {
        String[] lines = sectionText.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().equals("WINNER") && i + 1 < lines.length) {
                return lines[i + 1].trim();
            }
        }
        return lines.length > 1 ? lines[1].trim() : sectionText.trim();
    }
}