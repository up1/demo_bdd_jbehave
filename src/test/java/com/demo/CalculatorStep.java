package com.demo;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

public class CalculatorStep {
	
	Calculator calculator;
	int result;
	
	@Given("Add number process")
	public void addNumberProcess(){
		calculator = new Calculator();
	}
	
	@When("User fill in $a and $b")
	public void fillInTwoNumber(int a, int b) {
		result = calculator.add(a, b);
	}
	
	@Then("Result will be $result")
	public void resultShouldBrCorrect(int expectedResult){
		Assert.assertEquals(expectedResult, this.result);
	}
	
	

}
