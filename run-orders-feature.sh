#!/bin/bash

# Run orders.feature (@e2e @account) specifically
echo "Running orders.feature test (@e2e @account)..."
echo ""

mvn clean test \
    -Dtest=OrdersFeatureRunner \
    -Denv=local \
    -Dbrowser=chrome \
    -Dheadless=false

echo ""
echo "Test execution completed!"
echo "View reports:"
echo "  - Cucumber HTML: target/cucumber-reports/orders/index.html"
echo "  - Allure: mvn allure:serve"
