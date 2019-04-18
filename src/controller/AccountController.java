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

/**
 * This is the Controller for Account
 * It has all the functionality for Account GUI
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class AccountController {
	
  /**
   * Submit Buuton
   */
  @FXML private Button submitButton;
  
  /**
   * Text Field to input user name
   */
  @FXML private TextField accountField;
  
  /**
   * Cancel button
   */
  @FXML private Button cancelButton;
  
  /**
   * Label to display the wrong inputs
   */
  @FXML private Label errorLabel;
  
  private Stage primaryStage;
 
  private String previousWindow;

  /**
   * This is the Start method that initializes
   * everything for the account interface
   * @param primaryStage
   */
  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage;  
    
    primaryStage.setOnCloseRequest(event -> {
      
      
      try {
           UsersList.save(LoginController.userList.getUserList());
       } catch (IOException er) {
           // TODO Auto-generated catch block
           er.printStackTrace();
       }
    });
  }
  
  
  /**
   * Sets the previous window
   * @param previousWindow
   */
  public void setPreviousWindow(String previousWindow) {   
    this.previousWindow = previousWindow;    
  } 
  
  /**
   * Creates Account by taking 
   * the user name from the textfield
   * @param ae
   * @throws IOException
   */
  @FXML
  void createAccount(ActionEvent ae) throws IOException {
    if (LoginController.userList.getUser(accountField.getText().trim()) == null) {
    	
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
      
      LoginController.userList.addUser(accountField.getText(), accountField.getText());
      
      if (previousWindow.equals("login")) {       
        LoginController.currUser = LoginController.userList.getUser(accountField.getText());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        UserController userController = loader.getController();
        userController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show();  
      } else {
        
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
  
  /**
   * Goes back to the previous window
   * when cancel button is pressed
   * @param event
   * @throws IOException
   */
  @FXML
  void cancel(ActionEvent event) throws IOException {
    if (previousWindow.equals("login")) {
     
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      LoginController loginController = loader.getController();
      loginController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    } else {
      
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
