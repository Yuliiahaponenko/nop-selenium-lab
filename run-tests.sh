#!/bin/bash

# nopCommerce Test Runner Script
# Usage: ./run-tests.sh [smoke|regression|e2e|all]

ENV=${ENV:-local}
BROWSER=${BROWSER:-chrome}
HEADLESS=${HEADLESS:-false}
TAGS=${TAGS:-"@smoke and not @wip"}

case "$1" in
    smoke)
        TAGS="@smoke and not @wip"
        echo "Running smoke tests..."
        ;;
    regression)
        TAGS="@regression and not @wip"
        echo "Running regression tests..."
        ;;
    e2e)
        TAGS="@e2e and not @wip"
        echo "Running E2E tests..."
        ;;
    all)
        TAGS="not @wip"
        echo "Running all tests..."
        ;;
    *)
        echo "Usage: $0 [smoke|regression|e2e|all]"
        echo "Example: $0 smoke"
        exit 1
        ;;
esac

mvn clean test \
    -Denv=${ENV} \
    -Dbrowser=${BROWSER} \
    -Dheadless=${HEADLESS} \
    -Dtags="${TAGS}"

echo ""
echo "Test execution completed!"
echo "View reports:"
echo "  - Cucumber HTML: target/cucumber-reports/index.html"
echo "  - Allure: mvn allure:serve"
