package com.imdb.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
@Component
public class CookieConsent {

    private final SelenideElement acceptButton = $("button[aria-label='Accept all'], #L2AGLb, [data-testid='accept-button']");
    private final SelenideElement consentBanner = $("div[aria-modal='true'], #CXQnmb, [data-testid='consent-banner']");

    @Step("Accept cookies if consent banner is present")
    public void acceptIfPresent() {
        try {
            if (consentBanner.is(visible, Duration.ofSeconds(3))) {
                log.info("Cookie consent banner detected, accepting");
                acceptButton.shouldBe(visible, Duration.ofSeconds(3)).click();
                log.info("Cookie consent accepted");
            } else {
                log.debug("No cookie consent banner present");
            }
        } catch (Throwable e) {
            log.debug("Cookie consent handling skipped: {}", e.getMessage());
        }
    }
}