package com.google.testtask;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GMailSendLetterTest {

	private static GMailPage mailPage;
	private WebElement letterText;
	private final static String EXPECTED_TEXT = "Hello, world!";
	
	@BeforeClass
	public void initialize() throws InterruptedException {
		mailPage = new GMailPage();
		letterText = mailPage.getUnreadedLetterText();
	}
	
	@Test
	public void shouldGetLetterElementAndGetItTextWhenWebElementIsValid() {
		//when
		String actualText = letterText.getText();
		//then
		Assert.assertEquals(EXPECTED_TEXT, actualText);
	}
	
	@AfterClass
	public void endTest() {
		mailPage.quit();
	}
}
