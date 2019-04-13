package controller;

import java.io.IOException;
import java.util.Optional;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.User;
import model.UsersList;

public class AlbumController {
  
  @FXML private Hyperlink logout;
  
  @FXML private Label headerLabel;
  
  @FXML private Button back; 
  
  private Stage primaryStage;
  private Album album;
  User user;
  
  public void setAlbum(Album album) {   
    this.album = album;    
  }  
  
  public void setUser(User user) {   
    this.user = user;         
  } 

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(album.getName());
    
    primaryStage.setOnCloseRequest(event -> {
     
      
      try {
           UsersList.save(Photos.userList.getUserList());
       } catch (IOException er) {
           // TODO Auto-generated catch block
           er.printStackTrace();
       }
       
    });
  }
  
  public void startA(Album thisAlbum) {
	  
  }
  
  public static void closeAlbum() {
	}
  
  @FXML
  private void back(ActionEvent ae) throws IOException {
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
    
  }

  @FXML
  private void logout(ActionEvent ae) throws IOException {
    
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to logout?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
      
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
    }
    
  }

}
