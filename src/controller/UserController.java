package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.UsersList;

public class UserController {
  
  @FXML private Hyperlink logout;
  @FXML private Button tempButton;  

  @FXML
  private Button delete;

  @FXML
  private Button add;
  
  @FXML
  private TextField search;

  @FXML
  private TableView<?> table;
  
  @FXML private Label headerLabel;
  
  private Stage primaryStage;
  private User user;
  
  private HashMap<String, User> userList;
  
  private Album albums;
  
  public void setUser(User user) {   
    this.user = user;    
  } 

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(user.getName());
    
primaryStage.setOnCloseRequest(event -> {
		
		
		try {
			 UsersList.save(LoginController.userList.getUserList());
		 } catch (IOException er) {
			 // TODO Auto-generated catch block
			 er.printStackTrace();
		 }
		 
		 
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(getClass().getResource("/view/Login.fxml"));
		 
		 Parent root=null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Stage stage = new Stage();

		 LoginController listController = loader.getController();


		 stage.initModality(Modality.APPLICATION_MODAL);
		 stage.setOpacity(1);
		 stage.setTitle("Login");
		 stage.setScene(new Scene(root, 453, 357));
		 stage.show();
	    // Save file
	});
    
  }
  
  /**
   * Initializes fields every time this screen is loaded. Also loads in the albums
   * from the user. 
   */
  public void startU() {
	  
  }
  
  
  
  public void setUserList(HashMap<String, User> userList) {   
    this.userList = userList;    
  } 
  
  // This method is initiated when the Temp button is pressed. This is just so we can get to an Album page
  // without having to first implement the TableView. It will be changed later.
  @FXML
  private void goToAlbum(ActionEvent ae) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Album.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AlbumController albumController = loader.getController();
    //albumController.setAlbum(user.getAlbumList("Album1"));
    albumController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
  }
  
  @FXML
  void add(ActionEvent event) {
    
  }

  @FXML
  void delete(ActionEvent event) {

  }

  @FXML
  void search(ActionEvent event) {

  }

  @FXML
  private void logout(ActionEvent ae) throws IOException {
    
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to logout?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Login.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      LoginController loginController = loader.getController();
      loginController.setUserList(userList);
      loginController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();
    }
    
  }

}
