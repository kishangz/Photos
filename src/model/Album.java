package model;

import java.util.ArrayList;

public class Album {
  
  private String name;
  ArrayList<Photo> photos;

  public Album(String name) {
    
    this.name = name; 
    photos = new ArrayList<>();
  }
  
  public String getName() {
    return name;
  }

}
