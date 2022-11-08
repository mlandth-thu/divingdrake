package de.mlandth.divingdrake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author mlandth
 *
 * original code base from:
 * https://github.com/Da9el00/FlappyBirdJavaFX
 */

/**
 * This is the Main class of this game.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Init view
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Required for capturing keyboard strokes
        scene.getRoot().requestFocus();

        //Show view
        stage.setTitle("Diving drake");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}