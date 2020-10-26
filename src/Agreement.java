import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Agreement {
	private String SupplierID;
	private String Days;
	private String QA;//assume that quantity agreement will be saved as a string in DB from comfortability
	private String WayDelivery;
	private List<Product> Products = new ArrayList<Product>();

	//create Agreement object that represents a row in Agreements Table 
	public Agreement(String SupplierID,String Days,/*QuantityAgreement*/String QA,String WayDelivery,List<Product> Products){
		this.SupplierID = SupplierID;
		this.Days = Days;
		this.QA = QA;
		this.WayDelivery = WayDelivery;
		this.Products = Products;
	}

	public void SaveToDB(DB db){
		String newData1 = "\"" + SupplierID + "\"" + "," +"\""+Days + "\"" +"," + "\"" + WayDelivery + "\""+","+"\"" + QA + "\"";
		String query1 = "insert into Agreements (SupplierID,Days,WayDelivery,QA) values ("+newData1+")" ;
		try { //need to send QA AND Products
			db.st = db.c.createStatement();
			db.st.executeUpdate(query1);
			
			for(int i=0;i<Products.size();i++){
				String newData2 = "\"" + SupplierID + "\"" + "," +"\""+ Products.get(i).GetName() + "\"" +","+"\"" +Products.get(i).GetCatalogID()+"\"" +","+ "\""+Products.get(i).GetPrice() + "\"";
				String query2 = "insert into ItemsOfAgreements (SupplierID,ProductName,CatalogID,Price) values ("+newData2+")" ;
				try {
					db.st = db.c.createStatement();
					db.st.executeUpdate(query2);
				}
				catch(Exception e){
					System.out.println("action Saving products of agreements failed");
					System.out.println(e.getMessage());

				}
			}
			System.out.println("action complete successfully");
		} catch (SQLException e) {
			System.out.println("action for creating Agreements failed");
			System.out.println(e.getMessage());
		}	
	}
}
