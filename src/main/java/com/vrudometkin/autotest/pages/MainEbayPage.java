package com.vrudometkin.autotest.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by vrudometkin on 14/11/2017.
 */
@Pages.Name("Ebay")
public class MainEbayPage extends Pages{

    @FindBy(xpath = "//input[@id='gh-ac']")
    @Name("Search")
    private SelenideElement fieldSearch;

    @FindBy(xpath = "//*[@id='gh-btn']")
    @Name("Search")
    private SelenideElement btnSearch;

}
