package rf.javafx.util;

import java.util.ArrayList;

import components4jfx.components.Components4JFXFactory;
import components4jfx.components.UndecoratedWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class JFXStageBuilder {



	/***
	 *
	 * @param a_scene
	 * @throws Exception
	 */
	public static void setStylesheets(Scene aScene, ArrayList<String> aStylesheetPaths) throws Exception {
		try {
			aStylesheetPaths.forEach(s -> {
				aScene.getStylesheets().add(StageBuilder.class.getResource(s).toExternalForm());
			});
		}catch(Exception e) {
			throw new Exception("An error occurred while trying to load the and apply stylesheets\n" + e);
		}
	}

	/***
	 *
	 * @param a_fxmlPath
	 * @param a_stylesheetPaths
	 * @param a_controller
	 * @return
	 * @throws Exception
	 */
	public static Parent loadFXML(String a_fxmlPath, Object a_controller) throws Exception {

		try {
			FXMLLoader loader = new FXMLLoader(StageBuilder.class.getResource(a_fxmlPath));
			if(a_controller != null) {
				loader.setController(a_controller);
			}
			return loader.load();
		}catch(Exception e) {
			System.out.println("An error occurred while trying to load the fxml: " + a_fxmlPath + "\n");
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new Exception(e);

		}
	}

	/***
	 *
	 * @param a_stage
	 * @throws Exception
	 */
	public static void setStageIcon(Stage a_stage, String aStageIconPath) throws Exception {
		if(aStageIconPath != "") {
			try {
				a_stage.getIcons().add(new Image(StageBuilder.class.getResourceAsStream(aStageIconPath)));
			}catch(Exception e) {
				throw new Exception("An error occurred while trying to load the stage icon from path: " + aStageIconPath + "\n" + e);
			}
		}
	}



	public static class StageBuilder{

		private String fxmlPath;
		private Object controller;
		private ArrayList<String> stylesheetPaths = new ArrayList<String>();

		private String stageTitle = "";
		private String stageIconPath = "";
		private StageStyle stageStyle = StageStyle.DECORATED;

		private double stageWidth = 100;
		private double stageHeight = 100;

		private double stageXPos = 1;
		private double stageYPos = 1;
		private boolean useCustomBorder = false;
		private double minStageWidth = 150;
		private double minStageHeight = 50;

		/***
		 *
		 * @param a_fxmlPath
		 */
		public StageBuilder (String a_fxmlPath) {
			this.fxmlPath = a_fxmlPath;
		}
		/***
		 *
		 * @param a_controller
		 * @return
		 */
		public StageBuilder withController(Object a_controller) {
			this.controller = a_controller;
			return this;
		}
		/***
		 *
		 * @param a_stylesheetPaths
		 * @return
		 */
		public StageBuilder withStylesheets(ArrayList<String> a_stylesheetPaths) {
			a_stylesheetPaths.forEach(p -> {
				this.stylesheetPaths.add(p);
			});
			return this;
		}
		/***
		 *
		 * @param a_stageTitle
		 * @return
		 */
		public StageBuilder withTitle(String a_stageTitle) {
			this.stageTitle = a_stageTitle;
			return this;
		}
		/***
		 *
		 * @param a_stageIconPath
		 * @return
		 */
		public StageBuilder withIcon(String a_stageIconPath) {
			this.stageIconPath = a_stageIconPath;
			return this;
		}
		/***
		 *
		 * @param a_stageStyle
		 * @return
		 */
		public StageBuilder withStageStyle(StageStyle a_stageStyle) {
			this.stageStyle = a_stageStyle;
			return this;
		}
		/***
		 *
		 * @param a_width
		 * @param a_height
		 * @return
		 */
		public StageBuilder withDimensions(double a_width, double a_height) {
			this.stageWidth = a_width;
			this.stageHeight = a_height;
			return this;
		}

		/***
		 *
		 * @param a_width
		 * @param a_height
		 * @return
		 */
		public StageBuilder withMinWindowSize(double aWidth, double aHeight) {
			this.minStageWidth = aWidth;
			this.minStageHeight = aHeight;
			return this;
		}
		/***
		 *
		 * @param a_xPos
		 * @param a_yPos
		 * @return
		 */
		public StageBuilder withPosition(double a_xPos, double a_yPos) {
			this.stageXPos = a_xPos;
			this.stageYPos = a_yPos;
			return this;
		}

		public StageBuilder withCustomResizableBorders() {
			useCustomBorder = true;
			return this;
		}

		/***
		 *
		 * @return
		 * @throws Exception
		 */
		public Stage build() throws Exception {
			Stage stage = new Stage();
			Scene scene = null;

			/* create and setup scene*/
			Parent parent = loadFXML(fxmlPath, controller);
			if(useCustomBorder) {
				scene = new Scene((Parent) createResizableBorderPane(parent, minStageWidth, minStageHeight));
			}else {
				scene = new Scene(parent);
			}

			setStylesheets(scene, stylesheetPaths);

			/* Setup the stage */
			stage.setScene(scene);
			stage.setWidth(stageWidth);
			stage.setHeight(stageHeight);
			stage.setX(stageXPos);
			stage.setY(stageYPos);
			stage.setTitle(stageTitle);
			stage.initStyle(stageStyle);
			setStageIcon(stage, stageIconPath);

			scene.setFill(Color.TRANSPARENT);
			// stage.initStyle(StageStyle.TRANSPARENT);

			return stage;
		}


		private static UndecoratedWindow createResizableBorderPane(Parent a_parent, double minWidth, double minHeight) {
			double thickness = 6;
			double cornerSize = 10;
			// String color = "#910000";
			String color = "#121212";
			boolean enableDragToResize = true;
			boolean enableWindowSnap = false;


			return Components4JFXFactory.createNewUndecoratedWindow(thickness,
					cornerSize,
					color,
					minWidth,
					minHeight,
					enableDragToResize,
					enableWindowSnap,
					a_parent);
		}


	}//End of Builder.

	private JFXStageBuilder() {
		/* Private constructor, can only be built with the build pattern.*/
	}

}
