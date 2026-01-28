#!/bin/bash

# Run login.feature specifically
echo "Running login.feature test..."
echo ""

mvn clean test \
    -Dtest=LoginFeatureRunner \
    -Denv=local \
    -Dbrowser=chrome \
    -Dheadless=false

echo ""
echo "Test execution completed!"
echo "View reports:"
echo "  - Cucumber HTML: target/cucumber-reports/login/index.html"
echo "  - Allure: mvn allure:serve"
