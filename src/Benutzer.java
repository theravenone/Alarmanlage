
public class Benutzer {
	
	private String userName;
	private String password;
	
	public Benutzer(String userName, String password){
		this.userName=userName;
		this.password=password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public boolean checkPassword(String testvalue){
		if(testvalue==this.password){
			return true;
		}else{
			return false;
		}
	}
	
	
}
