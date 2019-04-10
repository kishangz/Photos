package controller;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class LoginController {
  
  @FXML private Button loginButton;
  @FXML private TextField loginField;
  @FXML private Hyperlink signUp;
  
  private Stage primaryStage;
  
  //Temporarily just creating the user list here rather than passing it in from the main method after 
  // loading it from file;
  private HashMap<String, User> userList =  new HashMap<>();

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage;    
  }
  
  public void setUserList(HashMap<String, User> userList) {   
    this.userList = userList;    
  } 
  
  @FXML
  private void login(ActionEvent ae) throws IOException {    
    
    if(loginField.getText().equals("admin")) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Admin.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      AdminController adminController = loader.getController();
      adminController.setUserList(userList);
      adminController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    } else {
      
      User user = userList.get(loginField.getText());
      
      if (user != null) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        UserController userController = loader.getController();
        userController.setUserList(userList);
        userController.setUser(user);
        userController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show(); 
      }
           
    }
  }
  
  @FXML
  private void signUp(ActionEvent ae) throws IOException {
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Account.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AccountController accountController = loader.getController();
    accountController.setUserList(userList);
    accountController.setPreviousWindow("login");
    accountController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
    
      
    
  }

}
