package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("A Star Caves");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();

        Controller controller = loader.getController();


    }


    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        launch(args);


    }


}
