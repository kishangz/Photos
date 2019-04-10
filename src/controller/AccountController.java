package controller;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class AccountController {
  
  @FXML private Button submitButton;
  @FXML private TextField accountField;
  @FXML private Button cancelButton;
  
  private Stage primaryStage;
  
  private HashMap<String, User> userList;
  private String previousWindow;

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage;    
  }
  
  public void setUserList(HashMap<String, User> userList) {   
    this.userList = userList;    
  } 
  
  public void setPreviousWindow(String previousWindow) {   
    this.previousWindow = previousWindow;    
  } 
  
  @FXML
  void createAccount(ActionEvent ae) throws IOException {
    if (userList.get(accountField.getText()) == null) {
      
      User user = new User(accountField.getText());
      userList.put(accountField.getText(), user);
      
      if (previousWindow.equals("login")) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        UserController userController = loader.getController();
        userController.setUser(user);
        userController.setUserList(userList);
        userController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show();  
      } else {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Admin.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        AdminController adminController = loader.getController();
        adminController.setUserList(userList);
        adminController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
      }
      
       
    }
    
    // TODO Add duplicate check 
    
  }
  
  @FXML
  void cancel(ActionEvent event) throws IOException {
    if (previousWindow.equals("login")) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      LoginController loginController = loader.getController();
      loginController.setUserList(userList);
      loginController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    } else {
      
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Admin.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      AdminController adminController = loader.getController();
      adminController.setUserList(userList);
      adminController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    }

  }

}
