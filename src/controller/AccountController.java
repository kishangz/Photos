package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

public class AccountController {
	
	@FXML private Button submitButton;
	@FXML private Button cancelButton;
	
	@FXML private TextField loginField;
	
	private static User user;
	
	static Stage primaryStage;
	
	static AdminController adminController;
	static UserController userController;
	
	static Scene adminScene;
	static Scene userScene;
	
	static ObservableList<User> userList = FXCollections.observableArrayList();
	
	public void start(Stage primaryStage) {   
		this.primaryStage = primaryStage;    
	}
	
	@FXML
	public void signup(ActionEvent ae) throws IOException{
		
		
	}
	
	@FXML
	public void goToCancel(ActionEvent ae) throws IOException{
		
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/view/Login.fxml"));
	    AnchorPane root = (AnchorPane)loader.load();
	   
	    
	    primaryStage.getScene().setRoot(root);
	    primaryStage.show();  
		
	}
	
	@FXML
	public void submit(ActionEvent ae) throws IOException{
		
		FXMLLoader loader = new FXMLLoader();
		checkUserName(ae);
	    loader.setLocation(getClass().getResource("/view/User.fxml"));
	    AnchorPane root = (AnchorPane)loader.load();
	   
	    
	    primaryStage.getScene().setRoot(root);
	    primaryStage.show();  
	}
	
	private User getUserFromName(String name) {
		for(User u : userList) {
			if(u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}
	
	public static void setUser(User usr) {
		user = usr;
	}
		
	public void checkUserName(ActionEvent e) {
		
		String userString = loginField.getText().trim();
		
		if(userString.isEmpty()) {
			
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Error");
			alert.setContentText("User name cannot be blank.");
			alert.showAndWait();
        
		}else {
			if(userInList(userString)) {
				User userLoggingIn = getUserFromName(userString);
				setUser(userLoggingIn);
				if (userString.equals("admin")) {
					
					//loader.setLocation(getClass().getResource("/view/User.fxml"));
					loginField.clear(); //clears login text 
				} else {
					//toUser();
					loginField.clear();
					
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("User does not exist.");
	            alert.showAndWait();
				}
			}
		}
	
	public static boolean userInList(String usr) {
		
		boolean found=false;
	
		for (int i=0;i<userList.size();i++) {
			if(userList.get(i).getName().equals(usr)) {
				found = true;
			}
		}	
		return found;
	}
	
}
