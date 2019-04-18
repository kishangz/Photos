package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import app.Photos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UsersList;

/**
 * This is the controller for the Admin it has
 * all the functionality for the Admin GUI.
 * @author Kishan Zalora Eyob Tesfaye
 */
public class AdminController {
  /**
   * Logout button
   */
  @FXML private Hyperlink logout;
  
  /**
   * Delete User
   */
  @FXML private Button deleteButton;
  
  /**
   * Add User
   */
  @FXML private Button addButton;
  
  private Stage primaryStage;
  
  /**
   * Observable List of String
   */
  private ObservableList<String> obsList = FXCollections.observableArrayList();  
  
  /**
   * ListView to display the users
   */
  @FXML private ListView<String> listView;
  
/**
 * This is the Start method that initializes
 * everything for the Admin interface
 * @param primaryStage
 */
  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage;   
    Set<String> set = LoginController.userList.getUserList().keySet();
    obsList.addAll(set);
    
    listView.setItems(obsList); 
    
    listView.getSelectionModel().select(0);
    
    String selected = listView.getSelectionModel().getSelectedItem();         
    if(selected != null) {
      deleteButton.setDisable(false);
    }
    
    listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
          if(newValue == null) {
                            
              deleteButton.setDisable(true);
              
          }
        }
    });
    
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
   * Adds a user if it doesnt exist
   * @param event
   * @throws IOException
   */
  @FXML
  void add(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Account.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AccountController accountController = loader.getController();
    
    accountController.setPreviousWindow("admin");
    accountController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
    
  }

  /**
   * Deletes a user from the listt
   * @param event
   */
  @FXML
  void delete(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to delete this user?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
        
        LoginController.userList.getUserList().remove(listView.getSelectionModel().getSelectedItem());
        obsList.remove(listView.getSelectionModel().getSelectedItem());
    }
  }
  
  /**
   * Prompt that asks if you wish to logout and
   * takes the user to the login page to log back
   * in before exiting the program
   * @param ae
   * @throws IOException
   */
  @FXML
  private void logout(ActionEvent ae) throws IOException {
    
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to logout?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
    	
		try {
			 UsersList.save(LoginController.userList.getUserList());
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
    }
    
  }
}
