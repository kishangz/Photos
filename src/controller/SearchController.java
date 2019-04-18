package controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.UsersList;

/**
 * This is the Controller for Search
 * It has all the functionality for Search GUI
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class SearchController {
  
	/**
	 * Logout Button
	 */
  @FXML private Hyperlink logout;
  
  /**
   * Search Label
   */
  @FXML private Label searchLabel;
  
  /**
   * Back Button
   */
  @FXML private Button back; 
  
  /**
   * TilePane to display the Pictures
   */
  @FXML
  private TilePane pics;
  
  /**
   * Stack of IamgeView
   */
  private Stack<ImageView> imageStack = new Stack<ImageView>();

  /**
   * Stage primaryStage
   */
  private Stage primaryStage;

  /**
   * String Previous Window
   */
  private String previousWindow;
  
  /**
   * Date Picker end date
   */
  @FXML
  private DatePicker endDate;
  
  /**
   * Date picker from Date
   */
  @FXML
  private DatePicker fromDate;
  
  /**
   * Photo Clipboard
   */
  private Photo clipboard;

  /**
   * Album album
   */
  private Album album;
  
  /**
   * Arraylist of photo
   */
  private ArrayList<Photo> results = new ArrayList<Photo>();
  
  /**
   * Textfiedld for searching tags
   */
  @FXML private TextField tagSearchField;
  
  /**
   * This is the Start method that initializes
   * everything for the search interface
   * @param primaryStage
   */
  public void start(Stage primaryStage) {   
    this.primaryStage = primaryStage; 
    
    
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
   * Sets previous window
   * @param previousWindow
   */
  public void setPreviousWindow(String previousWindow) {   
    this.previousWindow = previousWindow;    
  } 
  
  /**
   * Sets album
   * @param album
   */
  public void setAlbum(Album album) {   
    this.album = album;    
  }  
  
  /**
   * sets clipboard
   * @param clipboard
   */
  public void setClipboard(Photo clipboard) {   
    this.clipboard = clipboard;    
  } 
  
  /**
   * Tag Search button
   * @param event
   */
  @FXML
  void tagSearch(ActionEvent event) {
    pics.getChildren().clear();
    results.clear();
    searchLabel.setText("Results for \"" + tagSearchField.getText() + "\":");
    String input = tagSearchField.getText();
    
    if (input.equals("")) {
      return;
    }
      
    String[] s = input.split("AND | and | OR | or");      
    
    String[] s2;
    s2 = s[0].split("=");
    if (s2.length != 2) {
      return;
    }
    String type1 = s2[0].trim();
    String value1 = s2[1].trim();   
    
    if (s.length == 1) {
      HashMap<String, Album> albumList = LoginController.currUser.getAlbumList();        
      Collection<Album> a = albumList.values();
      Iterator<Album> aIterator = a.iterator();
      
      while (aIterator.hasNext()) {
        
        Album album = aIterator.next();
        
        HashMap<String, Photo> photoList = album.getListOfPhotos();
        Collection<Photo> p = photoList.values();
        Iterator<Photo> pIterator = p.iterator();
        
        while (pIterator.hasNext()) {
          
          Photo photo = pIterator.next();
          Iterator<model.Tag> tIterator = photo.getTags().iterator();
          
          while (tIterator.hasNext()) {
            model.Tag tag = tIterator.next();
            
            if(type1.equalsIgnoreCase(tag.getType()) && value1.equalsIgnoreCase(tag.getValue())) {
              results.add(photo);
              break;
            }
          }
        }          
      }        
    }
    
    String type2;
    String value2;    
    
    if (s.length == 2) {
      s[1] = s[1].trim();
      s2 = s[1].split("=");
      if (s2.length != 2) {
        return;
      }
      type2 = s2[0];
      value2 = s2[1];
      
      
      if (input.contains(" and ") || input.contains(" AND ")) {
        HashMap<String, Album> albumList = LoginController.currUser.getAlbumList();        
        Collection<Album> a = albumList.values();
        Iterator<Album> aIterator = a.iterator();
        
        while (aIterator.hasNext()) {
          
          Album album = aIterator.next();
          
          HashMap<String, Photo> photoList = album.getListOfPhotos();
          Collection<Photo> p = photoList.values();
          Iterator<Photo> pIterator = p.iterator();
          
          while (pIterator.hasNext()) {
            
            Photo photo = pIterator.next();
            Iterator<model.Tag> tIterator = photo.getTags().iterator();
            
            int i = 0;
            while (tIterator.hasNext()) {
              model.Tag tag = tIterator.next();
              
              if((type1.equalsIgnoreCase(tag.getType()) && value1.equalsIgnoreCase(tag.getValue())) 
                  || (type2.equalsIgnoreCase(tag.getType()) && value2.equalsIgnoreCase(tag.getValue()))) {
                
                if (i == 1) {
                  results.add(photo);
                  break;
                }
                i++;
                
              }
            }
          }          
        }          
        
      } else if (input.contains(" or ") || input.contains(" OR ")) {
        HashMap<String, Album> albumList = LoginController.currUser.getAlbumList();        
        Collection<Album> a = albumList.values();
        Iterator<Album> aIterator = a.iterator();
        
        while (aIterator.hasNext()) {
          
          Album album = aIterator.next();
          
          HashMap<String, Photo> photoList = album.getListOfPhotos();
          Collection<Photo> p = photoList.values();
          Iterator<Photo> pIterator = p.iterator();
          
          while (pIterator.hasNext()) {
            
            Photo photo = pIterator.next();
            Iterator<model.Tag> tIterator = photo.getTags().iterator();              
        
            while (tIterator.hasNext()) {
              model.Tag tag = tIterator.next();
              
              if((type1.equalsIgnoreCase(tag.getType()) && value1.equalsIgnoreCase(tag.getValue())) 
                  || (type2.equalsIgnoreCase(tag.getType()) && value2.equalsIgnoreCase(tag.getValue()))) {
                  results.add(photo);
                  break;
                }             
                
              }
            }
          }          
        } 
       } 
    
    
      
    Iterator<Photo> rIterator = results.iterator();
    while (rIterator.hasNext()) 
    {
        Photo photo = rIterator.next();
        Image image = new Image(photo.getFile().toURI().toString());
        ImageView imageView= new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        pics.getChildren().addAll(imageView);        
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
   * Selects an image and displays it
   * @param imageView
   */
  
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
  
  /**
   * Search for the dates given the range
   * @param event
   */
  @FXML
  void dateSearch(ActionEvent event) {
    pics.getChildren().clear();
    results.clear();
    searchLabel.setText("Results for \"Photos taken between " + fromDate.getValue() + " and " + endDate.getValue() + "\":");
    
    if (fromDate.getValue() == null || endDate.getValue() == null) {
      return;
    }    
    
    
    HashMap<String, Album> albumList = LoginController.currUser.getAlbumList();        
    Collection<Album> a = albumList.values();
    Iterator<Album> aIterator = a.iterator();
    
    while (aIterator.hasNext()) {
      
      Album album = aIterator.next();
      
      HashMap<String, Photo> photoList = album.getListOfPhotos();
      Collection<Photo> p = photoList.values();
      Iterator<Photo> pIterator = p.iterator();
      
      while (pIterator.hasNext()) {
        
        Photo photo = pIterator.next();
        if ((photo.getDateFormat().compareTo(fromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) >= 0) 
            && (photo.getDateFormat().compareTo(endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) <= 0)) {
          results.add(photo);
        }
      }          
    }  
    
    Iterator<Photo> rIterator = results.iterator();
    while (rIterator.hasNext()) 
    {
        Photo photo = rIterator.next();
        Image image = new Image(photo.getFile().toURI().toString());
        ImageView imageView= new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(110);
        imageView.setFitWidth(110);
        pics.getChildren().addAll(imageView);        
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
   * Creates an album 
   * @param event
   * @throws IOException
   */
  @FXML
  void createAlbum(ActionEvent event) throws IOException {
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
        Iterator<Photo> rIterator = results.iterator();
        
        while (rIterator.hasNext()) {
          album.addPhoto(rIterator.next());
        }
        
        LoginController.currUser.getAlbumList().put(result.get(), album);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Album.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        AlbumController albumController = loader.getController();
       
        albumController.setAlbum(album);
        albumController.setClipboard(clipboard);
        albumController.start(primaryStage);
        
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
      }
    }   

  }
  
  /**
   * Goes back to previous window the user was in 
   * @param ae
   * @throws IOException
   */
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
