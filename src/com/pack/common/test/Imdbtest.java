package com.pack.common.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.*;
import org.testng.annotations.*;

import com.pack.base.testbasesetup;
import com.pack.common.pageObjects.ImdbPageObjects;
import com.pack.common.pageObjects.dbMethods;

public class Imdbtest extends testbasesetup{
private WebDriver driver;
//public dbMethods dbMethods;
//private ImdbPageObjects ImdbPageObjects;
	@BeforeClass
	public void setUp() throws IOException {
		driver=super.getDriver();
		createNewDatabase("test.db");
		createNewTable();
		delete();
		ImdbPageObjects ImdbPageObjects = new ImdbPageObjects();
		ImdbPageObjects.getparam(driver);
	} 
	
	@AfterClass
	public void closeDriver()
	{
		driver.close();
	}
	//Verify if we are of the right site
	@Test
	public void verifyTitle() {
		assertEquals(driver.getTitle(), "IMDb Top 250 - IMDb");
	}
	//verify the record count 
	@Test
	public void verifyTotalCount() throws SQLException {
		try {
			dbMethods dbMethods= new dbMethods();
			String count = dbMethods.query("select count(*) count from imdbmovies");
			assertEquals(count, "250");
			System.out.println("Top 250 movies are loaded in database sucessfully");
			
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
	}
	//verify uniqueness
	@Test
	public void verifyUniqueRecords() throws SQLException {
		dbMethods dbMethods= new dbMethods();
		String totalcount = dbMethods.query("select count(*) count from imdbmovies");
		String uniquecount = dbMethods.query("select count(distinct(id)) count from imdbmovies");
		assertEquals(totalcount, uniquecount);
		System.out.println("All 250 records in database are unique");
	}
	//verify records in database
	@Test
	public void verifyrecords() throws SQLException {
		
		dbMethods dbMethods= new dbMethods();
		ImdbPageObjects ImdbPageObjects = new ImdbPageObjects();
		WebElement moviestable= ImdbPageObjects.getTop250MoviesTable(driver);
		List<WebElement> movierow = moviestable.findElements(By.tagName("tr"));
		String url = "jdbc:sqlite:C://IMDBMovies/sqlite/test.db";
        Connection conn = DriverManager.getConnection(url);
        Statement stmt  = conn.createStatement();
		for(int i = 1; i<=movierow.size();i++) {	
		List<WebElement> clm = movierow.get(i-1).findElements(By.tagName("td"));
		String rankAndTitle = clm.get(1).getText();
		String ratingColumn = clm.get(2).getText();
        ResultSet rs    = stmt.executeQuery("Select * from imdbmovies where id = "+i);
        assertEquals(ImdbPageObjects.getid(rankAndTitle), rs.getString("id"));
        assertEquals(ImdbPageObjects.getMovieTitle(rankAndTitle), rs.getString("moviename"));
        assertEquals(ImdbPageObjects.getYearOfRelease(rankAndTitle), rs.getString("year"));
        assertEquals(ImdbPageObjects.getRating(ratingColumn), rs.getString("rating"));
		System.out.println(i+"th record is matched");
		}

	}
}
