package model;

import java.util.ArrayList;

public class Album {
  
  private String name;
  private ArrayList<Photo> photos;
  private int numPhotos;

  public Album(String name) {
    
    this.name = name; 
    photos = new ArrayList<Photo>();
    numPhotos = 0;
    
  }
  
  public ArrayList<Photo> getPhotos() {
	  return photos;
	}
  
  public String getNumPhotos() {
    return "" + numPhotos;
  }
  
  public String getName() {
    return name;
  }
  
  public void addPhoto(Photo photo) {
    photos.add(photo);
    numPhotos++;
  }
  
  public void removePhoto(Photo photo) {
    photos.remove(photo);
    numPhotos--;
  }

}
