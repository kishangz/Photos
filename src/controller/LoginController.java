package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UsersList;

public class LoginController {
  
  @FXML private Button loginButton;
  @FXML private TextField loginField;
  @FXML private Hyperlink signUp;
  @FXML private Label errorLabel;
  
  private Stage primaryStage;
  
  String userEntered;
  
  //Temporarily just creating the user list here rather than passing it in from the main method after 
  // loading it from file;
  

  public void start(Stage primaryStage) throws FileNotFoundException, IOException {   
    this.primaryStage = primaryStage;
    
    try {
      Photos.userList.setUserList(UsersList.read());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
    
    primaryStage.setOnCloseRequest(event -> {
		
		
		try {
			 UsersList.save(Photos.userList.getUserList());
		 } catch (IOException er) {
			 // TODO Auto-generated catch block
			 er.printStackTrace();
		 }
		 
	});
    
  }
  
  
  
  @FXML
  private void login(ActionEvent ae) throws IOException { 
	  
	  userEntered = loginField.getText().trim(); 	
  	
    
    if(userEntered.equalsIgnoreCase("admin")) {
      
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
    } else {
      
      User user = Photos.userList.getUser(userEntered);
      
      if (user != null) {
        
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
        
        userController.setUser(user);
        userController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show(); 
      } else {
        
        errorLabel.setVisible(true);
      }
           
    }
  }
  
  @FXML
  private void signUp(ActionEvent ae) throws IOException {
    
    try {
      UsersList.save(Photos.userList.getUserList());
  } catch (IOException er) {
      // TODO Auto-generated catch block
      er.printStackTrace();
  }
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Account.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AccountController accountController = loader.getController();
    
    accountController.setPreviousWindow("login");
    accountController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
    
      
    
  }

}
