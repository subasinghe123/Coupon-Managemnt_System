package couponApi;

import java.sql.*;

import connection.CouponConnection;

public class Coupon {

	CouponConnection couponConnection = new CouponConnection();
    
	public String insertCoupon(String name, String expireDate, String saving, String description) 
	{ 
		String output = ""; 
		try
		{ 
			Connection connect = couponConnection.getConnection(); 
			if (connect == null) 
			{
				return "Error while connecting to the database for inserting."; 
			} 
			// create a prepared statement
			String query = " insert into coupon(`couponID`,`name`,`expireDate`,`saving`,`description`)"
					+ " values (?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = connect.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, expireDate);
			preparedStmt.setString(4, saving);
			preparedStmt.setString(5, description);
			// execute the statement3
			preparedStmt.execute(); 
			connect.close(); 
			String newCoupon = readCoupons();
			output =  "{\"status\":\"success\", \"data\": \""
					+ newCoupon + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Coupon.\"}"; 
			System.err.println(e.getMessage()); 
		} 
		return output; 
	 } 
	
	public String readCoupons(){
    	
		String output = "";
		
		try{
			Connection connect = couponConnection.getConnection();
			if (connect == null){
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table class='table table-bordered' style='border-color:#1A237E; overflow-x:auto;'>"
					+ "<thead>"
					+ "<tr>"
					+ "<th scope='col'>#</th>"
					+ "<th scope='col'>ID</th>"
					+ "<th scope='col'>Name</th>"
					+ "<th scope='col'>Expiry date</th>"
					+ "<th scope='col'>Saving</th>"
					+ "<th scope='col'>Description</th>"
					+ "<th scope='col'>Update</th>"
					+ "<th scope='col'>Delete</th>"
					+ "</tr>"
					+ "</thead>";
			String query = "select * from `coupon`";
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			
			output+= "<tbody>";
			
			// iterate through the rows in the result set
			while (rs.next()){
				i++;
				String hash = Integer.toString(i);
				String couponID = Integer.toString(rs.getInt("couponID"));
				String name = rs.getString("name");
				String expireDate = rs.getString("expireDate");
				String saving = rs.getString("saving");
				String description = rs.getString("description");
				
				// Add into the html table
				output += "<tr>";
				output += "<td scope='row'>" + hash + "</td>";
				output += "<td>" + couponID + "</td>";
				output += "<td>" + name + "</td>"; 
				output += "<td>" + expireDate + "</td>";
				output += "<td>" + saving + "</td>";
				output += "<td>" + description + "</td>"; 
				// buttons
				output += "<td>"
						+ "<button type='submit' name='btnUpdate' class='btn operation-update btnUpdate' style='text-decoration: none;' data-couponid='" + couponID + "'>"
						+ "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-arrow-clockwise operation-update' viewBox='0 0 16 16'>"
						+ "<path fill-rule='evenodd' d='M8 3a5 5 0 1 0 4.546 2.914.5.5 0 0 1 .908-.417A6 6 0 1 1 8 2v1z'/>"
						+ "<path d='M8 4.466V.534a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384L8.41 4.658A.25.25 0 0 1 8 4.466z'/>"
						+ "</svg>Update"
						+ "</button>"
						+ "</td>"
						+ "<td>"
						+ "<button type='submit' name='btnRemove' class='btn operation-delete btnRemove' style='text-decoration: none;' data-couponid='" + couponID + "'>"
						+ "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' fill='currentColor' class='bi bi-x operation-delete' viewBox='0 0 16 16'>"
						+ "<path d='M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z'/>"
						+ "</svg>Delete"
						+ "</button>"
						+ "</td>"
						+ "</tr>";
				}
				connect.close();
				
				output += "</tbody>";
				
				// Complete the html table
				output += "</table>";
			}
			catch (Exception e){
				output = "Error while reading the Coupons.";
				System.err.println(e.getMessage());
			}
		return output;
	}
    
    public String updateCoupon(String ID, String name, String expireDate, String saving, String description)
    { 
    	String output = ""; 
    try
    { 
    	Connection connect = couponConnection.getConnection(); 
    	if (connect == null) 
    	{
    		return "Error while connecting to the database for updating."; 
    	} 
    	// create a prepared statement
    	String query = "UPDATE coupon SET name=?, expireDate=?, saving=?, description=? WHERE couponID=?"; 
    	
    	PreparedStatement preparedStmt = connect.prepareStatement(query); 
    	// binding values
    	preparedStmt.setString(1, name);
    	preparedStmt.setString(2, expireDate);
    	preparedStmt.setString(3, saving); 
    	preparedStmt.setString(4, description); 
    	preparedStmt.setInt(5, Integer.parseInt(ID)); 
    	// execute the statement
    	preparedStmt.execute(); 
    	connect.close(); 
    	String newCoupon = readCoupons();    
		output = "{\"status\":\"success\", \"data\": \"" +        
				newCoupon + "\"}";  
    	} 
    	catch (Exception e) 
    	{ 
    		output =  "{\"status\":\"error\", \"data\": \"Error while updating the Coupon.\"}";
    		System.err.println(e.getMessage()); 
    	} 
    	return output; 
    }   

    public String deleteCoupon(String couponID){
    	
		String output = "";
		
		try{
			Connection connect = couponConnection.getConnection();
			if (connect == null){
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from `coupon` where couponID=?";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(couponID));

			// execute the statement
			preparedStmt.execute();
			connect.close();
			String newCoupon = readCoupons();    
			output = "{\"status\":\"success\", \"data\": \"" +       
					newCoupon + "\"}";  
		}
		catch (Exception e){
			output = "Error while deleting the Coupon.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
