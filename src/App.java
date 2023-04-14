import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
  private Button openTableButton;
  private Button addPatientButton;
  private Button exitButton;

  public void start(Stage stage) {
    openTableButton = new Button("Daftar Pasien");
    openTableButton.setOnAction(e -> {
      PatientTableView patientTableView = new PatientTableView();
      patientTableView.start(new Stage());
    });

    addPatientButton = new Button("Tambah Pasien Baru");
    addPatientButton.setOnAction(e -> {
      CreatePatientsForm createPatientsForm = new CreatePatientsForm();
      createPatientsForm.start(new Stage());
    });

    exitButton = new Button("Keluar");
    exitButton.setOnAction(e -> Platform.exit());

    VBox dashboardLayout = new VBox(10);
    dashboardLayout.setAlignment(Pos.CENTER);
    dashboardLayout.getChildren().addAll(openTableButton, addPatientButton, exitButton);

    Scene scene = new Scene(dashboardLayout, 400, 300);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}