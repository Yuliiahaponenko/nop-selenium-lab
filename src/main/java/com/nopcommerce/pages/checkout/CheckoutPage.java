package com.nopcommerce.pages.checkout;

import com.nopcommerce.core.base.BasePage;
import com.nopcommerce.utils.waits.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CheckoutPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutPage.class);
    
    // Guest Checkout - using ID for radio, CSS for button (no ID available)
    private static final By GUEST_CHECKOUT_BUTTON = By.cssSelector("button.checkout-as-guest-button, input[value='Checkout as Guest'], button[onclick*='setLocation'][value*='Guest'], .checkout-as-guest");
    private static final By GUEST_CHECKOUT_RADIO = By.id("checkoutasguest");
    
    // Billing Address - using IDs where available
    private static final By BILLING_ADDRESS_SECTION = By.cssSelector("#billing-new-address-form, .billing-address-form, #checkout-step-billing, .checkout-step-billing");
    private static final By NEW_ADDRESS_DROPDOWN = By.id("billing-address-select");
    private static final By FIRST_NAME_INPUT = By.id("BillingNewAddress_FirstName");
    private static final By LAST_NAME_INPUT = By.id("BillingNewAddress_LastName");
    private static final By EMAIL_INPUT = By.id("BillingNewAddress_Email");
    private static final By COUNTRY_DROPDOWN = By.id("BillingNewAddress_CountryId");
    // State/Province is required for some countries (e.g. United States)
    // Different nopCommerce builds use different IDs, so keep both.
    private static final By STATE_PROVINCE_DROPDOWN = By.id("BillingNewAddress_StateProvinceId");
    private static final By STATE_ID_DROPDOWN = By.id("BillingNewAddress_StateId");
    private static final By CITY_INPUT = By.id("BillingNewAddress_City");
    private static final By ADDRESS1_INPUT = By.id("BillingNewAddress_Address1");
    private static final By ZIP_INPUT = By.id("BillingNewAddress_ZipPostalCode");
    private static final By PHONE_INPUT = By.id("BillingNewAddress_PhoneNumber");
    private static final By BILLING_VALIDATION_SUMMARY = By.cssSelector(".message-error, .validation-summary-errors, .field-validation-error");
    // Multiple locators for continue billing button - try ID first, then CSS fallbacks
    private static final By[] CONTINUE_BILLING_BUTTONS = {
        By.cssSelector("button.new-address-next-step-button"),
        By.cssSelector("input.button-1.new-address-next-step-button"),
        By.cssSelector("button.button-1.new-address-next-step-button"),
        By.cssSelector("input[onclick*='Billing.save()']"),
        By.cssSelector("button[onclick*='Billing.save()']"),
        By.cssSelector("button[type='button'][onclick*='Billing']"),
        By.cssSelector("input[type='button'][onclick*='Billing']"),
        By.cssSelector("button[title='Continue']"),
        By.cssSelector("input[value='Continue']"),
        By.cssSelector("button[value='Continue']"),
        // very last resort
        By.xpath("//button[contains(@class,'new-address-next-step-button') or contains(@onclick,'Billing')] | //input[@type='button' and contains(@onclick,'Billing')]")
    };

    // Shipping - try ID first, then CSS fallback
    private static final By CONTINUE_SHIPPING_BUTTON_ID = By.id("shipping-method-next-step-button");
    private static final By CONTINUE_SHIPPING_BUTTON = By.cssSelector("button.shipping-method-next-step-button");

    // Payment - try IDs first, then CSS fallbacks
    private static final By CONTINUE_PAYMENT_BUTTON_ID = By.id("payment-method-next-step-button");
    private static final By CONTINUE_PAYMENT_BUTTON = By.cssSelector("button.payment-method-next-step-button");
    private static final By CONTINUE_PAYMENT_INFO_BUTTON_ID = By.id("payment-info-next-step-button");
    private static final By CONTINUE_PAYMENT_INFO_BUTTON = By.cssSelector("button.payment-info-next-step-button");

    // Confirm Order - try IDs first, then CSS fallbacks
    private static final By[] CONFIRM_ORDER_BUTTONS = {
        By.id("confirm-order-buttons-container"), // Container ID first
        By.id("confirm-order-next-step-button"), // Try common ID pattern
        By.cssSelector("#confirm-order-buttons-container button"), // ID-based CSS
        By.cssSelector("#confirm-order-buttons-container input[type='button']"), // ID-based CSS
        By.cssSelector("button.confirm-order-next-step-button"),
        By.cssSelector("input.button-1.confirm-order-next-step-button"),
        By.cssSelector("button.button-1.confirm-order-next-step-button"),
        By.cssSelector("input[onclick*='ConfirmOrder.save()']"),
        By.cssSelector("button[onclick*='ConfirmOrder.save()']"),
        By.cssSelector("button[type='button'][onclick*='ConfirmOrder']"),
        By.cssSelector("input[type='button'][onclick*='ConfirmOrder']"),
        By.cssSelector("button[title='Confirm']"),
        By.cssSelector("input[value='Confirm']"),
        By.cssSelector("button[value='Confirm']"),
        By.cssSelector("button[title='Complete']"),
        By.cssSelector("input[value='Complete']"),
        By.xpath("//div[@id='confirm-order-buttons-container']//button | //div[@id='confirm-order-buttons-container']//input[@type='button']"),
        By.xpath("//button[contains(@class, 'confirm-order')] | //input[contains(@class, 'confirm-order')]")
    };
    
    // Order success message and number - no IDs available, using CSS
    private static final By ORDER_SUCCESS_MESSAGE = By.cssSelector(".order-completed");
    private static final By ORDER_NUMBER = By.cssSelector(".order-number strong");

    public void enterBillingAddress(String firstName, String lastName, String email, 
                                   String country, String city, String address1, 
                                   String zip, String phone) {
        // Wait for checkout page to load
        waitForCheckoutPageToLoad();
        
        // Handle guest checkout if needed
        handleGuestCheckout();
        
        // Select "New Address" if dropdown exists (for registered users)
        selectNewAddressIfNeeded();
        
        // Wait for billing form to be visible and ready
        waitForBillingFormToBeReady();
        
        logger.info("Entering billing address for: {} {}", firstName, lastName);
        sendKeys(FIRST_NAME_INPUT, firstName);
        sendKeys(LAST_NAME_INPUT, lastName);
        sendKeys(EMAIL_INPUT, email);
        
        // Select country from dropdown
        if (isElementVisible(COUNTRY_DROPDOWN)) {
            WebElement countryElement = Waiter.waitForElementVisible(driver, COUNTRY_DROPDOWN);
            Select countrySelect = new Select(countryElement);
            try {
                countrySelect.selectByVisibleText(country);
                logger.debug("Selected country: {}", country);
            } catch (Exception e) {
                // If exact match fails, try selecting first option
                logger.debug("Could not select country by text '{}', trying first option", country);
                if (countrySelect.getOptions().size() > 1) {
                    countrySelect.selectByIndex(1);
                }
            }
        }

        // Some countries require State/Province selection (e.g. United States).
        // In your test data city="New York", which also matches a valid State option,
        // so we try selecting state by the city value first, then fallback.
        selectStateIfPresent(city);
        
        sendKeys(CITY_INPUT, city);
        sendKeys(ADDRESS1_INPUT, address1);
        sendKeys(ZIP_INPUT, zip);
        sendKeys(PHONE_INPUT, phone);
        logger.info("Billing address entered successfully");
    }

    private void selectStateIfPresent(String preferredStateName) {
        By stateLocator = null;
        if (isElementVisible(STATE_PROVINCE_DROPDOWN)) {
            stateLocator = STATE_PROVINCE_DROPDOWN;
        } else if (isElementVisible(STATE_ID_DROPDOWN)) {
            stateLocator = STATE_ID_DROPDOWN;
        }

        if (stateLocator == null) {
            logger.debug("No state/province dropdown detected");
            return;
        }

        try {
            // Wait a bit for states to load after country change
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        WebElement stateElement = Waiter.waitForElementVisible(driver, stateLocator);
        Select stateSelect = new Select(stateElement);

        // If dropdown has only 1 option (often "-- Select --"), nothing to pick yet
        if (stateSelect.getOptions().size() <= 1) {
            logger.debug("State dropdown has no selectable options yet");
            return;
        }

        // Try preferred state name first (case-insensitive)
        if (preferredStateName != null && !preferredStateName.isBlank()) {
            try {
                stateSelect.selectByVisibleText(preferredStateName);
                logger.debug("Selected state/province: {}", preferredStateName);
                return;
            } catch (Exception ignored) {
                // Try a loose match (contains)
                try {
                    String preferredLower = preferredStateName.toLowerCase();
                    for (WebElement opt : stateSelect.getOptions()) {
                        String txt = opt.getText() == null ? "" : opt.getText();
                        if (!txt.isBlank() && txt.toLowerCase().contains(preferredLower)) {
                            stateSelect.selectByVisibleText(txt);
                            logger.debug("Selected state/province by partial match: {}", txt);
                            return;
                        }
                    }
                } catch (Exception ignored2) {
                    // fallthrough
                }
            }
        }

        // Fallback: pick first non-empty, non-placeholder option
        for (WebElement opt : stateSelect.getOptions()) {
            String txt = opt.getText() == null ? "" : opt.getText().trim();
            if (!txt.isBlank() && !txt.toLowerCase().contains("select")) {
                try {
                    stateSelect.selectByVisibleText(txt);
                    logger.debug("Selected state/province fallback: {}", txt);
                    return;
                } catch (Exception ignored) {
                    // continue
                }
            }
        }
    }
    
    private void waitForCheckoutPageToLoad() {
        try {
            // Wait for URL to contain checkout
            Waiter.waitForUrlContains(driver, "checkout");
            logger.debug("Checkout page URL confirmed");
            
            // Wait a moment for page to load
            Thread.sleep(2000);
            
            // Check if billing section is present (might not be if guest checkout needed)
            try {
                Waiter.waitForElementPresent(driver, BILLING_ADDRESS_SECTION);
                logger.debug("Billing address section found");
            } catch (Exception e) {
                logger.debug("Billing section not immediately visible, may need guest checkout");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.warn("Checkout page load check: {}", e.getMessage());
        }
    }
    
    private void handleGuestCheckout() {
        try {
            // Check if guest checkout radio button exists
            if (isElementVisible(GUEST_CHECKOUT_RADIO)) {
                WebElement guestRadio = Waiter.waitForElementClickable(driver, GUEST_CHECKOUT_RADIO);
                if (!guestRadio.isSelected()) {
                    guestRadio.click();
                    logger.debug("Selected guest checkout radio button");
                    Thread.sleep(500);
                }
            }
            
            // Check if guest checkout button exists and click it
            if (isElementVisible(GUEST_CHECKOUT_BUTTON)) {
                logger.debug("Guest checkout button found, clicking it");
                click(GUEST_CHECKOUT_BUTTON);
                logger.info("Clicked guest checkout button");
                
                // Wait for billing form to appear after guest checkout
                Thread.sleep(2000);
            } else {
                logger.debug("No guest checkout button found, user may already be logged in or form is already visible");
            }
        } catch (Exception e) {
            logger.debug("Guest checkout handling: {}", e.getMessage());
            // Continue anyway - form might already be visible
        }
    }
    
    private void selectNewAddressIfNeeded() {
        try {
            // Check if address dropdown exists (for registered users with saved addresses)
            if (isElementVisible(NEW_ADDRESS_DROPDOWN)) {
                WebElement dropdown = Waiter.waitForElementVisible(driver, NEW_ADDRESS_DROPDOWN);
                Select addressSelect = new Select(dropdown);
                
                // Check if "New Address" option exists
                boolean hasNewAddress = addressSelect.getOptions().stream()
                    .anyMatch(option -> option.getText().toLowerCase().contains("new"));
                
                if (hasNewAddress) {
                    // Select "New Address" option
                    addressSelect.selectByVisibleText("New Address");
                    logger.debug("Selected 'New Address' from dropdown");
                    
                    // Wait for form to appear
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            logger.debug("No address dropdown found or already on new address form: {}", e.getMessage());
        }
    }
    
    private void waitForBillingFormToBeReady() {
        try {
            // Try multiple locators for first name field (different nopCommerce versions)
            By[] firstNameLocators = {
                FIRST_NAME_INPUT,
                By.id("BillingNewAddress_FirstName"),
                By.name("BillingNewAddress.FirstName"),
                By.cssSelector("input[id*='FirstName']"),
                By.cssSelector("#BillingNewAddress_FirstName")
            };
            
            WebElement firstNameField = null;
            Exception lastException = null;
            
            for (By locator : firstNameLocators) {
                try {
                    firstNameField = Waiter.waitForElementVisible(driver, locator);
                    logger.debug("Billing form is ready - first name field visible using: {}", locator);
                    break;
                } catch (Exception e) {
                    lastException = e;
                    logger.debug("First name field not found with locator: {}", locator);
                }
            }
            
            if (firstNameField == null) {
                // Last resort: try with shorter timeout
                try {
                    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    firstNameField = shortWait.until(
                        ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_INPUT)
                    );
                    logger.debug("Billing form found with shorter timeout");
                } catch (Exception e) {
                    logger.error("Billing form not ready. Current URL: {}", driver.getCurrentUrl());
                    try {
                        String pageSource = driver.getPageSource();
                        logger.error("Page source snippet: {}", pageSource.substring(0, Math.min(500, pageSource.length())));
                    } catch (Exception psException) {
                        logger.error("Could not get page source");
                    }
                    throw new RuntimeException(
                        String.format("Billing address form is not visible. URL: %s. Tried multiple locators.", driver.getCurrentUrl()),
                        lastException != null ? lastException : e
                    );
                }
            }
            
            // Additional wait for any dynamic content
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Billing form not ready. Current URL: {}", driver.getCurrentUrl());
            throw new RuntimeException("Billing address form is not visible. Please verify checkout page has loaded correctly.", e);
        }
    }

    public void clickContinueBilling() {
        String beforeUrl = driver.getCurrentUrl();

        WebElement continueButton = null;
        Exception lastException = null;

        // This site does NOT have `billing-buttons-container` or `billing-next-step-button`,
        // so we only try real button/input locators to avoid 20s waits.
        for (By locator : CONTINUE_BILLING_BUTTONS) {
            try {
                continueButton = Waiter.waitForElementClickable(driver, locator);
                logger.debug("Found continue billing button using: {}", locator);
                break;
            } catch (Exception e) {
                lastException = e;
                logger.debug("Continue billing button not found with: {}", locator);
            }
        }

        if (continueButton == null) {
            logger.error("Could not find continue billing button. Current URL: {}", driver.getCurrentUrl());
            throw new RuntimeException(
                String.format("Continue billing button not found. URL: %s. Tried container-first + multiple locators.", driver.getCurrentUrl()),
                lastException
            );
        }

        // Click (normal, then JS fallback)
        try {
            continueButton.click();
            logger.info("Clicked continue billing button");
        } catch (Exception e) {
            logger.warn("Normal click failed, trying JavaScript click: {}", e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
            logger.info("Clicked continue billing button using JavaScript");
        }

        // Verify we actually progressed (URL should change away from billingaddress)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> !d.getCurrentUrl().contains("billingaddress"));
            logger.info("Billing step completed. URL is now: {}", driver.getCurrentUrl());
        } catch (Exception e) {
            // If we didn't progress, there is usually a validation error on the page.
            String urlNow = driver.getCurrentUrl();
            String validationText = "";
            try {
                if (isElementVisible(BILLING_VALIDATION_SUMMARY)) {
                    validationText = getText(BILLING_VALIDATION_SUMMARY);
                }
            } catch (Exception ignored) {}

            throw new RuntimeException(
                String.format(
                    "Billing step did not advance. Before URL: %s, after URL: %s. Validation: %s",
                    beforeUrl,
                    urlNow,
                    (validationText == null || validationText.isBlank()) ? "<none detected>" : validationText
                ),
                e
            );
        }
    }

    public void clickContinueShipping() {
        // Wait for shipping step to be ready
        try {
            Waiter.waitForUrlContains(driver, "shipping");
            logger.debug("On shipping step");
        } catch (Exception e) {
            logger.debug("URL doesn't contain 'shipping', may still be loading");
        }
        
        WebElement shippingButton = null;
        // Try ID first, then CSS fallback
        try {
            shippingButton = Waiter.waitForElementClickable(driver, CONTINUE_SHIPPING_BUTTON_ID);
            logger.debug("Found shipping button by ID");
        } catch (Exception e) {
            try {
                shippingButton = Waiter.waitForElementClickable(driver, CONTINUE_SHIPPING_BUTTON);
            } catch (Exception e2) {
                // Try alternative locators
                try {
                    shippingButton = driver.findElement(By.cssSelector("button.shipping-method-next-step-button, input[onclick*='ShippingMethod.save()'], #shipping-method-buttons-container button"));
                } catch (Exception e3) {
                    logger.warn("Continue shipping button not found, may already be on next step");
                    return;
                }
            }
        }
        
        if (shippingButton != null) {
            try {
                shippingButton.click();
                logger.info("Clicked continue shipping button");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shippingButton);
                logger.info("Clicked continue shipping button using JavaScript");
            }
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickContinuePayment() {
        // Wait for payment step to be ready
        try {
            Waiter.waitForUrlContains(driver, "payment");
            logger.debug("On payment step");
        } catch (Exception e) {
            logger.debug("URL doesn't contain 'payment', may still be loading");
        }
        
        WebElement paymentButton = null;
        // Try ID first, then CSS fallback
        try {
            paymentButton = Waiter.waitForElementClickable(driver, CONTINUE_PAYMENT_BUTTON_ID);
            logger.debug("Found payment button by ID");
        } catch (Exception e) {
            try {
                paymentButton = Waiter.waitForElementClickable(driver, CONTINUE_PAYMENT_BUTTON);
            } catch (Exception e2) {
                // Try alternative locators
                try {
                    paymentButton = driver.findElement(By.cssSelector("button.payment-method-next-step-button, input[onclick*='PaymentMethod.save()'], #payment-method-buttons-container button"));
                } catch (Exception e3) {
                    logger.warn("Continue payment button not found, may already be on next step");
                    return;
                }
            }
        }
        
        if (paymentButton != null) {
            try {
                paymentButton.click();
                logger.info("Clicked continue payment button");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", paymentButton);
                logger.info("Clicked continue payment button using JavaScript");
            }
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickContinuePaymentInfo() {
        // Wait for payment info step to be ready
        try {
            Waiter.waitForUrlContains(driver, "paymentinfo");
            logger.debug("On payment info step");
        } catch (Exception e) {
            logger.debug("URL doesn't contain 'paymentinfo', may still be loading");
        }
        
        WebElement paymentInfoButton = null;
        // Try ID first, then CSS fallback
        try {
            paymentInfoButton = Waiter.waitForElementClickable(driver, CONTINUE_PAYMENT_INFO_BUTTON_ID);
            logger.debug("Found payment info button by ID");
        } catch (Exception e) {
            try {
                paymentInfoButton = Waiter.waitForElementClickable(driver, CONTINUE_PAYMENT_INFO_BUTTON);
            } catch (Exception e2) {
                // Try alternative locators
                try {
                    paymentInfoButton = driver.findElement(By.cssSelector("button.payment-info-next-step-button, input[onclick*='PaymentInfo.save()'], #payment-info-buttons-container button"));
                } catch (Exception e3) {
                    logger.warn("Continue payment info button not found, may already be on next step");
                    return;
                }
            }
        }
        
        if (paymentInfoButton != null) {
            try {
                paymentInfoButton.click();
                logger.info("Clicked continue payment info button");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", paymentInfoButton);
                logger.info("Clicked continue payment info button using JavaScript");
            }
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void clickConfirmOrder() {
        // First, verify we're on the confirm order page
        String currentUrl = driver.getCurrentUrl();
        logger.debug("Current URL before confirm order: {}", currentUrl);
        
        // Check if we're still on an earlier step
        if (currentUrl.contains("billingaddress")) {
            throw new RuntimeException(
                String.format("Still on billing address page. URL: %s. Checkout flow may not have progressed properly. " +
                    "Please verify billing, shipping, payment, and payment info steps completed successfully.", currentUrl)
            );
        }
        
        if (currentUrl.contains("shipping")) {
            throw new RuntimeException(
                String.format("Still on shipping page. URL: %s. Please verify shipping step completed.", currentUrl)
            );
        }
        
        if (currentUrl.contains("payment") && !currentUrl.contains("paymentinfo")) {
            throw new RuntimeException(
                String.format("Still on payment method page. URL: %s. Please verify payment step completed.", currentUrl)
            );
        }
        
        // Wait for confirm order page to be ready
        try {
            // Wait a moment for page to stabilize
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        WebElement confirmButton = null;
        Exception lastException = null;
        
        // Try multiple locators for confirm order button
        for (By locator : CONFIRM_ORDER_BUTTONS) {
            try {
                confirmButton = Waiter.waitForElementClickable(driver, locator);
                logger.debug("Found confirm order button using: {}", locator);
                break;
            } catch (Exception e) {
                lastException = e;
                logger.debug("Confirm order button not found with: {}", locator);
            }
        }
        
        if (confirmButton == null) {
            // Last resort: try to find any button/input in confirm order section
            try {
                WebElement confirmContainer = driver.findElement(By.id("confirm-order-buttons-container"));
                java.util.List<WebElement> buttons = confirmContainer.findElements(By.tagName("button"));
                if (buttons.isEmpty()) {
                    buttons = confirmContainer.findElements(By.cssSelector("input[type='button']"));
                }
                if (!buttons.isEmpty()) {
                    confirmButton = buttons.get(0);
                    logger.debug("Found confirm button in confirm order container");
                }
            } catch (Exception e) {
                logger.debug("Could not find button in confirm order container: {}", e.getMessage());
            }
        }
        
        if (confirmButton == null) {
            // Try finding by text content
            try {
                java.util.List<WebElement> allButtons = driver.findElements(By.tagName("button"));
                for (WebElement btn : allButtons) {
                    String text = btn.getText().toLowerCase();
                    if (text.contains("confirm") || text.contains("complete") || text.contains("place order")) {
                        confirmButton = btn;
                        logger.debug("Found confirm button by text: {}", text);
                        break;
                    }
                }
            } catch (Exception e) {
                logger.debug("Could not find button by text: {}", e.getMessage());
            }
        }
        
        if (confirmButton == null) {
            logger.error("Could not find confirm order button. Current URL: {}", driver.getCurrentUrl());
            throw new RuntimeException(
                String.format("Confirm order button not found. URL: %s. Tried multiple locators. " +
                    "Please verify you're on the confirm order page.", driver.getCurrentUrl()),
                lastException
            );
        }
        
        // Try normal click first
        try {
            confirmButton.click();
            logger.info("Successfully clicked confirm order button");
        } catch (Exception e) {
            // If click fails, try JavaScript click
            logger.warn("Normal click failed, trying JavaScript click: {}", e.getMessage());
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmButton);
                logger.info("Successfully clicked confirm order button using JavaScript");
            } catch (Exception jsException) {
                logger.error("JavaScript click also failed");
                throw new RuntimeException("Could not click confirm order button", jsException);
            }
        }
        
        // Wait for order confirmation page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isOrderSuccessful() {
        return isElementVisible(ORDER_SUCCESS_MESSAGE);
    }

    public String getOrderNumber() {
        if (isOrderSuccessful()) {
            return getText(ORDER_NUMBER);
        }
        return "";
    }

    public void completeCheckout(String firstName, String lastName, String email,
                                String country, String city, String address1,
                                String zip, String phone) {
        enterBillingAddress(firstName, lastName, email, country, city, address1, zip, phone);
        clickContinueBilling();
        clickContinueShipping();
        clickContinuePayment();
        clickContinuePaymentInfo();
        clickConfirmOrder();
    }
}
