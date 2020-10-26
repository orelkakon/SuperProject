import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
	private String SupplierID;
	private ArrayList <Product> products;
	private String OrderDate;
	private ArrayList <String> amounts;


	public Order(String S,ArrayList<Product> p,ArrayList<String> a,String d){
		SupplierID=S;
		products=p;
		amounts=a;
		OrderDate=d;
	}


	public  void InsertProductToOrder(String SuppID,String CatalogID,String amount,DB db){
		String querry="SELECT * FROM ItemsOfAgreements WHERE SupplierID = "+SuppID+" AND CatalogID="+CatalogID;
		try {
			ResultSet  res= db.st.executeQuery(querry);
			while (res.next()) {
				String SupID = res.getString("SupplierID");
				String PNAme = res.getString("ProductName");
				String CID = res.getString("CatalogID");
				int price = res.getInt("Price");
				products.add(new Product(PNAme,SupID,CID,price));
				amounts.add(amount);
				}
			}
		catch(Exception e){
			System.out.println("Action failed,try again");
		}
	}
	public void PrintOrder(){
		System.out.println("SupplierID:  "+SupplierID);
		for(int i=0;i<products.size();i++){
			System.out.println(products.get(i).toStringA()+"  amount: "+amounts.get(i));
		}
		System.out.println("Date of order:  " +OrderDate);
	}

	public static void GetProductsOfSupplier(String SupplierID,DB db){
		String querry="select * from ItemsOfAgreements WHERE SupplierID = "+SupplierID;
		try {
			db.st = db.c.createStatement();
			ResultSet res= db.st.executeQuery(querry);
			while (res.next()) {
				String SupID = res.getString("SupplierID");
				String PNAme = res.getString("ProductName");
				String CID = res.getString("CatalogID");
				int price = res.getInt("Price");
				System.out.println(SupID + "      " + PNAme +
						"           " + CID + "       " + price);
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Action failed,Try again");
		}
	}
}
