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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.Photos;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UsersList;

/**
 * This is the Controller for Album
 * It has all the functionality for Album GUI
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class AlbumController {
	
	/**
	 * Logout Button
	 */
  @FXML private Hyperlink logout;
  
  /**
   * To display the album name
   */
  @FXML private Label headerLabel;
  
  /**
   * Tile pane to display the pics in tiles
   */
  @FXML private TilePane pics;
  
  /**
   * VIew Button takes you to the photo
   */
  @FXML private Button view;

  /**
   * Delete button deletes the picture
   */
  @FXML private Button delete;

  /**
   * Adds An picture
   */
  @FXML private Button add;
  
  /**
   * Cuts a picture from an album
   */
  @FXML private MenuItem cut;

  /**
   * Copys a picture to the album
   */
  @FXML private MenuItem copy;

  /**
   * Pastes a picture in an album
   */
  @FXML private MenuItem paste;
  
  /**
   * Array of Photos
   */
  File[] stockPhoto;
    
  /**
   * Observable List of String
   */
  ObservableList<String> obsList; 
  
  /**
   * Sets the clipboard equals to null
   */
  private Photo clipboard = null;
  
  /**
   * Array list of File
   */
  ArrayList<File> fileStock = new ArrayList<File>(); 
  
  /**
   * Stack of Imageview 
   */
  private Stack<ImageView> imageStack = new Stack<ImageView>();
  /**
   * ArrayList of Photo
   */
  private ArrayList<Photo> PhotoList = new ArrayList<Photo>();
  
  /**
   * Caption
   */
  private String caption;
  
  /**
   * Back button
   */
  @FXML private Button back; 
  
  private Stage primaryStage;
  private Album album;
  User user;
  
  /**
   * Sets the Ablum
   * @param album
   */
  public void setAlbum(Album album) {   
    this.album = album;    
  }
  
  /**
   * Sets User
   * @param user
   */
  
  public void setUser(User user) {   
    this.user = user;         
  } 
  
  /**
   * Sets Clipboard
   * @param clipboard
   */
  public void setClipboard(Photo clipboard) {   
    this.clipboard = clipboard;    
  } 

  /**
   * This is the Start method that initializes
   * everything for the Album interface
   * @param primaryStage
   */
  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    headerLabel.setText(album.getName());
    	
    displayImages(album.getName());    	    

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
   * Adds an Picture to the album
   * @param event
   */
  @FXML
  void add(ActionEvent event) {
    FileChooser f = new FileChooser();
    File file = f.showOpenDialog(primaryStage);
    
    if (file != null) {
      caption = "";
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
      
      LoginController.currUser.getAlbumList().get(album.getName()).addPhoto(photo);
      
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(110);
      imageView.setFitWidth(110);
      
      imageView.setPickOnBounds(true);
      imageView.setUserData(photo.getPhotoName());
      
      pics.getChildren().addAll(imageView);
      
      PhotoList.add(photo);
      
      ObservableList<Node> childNode = pics.getChildren();

      for(int i = 0; i < childNode.size(); i++)
      {
          Node temp1 = childNode.get(i);
          temp1.setOnMouseClicked(Event -> {
              
              imageSelect((ImageView) temp1);

          });
      }
    }
  }

  /**
   * Deletes an picture from the album
   * @param event
   */
  @FXML
  void delete(ActionEvent event) {
	  
	  if(imageStack.isEmpty())
		{
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Information Dialog");
			alert2.setHeaderText(null);
			alert2.setContentText("click on the image first");
			alert2.showAndWait();
		}
		else
		{
			
			ImageView popedImage = imageStack.pop();

			int imageIndex = pics.getChildren().indexOf(popedImage);

			Photo imagePhoto = PhotoList.get(imageIndex);
			LoginController.currUser.getAlbumList().get(album.getName()).getListOfPhotos().remove(imagePhoto.getPhotoName());
			pics.getChildren().remove(popedImage);// delete from tile pane

			PhotoList.remove(popedImage);// delete from photoList (array)
			Album temp = LoginController.currUser.getAlbumList().get(album.getName());
			displayImages(album.getName());
			
		}	  

  }
  /**
   * Copies a picture from the Album
   * @param event
   */
  @FXML
  void copy(ActionEvent event) {
    
    if (!imageStack.isEmpty()) {
      clipboard = album.getListOfPhotos().get(imageStack.peek().getUserData());
    }
    
  }

  /**
   * Cuts an picture from the Album
   * @param event
   */
  @FXML
  void cut(ActionEvent event) {
    
    if (!imageStack.isEmpty()) {
      ImageView imageView = imageStack.pop();
      clipboard = album.getListOfPhotos().get(imageView.getUserData());
      album.removePhoto(clipboard.getPhotoName());
      pics.getChildren().remove(imageView);
      PhotoList.remove(clipboard);
    }
    
  }
  
  /**
   * Pastes an picture to the Album
   * @param event
   */
  @FXML
  void paste(ActionEvent event) {
    if (clipboard != null) {
      album.addPhoto(clipboard);
      
      Image image = new Image(clipboard.getFile().toURI().toString());
      
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(110);
      imageView.setFitWidth(110);
    
      imageView.setUserData(clipboard.getPhotoName());      
      
      pics.getChildren().addAll(imageView);
      
      PhotoList.add(clipboard);
      
      ObservableList<Node> childNode = pics.getChildren();

      for(int i = 0; i < childNode.size(); i++)
      {
          Node temp1 = childNode.get(i);
          temp1.setOnMouseClicked(Event -> {
              
              imageSelect((ImageView) temp1);

          });
      }
      
    }
    

  }
  
  /**
   * Views an selected picture from the album in an separate window
   * @param event
   * @throws IOException
   */
  @FXML
  void view(ActionEvent event) throws IOException {
	  

    if (imageStack.isEmpty()) {
      return;
      
    }


	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("/view/Photo.fxml"));
	  AnchorPane root = (AnchorPane)loader.load();
	  
	  ImageView popedImage = imageStack.pop();

	  int imageIndex = pics.getChildren().indexOf(popedImage);

	  Photo imagePhoto = PhotoList.get(imageIndex);

	  PhotoController listController = loader.getController();
	  
	  listController.setAlbum(this.album);
	  
	  listController.setImageInView(popedImage);
	  
	  listController.setTagsList(LoginController.currUser.getAlbumList().get(album.getName()).getListOfPhotos().get(imagePhoto.getPhotoName()).getTags());
	  
	  
	  
	  try {
			listController.start(primaryStage, PhotoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    primaryStage.getScene().setRoot(root);
	    primaryStage.show();
  }
   /**
    * This will display the images on an album clicked
    * @param album
    */
  private void displayImages(String album)
	{
		
		if(LoginController.currUser.getAlbumList().get(album).getListOfPhotos().isEmpty())
		{
			pics.getChildren().clear();
			PhotoList.clear();
			while(!imageStack.isEmpty())
			{
				imageStack.pop();
			}
		}
		else
		{
			
			pics.getChildren().clear();
			PhotoList.clear();
			while(!imageStack.isEmpty())
			{
				imageStack.pop();
			}
			
			HashMap<String, Photo> photos = LoginController.currUser.getAlbumList().get(album).getListOfPhotos();

			Set<Map.Entry<String, Photo>> photoSet = photos.entrySet();

			Iterator<Entry<String, Photo>> it = photoSet.iterator();
			while (it.hasNext()) 
			{
				Entry entry = it.next();
				String key = (String) entry.getKey();
				Photo keyPhoto = photos.get(key);
				File photoFile = keyPhoto.getFile();
				Image image = new Image(photoFile.toURI().toString());
				ImageView imageView= new ImageView();
				imageView.setUserData(keyPhoto.getPhotoName());
				imageView.setImage(image);
				imageView.setFitHeight(110);
				imageView.setFitWidth(110);
				pics.getChildren().addAll(imageView);
				PhotoList.add(keyPhoto);
			}
		}
		if(!pics.getChildren().isEmpty())
		{
			imageSelect((ImageView) pics.getChildren().get(0));
		}
		ObservableList<Node> childNode = pics.getChildren();
		
		for(int i = 0; i < childNode.size(); i++)
		{
			Node temp = childNode.get(i);
			temp.setOnMouseClicked(Event -> {
				
				imageSelect((ImageView) temp);
				
			});
		}
	}
  

  /**
   * This will Set the properties for an selected image
   * @param imageView
   */
  private void imageSelect(ImageView imageView) {
	if (!imageStack.isEmpty()) {
	  ImageView i = imageStack.pop();
	  i.setEffect(null);
    }
    imageStack.push(imageView);

	  DropShadow dropShadow = new DropShadow();
	  dropShadow.setColor(Color.DODGERBLUE);
	  imageView.setEffect(dropShadow);
}
  /**
   * Takes you back to the User window
   * @param ae
   * @throws IOException
   */
  @FXML
  private void back(ActionEvent ae) throws IOException {
    try {
      UsersList.save(LoginController.userList.getUserList());
   } catch (IOException er) {
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
  
  /**
   * Input an Tag=Value to see the pics with tags and dates
   * @param event
   * @throws IOException
   */
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
