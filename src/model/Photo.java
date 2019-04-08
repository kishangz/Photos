package model;

import java.sql.Date;

public class Photo {
  
  String caption;
  Date date;  

  public Photo(String caption) {
    this.caption = caption;
  }
  
  public String getCaption() {
    return caption;
  }
  
  public void setCaption(String caption) {
    this.caption = caption;
  }

}
