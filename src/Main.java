import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Main {
	public static void main(String[]args){
		Scanner input = new Scanner(System.in);
		final String  PassWord = "0000";  
		DB db = new DB();
		System.out.println("Wellcome to the Softwre of SUPER LI");
		System.out.println("please insert Enter to go on OR insert Exit to leave");
		String start = input.nextLine();
		boolean T = false;
		boolean just = false;
		String x = "0";
		while(!start.equals("Exit")){
			if(start.equals("Enter")){
				while(x.equals("0")){
					if(x.equals("Exit")){
						System.exit(0);
					}
					if(!T){
						System.out.println("To CREATE a new supplier card insert 1");
						System.out.println("To CREATE order insert 2");
						System.out.println("To GET information about a supplier insert 3");
						System.out.println("To UPDATE information about a supplier insert 4");
						System.out.println("To DELETE a supplier or contact person of a supplier insert 5");
						System.out.println("To ADD contact person of a supplier insert 6");
						T=true;
					}

					x = input.nextLine();
					switch(x){
					case "1":{
						just=true;
						System.out.println("Enter PASSWORD please for permission");
						String pass=input.nextLine();
						if(!PassWord.equals(pass)){
							System.out.println("The password wrong, Action failed");
							break;
						}
						System.out.println("Enter SupplierID ,Terms of payment ,Bank Account number ,Supplier's name ,Area of Supplier");
						ArrayList<ContactPerson> LCP=new ArrayList<ContactPerson>();
						String SupplierID = input.nextLine();
						String Payment = input.nextLine();
						String BankAccount = input.nextLine();
						String Name = input.nextLine();
						String Area = input.nextLine();
						System.out.println("Enter Supplier's contact Person Name, Phone Number");
						String CPName=input.nextLine();
						String PhoneNumber=input.nextLine();
						ContactPerson cp=new ContactPerson(PhoneNumber, CPName);/////////////////////////////////////////////////////////
						LCP.add(cp);
						System.out.println("Enter yes if you want ADD another contact person ,OR no if you dont want");
						String next=input.nextLine();
						while(next.equals("yes")){
							CPName=input.nextLine();
							PhoneNumber=input.nextLine();
							LCP.add(new ContactPerson(PhoneNumber,CPName));
							System.out.println("Enter yes if you want ADD another contact person, OR no if you dont want");
							next=input.nextLine();
						}
						Supplier NewSupp = new Supplier(SupplierID,Payment,BankAccount,Name,Area,LCP);/////////////////////////////
						boolean does = NewSupp.SaveToDB(db);/////////////////////////////////////////////
						if(does){
							System.out.println("After finishing successfully to create a new supplier card, you MUST right now to createing an agreement with the supplier");
							System.out.println();
							System.out.println("Enter the days of delivery (example ABD represents sunday, monday, wedensday) OR insert none");
							String Days=input.nextLine();
							System.out.println("Enter the delivery way");
							String DeliveryWay=input.nextLine();
							System.out.println("Now you start to add products to Agreement");
							System.out.println("for FINISH the process write STOP to keep going write KEEP ");
							String ProductName;
							String ProductID;
							double ProductPrice;
							ArrayList<Product> ProductList= new ArrayList<Product>();
							String t = input.nextLine();
							boolean keep = true;
							if(t.equals("STOP")){
								keep=false;
							}
							while(keep){
								System.out.println("Enter the product Name , CatalogID and Price");
								ProductName=input.next();
								ProductID=input.next();
								try{
									ProductPrice=Double.parseDouble(input.next());
									if(ProductPrice<1)
										throw new Exception();
								}catch(Exception e){
									ProductPrice=0;
									System.out.println("Product price MUST be a real number greater than 0");
									System.out.println("You Entered illegal input so the price now is 1");
								}
								ProductList.add(new Product(ProductName,SupplierID,ProductID,ProductPrice));
								System.out.println();
								System.out.println("To finish write STOP, To keep going write KEEP");
								t=input.next();
								if(t.equals("STOP")){
									keep=false;
								}
							}

							String quantityAgreement="";
							System.out.println("After finishing enter the products,Enter yes to insert quantity agreement,or no to continue");
							String m=input.next();
							if(m.equals("yes")){
								System.out.println("Enter PruductName , CatalogID , Amount and Price.  "+"if you finish to write the agreement write STOP");
								boolean stop=false;
								String tmp="Quantity Agreement:"+"\n";
								while(!stop){
									String p=input.next();
									if(!p.equals("STOP")){
									String c=input.next();
									String a=input.next();
									String price=input.next();
									tmp="PruductName: "+p + " CatalogID: "+c+ " Amount: "+a+" Price: "+price;
									quantityAgreement=quantityAgreement+tmp+"\n";
									System.out.println("Enter PruductName , CatalogID , Amount and Price.   "+"if you finish to write the agreement write STOP");
									
									}
									else{
										stop=true;
									}
								}
							}
							Agreement agreement=new Agreement(SupplierID,Days,quantityAgreement,DeliveryWay,ProductList);
							agreement.SaveToDB(db);
														
							}
						break;	
					}
					case "2":{
						System.out.println("Enter SupplierID that you want to see his products");
						String SupplierID=input.nextLine();
						System.out.println("SupplierID:"+" ProductName:"+" CatalogID:"+ " Price:"  );
						Order.GetProductsOfSupplier(SupplierID, db);
						System.out.println("When you want to finish the order Enter STOP , To keep going push the button Enter");
						ArrayList<String> p = new ArrayList<String>();
						ArrayList<String> s = new ArrayList<String>();
						while(!input.nextLine().equals("STOP")){
							System.out.println("Enter catalogID of product, and the amount from him");
							String CatalogID=input.nextLine();
							String amount=input.nextLine();
							//need to check if the product not exist(catalogID)
							p.add(CatalogID);
							s.add(amount);
							System.out.println("TO finish Enter STOP , else push button Enter");
						}
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						String d=dateFormat.format(date);
						System.out.println("The Order Info:");
						for(int i=0;i<p.size();i++){
							System.out.println("CataolgID: "+ p.get(i) + "     " +"Amount: "+ s.get(i) );
						}
						System.out.println(d);
						break;
					}		
					case "3":{
						System.out.println("Enter The Supplier ID");
						String SuppID=input.nextLine();
						Supplier.getInformation(SuppID, db);

						break;
					}
					case "4":{
						System.out.println("Enter SupplierID");
						String SuppID=input.nextLine();
						System.out.println("Enter A to update Terms of Payment ,B to update Bank Account, C to update Supplier Name, D to update Area Sells");
						System.out.println("(Only one value can be updated at a time)");
						String InformationType=input.nextLine();
						if(InformationType.equals("A"))
							InformationType="Payment";
						else if(InformationType.equals("B"))
							InformationType="BankAccount";
						else if(InformationType.equals("C"))
							InformationType="SupplierName";
						else if(InformationType.equals("D"))
							InformationType="Area";
						else{
							System.out.println("illegal input - you return to option stage");
							continue;
						}
						System.out.println("Enter the new value");
						String NewValue="\""+input.nextLine()+ "\"";
						Supplier.setInformation(SuppID, db, InformationType, NewValue);


						break;
					}
					case "5":{
						System.out.println("Enter 1 to delete Supplier, OR 2 to delete a contact person of a supplier");
						String Case=input.nextLine();
						if(Case.equals("1")){
							System.out.println("Enter Supplier ID");
							Supplier.DeleteSupplier(input.nextLine(), db);

						}
						else if (Case.equals("2")){
							System.out.println("Enter Supplier ID and you will get list of his contacts");
							String sID = input.nextLine();
							String querry="SELECT  Name,Phone from ContactPerson where SupplierID= "+sID;
							try {
								db.st = db.c.createStatement();
								db.st.executeQuery(querry);
								ResultSet res= db.st.executeQuery(querry);
								while (res.next()) {
									String Phone = res.getString("Phone");
									String Name = res.getString("Name");
									System.out.println("Name: "+Name +"    Phone Number: "+Phone);
								}
								System.out.println();
								System.out.println("Enter Phone Number of the contact person you want to delete");
								Supplier.DeleteSupplier(sID, db,input.nextLine());

							} catch (SQLException e) {
								System.out.println("Action failed");
							}

						}
						else{
							System.out.println("illegal input - you return to option stage");
							continue;
						}

						break;
					}
					case "6":{
						System.out.println("Enter SupplierID, Contact person name And Phone number");
						String SuppID=input.nextLine();
						//SuppID="\""+SuppID+"\"";
						String Name=input.nextLine();
						Name="\""+Name+"\"";
						String Phone=input.nextLine();
						Phone = "\""+Phone+"\"";
						Supplier.AddContactPersonSupplier(SuppID,Name,Phone, db);

						break;
					}
					default:{
						if(x.equals("Exit")){
							System.exit(0);
						}
						if(just==false){
							System.out.println("please Try again - no operation for this value");
						}
						x="0";
						break;
					}

					}
				}
				x="0";
			}
			else{
				System.out.println("Try again");
				start=input.next();
				continue;
			}
		}
		input.close();
		db.CloseConnection();
	}
}
