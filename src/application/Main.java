package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * <h1>Main</h1>
 * <p>Initialize the application</p>
 * 
 * @author D4vsus
 */
public class Main extends Application {
	
	//methods
	
	/**
	 * <h1>start()</h1>
	 * <p>Initialize the application</p>t
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			MainWindowController.initAlgorithm();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Weka example");
			primaryStage.show();
		} catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.toString());
			alert.setTitle("Error");
			alert.show();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
