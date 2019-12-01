import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Item {
	ArrayList<String> book_name = new ArrayList<String>();
	ArrayList<String> author = new ArrayList<String>();
	ArrayList<Float> price = new ArrayList<Float>();
	ArrayList<Integer> quantity = new ArrayList<Integer>();
	public void add_Item(String bk_name,String auth,float pric,int quant) {
		book_name.add(bk_name);
		author.add(auth);
		price.add(pric);
		quantity.add(quant);		
	}
	public void generate_Bill(Statement stmt, String customer_name,int bill_number,String mob_no,Customer_details cd,Connection conn) throws Exception{
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		System.out.print("| Bill No. : "+bill_number+"        ");
		Date d = new Date();
		String date = d.toString();
		System.out.println(date);
		System.out.println("| Customer Name: "+customer_name);
		System.out.println("| Mobile no: "+mob_no+"\n");
		float total_amount = 0;
		//System.out.println("ahsgdgjskfkdglkfh;j  "+book_name.size()+"   "+author.size()+"         "+price.size()+"      "+quantity.size() );
		for(int i=0;i<book_name.size();i++) {
			float pricc = price.get(i);
			int quan = quantity.get(i);			
			float ttl = pricc*(float)quan;
			total_amount += ttl;
			System.out.println("| "+book_name.get(i)+" by "+author.get(i)+"     "+pricc+"*"+quan+"         "+ttl);
		}
		System.out.println("\n\n                                               Total Amount: "+total_amount);
		//Customer_details cdd = new Customer_details();
		cd.customer_data(stmt,customer_name,mob_no,total_amount,conn);
		int disc  = cd.discount();
		float actual_payable = total_amount*(float)(100-disc)/(float)100;
		//String update_amount = "update Customer set bought= "+actual_payable+ " where mob_no ="+mob_no ;
  	    //stmt.executeUpdate(update_amount);
		System.out.println("\n\n                                               Payable Amount: "+actual_payable);
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
	} 
}
