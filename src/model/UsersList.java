package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UsersList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, User> userList;
	
	public static final String storeDir = "data";
	
	public static final String storeFile = "users";
	
	public UsersList() {
		userList = new HashMap<String, User>();
	}
	
	public void addUser(String key, String userName) {
		
		User temp = new User(key, userName);
		userList.put(key, temp);
	}
	
	public void deleteUser(String key) {
		userList.remove(key);
	}
	
	public User getUser(String key) {
		return userList.get(key);
	}
	
	public HashMap<String, User> getUserList(){
		return userList;
	}
	
	public void setUserList(HashMap<String, User> usersList) {
		
		this.userList = usersList;
	}
	
	public ArrayList<String> getListOfUsers(){
		
		Iterator<String> it = userList.keySet().iterator();
		String key;
		ArrayList<String> u = new ArrayList<String>();
		while(it.hasNext()) {
			key = it.next();
			String user = userList.get(key).toString();
			u.add(user.substring(0, user.indexOf(' ')));
		}
		return u;
	}
	
	public void read() throws FileNotFoundException, IOException, ClassNotFoundException{
		
		File file = new File(storeDir + File.separator + storeFile);
		
		try {
			if(file.length() == 0) {
				file.createNewFile();
			}
			else {
				ObjectInputStream tempIn = new ObjectInputStream(new FileInputStream(file));
				userList = (HashMap<String, User>) tempIn.readObject();
				
			}
		}catch(IOException e){
			
		}
		
	}
	
	public static void save(HashMap<String, User> userList) throws IOException
	{
		ObjectOutputStream tempOut = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		tempOut.writeObject(userList);
	}
	
	
}
