# SDET Homework - Thanida Chaiwongnon

This repository contains automated test scenarios for the SauceDemo website (https://www.saucedemo.com/) using Selenium WebDriver with Java and TestNG framework.

## Project Structure

```
sdet-homework/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/
│   │           ├── LoginPage.java
│   │           ├── InventoryPage.java
│   │           ├── CartPage.java
│   │           ├── CheckoutPage.java
│   │           └── CheckoutCompletePage.java
│   └── test/
│       └── java/
│           └── tests/
│               ├── TestScenario1.java
│               └── TestScenario2.java
├── pom.xml
└── README.md
```

## Technologies Used

- **Java 8+** - Programming language
- **Selenium WebDriver 4.11.0** - Browser automation
- **TestNG** - Testing framework
- **Maven** - Build and dependency management
- **WebDriverManager** - Automatic WebDriver management
- **Chrome WebDriver** - Browser driver

## Test Scenarios

### Scenario 1: Complete E2E Purchase Flow (`TestScenario1.java`)
- Validate that the login is successful.
- Sort the products by price from high to low.
- In the cart page, validate the items(Qty, item name, etc.) in the shopping cart
- Validate the ‘Remove’ button for each item
- In Checkout: Overview page, validate Price Total details.
- In Checkout: Complete page, validate successful order message.

### Scenario 2: Locked-out User Login Test (`TestScenario2.java`)
- Validate that the user did not logged in successfully.
- Validate that correct error message is displayed.

## Prerequisites

Before running the tests, ensure you have:

1. **Java 8 or higher** installed
   ```bash
   java -version
   ```

2. **Maven** installed
   ```bash
   mvn -version
   ```

3. **Google Chrome browser** installed (latest version recommended)

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd sdet-homework
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## How to Run Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
# Run Scenario 1 - Complete purchase flow
mvn test -Dtest=TestScenario1

# Run Scenario 2 - Locked user test
mvn test -Dtest=TestScenario2
```

## Test Reports

After running tests, reports are generated in:
- **Surefire Reports:** `target/surefire-reports/`
- **TestNG Reports:** `test-output/` (if generated)

## Page Object Model

This project implements the Page Object Model (POM) design pattern:

- **LoginPage:** Handles login functionality
- **InventoryPage:** Manages product selection and cart operations
- **CartPage:** Handles cart review and checkout navigation
- **CheckoutPage:** Manages checkout form and order completion
- **CheckoutCompletePage:** Verifies order completion

## Key Features

- **Explicit Waits:** Waiting strategies for element interactions
- **JavaScript Fallbacks:** Alternative interaction methods for problematic elements
- **Chrome Options:** Configured to disable password manager and notifications
- **WebDriverManager:** Automatic driver management
- **Cross-platform Support:** Compatible with Mac, Windows, and Linux

## Test Data

### Valid Login Credentials:
- Username: `standard_user`
- Password: `secret_sauce`

### Locked User Credentials:
- Username: `locked_out_user`
- Password: `secret_sauce`

### Test Products:
- Sauce Labs Backpack ($29.99)
- Sauce Labs Bike Light ($9.99)

### Checkout Information:
- First Name: Thanida
- Last Name: Chaiwongnon
- Postal Code: 10900

### Common Issues:

- **ChromeDriver Version Mismatch:** Update Chrome browser to latest version


## Maven Dependencies

Key dependencies included in `pom.xml`:
- Selenium WebDriver
- TestNG
- WebDriverManager
- Maven Surefire Plugin

## Author

**Thanida Chaiwongnon**  
SDET Homework Assignment