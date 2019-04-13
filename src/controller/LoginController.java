package controller;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
  
  protected static User currUser;
  
  public static int counter = 0;
  
  //Temporarily just creating the user list here rather than passing it in from the main method after 
  // loading it from file;
  public static UsersList userList =  new UsersList();

  public void start(Stage primaryStage) {   
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
  
  public void setUserList(HashMap<String, User> userList) {   
    this.userList.setUserList(userList);    
  } 
  
  @FXML
  private void login(ActionEvent ae) throws IOException { 
	  
	  userEntered = loginField.getText().trim();
	  
	  try {
			userList.setUserList(userList.read());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  	
  	if(userList.getUserList() == null)
		{
			//System.out.println("its empty");
			userList = new UsersList();
		}
	
    
    if(userEntered.equalsIgnoreCase("admin")) {
    	
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Admin.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      AdminController adminController = loader.getController();
      adminController.setUserList(userList.getUserList());
      adminController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    } 
    else if(userEntered.equalsIgnoreCase("stock")){
    	
    	if (this.userList.getUser("stock") == null)
    	{
    		this.userList.addUser("stock", "stock");
    	}
    	
    	this.currUser = this.userList.getUser("stock");
    	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/User.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		UserController userController = loader.getController();
		userController.setUserList(userList.getUserList());
        userController.setUser(this.currUser);
        userController.start(primaryStage);
    	
        primaryStage.getScene().setRoot(root);
        primaryStage.show(); 
    }
    else {
      
      User user = userList.getUser(userEntered);
      
      if (user != null) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        UserController userController = loader.getController();
        userController.setUserList(userList.getUserList());
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
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Account.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AccountController accountController = loader.getController();
    accountController.setUserList(userList.getUserList());
    accountController.setPreviousWindow("login");
    accountController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
    
      
    
  }

}
