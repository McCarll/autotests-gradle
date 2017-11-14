package com.vrudometkin.autotest.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by vrudometkin on 14/11/2017.
 */
@Pages.Name("Search result")
public class SearchResultPage extends MainEbayPage{

    @FindBy(xpath = "//h2[text()='Items in search results']")
    @Name("Search")
    private SelenideElement fieldSearch;

}
