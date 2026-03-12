# IMDb UI Tests

UI test automation framework for [IMDb](https://www.imdb.com/) built with Java, Selenide, and TestNG.

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 21 | Programming language |
| Selenide | UI automation framework |
| Gradle (Kotlin DSL) | Build tool |
| TestNG | Test runner |
| Spring Boot | Dependency injection, test configuration |
| Allure Report | Test reporting with steps, screenshots, attachments |
| Lombok | Boilerplate reduction (@Slf4j, @RequiredArgsConstructor) |
| Owner | Configuration management via interfaces |
| AssertJ | Fluent assertions and soft assertions |
| SLF4J + Logback | Logging |

## Project Structure

```
src/
├── main/java/com/imdb/
│   ├── TestingApplication.java            # Spring Boot entry point
│   ├── config/
│   │   └── AppConfig.java                 # Owner config: base URL, browser, timeout
│   ├── pages/
│   │   ├── BasePage.java                  # Common methods: waitForPageLoad, scrollTo
│   │   ├── GooglePage.java                # Google search + results
│   │   ├── ImdbHomePage.java              # IMDb home — search, navigation, logo
│   │   ├── ImdbSearchResultsPage.java     # IMDb search results page
│   │   ├── ImdbMoviePage.java             # Movie page — title, rating
│   │   └── ImdbOscarsNomineesPage.java    # Oscars nominees — categories, winners
│   ├── components/
│   │   ├── NavigationBar.java             # IMDb nav bar (reusable)
│   │   ├── SearchComponent.java           # Search bar with autocomplete (reusable)
│   │   └── CookieConsent.java             # Cookie/CAPTCHA popup handler
│   ├── steps/
│   │   ├── GoogleSearchSteps.java         # Google search step methods
│   │   ├── SearchSteps.java               # IMDb search & autocomplete steps
│   │   ├── OscarsSteps.java               # Oscars verification steps
│   │   └── NavigationSteps.java           # Shared navigation steps (logo, home page)
│   ├── helpers/
│   │   └── BrowserHelper.java             # Scroll, switch tab
│   └── listeners/
│       └── TestListener.java              # TestNG listener: logging on start/pass/fail/skip
├── main/resources/
│   ├── app.properties                     # Configuration properties
│   └── logback.xml                        # Logging config
└── test/
    ├── java/com/imdb/tests/
    │   ├── BaseTest.java                  # Browser setup, Allure listener, screenshot on failure
    │   ├── GoogleSearchTest.java          # Google → IMDb navigation flow
    │   ├── OscarsWinnerTest.java          # Oscars winner validation (data-driven)
    │   └── AutocompleteSearchTest.java    # Autocomplete movie search
    └── resources/testdata/
        └── oscars-winners.json            # Test data for Oscars data-driven tests
```

## Test Cases

### 1. Google Search Flow (`GoogleSearchTest`)
- Navigate to IMDb through Google search
- Verify IMDb appears in top 3 Google results

### 2. Oscars Winner Validation (`OscarsWinnerTest`)
- Smoke: verify a single known Oscar winner (Best Cinematography 1998)
- Data-driven test for multiple Oscar categories across years (Best Picture, Best Director, Best Actor, etc.)

### 3. Autocomplete Search (`AutocompleteSearchTest`)
- Autocomplete suggests "The Shawshank Redemption" with rating > 9.0
- Full name search returns correct results

## Prerequisites

- **Java 21** (or higher)
- **Chrome** browser installed
- **Gradle 8.12** (wrapper included)

## Running Tests

### Run all tests
```bash
./gradlew test
```

### Run in headless mode (CI)
```bash
./gradlew test -Dheadless=true
```

### Configuration

Edit `src/main/resources/app.properties` or pass system properties:

| Property | Default | Description |
|----------|---------|-------------|
| `base.url` | `https://www.imdb.com` | Base URL |
| `browser` | `chrome` | Browser type |
| `timeout` | `10` | Element wait timeout (seconds) |
| `headless` | `false` | Headless browser mode |
| `browser.size` | `1920x1080` | Browser window size |
| `page.load.timeout` | `30` | Page load timeout (seconds) |

## Allure Report

### Generate locally
```bash
allure generate build/allure-results -o build/allure-report --clean
allure open build/allure-report
```

### CI/CD
Allure report is automatically generated and deployed to GitHub Pages on every push to `main`.

## Key Design Decisions

- **Page Object Model** with reusable components (SearchComponent, NavigationBar, CookieConsent)
- **Step layer** for reusable test steps with verification methods (GoogleSearchSteps, SearchSteps, OscarsSteps, NavigationSteps)
- **Spring Boot DI** for wiring pages, components, and steps
- **Allure `@Step` annotations** on all step and page methods for detailed reporting
- **`@Slf4j` logging** on every action for debugging and traceability
- **CSS selectors** preferred over XPath; selectors extracted to fields/constants
- **Soft assertions** (AssertJ) for tests verifying multiple fields
- **Data-driven tests** via TestNG `@DataProvider` with JSON test data
- **Cookie consent** handled gracefully — does not fail if banner is absent
- **CAPTCHA detection** on Google — skips test instead of false failure
- **Automatic screenshots** on test failure attached to Allure report
- **PageLoadStrategy.EAGER** for faster page loads on CI
