package com.imdb.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.imdb.components.CookieConsent;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.testng.SkipException;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class GooglePage extends BasePage {

    private static final String GOOGLE_URL = "https://www.google.com";

    private final SelenideElement searchInput = $("textarea[name='q']");
    private final SelenideElement captchaForm = $("#captcha-form");
    private final SelenideElement captchaFormAlt = $("form#captcha-form");
    private final SelenideElement recaptcha = $("div.g-recaptcha");
    private final ElementsCollection searchResults = $$("#search a[href][data-ved], #search a[href][ping], #rso a[href]");

    private final CookieConsent cookieConsent;

    @Step("Open Google search page")
    public GooglePage openPage() {
        log.info("Opening Google search page");
        open(GOOGLE_URL);
        cookieConsent.acceptIfPresent();
        checkForCaptcha();
        waitForPageLoad();
        return this;
    }

    @Step("Search for: {query}")
    public void search(String query) {
        log.info("Searching Google for: {}", query);
        sleep(1000);
        searchInput.shouldBe(visible).setValue(query).pressEnter();
    }

    @Step("Click search result containing URL: {urlPart}")
    public void clickResultByUrl(String urlPart) {
        checkForCaptcha();
        log.info("Looking for search result with URL containing: {}", urlPart);
        searchResults.shouldHave(sizeGreaterThan(0));
        SelenideElement result = searchResults.stream()
                .filter(el -> {
                    String href = el.getAttribute("href");
                    return href != null && href.contains(urlPart);
                })
                .findFirst()
                .orElseThrow(() -> new AssertionError("No search result found with URL containing: " + urlPart));
        log.info("Found result, clicking: {}", result.getAttribute("href"));
        result.shouldBe(visible).click();
    }

    @Step("Get all result URLs")
    public List<String> getResultUrls() {
        searchResults.shouldHave(sizeGreaterThan(0));
        List<String> urls = searchResults.stream()
                .map(el -> el.getAttribute("href"))
                .filter(href -> href != null && !href.isEmpty())
                .toList();
        log.info("Found {} result URLs", urls.size());
        return urls;
    }

    @Step("Check if result with URL containing '{urlPart}' is present in top {topN} results")
    public boolean isResultPresentInTop(String urlPart, int topN) {
        List<String> urls = getResultUrls();
        boolean present = urls.stream()
                .limit(topN)
                .anyMatch(url -> url.contains(urlPart));
        log.info("URL containing '{}' present in top {}: {}", urlPart, topN, present);
        return present;
    }

    private void checkForCaptcha() {
        boolean hasCaptcha = captchaForm.is(exist)
                || captchaFormAlt.is(exist)
                || recaptcha.is(exist);
        if (hasCaptcha) {
            log.warn("Google CAPTCHA detected — skipping test");
            throw new SkipException("Google CAPTCHA detected. Test skipped to avoid false failure.");
        }
    }
}