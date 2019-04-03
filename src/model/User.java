package model;

import java.util.HashMap;

public class User {
  
  private String name;
  HashMap<String, Album> albums;

  public User(String name) {
    
    this.name = name; 
    albums = new HashMap<>();
  }
  
  public String getUsername() {
    return name;
  }

}
