package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangehrm.base.BaseTest;

public class DBconnectionUtil {

	private static final String DB_URL="jdbc:mysql://localhost:3307/orangehrm";
	private static final String USERNAME="root";
	private static final String PASSWORD="";
	public static final Logger log=BaseTest.log;
	
	public static Connection getDBconnection() {
		log.info("DB connection starting");
		try {
			Connection con= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			log.info("DB Connected successfully");
			return con;
		} catch (SQLException e) {
			log.info("unable to connect DB");
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String, String> getEmloyeeDetails(String emp_id) {
		String query="SELECT employee_id,emp_firstname,emp_middle_name,emp_lastname FROM hs_hr_employee WHERE employee_id="+emp_id+";";
		Map<String, String> empDetails=new HashMap<>();
		try(Connection conn=getDBconnection();
			Statement stmt=	conn.createStatement();
			ResultSet rs= stmt.executeQuery(query);)	{
			log.info("Executing query : "+query);
			if(rs.next()) {
				String firstName=	rs.getString("emp_firstname");
				String middleName=	rs.getString("emp_middle_name");
				String lastName=	rs.getString("emp_lastname");
				
				//store in map
				empDetails.put("firstName",firstName );
				empDetails.put("middleName", middleName!=null? middleName:"");
				empDetails.put("lastName", lastName);
				
				log.info("query executed successfully");
				log.info("employee data fetched");
				log.info("firstName:"+firstName+"\n"+ "middleName:"+middleName+"\n"+"lastName:"+lastName);
			}
			else {
				log.info("employee not found");
			}
		}
		catch(Exception e) {
			log.info("error while executing query");
			e.printStackTrace();
		}
		return empDetails;
	}
}
