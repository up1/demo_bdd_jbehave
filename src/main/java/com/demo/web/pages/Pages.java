package com.demo.web.pages;

import org.jbehave.web.selenium.WebDriverProvider;

public class Pages {
	private final WebDriverProvider driverProvider;
	private Home home;
	private Search search;

	public Pages(WebDriverProvider driverProvider) {
		this.driverProvider = driverProvider;
	}

	public Home home() {
		if (home == null) {
			home = new Home(driverProvider);
		}
		return home;
	}

	public Search search() {
		if (search == null) {
			search = new Search(driverProvider);
		}
		return search;
	}
}
