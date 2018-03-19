package com.pack.common.test;

import java.io.IOException;

import org.openqa.selenium.*;
import org.testng.annotations.*;

import com.pack.base.testbasesetup;
import com.pack.common.pageObjects.ImdbPageObjects;
import com.pack.common.pageObjects.dbMethods;

public class Imdbtest extends testbasesetup{
private WebDriver driver;
private dbMethods dbMethods;
private ImdbPageObjects ImdbPageObjects;
	@BeforeClass
	public void setUp() throws IOException {
		driver=getDriver();
		createNewDatabase("test.db");
		createNewTable();
		delete();
		ImdbPageObjects.getparam();
	} 

	@Test
	public void verifyData() {
		
		dbMethods.query("select count(*) from imdbmovies");
	}
	
	
	
}
