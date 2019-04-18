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

/**
 * This is the Controller for Login
 * It has all the functionality for LoginGUI
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class LoginController {
  
	/**
	 * Login Button
	 */
  @FXML private Button loginButton;
  
  /**
   * Login Field to enter the username
   */
  @FXML private TextField loginField;
  
  /**
   * Signup link to create an account
   */
  @FXML private Hyperlink signUp;
  
  /**
   * Error label for a wrong input
   */
  @FXML private Label errorLabel;
  
  /**
   * primary Stage
   */
  private Stage primaryStage;
  
  /**
   * Userentered string
   */
  String userEntered;
  
  /**
   * User Current user
   */
  protected static User currUser;
  
  /**
   * int counter
   */
  public static int counter = 0;
 
  /**
   * userList
   */
  public static UsersList userList;
  
  /**
   * This is the Start method that initializes
   * everything for the login interface
   * @param primaryStage
   * @throws FileNotFoundException
   * @throws IOException
   */
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
  
  /**
   * Sets the UserList
   * @throws FileNotFoundException
   * @throws IOException
   */
  public void setUserList() throws FileNotFoundException, IOException { 
    userList =  new UsersList();
    
    try {
      userList.read();
     } catch (ClassNotFoundException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }  
  } 
  
  /**
   * Login Button that checks if user 
   * entered is either admin stock or an new user
   * @param ae
   * @throws IOException
   */
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
  
  /**
   * Signup button that takes you to account page to make an account
   * @param ae
   * @throws IOException
   */
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
