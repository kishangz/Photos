package app;

import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.UsersList;

public class Photos extends Application {
  
  public static UsersList userList =  new UsersList();   
  
  @Override
  public void start(Stage primaryStage)
  throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      try {
        userList.setUserList(UsersList.read());
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
      
      LoginController loginController = loader.getController();
      loginController.start(primaryStage);
      
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Photos");
      primaryStage.setResizable(false);  
      primaryStage.show();
  }
  
  public static void main(String[] args) {
      launch(args);
  }

}
