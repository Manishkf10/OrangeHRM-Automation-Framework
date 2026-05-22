# OrangeHRM Automation Framework

An enterprise-grade automation testing framework for OrangeHRM with comprehensive support for UI, API, and database testing.

## 🎯 Overview

This framework provides a robust suite of automated tests to ensure reliability, functionality, and performance of the OrangeHRM application. It follows industry best practices and provides reusable components, utilities, and well-structured test organization.

---

## 🚀 Key Features

### UI Automation
- **Selenium WebDriver Integration** - Advanced web automation with Selenium 4.41.0
- **Cross-Browser Testing** - Support for Chrome, Firefox, Edge, and Safari
- **WebDriver Manager** - Automatic driver management and configuration
- **Page Object Model** - Maintainable and scalable test architecture
- **Locator Strategies** - Support for XPath, CSS selectors, and other locators

### Test Framework & Reporting
- **TestNG Framework** - Powerful test execution and organization (v7.7.1)
- **ExtentReports** - Beautiful HTML test reports with screenshots and logs (v5.1.2)
- **TestNG Listeners** - Event-driven test execution and custom reporting
- **Test Suites** - Flexible test grouping and execution via XML configuration

### Logging & Monitoring
- **Apache Log4j 2** - Enterprise-grade logging framework (v2.23.1)
  - Log4j Core for comprehensive logging
  - SLF4J Binding for unified logging interface
- **Custom Logging** - Detailed execution logs for debugging
- **Log Levels** - INFO, DEBUG, ERROR, WARN support

### Data Management & Excel Support
- **Apache POI** - Excel file handling and data-driven testing (v5.5.1)
  - POI OOXML for .xlsx files
  - POI Scratchpad for .xls files
- **Data-Driven Testing** - Parameterized tests with external data sources
- **Test Data Management** - Centralized test data handling

### Database Testing
- **MySQL Integration** - Native MySQL database connectivity (v9.5.0)
- **Database Validation** - Query execution and result verification
- **Data Integrity Testing** - Backend validation of frontend operations

### API Testing
- **REST Assured** - Comprehensive REST API testing (v6.0.0)
- **HTTP Methods** - GET, POST, PUT, DELETE, PATCH support
- **Request/Response Validation** - JSON and XML response handling
- **API Authentication** - Bearer tokens and custom headers
- **Integration Testing** - UI + API combined test scenarios

### Utilities & Helpers
- **Apache Commons IO** - File I/O operations (v2.21.0)
- **Java Faker** - Test data generation for realistic scenarios (v1.0.2)
- **Custom Utilities** - Screenshots, waits, synchronization helpers
- **Environment Configuration** - Multi-environment support

---

## 🛠️ Technology Stack

### Build & Dependency Management
- **Maven** - Project build and dependency management
- **Java 17** - Target runtime version
- **Maven Surefire Plugin** - Test execution and reporting

### Core Technologies
| Technology | Version | Purpose |
|------------|---------|---------|
| Selenium WebDriver | 4.41.0 | Web UI Automation |
| TestNG | 7.7.1 | Test Framework |
| WebDriver Manager | 5.6.3 | Driver Management |
| ExtentReports | 5.1.2 | Test Reporting |
| Apache Log4j | 2.23.1 | Logging Framework |
| Apache POI | 5.5.1 | Excel Data Handling |
| MySQL Connector | 9.5.0 | Database Connectivity |
| REST Assured | 6.0.0 | API Testing |
| Java Faker | 1.0.2 | Test Data Generation |
| Apache Commons IO | 2.21.0 | File Operations |

---

## 📁 Project Structure

```
OrangeHRM-Automation-Framework/
├── src/
│   ├── test/
│   │   ├── java/                 # Test classes and utilities
│   │   └── resources/            # Test configuration and data
│   └── main/                     # Page objects and core framework
├── extentReports/                # Generated HTML test reports
├── logs/                         # Test execution logs
├── test-output/                  # TestNG output directory
├── pom.xml                       # Maven configuration
├── .classpath                    # Eclipse classpath
├── .project                      # Eclipse project configuration
└── README.md                     # This file
```

---

## 🎨 Framework Architecture

### Layered Architecture
```
├── Test Layer (TestNG Test Classes)
├── Page Object Layer (Page Classes)
├── Test Utilities Layer (Helpers & Utils)
├── Logging Layer (Log4j)
├── Report Layer (ExtentReports)
└── Infrastructure Layer (Selenium, APIs, Database)
```

---

## 🔧 Prerequisites

- **Java Development Kit (JDK) 17+**
- **Maven 3.6+**
- **Git**
- **IDE** (Eclipse, IntelliJ IDEA, or VS Code)
- **MySQL Server** (for database testing)
- **Browser Drivers** (automatically managed by WebDriver Manager)

---

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/Manishkf10/OrangeHRM-Automation-Framework.git
cd OrangeHRM-Automation-Framework
```

### 2. Checkout the Development Branch
```bash
git checkout manish
```

### 3. Install Dependencies
```bash
mvn clean install
```

### 4. Configure Environment
Create `src/test/resources/config.properties` with:
```properties
base.url=http://your-orangehrm-instance
browser=chrome
timeout=10
headless=false
db.hostname=localhost
db.username=root
db.password=your_password
db.name=orangehrm
```

### 5. Verify Installation
```bash
mvn clean verify
```

---

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Suite
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/remoteApplicationTests.xml
```

### Run Specific Test Class
```bash
mvn test -Dtest=LoginTests
```

### Run Tests with Specific Tag
```bash
mvn test -Dgroups=smoke
```

### Generate Report After Tests
```bash
mvn clean test verify
```

---

## 📊 Test Reporting

### ExtentReports
- **Location**: `./extentReports/`
- **Format**: HTML with rich formatting
- **Includes**: 
  - Test execution status
  - Screenshots on failure
  - Execution timeline
  - Device and browser information
  - Custom logs

### View Report
Open `extentReports/index.html` in any web browser

---

## 📝 Test Data & Configuration

### Excel Data Sources
- Place test data in `src/test/resources/data/` directory
- Supported formats: `.xlsx` and `.xls`
- Utilize POI for dynamic data reading

### Environment Configuration
- Supports multiple environments (DEV, QA, PROD)
- Configure via properties files or system variables
- Database connections for backend validation

---

## 🔍 Logging

### Log Levels
- **DEBUG**: Detailed diagnostic information
- **INFO**: General informational messages
- **WARN**: Warning messages
- **ERROR**: Error messages with stack traces

### View Logs
```bash
# Tail live logs during test execution
tail -f logs/test.log

# View all logs
cat logs/test.log
```

---

## 🗄️ Database Testing

### Supported Operations
- **Query Execution**: SELECT, INSERT, UPDATE, DELETE
- **Data Validation**: Pre and post-condition checks
- **Integrity Testing**: Foreign key and constraint validation
- **Performance Testing**: Query execution time analysis

### Configuration
```properties
db.hostname=localhost
db.port=3306
db.username=root
db.password=your_password
db.name=orangehrm
```

---

## 🌐 API Testing

### Supported Endpoints
- Authentication APIs
- Employee Management APIs
- Leave Management APIs
- Attendance APIs
- Other REST endpoints

### Example API Test
```java
given()
    .header("Authorization", "Bearer " + token)
    .contentType(ContentType.JSON)
.when()
    .get("/api/v1/employees")
.then()
    .statusCode(200)
    .body("data.size()", greaterThan(0));
```

---

## 📚 Best Practices

### Test Design
- ✅ Use Page Object Model for UI tests
- ✅ Implement Data-Driven testing with external data
- ✅ Separate test logic from automation code
- ✅ Use meaningful test names and descriptions

### Maintenance
- ✅ Regular framework updates
- ✅ Reusable test utilities and helpers
- ✅ Centralized configuration management
- ✅ Version control best practices

### Performance
- ✅ Parallel test execution (TestNG feature)
- ✅ Optimized waits and synchronization
- ✅ Resource cleanup in test teardown
- ✅ Headless browser mode for CI/CD

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

### Code Standards
- Follow Java naming conventions
- Write meaningful comments and documentation
- Include test cases for new features
- Ensure all tests pass before submitting PR

---

## 📋 Current Updates

Refer to `need to update.txt` for ongoing enhancements and planned features.

---

## ❓ Troubleshooting

### Common Issues

**Issue**: WebDriver not found
- **Solution**: Ensure WebDriver Manager dependency is present and update drivers via Maven

**Issue**: Tests timeout
- **Solution**: Increase timeout values in configuration properties

**Issue**: Database connection failed
- **Solution**: Verify MySQL server is running and credentials are correct

**Issue**: Report not generated
- **Solution**: Check `extentReports/` directory permissions and disk space

---

## 📞 Support & Contact

For issues, questions, or feature requests:
- Open an issue on the GitHub repository
- Contact: [Manishkf10](https://github.com/Manishkf10)

---

## 📄 License

This project is open source and available under the MIT License.

---

## 🙏 Acknowledgments

- Built with [Selenium](https://www.selenium.dev/)
- Tested with [TestNG](https://testng.org/)
- Reported with [ExtentReports](https://www.extentreports.com/)
- Database support via [MySQL](https://www.mysql.com/)
- API testing via [REST Assured](https://rest-assured.io/)

---

**Last Updated**: May 2026  
**Version**: 0.0.1-SNAPSHOT  
**Maintainer**: Manishkf10
