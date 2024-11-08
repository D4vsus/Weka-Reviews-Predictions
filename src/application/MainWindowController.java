package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * <h1>MainWindowController</h1>
 * <p>Add functionality to the window</p>
 * 
 * @author D4vsus
 */
public class MainWindowController {
	//variables and objects
	private static Classifier algorithm;
	@FXML
	private TextArea inputArea;
	@FXML
	private Label feedBack;
	
	//methods
	
	/**
	 * <h1>initAlgorithm()</h1>
	 * <p>Load the algorithm file from the resources</p>
	 */
	public static void initAlgorithm() {
		try {
			algorithm = (Classifier) SerializationHelper
					.read(new FileInputStream(MainWindowController.class.getResource("/res/model.model").getFile()));
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("File not found");
			alert.setContentText(e.toString());
			alert.show();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.toString());
			alert.setTitle("Error");
			alert.show();
			e.printStackTrace();
		}

	}
	
	/**
	 * <h1>predict()</h1>
	 * <p>Make a prediction each time the user types in the keyboard</p>
	 */
	@FXML
	protected void predict() {
		try {
			//create the values for the label field
			ArrayList<String> labelValues = new ArrayList<String>();
			labelValues.add("POSITIVE");
			labelValues.add("NEGATIVE");

			//create the attributes
			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			attributes.add(new Attribute("Review", (ArrayList<String>) null)); // String attribute
			attributes.add(new Attribute("label", labelValues)); // Nominal attribute for label

			//create the dataset
			Instances instances = new Instances("Predictions", attributes, 1);
			instances.setClassIndex(1); // Set the class attribute index

			//create the instance for the dataset
			Instance instance = new DenseInstance(2);
			instance.setDataset(instances);
			instance.setValue(attributes.get(0), inputArea.getText()); // set the text of the text field as the Review column
			instances.add(instance); 
			
			//make the prediction
			double[] results = algorithm.distributionForInstance(instance);
			int prediction = -1; //if the prediction don't have enough confidence, by default will be one
			double mostConfidentClass = 0;
			int resultSize = results.length;
			
			//get the index of the most confident result
			for (int i = 0; i < resultSize; i++) {
				//if the most confident value is lower than 65% ignore and if is more confident than the most confident class in the moment
				if (results[i] > 0.65 && results[i] > mostConfidentClass) {
					mostConfidentClass = results[i]; // set the new most confident class;
					prediction = i; // assign it to the prediction
				}
			}
			
			//output the instance
			switch (prediction) {
			case 0:
				feedBack.setText("POSITIVE");
				break;
			case 1:
				feedBack.setText("NEGATIVE");
				break;
			default:
				feedBack.setText("?");
				break;
			}

		} catch (FileNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("File not found");
			alert.setContentText(e.toString());
			alert.show();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.toString());
			alert.setTitle("Error");
			alert.show();
			e.printStackTrace();
		}
	}
}
