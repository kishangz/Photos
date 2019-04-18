package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

import app.Photos;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UsersList;

/**
 * This is the Controller for User
 * It has all the functionality for User GUI
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class UserController {
  
	/**
	 * Logout Button
	 */
  @FXML private Hyperlink logout;
  
  /**
   * rename Button
   */
  @FXML private Button renameButton;  

  /**
   * Delete Button
   */
  @FXML private Button delete;
  
  /**
   * View Button
   */
  @FXML private Button view;

  /**
   * Add Button
   */
  @FXML private Button add;
  
  /**
   * Table View of table
   */
  @FXML private TableView<Album> table;
  
  /**
   * Album Column of Album
   */
  @FXML TableColumn<Album, String> albumCol;

  /**
   * Quantity Column of Album
   */
  @FXML TableColumn<Album, String> quantityCol;  
  
  /**
   * Earliest Date Column of Album
   */
  @FXML TableColumn<Album, String> earliestCol;

  /**
   * Latest Date Column of Album
   */
  @FXML TableColumn<Album, String> latestCol;  
  
  /**
   * Photo clipboard
   */
  private Photo clipboard = null;
  
  /*
   * Label for header
   */
  @FXML private Label headerLabel;
  
  /**
   * Tilepane for pictures
   */
  @FXML private TilePane pics; 
  
  /**
   * Stage PrimaryStage
   */
  private Stage primaryStage;
  
  /**
   * User user
   */
  private User user;
  
  /**
   * File Array of photos
   */
  File[] stockPhoto;
  
  /**
   * Listview of String
   */
  ListView<String> listView;
  
  /**
   * Observabke List of String
   */
  ObservableList<String> obList; 
  
  /**
   * ArrayLsit of file
   */
  ArrayList<File> fileStock = new ArrayList<File>(); 
  
  /**
   * Stack of ImageView
   */
  private Stack<ImageView> imageStack = new Stack<ImageView>();
  
  /**
   * ArrayList of Photo
   */
  private ArrayList<Photo> PhotoList = new ArrayList<Photo>();
  
  /**
   * HashMap of User
   */
  private HashMap<String, User> userList;
  
  /**
   * HashMap of Album
   */
  private HashMap<String, Album> albumList; 
  
  /**
   * Observable List of Album
   */
  private ObservableList<Album> obsList = FXCollections.observableArrayList();
 
  /**
   * Sets Clipboard
   * @param clipboard
   */
  public void setClipboard(Photo clipboard) {   
    this.clipboard = clipboard;    
  } 
  
  /**
   * Sets User List
   * @param userList
   */
  public void setUserList(HashMap<String, User> userList) {   
	    this.userList = userList;    
	  } 

  /**
   * This is the Start method that initializes
   * everything for the User interface
   * @param primaryStage
   */
  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(LoginController.currUser.getName());
    albumList = LoginController.currUser.getAlbumList();
    
    Collection<Album> set = albumList.values();
    obsList.addAll(set);
    table.setItems(obsList);
    albumCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
    quantityCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAlbumSize()));
    earliestCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEarliestDate()));
    latestCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLatestDate()));
    
    table.getSelectionModel().selectFirst();
    
    Album selected = table.getSelectionModel().getSelectedItem();         
    if(selected != null) {
      delete.setDisable(false);
      view.setDisable(false);
      renameButton.setDisable(false);
    }
    
    table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
      @Override
      public void changed(ObservableValue<? extends Album> obs, Album oldValue, Album newValue) {
          if(newValue == null) {
                            
              delete.setDisable(true);
              view.setDisable(true);
              renameButton.setDisable(true);
              
          } else {
            delete.setDisable(false);
            view.setDisable(false);
            renameButton.setDisable(false);
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
    
    if(LoginController.currUser.getUserKey().equalsIgnoreCase("stock") && LoginController.counter == 0)
	{

		File directory = new File("IMG");

		if(directory.isDirectory())
		{
			stockPhoto = directory.listFiles();
		}

		for(File f: stockPhoto)
		{
			fileStock.add(f);
		}

		if (LoginController.currUser.getAlbumList().get("stock") == null)
		{
		  String albumName = "stock";
          Album stock = new Album(albumName);
          LoginController.currUser.getAlbumList().put(albumName, stock);
          
		  
		  for(int i = 0; i < fileStock.size(); i++)
	        {
	            Photo tempPhoto = new Photo(fileStock.get(i).toURI().toString(), "", fileStock.get(i));
	            
	            LoginController.currUser.getAlbumList().get("stock").addPhoto(tempPhoto);

	        }
		    obsList.add(stock);
	        
	        LoginController.counter++;
			
		}
	}
  }
  
  /**
   * Goes to the album of the user logged in
   * @param ae
   * @throws IOException
   */
  @FXML
  private void goToAlbum(ActionEvent ae) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Album.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    AlbumController albumController = loader.getController();
    
    albumController.setUser(user);
    albumController.setAlbum(table.getSelectionModel().getSelectedItem());
    albumController.setClipboard(clipboard);
    albumController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  
  }
  
  /**
   * Button that Adds an album 
   * @param event
   */
  @FXML
  void add(ActionEvent event) {
    TextInputDialog d = new TextInputDialog();
    d.setTitle("Add Album");
    d.setHeaderText("Add album");
    d.setContentText("Enter album name:");
    d.initOwner(primaryStage);
    
    Optional<String> result = d.showAndWait();
    if (result.isPresent()) {
      
      if (LoginController.currUser.getAlbumList().get(result.get()) != null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Duplicate Album");
        alert.setContentText("An album with that name already exists.");
        alert.showAndWait();
      } else {
        Album album = new Album(result.get());
        LoginController.currUser.getAlbumList().put(result.get(), album);
        obsList.add(album);
      }
    }
  }

  /**
   * Delete button that asks the user if they 
   * wish to delete a picture before deleting it 
   * @param event
   */
  @FXML
  void delete(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to delete this album?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
        
    	LoginController.currUser.getAlbumList().remove(table.getSelectionModel().getSelectedItem().getName());
        obsList.remove(table.getSelectionModel().getSelectedItem());
    }

  }
  
  /**
   * Renames an album and check if an 
   * album with that name already exists
   * @param event
   */
  @FXML
  void rename(ActionEvent event) {
    TextInputDialog d = new TextInputDialog();
    d.setTitle("Rename Album");
    d.setHeaderText("Rename album");
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
        
        String oldName = table.getSelectionModel().getSelectedItem().getName();
        obsList.get(obsList.indexOf(table.getSelectionModel().getSelectedItem())).setAlbumName(result.get());;
        albumList.get(oldName).setAlbumName(result.get());
        albumList.put(result.get(), albumList.get(oldName));
        albumList.remove(oldName);
        table.refresh();
        
      }
    }
  }

  /**
   * Search Button to search for tags and dates
   * @param event
   * @throws IOException
   */
  @FXML
  void search(ActionEvent event) throws IOException {
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Search.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    SearchController searchController = loader.getController();
    
    
    searchController.setClipboard(clipboard);
    searchController.setPreviousWindow("user");
    searchController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();  

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
