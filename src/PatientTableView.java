import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class PatientTableView extends Application {
  private PatientDao patientDao = new PatientDao();

  public void start(Stage stage) {

    List<Patient> patients = patientDao.getAllPatients();

    ObservableList<Patient> patientObservableList = FXCollections.observableArrayList(patients);

    TableColumn<Patient, String> nikColumn = new TableColumn<>("NIK");
    nikColumn.setCellValueFactory(new PropertyValueFactory<>("nik"));

    TableColumn<Patient, String> nameColumn = new TableColumn<>("Nama");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Patient, String> addressColumn = new TableColumn<>("Alamat");
    addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

    TableColumn<Patient, LocalDate> dobColumn = new TableColumn<>("Tanggal Lahir");
    dobColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    TableColumn<Patient, Void> updateColumn = new TableColumn<>("");
    updateColumn.setCellFactory(column -> new TableCell<Patient, Void>() {
      private final Button updateButton = new Button("Update");

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
          setGraphic(null);
        } else {
          Patient patient = getTableView().getItems().get(getIndex());

          updateButton.setOnAction(event -> {
            // Open update form for selected patient
            UpdatePatientForm updatePatientForm = new UpdatePatientForm(patient);
            updatePatientForm.showAndWait();

            // Refresh the table after the update form is closed
            List<Patient> updatedPatients = patientDao.getAllPatients();
            patientObservableList.setAll(updatedPatients);
          });

          setGraphic(updateButton);
        }
      }
    });

    TableColumn<Patient, Void> deleteColumn = new TableColumn<>("");
    deleteColumn.setCellFactory(column -> new TableCell<Patient, Void>() {
      private final Button deleteButton = new Button("Delete");

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
          setGraphic(null);
        } else {
          Patient patient = getTableView().getItems().get(getIndex());

          deleteButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Apakah anda yakin ingin menghapus pasien ini?",
                ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
              patientDao.deletePatient(patient.getNik());

              List<Patient> updatedPatients = patientDao.getAllPatients();
              patientObservableList.setAll(updatedPatients);
            }
          });

          setGraphic(deleteButton);
        }
      }
    });

    TableColumn<Patient, Integer> indexColumn = new TableColumn<>("No");
    indexColumn.setSortable(false);
    indexColumn.setPrefWidth(50);

    indexColumn.setCellFactory(column -> {
      return new TableCell<Patient, Integer>() {
        @Override
        protected void updateItem(Integer item, boolean empty) {
          super.updateItem(item, empty);

          if (empty) {
            setText(null);
          } else {
            int index = getIndex() + 1;
            setText(String.valueOf(index));
          }
        }
      };
    });

    TableView<Patient> patientTableView = new TableView<>();
    patientTableView.setItems(patientObservableList);
    patientTableView.getColumns().add(indexColumn);
    patientTableView.getColumns().add(nikColumn);
    patientTableView.getColumns().add(nameColumn);
    patientTableView.getColumns().add(addressColumn);
    patientTableView.getColumns().add(dobColumn);
    patientTableView.getColumns().add(updateColumn);
    patientTableView.getColumns().add(deleteColumn);

    VBox vBox = new VBox();
    vBox.getChildren().add(patientTableView);

    Scene scene = new Scene(vBox);
    stage.setScene(scene);
    stage.setTitle("Daftar Pasien");
    stage.show();

  }
}