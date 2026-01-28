# Test Scenarios Count Summary

## Total Test Scenarios: **66**

---

## Breakdown by Feature File

### 🔐 Authentication (18 scenarios)

#### login.feature - **7 scenarios**
1. Valid login
2. Invalid login with wrong password
3. Invalid login with non-existent email
4. Login with empty email
5. Login with empty password
6. Login with invalid email format
7. Login with remember me checked

#### register.feature - **8 scenarios**
1. Register new user with unique email
2. Register with password mismatch
3. Register with invalid email format
4. Register with weak password
5. Register with existing email
6. Register with missing first name
7. Register with missing last name
8. Register with all valid fields including gender

#### logout.feature - **3 scenarios**
1. Logout successfully
2. Logout and verify session cleared
3. Attempt logout when not logged in

---

### 📦 Products (14 scenarios)

#### search.feature - **7 scenarios**
1. Search returns results
2. Search with no results
3. Search with empty string
4. Search with special characters
5. Search with partial product name
6. Search case insensitive
7. Search with multiple words

#### pdp.feature - **7 scenarios**
1. Open product details and verify information
2. View product with all details
3. Add product to cart with quantity
4. Add product with invalid quantity zero
5. Add product with invalid quantity negative
6. Add product with quantity exceeding stock
7. Navigate back to search results

---

### 🛒 Shopping Cart (12 scenarios)

#### cart.feature - **12 scenarios**
1. Add product to cart
2. Add multiple different products to cart
3. Add same product multiple times
4. Update quantity
5. Update quantity to maximum
6. Update quantity with invalid value
7. Update quantity with negative value
8. Remove item
9. Remove one item from multiple items
10. Clear entire cart
11. View cart total calculation
12. Continue shopping from cart

---

### 💳 Checkout (14 scenarios)

#### guest_checkout.feature - **8 scenarios**
1. Guest checkout with valid address
2. Guest checkout with missing required fields
3. Guest checkout with invalid email format
4. Guest checkout with invalid phone number
5. Guest checkout with invalid zip code
6. Guest checkout with missing city
7. Guest checkout with missing address
8. Guest checkout navigation through all steps

#### registered_checkout.feature - **6 scenarios**
1. Registered checkout and order confirmation
2. Registered checkout with saved address
3. Registered checkout with new address
4. Registered checkout with different shipping address
5. Registered checkout without accepting terms
6. Registered checkout review order details

---

### 👤 Account (7 scenarios)

#### orders.feature - **7 scenarios**
1. Order appears in order history
2. View order details from history
3. Verify order status in history
4. View multiple orders in history
5. Reorder from order history
6. Download invoice from order history
7. Order history empty for new user

---

### 🔄 End-to-End (1 scenario)

#### purchase_journey.feature - **1 scenario**
1. Complete purchase journey

---

## Test Coverage Summary

### By Test Type:
- **Positive Tests**: ~35 scenarios (happy paths, valid flows)
- **Negative Tests**: ~31 scenarios (validation errors, invalid inputs, edge cases)

### By Tag:
- **@smoke**: ~15 scenarios (critical path tests)
- **@regression**: ~35 scenarios (broader coverage)
- **@e2e**: ~8 scenarios (complete journeys)
- **@auth**: 18 scenarios
- **@products**: 14 scenarios
- **@cart**: 12 scenarios
- **@checkout**: 14 scenarios
- **@account**: 7 scenarios

### By Domain:
- **Authentication**: 18 scenarios (27%)
- **Products**: 14 scenarios (21%)
- **Cart**: 12 scenarios (18%)
- **Checkout**: 14 scenarios (21%)
- **Account**: 7 scenarios (11%)
- **E2E**: 1 scenario (2%)

---

## Step Definitions

- **Total Step Definitions**: ~150+ unique step definitions
- **Step Definition Files**: 5 files
  - AuthSteps.java
  - ProductSteps.java
  - CartSteps.java
  - CheckoutSteps.java
  - AccountSteps.java

---

## Framework Components

- **Page Objects**: 9 page classes
- **Feature Files**: 10 files
- **Test Scenarios**: 66 scenarios
- **Step Definitions**: ~150+ steps
- **Test Coverage**: Comprehensive positive and negative scenarios
