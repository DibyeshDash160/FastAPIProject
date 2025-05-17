## Project name : FastAPIProject
## Author : Dibyesh Dash

This repository handles API automation with full CRUD operations.

## 🚀 Running the Project

To execute the tests, use one of the following Maven commands:

```bash
# Run with a specific environment
mvn test -Denv="your-environment-name"

# If user not specify any env ame then application will take default value as production
mvn clean test

# To generate report use this command
allure serve allure-results

 ⚠️ Challenges Faced During Framework Build-Up

# 🔄 Environment Configuration Handling:
# Designing a flexible configuration management system that supports multiple environments without hardcoding values.

# 📦 Maven Integration with External Tools:
# Integrating Allure with Maven and ensuring the report is properly generated and served across different environments and CI/CD runners.

# ⚙️ Dynamic Test Execution Based on Environment:
# Ensuring that test data and endpoints change dynamically based on the env parameter without duplicating code.

# 🔁 Managing Merge Conflicts in CI/CD:
# Adding checks to fail CI/CD runs if merge conflicts occur to enforce branch safety and code quality.

# 📂 Framework Scalability:
# Structuring the project to support the addition of more test modules and future expansion without requiring major refactoring.