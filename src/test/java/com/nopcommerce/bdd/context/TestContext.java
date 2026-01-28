package com.nopcommerce.bdd.context;

import com.nopcommerce.core.driver.DriverManager;
import com.nopcommerce.pages.HomePage;
import com.nopcommerce.pages.account.OrderHistoryPage;
import com.nopcommerce.pages.auth.LoginPage;
import com.nopcommerce.pages.auth.RegisterPage;
import com.nopcommerce.pages.cart.CartPage;
import com.nopcommerce.pages.checkout.CheckoutPage;
import com.nopcommerce.pages.products.ProductPage;
import com.nopcommerce.pages.products.SearchPage;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private SearchPage searchPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderHistoryPage orderHistoryPage;
    private String uniqueEmail;
    private String orderNumber;

    public TestContext() {
        this.driver = DriverManager.getDriver();
        initializePages();
    }

    private void initializePages() {
        homePage = new HomePage();
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
        searchPage = new SearchPage();
        productPage = new ProductPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        orderHistoryPage = new OrderHistoryPage();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public SearchPage getSearchPage() {
        return searchPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public CheckoutPage getCheckoutPage() {
        return checkoutPage;
    }

    public OrderHistoryPage getOrderHistoryPage() {
        return orderHistoryPage;
    }

    public String getUniqueEmail() {
        return uniqueEmail;
    }

    public void setUniqueEmail(String uniqueEmail) {
        this.uniqueEmail = uniqueEmail;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
