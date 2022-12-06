package sac;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sac.control.ControllerFactory;
import sac.control.WelcomeMenuController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("WelcomeMenu.fxml"));
        fxmlLoader.setController(new WelcomeMenuController());
        stage.setScene(new Scene((fxmlLoader.load())));
        ControllerFactory.welcomeMenu = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}