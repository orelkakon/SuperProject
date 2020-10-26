
public class Product {
	private String Name;
	private String SupplierID;
	private String CatalogID;
	private double Price;
	
	public Product(String N,String sup,String cat,double productPrice){
		Name=N;
		SupplierID=sup;
		CatalogID=cat;
		Price=productPrice;
	}
	//set of price 

	public String GetName() {
		return Name;
	}

	public String toStringA() {
		String Result="Product Name: "+Name+"  CatalogID: "+CatalogID+"  Price: "+Price;
		return Result;
		
	}

	public String GetCatalogID() {
		return CatalogID;
	}

	public double GetPrice() {
		return Price;
	}
	public String GetSuppID() {
		return SupplierID;
	}
}
