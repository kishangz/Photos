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

/**
 * This is the Controller for Photo
 * It has all the functionality for Photo GUI
 * @author Kishan Zalora Eyob Tesfaye
 *
 */
public class PhotoController {
	
	/**
	 * Imageview to view an Image
	 */
	@FXML private ImageView imageView;
	
	/**
	 * TextArea to display the caption
	 */
	@FXML private TextArea isCaptioned;
	
	/**
     * Previous button
     */
	@FXML private Button prev;
	/**
     * Next button
     */
	@FXML private Button next;
	
	/**
	 * Logout button
	 */
	@FXML private Hyperlink logout;
	
	/**
	 * Back button
	 */
	@FXML private Button back; 
	
	/**
	 * Add tag Button
	 */
	@FXML private Button addTag; 
	
	/**
	 * Delete tag button
	 */
	@FXML private Button deleteTag; 
	
	/**
	 * Table column of Tag
	 */
	@FXML TableColumn<Tag, String> tableCol;
	
	/**
	 * Value column of Tag
	 */
	@FXML TableColumn<Tag, String> valueCol; 
	
	/**
	 * Tableview for Tag
	 */
	@FXML private TableView<Tag> table1;
	
	/**
	 * Date label
	 */
	@FXML private Label date;

	/**
	 * Primary Stage
	 */
	private Stage primaryStage;
	
	/**
	 * ArrayList of Photo
	 */
	private ArrayList<Photo> thisPhotoList;
	
	/**
	 * ArrayList of Tag
	 */
	private ArrayList<Tag> tagsList;
	
	/**
	 * Album Prev
	 */
	private Album prevAlbum;
	
	/**
	 * Observable list of Tag
	 */
	private ObservableList<Tag> obsList = FXCollections.observableArrayList();
	
	/**
	 * int i
	 */
	private int i = 0; 
	
	/**
	 * This is the Start method that initializes
	 * everything for the Photo interface
	 * @param primaryStage
	 * @param PhotoList
	 * @param caption
	 * @throws Exception
	 */
	public void start(Stage primaryStage, ArrayList<Photo> PhotoList, String caption) throws Exception {
		
		this.primaryStage = primaryStage;
		isCaptioned.setText(caption);
		
		isCaptioned.setEditable(false);
		obsList = FXCollections.observableArrayList(tagsList);
		table1.setItems(obsList);
		tableCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
		valueCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValue()));
		
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
	 * Sets tagList
	 * @param tagsList
	 */
	public void setTagsList(ArrayList<Tag> tagsList)
	{
		this.tagsList = tagsList;
	}
	
	/**
	 * Button that changes picture to previous and next
	 * @param album
	 */
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
		
		/**
		 * Set Image in view
		 * @param itsImage
		 */
	public void setImageInView(Image itsImage)
	{
		this.imageView.setImage(itsImage);
	}
	
	/**
	 * Back Button that takes the user back to the Album page
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
	    
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/view/Album.fxml"));
	    AnchorPane root = (AnchorPane)loader.load();
	    
	    AlbumController albumController = loader.getController();
	    
	    albumController.setUser(LoginController.currUser);
	    albumController.setAlbum(this.prevAlbum);
	    albumController.start(primaryStage);
	    
	    primaryStage.getScene().setRoot(root);
	    primaryStage.show();    
	    
	  }
	 
	 /**
	  * Adds tag for a selectes picture
	  * @param ae
	  * @throws IOException
	  */
	 @FXML private void addTag(ActionEvent ae) throws IOException{
		 
		 if(thisPhotoList.isEmpty())
			{
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Information Dialog");
				alert2.setHeaderText(null);
				alert2.setContentText("click on the image first");
				alert2.showAndWait();
			}
		 
			else
			{
				// Create the custom dialog.
				javafx.scene.control.Dialog<Pair<String, String>> dialog = new javafx.scene.control.Dialog<>();
				dialog.setTitle("Add Tag");
				dialog.setHeaderText("Tags must be in the form Type, Value. EX: location, Edison");

				// Set the button types.
				ButtonType loginButtonType = new ButtonType("Add Tag", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

				// Create the type and password value and fields.
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				TextField type = new TextField();
				type.setPromptText("Type");
				TextField value = new TextField();
				value.setPromptText("Value");

				grid.add(new Label("Tag Type:"), 0, 0);
				grid.add(type, 1, 0);
				grid.add(new Label("Tag Value:"), 0, 1);
				grid.add(value, 1, 1);
		
				dialog.getDialogPane().setContent(grid);
				
				// Request focus on the username field by default.
				Platform.runLater(() -> type.requestFocus());

				// Convert the result to a username-password-pair when the login button is clicked.
				dialog.setResultConverter(dialogButton -> {
				    if (dialogButton == loginButtonType) {
				        return new Pair<>(type.getText(), value.getText());
				    }
				    return null;
				});
				
				Optional<Pair<String, String>> result = dialog.showAndWait();
				//Add the tag
				result.ifPresent(usernamePassword -> {
					
				    String tagType = usernamePassword.getKey().trim();
				    String tagValue = usernamePassword.getValue().trim();
				   
				    
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
				    	
				    	Photo currAlbumString = thisPhotoList.get(i);
				    	model.Tag tempTag = new model.Tag(tagType, tagValue);

				    	if(currAlbumString.getTags().contains(tempTag))
				    	{
				    		Alert alert2 = new Alert(AlertType.INFORMATION);
				    		alert2.setTitle("Information Dialog");
				    		alert2.setHeaderText(null);
				    		alert2.setContentText("you added this tag a loooooong time ago");
				    		alert2.showAndWait();
				    	}
				    	else 
				    	{
				    		LoginController.currUser.getAlbumList().get(prevAlbum.getName()).getListOfPhotos().get(currAlbumString.getPhotoName()).addTag(tempTag);
				    		obsList.add(tempTag);
				    		table1.setItems(obsList);
				    		tableCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
				    		valueCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValue()));

				    	}
				    }			    
				});
			} 
	 }
	 
	 /**
	  * Deleted a selected tag for a given picture
	  * @param event
	  */
	  @FXML
	  void deleteTag (ActionEvent event) {
	    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure that you want to delete this album?");
	    alert.initOwner(primaryStage);
	            
	    Optional<ButtonType> result = alert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {  
	        
	    	Photo currAlbumString = thisPhotoList.get(i);
	    	Tag temp = table1.getSelectionModel().getSelectedItem();
	    	
    		LoginController.currUser.getAlbumList().get(prevAlbum.getName()).getListOfPhotos().get(currAlbumString.getPhotoName()).deleteTag(temp);
	        obsList.remove(table1.getSelectionModel().getSelectedItem());
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
