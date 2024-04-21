package hospital_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	
	private static final String username="root";
	
	private static final String password="root";
	
	public static void main(String [] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner s= new Scanner(System.in);
		try {
			Connection connection= DriverManager.getConnection(url, username, password);
			Patient p= new Patient(connection,s);
			Doctor d= new Doctor(connection);
			
			while(true) {
				System.out.println("\n-----------------------HOSPITAL MANAGEMENT SYSTEM-----------------------\n");
				System.out.println("1.Add Patient");
				System.out.println("2.View Patients"); 
				System.out.println("3.View Doctors");
				System.out.println("4.Book Appointment");
				System.out.println("5.Exit");
				System.out.println("Enter your choice: ");
				
				int choice=s.nextInt();
				switch(choice) {
				case 1:
					p.addPatient();							//Add Patient
					break;
				case 2:
					p.viewPatients();						//View Patients
					break;
				case 3:
					d.viewDoctors();						//View Doctors
					break;
				case 4:
					bookAppointment(p,d,connection,s);		//Book Appointment
					break;
				case 5:
					System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM");
					return;					
					
					default: 
						System.out.println("Enter correct choice...");
					
				}
			}
				
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void bookAppointment(Patient p,Doctor d,Connection connection,Scanner s) throws SQLException {
		System.out.println("Enter Patient ID:");
		int patient_ID=s.nextInt();
		System.out.println("Enter Doctor ID:");
		int doctor_ID=s.nextInt();
		System.out.println("Enter Appointment Date:(YYYY-MM-DD)");
		String appointmentDate=s.next();
		
		if(p.getPatientByID(patient_ID) && d.getDoctorByID(doctor_ID)) {
			
			if(checkDoctorAvailability(doctor_ID,appointmentDate,connection)) {
					String quary="INSERT INTO appointments(patient_ID,doctor_ID,appointment_date) VALUES(?,?,?)";
					try {
					PreparedStatement ps= connection.prepareStatement(quary);
					ps.setInt(1, patient_ID);
					ps.setInt(2, doctor_ID);
					ps.setString(3, appointmentDate);
					
					int affectRows=ps.executeUpdate();
					
					if(affectRows>0) {
						System.out.println("Appointment Booked");
					}
					else {
						System.out.println("Field to Book Appointment");
					}
					}catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Doctor not available on this date!");
				}
			
		}
		else {
			System.out.println("Either Doctor Or Patient doesn't exist!");
		}
	}
	
	public static  boolean checkDoctorAvailability(int doctor_ID, String appointmentDate, Connection connection) throws SQLException {
		String quary="SELECT COUNT(*) FROM appointments WHERE doctor_ID=? AND appointment_date=?";
		
		PreparedStatement ps= connection.prepareStatement(quary);
		ps.setInt(1, doctor_ID);
		ps.setString(2, appointmentDate);
		ResultSet resultset=ps.executeQuery();
		
		if(resultset.next()) {
			return true;
		}
		
		return false;
	}
	

}
