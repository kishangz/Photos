package model;

import java.util.HashMap;

public class User {  
  private String name;
  private HashMap<String, Album> albums;

  public User(String username) {
    
    this.name = username; 
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
  
  public void deleteAlbum(String albumName) {
    albums.remove(albumName);
  }
}
