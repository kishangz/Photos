package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
  
  @FXML private Button loginButton;
  @FXML private TextField loginField;
  
  private Stage primaryStage;

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage;    
  }
  
  @FXML
  private void login(ActionEvent ae) throws IOException {
    
    if(loginField.getText().equals("admin")) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Admin.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      AdminController adminController = loader.getController();
      adminController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    }
  }

}
