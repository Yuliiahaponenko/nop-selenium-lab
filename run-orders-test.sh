#!/bin/bash

# Run orders.feature specifically
echo "Running orders.feature test..."

mvn clean test \
    -Dtest=OrdersFeatureRunner \
    -Denv=local \
    -Dbrowser=chrome \
    -Dheadless=false

echo ""
echo "Test execution completed!"
echo "View reports: target/cucumber-reports/orders/index.html"
