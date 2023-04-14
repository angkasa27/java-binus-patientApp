import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdatePatientForm {
  private PatientDao patientDao = new PatientDao();
  private Stage stage;
  private TextField nikField;
  private TextField nameField;
  private TextField addressField;
  private DatePicker dobPicker;
  private Button updateButton;

  public UpdatePatientForm(Patient patient) {
    this.stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Update Patient");

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    Label nikLabel = new Label("NIK");
    nikField = new TextField(patient.getNik());
    nikField.setDisable(true);
    grid.add(nikLabel, 0, 0);
    grid.add(nikField, 1, 0);

    Label nameLabel = new Label("Nama");
    nameField = new TextField(patient.getName());
    grid.add(nameLabel, 0, 1);
    grid.add(nameField, 1, 1);

    Label addressLabel = new Label("Alamat");
    addressField = new TextField(patient.getAddress());
    grid.add(addressLabel, 0, 2);
    grid.add(addressField, 1, 2);

    Label dobLabel = new Label("Tanggal Lahir");
    dobPicker = new DatePicker();
    dobPicker.setValue(LocalDate.parse(patient.getDate()));
    grid.add(dobLabel, 0, 3);
    grid.add(dobPicker, 1, 3);

    updateButton = new Button("Update");
    updateButton.setOnAction(e -> {
      String name = nameField.getText();
      String address = addressField.getText();
      LocalDate dob = dobPicker.getValue();

      String errorMessage = "";

      if (name.isEmpty()) {
        errorMessage += "Nama harus diisi\n";
      } else if (name.length() > 20) {
        errorMessage += "Nama maksimal terdiri dari 20 karakter\n";
      }

      if (address.isEmpty()) {
        errorMessage += "Alamat harus diisi\n";
      } else if (address.length() > 20) {
        errorMessage += "Alamat maksimal terdiri dari 20 karakter\n";
      }

      if (dob == null) {
        errorMessage += "Tanggal lahir harus diisi\n";
      }

      if (!errorMessage.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Terdapat kesalahan dalam mengisi formulir");
        alert.setContentText(errorMessage);
        alert.showAndWait();
        return;
      } else {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dobString = dob.format(formatter);

        patient.setName(name);
        patient.setAddress(address);
        patient.setDate(dobString);

        patientDao.updatePatient(patient);
        stage.close();
      }
    });
    grid.add(updateButton, 1, 4);

    Scene scene = new Scene(grid, 400, 200);
    stage.setScene(scene);
  }

  public void showAndWait() {
    stage.showAndWait();
  }
}