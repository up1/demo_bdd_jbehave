package com.demo.web.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.demo.web.pages.Pages;

public class GoogleWebStep {
	private final Pages pages;

	public GoogleWebStep(Pages pages) {
		this.pages = pages;
	}

	@Given("I am on google.com")
	public void userIsOnHomePage() {
		pages.home().open();
	}

	@When("I enter \"$keyword\"")
	public void userFillInDataAndSeach(String keyword) {
		pages.search().find(keyword);
	}

	@Then("I should see \"$result\"")
	public void shouldSeeResult(String result) {
		pages.search().viewResult(result);
	}

}
