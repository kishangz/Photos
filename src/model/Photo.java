package model;

import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Photo {
  
  String caption;
  //Date date;
  
  private transient Image image;
  private transient ObservableList<Tag> tags = FXCollections.observableArrayList();
  
  private Calendar date = Calendar.getInstance();
  
  public Image getImage() {
	  return image;
	  
  }
  
  public Calendar getDate() {
	  return date;
	}
  
  public ObservableList<Tag> getTags() {
	  return tags;
	}
  
  public String getCaption() {
	  return caption;
	}

}
