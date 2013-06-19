package com.demo.web.pages;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverProvider;

public class Home extends AbstractPage {

	public Home(WebDriverProvider driverProvider) {
		super(driverProvider);
	}

	public void open() {
		get("http://www.google.com");
		manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

}
