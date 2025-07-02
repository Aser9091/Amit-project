# Demoblaze Test Automation

A test automation framework for the Demoblaze e-commerce website using Selenium WebDriver, Cucumber, and TestNG.

## Overview

This project contains automated tests for the Demoblaze demo website, covering user registration, login, product purchasing, and various negative scenarios. The framework uses Page Object Model (POM) design pattern for maintainable test code.

## Project Structure

```
src/
├── main/
│   ├── java/org/example/
│   │   └── Main.java
│   └── resources/
│       ├── features/
│       │   ├── SignUp.feature
│       │   ├── PurchaseTwoProducts.feature
│       │   └── NegativeScenarios.feature
│       └── config/
│           └── test.properties
└── test/
    ├── java/org/example/
    │   ├── runners/
    │   │   └── TestRunner.java
    │   ├── stepDefs/
    │   │   ├── Hooks.java
    │   │   ├── SignUpStepDef.java
    │   │   ├── PurchaseTwoProductsStepDef.java
    │   │   ├── NegativeScenariosStepDef.java
    │   │   └── SharedStepDef.java
    │   └── utils/
    │       ├── TestDataManager.java
    │       └── WebDriverManager.java
    └── pages/
        ├── HomePage.java
        ├── SignUpPage.java
        ├── LoginPage.java
        ├── ProductPage.java
        ├── CartPage.java
        └── CheckoutPage.java
```

## Test Scenarios

### Positive Scenarios
- **User Registration**: New user signup with validation
- **Product Purchase**: Adding multiple products to cart and completing checkout
- **Login Functionality**: Valid user authentication

### Negative Scenarios
- **Duplicate Registration**: Attempting to register with existing username
- **Invalid Login**: Testing with incorrect credentials
- **Expired Payment**: Checkout with expired credit card
- **Duplicate Cart Items**: Adding same product multiple times

## Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Chrome browser (WebDriverManager handles driver download)

### Configuration
1. Clone the repository
2. Navigate to project directory
3. Update `src/main/resources/config/test.properties` with your test data

### Running Tests

**Run all tests:**
```bash
mvn clean test
```

**Run specific feature:**
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

**Run with specific browser:**
```bash
mvn test -Dbrowser=firefox
```

## Key Features

- **Page Object Model**: Maintainable and reusable page objects
- **Data-Driven Testing**: External test data management
- **Cross-Browser Support**: Chrome, Firefox, Edge
- **Parallel Execution**: TestNG parallel test execution
- **Detailed Logging**: Comprehensive test execution logs
- **Environment Configuration**: Separate configs for different environments

## Test Reports

After test execution, reports are generated in:
- `target/cucumber-reports/cucumber-pretty.html`
- `target/cucumber-reports/CucumberTestReport.json`

## Contributing

1. Follow the existing code structure
2. Add appropriate test tags for categorization
3. Update documentation for new features
4. Ensure all tests pass before submitting

## Notes

- Tests are designed for the demo environment at https://www.demoblaze.com/
- Some scenarios may fail due to demo site limitations
- Test data is randomized to avoid conflicts in parallel execution 