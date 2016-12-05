package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Login.fxml"));
        stage.setTitle("Login");
        stage.setResizable(false);
        //stage.setScene(new Scene(root, 500, 500));
        stage.setScene(new Scene(root, 318, 500));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
