package controller;

import java.io.IOException;

import java.util.HashMap;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UsersList;


public class AccountController {
  
  @FXML private Button submitButton;
  @FXML private TextField accountField;
  @FXML private Button cancelButton;
  @FXML private Label errorLabel;
  
  private Stage primaryStage;
  
  //private static UsersList userList = new UsersList();
  private String previousWindow;

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage;    
  }
  
  
  
  public void setPreviousWindow(String previousWindow) {   
    this.previousWindow = previousWindow;    
  } 
  
  @FXML
  void createAccount(ActionEvent ae) throws IOException {
    if (Photos.userList.getUser(accountField.getText().trim()) == null) {
    	
    	//Since we dont have a password field im going
    	// to use the username as key and string for hashMap.
    	String id = (accountField.getText()).trim();
    	String name = (accountField.getText()).trim();
    	
     
      
     
      if(name.equalsIgnoreCase("") && id.equalsIgnoreCase(""))
      {
    	  Alert alert2 = new Alert(AlertType.INFORMATION);
    	  alert2.setTitle("Information Dialog");
    	  alert2.setHeaderText(null);
    	  alert2.setContentText("Enter a Username");
    	  alert2.showAndWait();
    	  return;
      }
      
      
      Photos.userList.addUser(accountField.getText(), accountField.getText());
      
      if (previousWindow.equals("login")) {
        try {
          UsersList.save(Photos.userList.getUserList());
      } catch (IOException er) {
          // TODO Auto-generated catch block
          er.printStackTrace();
      }
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        UserController userController = loader.getController();
        userController.setUser(Photos.userList.getUser(accountField.getText()));
        
        userController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show();  
      } else {
        try {
          UsersList.save(Photos.userList.getUserList());
      } catch (IOException er) {
          // TODO Auto-generated catch block
          er.printStackTrace();
      }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Admin.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        AdminController adminController = loader.getController();
        
        adminController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
      }
      
       
    } else {
      errorLabel.setVisible(true);
    }
    
    
    
  }
  
  @FXML
  void cancel(ActionEvent event) throws IOException {
    if (previousWindow.equals("login")) {
      try {
        UsersList.save(Photos.userList.getUserList());
    } catch (IOException er) {
        // TODO Auto-generated catch block
        er.printStackTrace();
    }
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      LoginController loginController = loader.getController();
      
      loginController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    } else {
      try {
        UsersList.save(Photos.userList.getUserList());
    } catch (IOException er) {
        // TODO Auto-generated catch block
        er.printStackTrace();
    }
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
