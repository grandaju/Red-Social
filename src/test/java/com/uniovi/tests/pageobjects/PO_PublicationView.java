package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PublicationView {
	static public void fillForm(WebDriver driver, String titlep, String textp) {
		WebElement title = driver.findElement(By.name("title"));
		title.click();
		title.clear();
		title.sendKeys(titlep);
		WebElement text = driver.findElement(By.name("text"));
		text.click();
		text.clear();
		text.sendKeys(textp);
		
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
