package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
  
  @FXML private Button loginButton;
  @FXML private TextField loginField;

  public void start(Stage primaryStage) {   
    
    
  }
  
  @FXML
  private void login(ActionEvent ae) {
    
    if(loginField.getText()) {
    }
  }

}
