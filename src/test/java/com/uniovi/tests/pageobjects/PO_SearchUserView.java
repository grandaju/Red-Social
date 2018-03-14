package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SearchUserView {
	static public void fillSearch(WebDriver driver, String usernamep) {
		WebElement searchText = driver.findElement(By.name("searchText"));
		searchText.click();
		searchText.clear();
		searchText.sendKeys(usernamep);
		
	
		
		// Pulsar el boton de Login.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
