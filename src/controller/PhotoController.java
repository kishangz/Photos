package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
	
	@FXML ListView<Tag> tagView;
	
	@FXML private ObservableList<Tag> obsList;
	
	@FXML private Button back; 

	private Stage primaryStage;
	
	private ArrayList<Photo> thisPhotoList;
	
	private Album prevAlbum;
	
	private int i = 0; 
	
	public void start(Stage primaryStage, ArrayList<Photo> PhotoList) throws Exception {
		
		this.primaryStage = primaryStage;
		
		if(PhotoList.isEmpty()) {
			System.out.println("its empty boss");
		}
		else {
			thisPhotoList = PhotoList;
			/**Photo keyPhoto = thisPhotoList.get(i);
			File photoFile = keyPhoto.getFile();
			Image image = new Image(photoFile.toURI().toString());
			imageView.setImage(image);
			//System.out.print(thisPhotoList.size());**/
			
		
		}
	}
	
	public void setAlbum(Album album) {   
	    this.prevAlbum = album;    
	  }
		
		@FXML void button (ActionEvent e) throws IOException
		{
			Button pressed = (Button)e.getSource();

			if(pressed == prev)
			{
				if(i == 0)
				{
					/**Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Information Dialog");
					alert2.setHeaderText(null);
					alert2.setContentText("How much back do you wanna go?!");
					alert2.showAndWait();**/
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
			}
			else if(pressed == next)
			{
				if(i == thisPhotoList.size()-1)
				{
					/**Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Information Dialog");
					alert2.setHeaderText(null);
					alert2.setContentText("Alright thats it! Thats the end of your images!");
					alert2.showAndWait();**/
				  i = 0;
				}
				else
				{
					i = i + 1;
					
				}
				
				Photo keyPhoto = thisPhotoList.get(i);
                File photoFile = keyPhoto.getFile();
                Image image = new Image(photoFile.toURI().toString());
                //System.out.println(keyPhoto.toString());
                imageView.setImage(image);
			}

		}

		
	public void setImageInView(Image itsImage)
	{
		this.imageView.setImage(itsImage);
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
	    albumController.setAlbum(this.prevAlbum);
	    albumController.start(primaryStage);
	    //albumController.start(primaryStage);
	    
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
