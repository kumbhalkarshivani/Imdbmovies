package com.pack.common.pageObjects;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pack.base.testbasesetup;

public class ImdbPageObjects {
	
	String id = null,year = null,rating = null,movieTitle = null; 


	public WebElement getTop250MoviesTable(WebDriver driver) {
		return (driver.findElement(By.xpath("//*[@id=\"main\"]/div/span/div/div/div[3]/table/tbody")));
	}
	public String getid(String rankAndTitle)
	{
		return (rankAndTitle.split("\\.")[0]);
	}
	
	public String getMovieTitle(String rankAndTitle)
	{
		return (rankAndTitle.split("\\.")[1].split("\\(")[0].trim());
	}
	
	public String getYearOfRelease(String rankAndTitle)
	{
		return (rankAndTitle.split("\\(")[1].split("\\)")[0]);
	}
	
	public String getRating(String rating)
	{
		return (rating);
	}
	
	public void getparam(WebDriver driver) throws IOException {
		WebElement moviestable= this.getTop250MoviesTable(driver);
		List<WebElement> movierow = moviestable.findElements(By.tagName("tr"));
		System.out.println("Inserting Data to the database....");
		dbMethods db = new dbMethods();
		for(int i =0;i<movierow.size();i++)
		{
			List<WebElement> clm = movierow.get(i).findElements(By.tagName("td"));
			for(int j=0;j<=clm.size();j++)
			{
			if(j==1) {					
				String rankAndTitle = clm.get(j).getText();
				id=this.getid(rankAndTitle);
				movieTitle=this.getMovieTitle(rankAndTitle);
				year=this.getYearOfRelease(rankAndTitle);
			}else if(j==2) {
				
				String ratingColumn =clm.get(j).getText();
				rating =this.getRating(ratingColumn);
			}

		}
			
			db.insert(id, movieTitle, year, rating);
			writetTofile(id, movieTitle, year, rating);
			
		}
		System.out.println("\n"+"Data load completed");
		System.out.println("Displaying all the data from data base");
		/**
	     * Fetching data from database
	     */
		db.selectAll("SELECT * FROM imdbmovies");

	}
	public static void writetTofile(String id, String movieTitle, String year, String rating) throws IOException{
    	
    	String filename = "C:\\IMDBMovies\\bin\\com\\pack\\common\\pageObjects\\sqlquerie.txt";
		FileWriter filewrite = new FileWriter(filename,true);
		BufferedWriter file = new BufferedWriter(filewrite);
        String sql = "INSERT INTO imdbmovies(id,movieTitle,year,rating) VALUES(\""+id+"\",\""+movieTitle+"\",\""+year+"\",\""+rating+"\");";
		file.write(sql);
		file.newLine();
		file.close();

    }
	
	
}
