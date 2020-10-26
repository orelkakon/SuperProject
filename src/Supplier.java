import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
	private String SupplierID;
	private String Payment;
	private String BankAccount;
	private String SupplierName;
	private String Area; 
	private List<ContactPerson> Contacts = new ArrayList<ContactPerson>();

	//create new Supplier object that represents a row in Supplier table in DataBase
	public Supplier(String SupplierID,String Payment,String BankAccount,String SupplierName,String Area,List<ContactPerson> Contacts){
		this.SupplierID = SupplierID;
		this.Payment = Payment;
		this.BankAccount = BankAccount;
		this.SupplierName = SupplierName;
		this.Area = Area;
		this.Contacts = Contacts;
	}

	public String GetArea(){
		return this.Area;
	}
	public boolean SaveToDB(DB db){
		String newData1 = "\"" + SupplierID + "\"" + "," +"\""+Payment + "\"" +"," + "\"" + BankAccount + "\""+ "," + "\"" + SupplierName + "\"" +"," + "\"" +Area+ "\"";
		String query1 = "INSERT into Supplier (SupplierID,Payment,BankAccount,SupplierName,Area) values ("+newData1+")" ;
		try {
			db.st = db.c.createStatement();
			db.st.executeUpdate(query1);
		} catch (Exception e) {
			System.out.println( "action for creating supplier card failed");
			return false;
		}
		for(int i=0;i<Contacts.size();i++){
			String newData2 = "\""  + Contacts.get(i).GetName() +"\"" + "," +"\""+ Contacts.get(i).GetPhone() + "\"" +"," + "\""+ SupplierID + "\"";
			String query2 = "insert INTO ContactPerson (Name,Phone,SupplierID) values ("+newData2+")" ;
			try {
				db.st = db.c.createStatement();
				db.st.executeUpdate(query2);
			}
			catch(Exception e){
				System.out.println( "action Saving Contacts failed");
				return false;
			}
		}
		System.out.println( "action complete successfully");
		return true;

	}
	//add static getters and setters on DB
	public static void getInformation(String SupplierID,DB db){
		String querry;
		ResultSet res=null;
		boolean Do = true;
		querry="SELECT * from Supplier where SupplierID = "+SupplierID;
		try {
			db.st = db.c.createStatement();
			res=db.st.executeQuery(querry);
			String SuppID=res.getString("SupplierID");
			String payment=res.getString("Payment");
			String Bank=res.getString("BankAccount");
			String Name=res.getString("SupplierName");
			String Area=res.getString("Area");
			System.out.println("Information:");
			System.out.println();
			System.out.println("SupplierID:"+SuppID+"   Payment:"+payment+"    BankAccount:"+Bank+"    Name:"+Name+ "    Area:"+Area);
		} catch (SQLException e) {
			System.out.println("Action Failed - illegal Supllier ID");
			Do=false;
		}
		querry="SELECT Name,Phone from ContactPerson WHERE SupplierID = "+SupplierID;
		try {
			db.st = db.c.createStatement();
			res=db.st.executeQuery(querry);
			if(Do){
				System.out.println();
				System.out.println("Contact list:");
				System.out.println();

			}
			while (res.next()) {
				String Name = res.getString("Name");
				String PN = res.getString("Phone");
				System.out.println(PN + "\t" + Name);
			}
		} catch (SQLException e) {
			System.out.println("Action Failed");	
		}


	}
	//check this (permissions)
	public static void setInformation(String SupplierID,DB db,String InformationType,String NewValue){
		String querry;
		querry="update Supplier set "+InformationType+" = " +NewValue+"where SupplierID="+SupplierID;
		try {
			db.st = db.c.createStatement();
			db.st.executeUpdate(querry);
			System.out.println("Action succeed");
		} catch (Exception e) {
			System.out.println("Action failed");
		}
	}

	//optional-to do also delete supplier and delete contact person
	public static void DeleteSupplier(String SupplierID,DB db){
		String querry="delete from Supplier where SupplierID= "+SupplierID;
		try {
			db.st = db.c.createStatement();
			db.st.executeUpdate(querry);
			System.out.println("Action succeed");
		} catch (SQLException e) {
			System.out.println("Action failed");
		}
	}
	public static void DeleteSupplier(String SupplierID,DB db,String Phone){
		String querry="delete from ContactPerson where SupplierID= "+SupplierID+" and Phone="+Phone;
		try {
			db.st = db.c.createStatement();
			db.st.executeUpdate(querry);
			System.out.println("Action succeed");
		} catch (SQLException e) {
			System.out.println("Action failed");
			//System.out.println(e.getMessage());
		}
	}

	public static void AddContactPersonSupplier(String suppID, String name, String phone, DB db) {
		String querry="select SupplierID from Supplier where SupplierID="+suppID;
		try {
			db.st=db.c.createStatement();
			ResultSet res=db.st.executeQuery(querry);
			String temp = res.getString("SupplierID");
			if(!temp.equals(suppID)){
				throw new SQLException();
			}
			querry="insert into ContactPerson (Name,Phone,SupplierID) values ("+name+","+phone+","+suppID+")";
			try {
				db.st = db.c.createStatement();
				db.st.executeUpdate(querry);
				System.out.println("Action succeed");
			} catch (SQLException e) {
				System.out.println("Action failed");
			}
		} catch (SQLException e) {
			System.out.println("supplier not found");
		}		

	}
}
