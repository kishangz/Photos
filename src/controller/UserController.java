package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.User;

public class UserController {
  
  @FXML private Hyperlink logout;
  @FXML private Button tempButton;  

  @FXML
  private Button delete;
  
  @FXML
  private Button view;

  @FXML
  private Button add;
  
  @FXML
  private TextField search;

  @FXML
  private TableView<Album> table;
  
  @FXML
  TableColumn<Album, String> albumCol;

  @FXML
  TableColumn<Album, String> quantityCol;  
  
  @FXML
  TableColumn<Album, String> earliestCol;

  @FXML
  TableColumn<Album, String> latestCol;  
  
  @FXML private Label headerLabel;
  
  private Stage primaryStage;
  private User user;
  
  private HashMap<String, User> userList;
  
  private HashMap<String, Album> albumList;
  private ObservableList<Album> obsList = FXCollections.observableArrayList();  
  
  public void setUser(User user) {   
    this.user = user;  
    albumList = user.getAlbumList();
   
  } 

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(user.getName());
    
    Collection<Album> set = albumList.values();
    obsList.addAll(set);
    table.setItems(obsList);
    albumCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
    quantityCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNumPhotos()));
    
    table.getSelectionModel().selectFirst();
    
    Album selected = table.getSelectionModel().getSelectedItem();         
    if(selected != null) {
      delete.setDisable(false);
      view.setDisable(false);
    }
    
    table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
      @Override
      public void changed(ObservableValue<? extends Album> obs, Album oldValue, Album newValue) {
          if(newValue == null) {
                            
              delete.setDisable(true);
              view.setDisable(true);
              
          } else {
            delete.setDisable(false);
            view.setDisable(false);
          }
        }
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
  
  @FXML
  private void goToAlbum(ActionEvent ae) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Album.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AlbumController albumController = loader.getController();
    albumController.setAlbum(table.getSelectionModel().getSelectedItem());
    albumController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
  }
  
  @FXML
  void add(ActionEvent event) {
    TextInputDialog d = new TextInputDialog();
    d.setTitle("Add Album");
    d.setHeaderText("Add album");
    d.setContentText("Enter album name:");
    d.initOwner(primaryStage);
    
    Optional<String> result = d.showAndWait();
    if (result.isPresent()) {
      
      if (albumList.get(result.get()) != null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Duplicate Album");
        alert.setContentText("An album with that name already exists.");
        alert.showAndWait();
      } else {
        Album album = new Album(result.get());
        albumList.put(result.get(), album);
        obsList.add(album);
      }
    }
  }

  @FXML
  void delete(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to delete this album?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
        
        albumList.remove(table.getSelectionModel().getSelectedItem().getName());
        obsList.remove(table.getSelectionModel().getSelectedItem());
    }

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
