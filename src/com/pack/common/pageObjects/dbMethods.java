package com.pack.common.pageObjects;

import java.sql.*;


public class dbMethods {

	  // Connect to a sample database
    
		public void createNewDatabase(String fileName) {
			 
	         String url = "jdbc:sqlite:C://IMDBMovies/sqlite/" + fileName;
	 
	        try (Connection conn = DriverManager.getConnection(url)) {
	            if (conn != null) {
	                DatabaseMetaData meta = conn.getMetaData();
	                System.out.println("The driver name is " + meta.getDriverName());
	                System.out.println("A new database has been created.");
	            }
	 
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
		
		   /**
	     * Create a new table in the test database
	     *
	     */
	    public void createNewTable() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:C://IMDBMovies/sqlite/test.db";
	        
	        // SQL statement for creating a new table
	        String sql = "CREATE TABLE IF NOT EXISTS imdbmovies (\n"
	                + "	id text PRIMARY KEY,\n"
	                + "	moviename text NOT NULL,\n"
	                + "	year text NOT NULL,\n"
	                + "	rating text\n"
	                + ");";
	        
	        try (Connection conn = DriverManager.getConnection(url);
	                Statement stmt = conn.createStatement()) {
	            // create a new table
	            stmt.execute(sql);
	            System.out.println("imdbmovies table has been created");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    /**
	     * Insert a new row into the imdbmovies table
	     *
	     */
	    public void insert(String id, String moviename,String year,String rating) {
	        String sql = "INSERT INTO imdbmovies(id,moviename,year,rating) VALUES(?,?,?,?)";
	        String url = "jdbc:sqlite:C://IMDBMovies/sqlite/test.db";
	       
	        //System.out.println("INSERT INTO imdbmovies(id,moviename,year,rating) VALUES(\""+id+"\",\""+moviename+"\",\""+year+"\",\""+rating+"\")");

	        try (Connection conn = DriverManager.getConnection(url);
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        	//System.out.println(sql);
	            pstmt.setString(1, id);
	            pstmt.setString(2, moviename);
	            pstmt.setString(3, year);
	            pstmt.setString(4, rating);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    /**
	     * select all rows in the warehouses table
	     */
	    public void selectAll(String sql){
	    	
	        String url = "jdbc:sqlite:C://IMDBMovies/sqlite/test.db";

	        try (Connection conn = DriverManager.getConnection(url);
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	            // loop through the result set
	            while (rs.next()) {
	                System.out.println(rs.getString("id") +  "\t" + 
	                                   rs.getString("moviename") + "\t" +
	                                   rs.getString("year") + "\t" +
	                                   rs.getString("rating"));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    /**
	     * truncate table
	     */
	    
	    public void delete() {
	        String sql = "DELETE FROM imdbmovies";
	        String url = "jdbc:sqlite:C://IMDBMovies/sqlite/test.db";

	        try (Connection conn = DriverManager.getConnection(url);
	                Statement stmt = conn.createStatement()) {
	            stmt.execute(sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    //to run custom query
	    public String query(String sql) throws SQLException{
	    	String url = "jdbc:sqlite:C://IMDBMovies/sqlite/test.db";

	        Connection conn = DriverManager.getConnection(url);
	        Statement stmt  = conn.createStatement();
	        ResultSet rs    = stmt.executeQuery(sql);
	        return (rs.getString("count"));

	       
	        
	    }
	      
}
