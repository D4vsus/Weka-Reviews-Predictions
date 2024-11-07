module wekaModelPredictionExample {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires weka.stable;
	
	opens application to javafx.graphics, javafx.fxml;
}
