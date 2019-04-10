package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
  
  private String name;
  ArrayList<Photo> photos;

  public Album(String name) {
    
    this.name = name; 
    photos = new ArrayList<>();
  }
  
  public List<Photo> getPhotos() {
	  return photos;
	}
  
  public String getName() {
    return name;
  }

}
