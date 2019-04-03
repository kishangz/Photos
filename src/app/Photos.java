package app;

import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Photos extends Application {
  
  @Override
  public void start(Stage primaryStage)
  throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      GridPane root = (GridPane)loader.load();
      
      LoginController loginController = loader.getController();
      loginController.start(primaryStage);
      
      Scene scene = new Scene(root, 800, 800);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Photos");
      primaryStage.setResizable(false);  
      primaryStage.show();
  }
  
  public static void main(String[] args) {
      launch(args);
  }

}
