package hospital_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	
	private Connection connection;	//interface
		
	public Doctor(Connection connection) {
		this.connection=connection;
	}
	
	public void viewDoctors() throws SQLException{
		
		String query="Select * from doctors";
		
		PreparedStatement preparedstatement = connection.prepareStatement(query);
		
		ResultSet resultset=preparedstatement.executeQuery();
		
		System.out.println("Doctors: ");
		
		System.out.println("+-----------+------------------+----------------+");
		System.out.println("| Doctor ID | Name             | Specialization |");
		System.out.println("+-----------+------------------+----------------+");
		while(resultset.next()) {
			int id= resultset.getInt("ID");
			String name= resultset.getString("Name");
			String specialization=resultset.getString("Specialization");
			System.out.printf("|%-11s|%-18s|%-16s|\n",id,name,specialization);
			System.out.println("+-----------+------------------+----------------+");
		}
		
	}
	
	public boolean getDoctorByID(int id) throws SQLException {
		String quary="select * from doctors where ID=?";
		int i=id;
		
		PreparedStatement ps= connection.prepareStatement(quary);
		ps.setInt(1, i);
		ResultSet resultset=ps.executeQuery();
		
		if(resultset.next())
			return true;
			
		return false;
	}
}
