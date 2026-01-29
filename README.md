# nopCommerce UI Automation Framework

A comprehensive BDD (Behavior-Driven Development) test automation framework for nopCommerce using Selenium WebDriver, Cucumber, and TestNG.

## 🎯 Overview

This framework demonstrates:
- **BDD approach** with Gherkin syntax (Cucumber)
- **Page Object Model (POM)** for maintainable test code
- **Data-driven testing** with JSON test data
- **Parallel execution** support via TestNG
- **Comprehensive reporting** (Allure + Cucumber HTML)
- **CI/CD integration** (GitHub Actions)

**Target Site**: https://nop-qa.portnov.com

## 📋 Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **Chrome/Firefox/Edge** browser installed
- **Git** (for CI/CD)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd nop-selenium-lab
```

### 2. Configure Test Data

Update test user credentials in `src/test/resources/config/local.properties`:

```properties
testUserEmail=your-test-email@example.com
testUserPassword=YourPassword123!
```

### 3. Run Tests

#### Run Smoke Tests (Default)
```bash
mvn clean test
```

#### Run with Specific Tags
```bash
# Smoke tests only
mvn test -Dtags="@smoke and not @wip"

# Regression tests
mvn test -Dtags="@regression and not @wip"

# E2E tests
mvn test -Dtags="@e2e and not @wip"

# Specific domain (e.g., cart)
mvn test -Dtags="@cart and not @wip"
```

#### Run with Custom Configuration
```bash
mvn test \
  -Denv=local \
  -DbaseUrl=https://nop-qa.portnov.com \
  -Dbrowser=chrome \
  -Dheadless=false \
  -Dtags="@smoke and not @wip"
```

### 4. View Reports

#### Cucumber HTML Report
```bash
open target/cucumber-reports/index.html
```

#### Allure Report
```bash
mvn allure:serve
```

## 📁 Project Structure

```
nop-selenium-lab/
├── src/
│   ├── main/java/com/nopcommerce/
│   │   ├── framework/
│   │   │   ├── config/          # Configuration management
│   │   │   ├── driver/           # WebDriver management
│   │   │   └── utils/            # Utility classes
│   │   └── pages/                # Page Object Model classes
│   │       ├── BasePage.java
│   │       ├── HomePage.java
│   │       ├── LoginPage.java
│   │       ├── RegisterPage.java
│   │       ├── ProductSearchPage.java
│   │       ├── ProductDetailsPage.java
│   │       ├── ShoppingCartPage.java
│   │       ├── CheckoutPage.java
│   │       └── OrderHistoryPage.java
│   └── test/
│       ├── java/com/nopcommerce/bdd/
│       │   ├── context/          # Test context (dependency injection)
│       │   ├── hooks/            # Cucumber hooks (setup/teardown)
│       │   ├── listeners/       # TestNG listeners
│       │   ├── runners/          # TestNG test runners
│       │   └── steps/            # Step definitions
│       └── resources/
│           ├── config/           # Environment configurations
│           ├── features/         # Gherkin feature files
│           │   ├── auth/
│           │   ├── products/
│           │   ├── cart/
│           │   ├── checkout/
│           │   ├── account/
│           │   └── e2e/
│           └── testdata/         # JSON test data files
├── .github/workflows/             # CI/CD workflows
├── pom.xml                        # Maven dependencies
├── testng.xml                     # TestNG configuration
└── README.md
```

## 🏷️ Tag Strategy

Tags act as test suites and control which tests run:

### Suite Tags
- `@smoke` - Fast, critical path tests (CI default)
- `@regression` - Broader test coverage
- `@e2e` - Complete end-to-end journeys
- `@wip` - Work in progress (excluded from CI)

### Domain Tags
- `@auth` - Authentication tests
- `@products` - Product search/browse tests
- `@cart` - Shopping cart tests
- `@checkout` - Checkout flow tests
- `@account` - Account/order history tests

### Example Tag Combinations
```bash
# Smoke suite (default CI)
@smoke and not @wip

# All checkout tests
@checkout and not @wip

# E2E only
@e2e and not @wip
```

## 🧪 Test Scenarios

### Authentication
- ✅ Valid login
- ✅ Invalid login (negative test)
- ✅ User registration with unique email
- ✅ Logout

### Products
- ✅ Product search with results
- ✅ Product search with no results
- ✅ Product details page validation

### Shopping Cart
- ✅ Add product to cart
- ✅ Update cart quantity
- ✅ Remove item from cart

### Checkout
- ✅ Guest checkout (if enabled)
- ✅ Registered user checkout
- ✅ Order confirmation

### Account
- ✅ Order history validation

### E2E
- ✅ Complete purchase journey (search → add → checkout → verify)

## ⚙️ Configuration

### Environment Configuration

Edit `src/test/resources/config/{env}.properties`:

```properties
baseUrl=https://nop-qa.portnov.com
browser=chrome
headless=false
implicitWait=10
explicitWait=20
testUserEmail=testuser@example.com
testUserPassword=Test123!
screenshotPath=screenshots/
logPath=logs/
```

### System Properties Override

You can override any config via system properties:

```bash
mvn test -Denv=ci -Dbrowser=firefox -Dheadless=true
```

## 🔧 Framework Components

### Page Object Model (POM)
- **BasePage**: Common page actions and utilities
- **Domain Pages**: Specific page classes (HomePage, LoginPage, etc.)
- **Locator Strategy**: IDs > name > data-* > CSS > XPath

### WebDriver Management
- **ThreadLocal** driver for parallel execution
- **WebDriverManager** for automatic driver setup
- **Explicit waits** via WebDriverWaitUtil

### Test Data Management
- **JSON files** for structured test data
- **Unique email generation** for registration tests
- **Data isolation** per test run

### Reporting
- **Cucumber HTML** reports
- **Allure** reports with screenshots
- **Logs** saved to `logs/` directory
- **Screenshots** on failure in `screenshots/`

## 🚦 CI/CD (GitHub Actions)

The framework includes a GitHub Actions workflow (`.github/workflows/ci.yml`) that:

1. Runs on push/PR to main/develop branches
2. Executes smoke tests in headless Chrome
3. Generates Allure reports
4. Uploads test artifacts (reports, screenshots, logs)

### Manual CI Trigger
```bash
# Push to trigger CI
git push origin main

# Or use workflow_dispatch in GitHub UI
```

## 📊 Running Tests in Parallel

The framework supports parallel execution via TestNG:

```xml
<!-- testng.xml -->
<suite name="nopCommerce Test Suite" parallel="methods" thread-count="2">
```

Adjust `thread-count` based on your system capacity.

## 🐛 Debugging

### Enable Debug Logging
Edit `src/test/resources/logback.xml`:

```xml
<root level="DEBUG">
```

### View Logs
```bash
tail -f logs/test.log
```

### Screenshots on Failure
Screenshots are automatically saved to `screenshots/` on test failure.

## 📝 Best Practices

1. **Idempotent Tests**: Each test is independent and can run in any order
2. **Unique Data**: Registration tests use unique emails per run
3. **Explicit Waits**: No Thread.sleep(); uses WebDriverWait
4. **Stable Locators**: Prefer IDs, names, data-* attributes
5. **Clean State**: Cookies cleared before each scenario
6. **Error Handling**: Screenshots and logs on failure

## 🔍 Troubleshooting

### Tests Fail with "Element not found"
- Check if locators match current site structure
- Verify site is accessible: `https://nop-qa.portnov.com`
- Increase explicit wait timeout in config

### Driver Issues
- Ensure browser is installed and up-to-date
- WebDriverManager should auto-download drivers
- Check browser version compatibility

### CI Failures
- Verify GitHub Actions has Chrome installed
- Check headless mode is enabled for CI
- Review logs in CI artifacts

## 📚 Dependencies

Key dependencies (see `pom.xml` for full list):
- **Selenium 4.15.0**
- **TestNG 7.8.0**
- **Cucumber 7.14.0**
- **WebDriverManager 5.6.2**
- **Allure 2.24.0**
- **AssertJ 3.24.2**
