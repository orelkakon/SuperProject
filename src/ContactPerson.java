
public class ContactPerson {
	private String Name;
	private String Phone;
	
	//constructor
	public ContactPerson(String Phone, String Name){
		this.Name=Name;
		this.Phone=Phone;
	}
	
	//getters
	public String GetName(){
		return this.Name;
	}
	public String GetPhone(){
		return this.Phone;
	}
}
