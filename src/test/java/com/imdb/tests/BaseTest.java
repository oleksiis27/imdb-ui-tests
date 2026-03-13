package com.imdb.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.imdb.TestingApplication;
import com.imdb.config.AppConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.ByteArrayInputStream;

@Slf4j
@SpringBootTest(classes = TestingApplication.class)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    private static final AppConfig config = AppConfig.getInstance();

    @BeforeSuite(alwaysRun = true)
    public void setupAllure() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false));
        log.info("AllureSelenide listener configured");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Configuration.browser = config.browser();
        Configuration.baseUrl = config.baseUrl();
        Configuration.timeout = config.timeout() * 1000L;
        Configuration.pageLoadTimeout = config.pageLoadTimeout() * 1000L;
        Configuration.browserSize = config.browserSize();
        Configuration.headless = config.headless();
        Configuration.screenshots = true;
        Configuration.savePageSource = false;

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", java.util.List.of("enable-automation"));
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        Configuration.browserCapabilities = options;

        log.info("Browser session started: {} | headless: {} | timeout: {}s",
                Configuration.browser, Configuration.headless, config.timeout());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && WebDriverRunner.hasWebDriverStarted()) {
            try {
                byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                        .getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on failure", "image/png",
                        new ByteArrayInputStream(screenshot), ".png");
                Allure.addAttachment("Page URL", "text/plain",
                        WebDriverRunner.getWebDriver().getCurrentUrl());
                log.info("Attached failure screenshot to Allure report");
            } catch (Exception e) {
                log.warn("Failed to attach screenshot: {}", e.getMessage());
            }
        }
        log.info("Closing browser session");
        Selenide.closeWebDriver();
    }
}