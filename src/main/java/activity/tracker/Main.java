package activity.tracker;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;

import javax.imageio.ImageIO;

import activity.tracker.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rf.javafx.util.StageFactory;

//import org.apache.logging.log4j.LogManager;

//import org.apache.logging.log4j.Logger;
// TODO:: add activity record
// TODO :: https://www.turais.de/how-to-custom-listview-cell-in-javafx/ list records in history and add 'add to favorites' button.
// TODO:: list favorites.

public class Main extends Application {

	private final String taskbarIcon = "/gui/trayIcon.png";

	// a timer allowing the tray icon to provide a periodic notification event.
	private Timer notificationTimer = new Timer();

	// format used to display the current time in a tray icon notification.
	private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

	private final StageFactory stageFactory = new StageFactory();
	private Stage stage;
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage aStage) throws Exception {

		try {
			MainController mainController = new MainController(stageFactory, aStage);
			aStage = stageFactory.createMainApplicationWindow(mainController);

			aStage.show();
			mainController.enableDragToMove(aStage);
			stage = aStage;
			// sets up the tray icon (using awt code run on the swing thread).
			javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
			stage.getIcons().add(new Image(taskbarIcon));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}


	// https://gist.github.com/jewelsea/e231e89e8d36ef4e5d8a
	/**
	 * Sets up a system tray icon for the application.
	 */
	private void addAppToTray() {
		try {
			// ensure awt toolkit is initialized.
			java.awt.Toolkit.getDefaultToolkit();

			// app requires system tray support, just exit if there is no support.
			if (!java.awt.SystemTray.isSupported()) {
				System.out.println("No system tray support, application exiting.");
				Platform.exit();
			}

			// set up a system tray icon.
			java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
			URL imageLoc = new URL("file://" + Main.class.getResource("/gui/trayIcon.png").getPath());
			java.awt.Image image = ImageIO.read(imageLoc);
			java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
			trayIcon.setToolTip("Activity Tracker");
			// if the user double-clicks on the tray icon, show the main app stage.
			trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

			// if the user selects the default menu item (which includes the app name),
			// show the main app stage.
			java.awt.MenuItem openItem = new java.awt.MenuItem("Open");
			openItem.addActionListener(event -> Platform.runLater(this::showStage));

			// the convention for tray icons seems to be to set the default icon for opening
			// the application stage in a bold font.
			java.awt.Font defaultFont = java.awt.Font.decode(null);
			java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
			openItem.setFont(boldFont);

			// to really exit the application, the user must go to the system tray icon
			// and select the exit option, this will shutdown JavaFX and remove the
			// tray icon (removing the tray icon will also shut down AWT).
			java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
			exitItem.addActionListener(event -> {
				notificationTimer.cancel();
				Platform.exit();
				tray.remove(trayIcon);
			});

			// setup the popup menu for the application.
			final java.awt.PopupMenu popup = new java.awt.PopupMenu();
			popup.add(openItem);
			popup.addSeparator();
			popup.add(exitItem);
			trayIcon.setPopupMenu(popup);

			// create a timer which periodically displays a notification message.
//			notificationTimer.schedule(new TimerTask() {
//				@Override
//				public void run() {
//					javax.swing.SwingUtilities
//							.invokeLater(() -> trayIcon.displayMessage("Are you still working on the same activity?",
//							"The time is now " + timeFormat.format(new Date()), java.awt.TrayIcon.MessageType.INFO));
//				}
//			}, 5_000, 60_000);

			// add the application tray icon to the system tray.
			tray.add(trayIcon);
		} catch (java.awt.AWTException | IOException e) {
			System.out.println("Unable to init system tray");
			e.printStackTrace();
		}
	}

	/**
	 * Shows the application stage and ensures that it is brought ot the front of
	 * all stages.
	 */
	private void showStage() {
		if (stage != null) {
			stage.setIconified(false);
			stage.show();
			stage.toFront();
		}
	}

}
