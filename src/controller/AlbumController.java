package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UsersList;

public class AlbumController {
  
  @FXML private Hyperlink logout;
  
  @FXML private Label headerLabel;
  
  @FXML private TilePane pics;
  
  @FXML
  private Button view;

  @FXML
  private Button delete;

  @FXML
  private Button add;
  
  @FXML
  private MenuItem cut;

  @FXML
  private MenuItem copy;

  @FXML
  private MenuItem paste;
  
  private Photo clipboard = null;
  
  File[] stockPhoto;
  
  ArrayList<File> fileStock = new ArrayList<File>(); 
  
  private Stack<ImageView> imageStack = new Stack<ImageView>();
  
  private ArrayList<Photo> PhotoList = new ArrayList<Photo>();
  
  private ArrayList<HBox> photoHBoxes = new ArrayList<HBox>();
  
  @FXML private Button back; 
  
  private Stage primaryStage;
  private Album album;
  User user;
  
  private ObservableList<HBox> obsList = FXCollections.observableArrayList();  
  @FXML private ListView<HBox> listView;
  
  public void setAlbum(Album album) {   
    this.album = album;    
  }  
  
  public void setClipboard(Photo clipboard) {   
    this.clipboard = clipboard;    
  } 
  
  public void setUser(User user) {   
    this.user = user;         
  } 

  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(album.getName());
    
    displayImages(album.getName());  
    
    listView.getSelectionModel().selectFirst();
    
    HBox selected = listView.getSelectionModel().getSelectedItem();         
    if(selected != null) {
      delete.setDisable(false);
      view.setDisable(false);
      
    }
    
    listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HBox>() {
      @Override
      public void changed(ObservableValue<? extends HBox> obs, HBox oldValue, HBox newValue) {
          if(newValue == null) {
                            
              delete.setDisable(true);
              view.setDisable(true);
              
              
          } else {
            delete.setDisable(false);
            view.setDisable(false);
            
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
  
public void startA(Album thisAlbum) {
	  
  }
  
  public static void closeAlbum() {
	}
  
  @FXML
  void add(ActionEvent event) {
    FileChooser f = new FileChooser();
    File file = f.showOpenDialog(primaryStage);
    
    if (file != null) {
      
      String caption = "";
      TextInputDialog d = new TextInputDialog();
      d.setTitle("Add Caption");
      d.setHeaderText("Add caption");
      d.setContentText("Enter photo caption:");
      d.initOwner(primaryStage);
      
      Optional<String> result = d.showAndWait();
      if (result.isPresent()) {
        caption = result.get();
      }      
      
      Photo photo = new Photo(file.toURI().toString(), caption, file);
      album.addPhoto(photo);
      Image image = new Image(photo.getFile().toURI().toString());
      
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(110);
      imageView.setFitWidth(110);
      HBox hBox = new HBox(10.0);
      hBox.setUserData(photo.getPhotoName());
      hBox.getChildren().add(imageView);
      Label label = new Label(photo.getCaption());
      hBox.getChildren().add(label);
      
      obsList.add(hBox);
      listView.scrollTo(hBox);
    }
    
        

  }

  @FXML
  void delete(ActionEvent event) {

    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to delete this photo?");
    alert.initOwner(primaryStage);
            
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {  
      
            
        Photo r =  album.getListOfPhotos().get((String) listView.getSelectionModel().getSelectedItem().getUserData());     
        album.removePhoto(r.getPhotoName());
        obsList.remove(listView.getSelectionModel().getSelectedItem());
        
    }
  }
  
  @FXML
  void view(ActionEvent event) {

  }
  
  
  private void displayImages(String album)
	{
    
    Iterator<Photo> photos = LoginController.currUser.getAlbumList().get(album).getListOfPhotos().values().iterator();
    
    while(photos.hasNext())
    {
      Photo photo = photos.next();
        
        Image image = new Image(photo.getFile().toURI().toString());
        ImageView imageView= new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        HBox hBox = new HBox(10.0);
        hBox.setUserData(photo.getPhotoName());
        hBox.getChildren().add(imageView);
        Label label = new Label(photo.getCaption());
        hBox.getChildren().add(label);
        photoHBoxes.add(hBox);

    }
    
    obsList.addAll(photoHBoxes);
    listView.setItems(obsList);
		
		
		
	}
  
  @FXML
  void copy(ActionEvent event) {
    HBox selected = listView.getSelectionModel().getSelectedItem();
    if (selected != null) {
      clipboard = album.getListOfPhotos().get((String) listView.getSelectionModel().getSelectedItem().getUserData());
    }
    
  }

  @FXML
  void cut(ActionEvent event) {
    HBox selected = listView.getSelectionModel().getSelectedItem();
    if (selected != null) {
      clipboard = album.getListOfPhotos().get(listView.getSelectionModel().getSelectedItem().getUserData());
      album.removePhoto((String) listView.getSelectionModel().getSelectedItem().getUserData());
      obsList.remove(listView.getSelectionModel().getSelectedItem());
      listView.refresh();
    }
    
  }
  
  
  @FXML
  void paste(ActionEvent event) {
    if (clipboard != null) {
      album.addPhoto(clipboard);
      
      Image image = new Image(clipboard.getFile().toURI().toString());
      
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(110);
      imageView.setFitWidth(110);
      HBox hBox = new HBox(10.0);
      hBox.setUserData(clipboard.getPhotoName());
      hBox.getChildren().add(imageView);
      Label label = new Label(clipboard.getCaption());
      hBox.getChildren().add(label);
      
      obsList.add(hBox);
      listView.refresh();
      listView.scrollTo(hBox);
      
    }
    

  }
  

  private void imageSelect(ImageView imageView) {
	// TODO Auto-generated method stub
	  
	  imageStack.push(imageView);
	  int imageDex = pics.getChildren().indexOf(imageView);
	  Photo imagePhoto = PhotoList.get(imageDex);
	
}

  @FXML
  private void back(ActionEvent ae) throws IOException {
    try {
      UsersList.save(LoginController.userList.getUserList());
   } catch (IOException er) {
      // TODO Auto-generated catch block
      er.printStackTrace();
   }
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/User.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    UserController userController = loader.getController();
    userController.setClipboard(clipboard);
    
    userController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();    
    
  }
  
  @FXML
  void search(ActionEvent event) throws IOException {
    
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/view/Search.fxml"));
    AnchorPane root = (AnchorPane)loader.load();
    
    SearchController searchController = loader.getController();
    
    searchController.setAlbum(album);
    searchController.setPreviousWindow("album");
    searchController.setClipboard(clipboard);
    searchController.start(primaryStage);
    
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
