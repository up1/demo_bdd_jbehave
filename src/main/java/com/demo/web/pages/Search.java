package com.demo.web.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class Search extends AbstractPage {

	public Search(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void open() {
		findElement(By.linkText("Find Steps")).click();
	}

	public void pageIsShown() {
		found("Match");
	}

	public void find(String keyword) {
		findElement(By.name("q")).sendKeys(keyword);
	}

	public void viewResult(String expectResult) {
		found(expectResult);
	}

}
