package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UsersList;

public class PhotoController {
	
	@FXML private ImageView imageView;
	
	@FXML private TextArea isCaptioned;
	
	/**
     * previous button
     */
	@FXML private Button prev;
	/**
     * next button
     */
	@FXML private Button next;
	
	@FXML private Hyperlink logout;
	
	//@FXML private ObservableList<Tag> obsList;
	
	@FXML private Button back; 
	
	@FXML private Button addTag; 
	
	@FXML private Button deleteTag; 
	
	@FXML TableColumn<Tag, String> tableCol;

	@FXML TableColumn<Tag, String> valueCol; 
	
	@FXML private TableView<Tag> table1;
	
	@FXML private Label date;
	
	Photo currPhoto = null;

	private Stage primaryStage;
	
	private ArrayList<Photo> thisPhotoList;
	
	private ArrayList<Tag> tagsList;
	
	private Album album;
	
	@FXML
    private Button editCaption;
	@FXML
    private Label captionLabel;

    @FXML
    private Label dateLabel;
    
    

    @FXML
    void editCaption(ActionEvent event) {

    }

	
	private ObservableList<Tag> obsList = FXCollections.observableArrayList();
	
	private int i = 0; 
	
	public void start(Stage primaryStage, ArrayList<Photo> PhotoList) throws Exception {
		
		this.primaryStage = primaryStage;
		
	
			thisPhotoList = PhotoList;
			i = thisPhotoList.indexOf(currPhoto);
			Photo keyPhoto = thisPhotoList.get(i);
			File photoFile = keyPhoto.getFile();
			Image image = new Image(photoFile.toURI().toString());
			imageView.setImage(image);
			captionLabel.setText(keyPhoto.getCaption());
			dateLabel.setText(keyPhoto.getDate());
			
			tagsList = keyPhoto.getTags();
			
			obsList.addAll(tagsList);
			table1.setItems(obsList);
	        tableCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
	        valueCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValue()));
		
		
		primaryStage.setOnCloseRequest(event -> {
		      
		      try {
		           UsersList.save(LoginController.userList.getUserList());
		       } catch (IOException er) {
		           // TODO Auto-generated catch block
		           er.printStackTrace();
		       }
		       
		    });
		
		
		
	}
	
	public void setTagsList(ArrayList<Tag> tagsList)
	{
		this.tagsList = tagsList;
	}

	public void setAlbum(Album album) {   
	    this.album = album;    
	  }
		
		@FXML void button (ActionEvent e) throws IOException
		{
			Button pressed = (Button)e.getSource();

			if(pressed == prev)
			{
				if(i == 0) {
		
				  i = thisPhotoList.size() - 1;
				}
				else
				{
					i = i - 1;
					
				}
				
				Photo keyPhoto = thisPhotoList.get(i);
                File photoFile = keyPhoto.getFile();
                Image image = new Image(photoFile.toURI().toString());
                imageView.setImage(image);
                captionLabel.setText(keyPhoto.getCaption());
                dateLabel.setText(keyPhoto.getDate());
                tagsList = keyPhoto.getTags();
                obsList.clear();
                obsList.addAll(tagsList);
                table1.setItems(obsList);
                table1.refresh();
			} else if (pressed == next) {
				if(i == thisPhotoList.size()-1)
				{
				
				  i = 0;
				}else {
					i = i + 1;
					
				}
				
				Photo keyPhoto = thisPhotoList.get(i);
                File photoFile = keyPhoto.getFile();
                Image image = new Image(photoFile.toURI().toString());
                //System.out.println(keyPhoto.toString());
                imageView.setImage(image);
                captionLabel.setText(keyPhoto.getCaption());
                dateLabel.setText(keyPhoto.getDate());
                tagsList = keyPhoto.getTags();
                obsList.clear();
                obsList.addAll(tagsList);
                table1.setItems(obsList);
                table1.refresh();
			}

		}

		
	public void setImageInView(ImageView imageView)
	{
	    currPhoto = album.getListOfPhotos().get(imageView.getUserData());
		
		
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
	    loader.setLocation(getClass().getResource("/view/Album.fxml"));
	    AnchorPane root = (AnchorPane)loader.load();
	    
	    AlbumController albumController = loader.getController();
	    
	    albumController.setUser(LoginController.currUser);
	    albumController.setAlbum(this.album);
	    albumController.start(primaryStage);
	    //albumController.start(primaryStage);
	    
	    primaryStage.getScene().setRoot(root);
	    primaryStage.show();    
	    
	  }
	 
	 
	 @FXML private void addTag(ActionEvent ae) throws IOException{
		 
	    TextInputDialog d = new TextInputDialog();
	    d.setTitle("Add Tag");
	    d.setHeaderText("Add Tag Type");
	    d.setContentText("Enter type:");
	    d.initOwner(primaryStage);
	    
	    Optional<String> result = d.showAndWait();
	    if (result.isPresent()) {
	      String tagType = result.get();
	      
	        TextInputDialog c = new TextInputDialog();
	        c.setTitle("Add Tag");
	        c.setHeaderText("Add Tag Value");
	        c.setContentText("Enter value:");
	        c.initOwner(primaryStage);
	        
	        Optional<String> result1 = c.showAndWait();
	        if (result1.isPresent()) {
	          String tagValue = result1.get();
	          
	          if(tagType.equalsIgnoreCase("") || tagValue.equalsIgnoreCase(""))
              {
                  Alert alert2 = new Alert(AlertType.INFORMATION);
                  alert2.setTitle("Information Dialog");
                  alert2.setHeaderText(null);
                  alert2.setContentText("How lazy!? You gotta input both fields man!!");
                  alert2.showAndWait();
              }
              else
              {
                  
                  
                  model.Tag tempTag = new model.Tag(tagType, tagValue);
                  

                  if(tagsList.contains(tempTag))
                  {
                      Alert alert2 = new Alert(AlertType.INFORMATION);
                      alert2.setTitle("Information Dialog");
                      alert2.setHeaderText(null);
                      alert2.setContentText("you added this tag a loooooong time ago");
                      alert2.showAndWait();
                  }
                  else 
                  {
                      tagsList.add(tempTag);
                      obsList.clear();
                      obsList.addAll(tagsList);
                      table1.setItems(obsList);
                      table1.refresh();
                      
                      

                  }
              }
	          
	          
	          
	          
	          
	          
	          
	        }
	        
	        
	    }
		

				
				
				
			
				
				
				
				
		
				   
				    
				    			    
			
			} 
	 
	 
	  @FXML
	  void deleteTag (ActionEvent event) {
	    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to delete this tag?");
	    alert.initOwner(primaryStage);
	            
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {  
	        
	    	Photo currAlbumString = thisPhotoList.get(i);
	    	Tag temp = table1.getSelectionModel().getSelectedItem();
	    	
    		LoginController.currUser.getAlbumList().get(album.getName()).getListOfPhotos().get(currAlbumString.getPhotoName()).deleteTag(temp);
	        obsList.remove(table1.getSelectionModel().getSelectedItem());
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
