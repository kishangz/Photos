package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;
import model.User;
import model.UsersList;

public class LoginController {
  
  @FXML private Button loginButton;
  @FXML private TextField loginField;
  @FXML private Hyperlink signUp;
  @FXML private Label errorLabel;
  
  private Stage primaryStage;
  
  String userEntered;
  
  protected static User currUser;
  
  public static int counter = 0;
  
  
  
  public static UsersList userList;
  
  //Temporarily just creating the user list here rather than passing it in from the main method after 
  // loading it from file;
  
  

  public void start(Stage primaryStage) throws FileNotFoundException, IOException {   
    this.primaryStage = primaryStage;
    
    
    
    primaryStage.setOnCloseRequest(event -> {
		
		
		try {
			 UsersList.save(userList.getUserList());
		 } catch (IOException er) {
			 // TODO Auto-generated catch block
			 er.printStackTrace();
		 }
		 
	});
    
  }
  
  public void setUserList() throws FileNotFoundException, IOException { 
    userList =  new UsersList();
    
    try {
      userList.read();
     } catch (ClassNotFoundException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }  
  } 
  
  
  @FXML
  private void login(ActionEvent ae) throws IOException { 
	  
	  userEntered = loginField.getText().trim(); 	
  	
    
    if(userEntered.equalsIgnoreCase("admin")) {
      
    	
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Admin.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      AdminController adminController = loader.getController();
      
      adminController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    } 
    else if(userEntered.equalsIgnoreCase("stock")){
    	
    	if (userList.getUser("stock") == null)
    	{
    		userList.addUser("stock", "stock");
    	}
    	
    	currUser = userList.getUser("stock");
    	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/User.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		UserController userController = loader.getController();
		
        
        userController.start(primaryStage);
    	
        primaryStage.getScene().setRoot(root);
        primaryStage.show(); 
    }
    else {
      
      currUser = userList.getUser(userEntered);
      
      if (currUser != null) {
        
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        UserController userController = loader.getController();
        
        
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
