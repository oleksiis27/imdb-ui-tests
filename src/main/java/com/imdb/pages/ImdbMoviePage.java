package com.imdb.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
@Component
public class ImdbMoviePage extends BasePage {

    private final SelenideElement movieTitle = $("h1[data-testid='hero__pageTitle']");
    private final SelenideElement ratingValue = $("[data-testid='hero-rating-bar__aggregate-rating__score'] span:first-child");


    @Step("Get movie title")
    public String getTitle() {
        String title = movieTitle.shouldBe(visible).getText();
        log.info("Movie title: {}", title);
        return title;
    }

    @Step("Get movie rating")
    public double getRating() {
        String ratingText = ratingValue.shouldBe(visible).getText();
        double rating = Double.parseDouble(ratingText);
        log.info("Movie rating: {}", rating);
        return rating;
    }

    @Step("Check if rating is above {threshold}")
    public boolean isRatingAbove(double threshold) {
        double rating = getRating();
        boolean result = rating > threshold;
        log.info("Rating {} > {}: {}", rating, threshold, result);
        return result;
    }
}