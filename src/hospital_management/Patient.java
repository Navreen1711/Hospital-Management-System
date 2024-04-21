package hospital_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

	private Connection connection; // interface

	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void addPatient() {
		
		System.out.println("Enter Patient Name:");
		String name = scanner.next();
		
		scanner.nextLine();
		
		System.out.println("Enter Patient Age:");
		int age = scanner.nextInt();

		System.out.println("Enter Patient Gender:");
		String gender = scanner.next();
		
		try {
		String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";

		PreparedStatement preparedstatement = connection.prepareStatement(query);
		preparedstatement.setString(1, name);
		preparedstatement.setInt(2, age);
		preparedstatement.setString(3, gender);

		int affectrows = preparedstatement.executeUpdate();

		if (affectrows > 0) {
			System.out.println("Patient added successfully!!");
		} else {
			System.out.println("Failed to add patient!!");
		}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewPatients() throws SQLException {
		String query = "Select * from patients";

		PreparedStatement preparedstatement = connection.prepareStatement(query);

		ResultSet resultset = preparedstatement.executeQuery();

		System.out.println("Patients: ");

		System.out.println("+------------+------------------+-------+--------+");
		System.out.println("| Patient ID | Name             |Age    | Gender |");
		System.out.println("+------------+------------------+-------+--------+");
		while (resultset.next()) {
			int id = resultset.getInt("ID");
			String name = resultset.getString("name");
			int age = resultset.getInt("age");
			String gender = resultset.getString("gender");
			System.out.printf("|%-12s|%-18s|%-7s|%-8s|\n",id,name,age,gender);
			System.out.println("+------------+------------------+-------+--------+");
		}

	}

	public boolean getPatientByID(int id) throws SQLException {
		String quary = "select * from patients where ID=?";
		int i = id;

		PreparedStatement ps = connection.prepareStatement(quary);
		ps.setInt(1, i);
		ResultSet resultset = ps.executeQuery();

		if (resultset.next())
			return true;

		return false;
	}

}
