import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {
  public List<Patient> getAllPatients() {
    List<Patient> patients = new ArrayList<>();
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "SELECT * FROM patients";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String nik = resultSet.getString("nik");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String date = resultSet.getString("birth_day");
        Patient patient = new Patient(nik, name, address, date);
        patients.add(patient);
      }
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return patients;
  }

  public void createPatient(Patient patient) {
    System.out.println(patient);
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "INSERT INTO patients (nik, name, address, birth_day) VALUES (?, ?, ?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, patient.getNik());
      preparedStatement.setString(2, patient.getName());
      preparedStatement.setString(3, patient.getAddress());
      preparedStatement.setString(4, patient.getDate());
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Patient getPatientByNik(String nik) {
    Patient patient = null;
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "SELECT * FROM patients WHERE nik = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, nik);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String date = resultSet.getString("birth_day");
        patient = new Patient(nik, name, address, date);
      }
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return patient;
  }

  public void updatePatient(Patient patient) {
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "UPDATE patients SET name = ?, address = ?, birth_day = ? WHERE nik = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, patient.getName());
      preparedStatement.setString(2, patient.getAddress());
      preparedStatement.setString(3, patient.getDate());
      preparedStatement.setString(4, patient.getNik());
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deletePatient(String nik) {
    try {
      Connection connection = DBConnection.getConnection();
      String sql = "DELETE FROM patients WHERE nik = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, nik);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
