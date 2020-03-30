package activity.tracker;

import activity.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import org.apache.logging.log4j.LogManager;

//import org.apache.logging.log4j.Logger;


public class Main extends Application {



	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aStage) throws Exception {

		try {

			final FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/root.fxml"));
			loader.setController(new MainController());
			final VBox root = (VBox) loader.load();
			final Stage stage = new Stage();
			stage.setTitle("Activity Tracker");
			stage.setScene(new Scene(root));

			stage.show();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		// Scene scene = new Scene();
		// aStage.setScene(scene);
		// aStage.show();
	}




}
