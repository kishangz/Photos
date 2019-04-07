package model;

import java.util.HashMap;

public class User {
  
  private String name;
  HashMap<String, Album> albums;

  public User(String name) {
    
    this.name = name; 
    albums = new HashMap<>();
  }
  
  public String getName() {
    return name;
  }
  
  public Album getAlbum(String albumName) {
    return albums.get(albumName);
  }
  
  public void addAlbum(String albumName) {
    albums.put(albumName, new Album(albumName));
  }
}
