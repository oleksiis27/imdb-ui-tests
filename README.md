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
│   │   ├── BasePage.java                  # Common methods: scrollToElement, getCurrentUrl
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
-  Verify a single known Oscar winner (Best Cinematography)
- Data-driven test for multiple Oscar categories across years (Best Picture, Best Director, Best Actor, etc.)

### 3. Autocomplete Search (`AutocompleteSearchTest`)
- Autocomplete suggestion and rate are correct
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

## Allure Report

### Generate locally
```bash
allure generate build/allure-results -o build/allure-report --clean
allure open build/allure-report
```

### CI/CD
Tests run automatically on every push to `main` via GitHub Actions (headless Chrome). Allure report is generated and deployed to GitHub Pages after each run.
