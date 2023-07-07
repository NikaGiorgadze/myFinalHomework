package tests.positivescenarios;

import dataobject.LoginData;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobject.BurgerMenuPage;
import pageobject.HomePage;
import pageobject.LoginPage;
import utils.Runner;

import java.util.Collections;
import java.util.List;

public class PositiveTests extends Runner {
    @Test(priority = 1, invocationCount = 1, enabled = true, testName = "ფილტრი ფასის ზრდადობის მიხედვით")
    @Description("ფილტრი ფასის ზრდადობის მიხედვით")
    @Severity(SeverityLevel.BLOCKER)
    @Link("https://www.saucedemo.com/")
    public void filterPriceLowToHigh() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.verifyLoginPage();
        loginPage.login(LoginData.userName, LoginData.password);

        HomePage homePage = new HomePage(driver, wait);
        homePage.verifyHomePage();

        //get product prices list before filter by price low to high
        List<Double> pricesBeforeFiltering = homePage.getProductsPricesList();
        System.out.println("Before sort: " + pricesBeforeFiltering);

        //sort prices by ascending order
        Collections.sort(pricesBeforeFiltering);
//        pricesBeforeFiltering.set(0, 250.0);
        System.out.println("After sorting by ascending order: " + pricesBeforeFiltering);

        //click filter dropDown
        homePage.clickFilterDropDown();
        homePage.clickFilterOptionByValue("Price (low to high)");

        List<Double> pricesAfterFiltering = homePage.getProductsPricesList();
        System.out.println("prices list after filter: " + pricesAfterFiltering);

        //verify that after filtering product prices is sorted correctly
        Assert.assertEquals(pricesAfterFiltering, pricesBeforeFiltering, "product prices is sorted");
    }

    @Test(priority = 2, invocationCount = 1, enabled = true, testName = "აპლიკაციის დარესეტება")
    @Description("აპლიკაციის დარესეტება")
    @Severity(SeverityLevel.BLOCKER)
    @Link("https://www.saucedemo.com/")
    public void resetAppStateFunctionality() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.verifyLoginPage();
        loginPage.login(LoginData.userName, LoginData.password);

        //select 4 products
        int selectedProductsCount = 4;
        HomePage homePage = new HomePage(driver, wait);
        homePage.verifyHomePage();
        homePage.selectProductByIndex(1);
        homePage.selectProductByIndex(2);
        homePage.selectProductByIndex(3);
        homePage.selectProductByIndex(4);

        //after selecting four product, verify shopping cart badge color and products count
        Assert.assertTrue(homePage.shoppingCartBadgeIsDisplayed(3), "shopping cart badge is displayed");
        Assert.assertEquals(homePage.getShoppingCartBadgeColor(), "rgba(226, 35, 26, 1)");
        Assert.assertEquals(homePage.getShoppingCartBadgeNumber(), selectedProductsCount,
                "selected products count should be: " + selectedProductsCount);

        homePage.clickBurgerMenu();

        BurgerMenuPage burgerMenuPage = new BurgerMenuPage(driver, wait);
        burgerMenuPage.selectBurgerMenuOptionByValue("Reset App State");

        //after selecting "Reset App State" cart should not be displayed
        Assert.assertFalse(homePage.shoppingCartBadgeIsDisplayed(3), "cart should not be displayed");
    }
}
