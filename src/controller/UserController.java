package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class UserController {
  
  @FXML private Hyperlink logout;
  
  @FXML private Label headerLabel;
  
  private Stage primaryStage;
  private User user;
  
  public void setUser(User user) {   
    this.user = user;    
  } 

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(user.getUsername());
  }   

  @FXML
  private void logout(ActionEvent ae) throws IOException {
    
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to logout?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      LoginController loginController = loader.getController();
      loginController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    }
    
  }

}
