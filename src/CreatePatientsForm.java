import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreatePatientsForm extends Application {
  private PatientDao patientDao = new PatientDao();

  public void start(Stage stage) {
    // create form labels
    Label nikLabel = new Label("NIK");
    Label nameLabel = new Label("Nama");
    Label addressLabel = new Label("Alamat");
    Label dobLabel = new Label("Tanggal Lahir");

    // create form fields
    TextField nikField = new TextField();
    TextField nameField = new TextField();
    TextField addressField = new TextField();
    DatePicker dobPicker = new DatePicker();

    // create submit button
    Button submitButton = new Button("Simpan");
    submitButton.setOnAction(e -> {
      String nik = nikField.getText();
      String name = nameField.getText();
      String address = addressField.getText();
      LocalDate dob = dobPicker.getValue();

      String errorMessage = "";

      // Validate NIK field
      if (nik.isEmpty()) {
        errorMessage += "NIK harus diisi\n";
      } else if (!nik.matches("[0-9]+")) {
        errorMessage += "NIK harus berisi angka saja\n";
      } else if (nik.length() > 15) {
        errorMessage += "NIK maksimal terdiri dari 15 digit angka\n";
      } else if (patientDao.getPatientByNik(nik) != null) {
        errorMessage += "NIK telah terdaftar dalam sistem\n";
      }

      // Validate Name field
      if (name.isEmpty()) {
        errorMessage += "Nama harus diisi\n";
      } else if (name.length() > 20) {
        errorMessage += "Nama maksimal terdiri dari 20 karakter\n";
      }

      // Validate Address field
      if (address.isEmpty()) {
        errorMessage += "Alamat harus diisi\n";
      } else if (address.length() > 20) {
        errorMessage += "Alamat maksimal terdiri dari 20 karakter\n";
      }

      // Validate DOB field
      if (dob == null) {
        errorMessage += "Tanggal lahir harus diisi\n";
      }

      if (!errorMessage.isEmpty()) {
        // Show error message if there are validation errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Terdapat kesalahan dalam mengisi formulir");
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return;
      } else {
        // Save the patient data if all fields are valid
        Patient patient = new Patient();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dobString = dob.format(formatter);

        patient.setNik(nik);
        patient.setName(name);
        patient.setAddress(address);
        patient.setDate(dobString);

        patientDao.createPatient(patient);
        stage.close();
      }
    });

    // create form layout
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(5);
    gridPane.setHgap(5);

    // add labels and fields to form
    gridPane.add(nikLabel, 0, 0);
    gridPane.add(nikField, 1, 0);
    gridPane.add(nameLabel, 0, 1);
    gridPane.add(nameField, 1, 1);
    gridPane.add(addressLabel, 0, 2);
    gridPane.add(addressField, 1, 2);
    gridPane.add(dobLabel, 0, 3);
    gridPane.add(dobPicker, 1, 3);
    gridPane.add(submitButton, 1, 4);

    // create scene
    Scene scene = new Scene(gridPane, 400, 300);
    stage.setScene(scene);
    stage.setTitle("Tambah Pasien Baru");
    stage.show();
  }
}