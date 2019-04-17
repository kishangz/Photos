package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.UsersList;

public class SearchController {
  
  @FXML private Hyperlink logout;
  
  @FXML private Label searchLabel;
  
  @FXML private Button back; 

  private Stage primaryStage;

  private String previousWindow;
  
  private Photo clipboard;

  private Album album;

  private String input;
  
  @FXML private TextField tagSearchField;
  
  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    searchLabel.setText("Results for \"" + input + "\":");
    
    searchByTags(input);
    
    primaryStage.setOnCloseRequest(event -> {     
      
      try {
           UsersList.save(LoginController.userList.getUserList());
       } catch (IOException er) {
           // TODO Auto-generated catch block
           er.printStackTrace();
       }
       
    });
  }
  
  public void setInput(String input) {   
    this.input = input;    
  }
  
  public void setPreviousWindow(String previousWindow) {   
    this.previousWindow = previousWindow;    
  } 
  
  public void setAlbum(Album album) {   
    this.album = album;    
  }  
  
  public void setClipboard(Photo clipboard) {   
    this.clipboard = clipboard;    
  } 
  
  @FXML
  void tagSearch(ActionEvent event) {
    searchByTags(tagSearchField.getText());

  }
  
  private void searchByTags(String input) {
    if (!input.equals("")) {
      
    }

  }
  
  @FXML
  void createAlbum(ActionEvent event) {

  }
  
  @FXML
  private void back(ActionEvent ae) throws IOException {
    try {
      UsersList.save(LoginController.userList.getUserList());
   } catch (IOException er) {
      // TODO Auto-generated catch block
      er.printStackTrace();
   }
    
    if (previousWindow.equals("album")) {
      
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Album.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      AlbumController albumController = loader.getController();
      
      
      albumController.setAlbum(album);      
      albumController.setClipboard(clipboard);
      albumController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();  
    } else {
      
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/User.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      UserController userController = loader.getController();
      userController.setClipboard(clipboard);
      
      userController.start(primaryStage);
      
      primaryStage.getScene().setRoot(root);
      primaryStage.show();   
    } 
    
  }
  
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
