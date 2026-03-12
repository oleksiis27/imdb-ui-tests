# IMDb UI Tests

UI test automation framework for [IMDb](https://www.imdb.com/) built with Java, Selenide, and TestNG.

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 21 | Programming language |
| Selenide | UI automation framework |
| Gradle (Kotlin DSL) | Build tool |
| TestNG | Test runner |
| Allure Report | Test reporting with steps, screenshots, attachments |
| Lombok | Boilerplate reduction (@Slf4j, @Getter) |
| Owner | Configuration management via interfaces |
| AssertJ | Fluent assertions and soft assertions |
| SLF4J + Logback | Logging (console + file) |
| DataFaker | Test data generation |

## Project Structure

```
src/
├── main/java/com/imdb/
│   ├── config/
│   │   └── AppConfig.java              # Owner config: base URL, browser, timeout
│   ├── pages/
│   │   ├── BasePage.java               # Common methods: waitForPageLoad, scrollTo
│   │   ├── GoogleSearchPage.java       # Google search page
│   │   ├── GoogleResultsPage.java      # Google search results
│   │   ├── ImdbHomePage.java           # IMDb home — search, navigation, logo
│   │   ├── ImdbSearchResultsPage.java  # IMDb search results page
│   │   ├── ImdbMoviePage.java          # Movie page — title, rating, year, genres
│   │   ├── ImdbOscarsPage.java         # Oscars main page
│   │   └── ImdbOscarsNomineesPage.java # Oscars nominees — categories, winners
│   ├── components/
│   │   ├── NavigationBar.java          # IMDb nav bar (reusable)
│   │   ├── SearchComponent.java        # Search bar with autocomplete (reusable)
│   │   └── CookieConsent.java          # Cookie popup handler
│   ├── helpers/
│   │   ├── BrowserHelper.java          # Scroll, switch tab, screenshot
│   │   └── WaitHelper.java             # Custom explicit waits
│   └── listeners/
│       ├── TestListener.java           # TestNG listener: logging on start/pass/fail/skip
│       └── AllureListener.java         # Screenshot + page URL on failure
├── main/resources/
│   ├── app.properties                  # Configuration properties
│   └── logback.xml                     # Logging config: console + rolling file
└── test/java/com/imdb/tests/
    ├── BaseTest.java                   # Browser setup, Allure Selenide listener
    ├── GoogleSearchTest.java           # Google → IMDb navigation flow
    ├── OscarsWinnerTest.java           # Oscars winner validation (data-driven)
    ├── AutocompleteSearchTest.java     # Autocomplete movie search
    └── ImdbAdditionalTests.java        # Home page smoke, movie page content, logo navigation
```

## Test Cases

### 1. Google Search Flow (`GoogleSearchTest`)
- Navigate to IMDb through Google search
- Verify IMDb appears in top Google results

### 2. Oscars Winner Validation (`OscarsWinnerTest`)
- Verify Titanic won Best Cinematography in 1998
- Data-driven test for multiple Oscar categories (Best Picture, Best Director, Best Actor, etc.)

### 3. Autocomplete Search (`AutocompleteSearchTest`)
- Autocomplete suggests "The Shawshank Redemption" and rating > 9.0
- Partial search "Incep" returns relevant suggestions including "Inception"
- Full name search returns correct results

### 4. Additional Tests (`ImdbAdditionalTests`)
- Home page smoke test (logo, search bar, title)
- Movie page displays all essential information (soft assertions)
- IMDb logo navigates back to home from any page

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

### Run smoke tests only
Tests tagged with `smoke` group run as part of the "Smoke Tests" suite defined in `testng.xml`.

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
./gradlew allureServe
```

### CI/CD
Allure report is automatically generated and deployed to GitHub Pages on every push to `main`.

## Key Design Decisions

- **Page Object Model** with reusable components (SearchComponent, NavigationBar, CookieConsent)
- **Allure `@Step` annotations** on all page object methods for detailed reporting
- **`@Slf4j` logging** on every action for debugging and traceability
- **Selenide best practices**: `$()` selectors, `shouldBe`/`shouldHave` conditions, no `Thread.sleep()`
- **Soft assertions** (AssertJ) for tests verifying multiple fields
- **Data-driven tests** via TestNG `@DataProvider` for Oscars categories
- **Cookie consent** handled gracefully — does not fail if banner is absent
- **Automatic screenshots** on test failure via AllureListener
- **TestNG groups** (`smoke`, `regression`) for selective test execution
