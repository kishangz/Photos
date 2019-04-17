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
  
  @FXML private TextField search;
  
  File[] stockPhoto;
  
  ListView<String> listView;   
  ObservableList<String> obsList; 
  
  private Photo clipboard = null;
  
  ArrayList<File> fileStock = new ArrayList<File>(); 
  
  private Stack<ImageView> imageStack = new Stack<ImageView>();
  
  private ArrayList<Photo> PhotoList = new ArrayList<Photo>();
  
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
  
  public void setClipboard(Photo clipboard) {   
    this.clipboard = clipboard;    
  } 

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
      
      Photo photo = new Photo(file.toURI().toString(), "", file);
      album.addPhoto(photo);
      Image image = new Image(photo.getFile().toURI().toString());
      
      LoginController.currUser.getAlbumList().get(album.getName()).addPhoto(photo);
      
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(110);
      imageView.setFitWidth(110);
      
      imageView.setPickOnBounds(true);
      
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
  
  @FXML
  void view(ActionEvent event) throws IOException {
	  
	  //JUST ADDED

	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("/view/Photo.fxml"));
	  AnchorPane root = (AnchorPane)loader.load();

	  PhotoController listController = loader.getController();
	  
	  listController.setImageInView(imageStack.pop().getImage());
	  
	  listController.setAlbum(this.album);
	  
	  try {
			listController.start(primaryStage, PhotoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    primaryStage.getScene().setRoot(root);
	    primaryStage.show();
	  
  }
   
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
  

  private void imageSelect(ImageView imageView) {
	if (!imageStack.isEmpty()) {
	  ImageView i = imageStack.pop();
	  i.setEffect(null);
    }
    imageStack.push(imageView);
	  //System.out.println("on stack");
	  DropShadow dropShadow = new DropShadow();
	  dropShadow.setColor(Color.DODGERBLUE);
	  imageView.setEffect(dropShadow);
}

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
    
    
    userController.start(primaryStage);
    
    primaryStage.getScene().setRoot(root);
    primaryStage.show();    
    
  }
  
  @FXML
  void search(ActionEvent event) throws IOException {
    if (search.getText() != null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/view/Search.fxml"));
      AnchorPane root = (AnchorPane)loader.load();
      
      SearchController searchController = loader.getController();
      
      searchController.setAlbum(album);
      searchController.setInput(search.getText());
      searchController.setPreviousWindow("album");
      searchController.setClipboard(clipboard);
      searchController.start(primaryStage);
      
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
