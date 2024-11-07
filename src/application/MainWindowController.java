package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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

public class MainWindowController {
	private static Classifier algorithm;

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

	@FXML
	private TextArea inputArea;
	@FXML
	private Label feedBack;

	@FXML
	protected void predict() {
		try {
			ArrayList<String> labelValues = new ArrayList<String>();
			labelValues.add("POSITIVE");
			labelValues.add("NEGATIVE");

			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			attributes.add(new Attribute("Review", (ArrayList<String>) null)); // String attribute
			attributes.add(new Attribute("label", labelValues)); // Nominal attribute for label

			Instances instances = new Instances("Predictions", attributes, 1);
			instances.setClassIndex(1); // Set the class attribute index

			// Create a new instance and set the dataset for the instance
			Instance instance = new DenseInstance(2);
			instance.setDataset(instances); // Important: Set dataset for instance

			instance.setValue(attributes.get(0), inputArea.getText()); // Set "Review" text
			// No need to set label value for prediction (unlabeled instance)

			instances.add(instance); // Add the instance to the dataset

			// Classify the instance
			double prediction = algorithm.classifyInstance(instance);
			if (prediction == 1.0) {
				feedBack.setText("NEGATIVE");
			} else if (prediction == 0.0) {
				feedBack.setText("POSITIVE");
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
