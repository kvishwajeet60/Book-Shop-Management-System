import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class Bookie {
	public static boolean check_valid_Id (int idd, Statement stmt) throws Exception{
		String sttr = "select count(*) from bookmanage";
	    ResultSet rs = stmt.executeQuery(sttr);
   	    int count = 0;
   	    while(rs.next())					
   	    	count = (int)rs.getInt(1);
   	    if(idd > count) {
   	    	return false;
   	    }
   	    return true;
	}
	public static int get_bill_No(Statement stmt) throws Exception{
		String sttr = "select bill_number from BillNo where id = 0";
	    ResultSet rs = stmt.executeQuery(sttr);
   	    int count = 0;
   	    while(rs.next())					
   	    	count = (int)rs.getInt(1);
   	    String update_billNo = "update BillNo set bill_number= "+(count+1)+ " where id =0" ;
	    stmt.executeUpdate(update_billNo);	
   	    return count+1;
	}
	public static void main(String[] args) throws Exception{
		Connection conn =null;
		Scanner sc = new Scanner(System.in);
	   try {
		    //Class.forName("oracle.jdbc.driver.OracleDriver");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "BookManage","9709882562");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookshopmanagement","root","9709882562");
			if(conn != null) {
			//System.out.println("Connected");
			Statement stmt =conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from BookManage");
			System.out.println("Available Books in shop are: \n");
			while(rs.next())
			{
				System.out.print(rs.getInt(1)+": "+rs.getString(2)+" by "+rs.getString(3)+"  ("+rs.getString(5)+")   "+"\n");
				System.out.print("    Price: "+rs.getFloat(6)+"\n");
			}	
			//customer();
			float total_amount =0f;
			Item it = new Item();
			Customer_details cd = new Customer_details();
			while(true) {
				    float final_price =0;
				    int final_quant =0;
			   	    System.out.println("\nChoose Id no. of book which you want?");
			   	    int idd = sc.nextInt();
			   	   
			   	    ////////////checking..... BookID is wrong or right
			   	    boolean status = check_valid_Id(idd,stmt);
			   	    if(status == false) {
			   	    	System.out.println("Chhose valid BookID.");
			   	    	continue;
			   	    }        ////////////////
			   	    
			   	    String sttr = "select quantity from bookmanage where id = "+ idd;
		   	        ResultSet rsst = stmt.executeQuery(sttr);
			   	    int quantity_original =0;
			   	    while(rsst.next())					
			   	    	quantity_original = (int)rsst.getFloat(1);
			   	    //if choosed books are not avalable
			   	    if(quantity_original == 0) {
			   	    	System.out.println("SORRY!!! This book isn't available now. Choose another book.");
			   	    }
			   	    else {
			   	       System.out.println("How much quantity you want?");			   	    
			   	       int quant = sc.nextInt();
			   	       
			   	    
			   	       if(quant > quantity_original) {
			   	    	   System.out.println("We have only "+quantity_original+ " books. So choose less.");
			   	           System.out.println("Now how much you want?");
			   	    	   quant = sc.nextInt();
			   	       }	   
			   	       //update_quantity after taking books
			   	       String update_quantity = "update bookmanage set quantity= "+(quantity_original-quant)+ " where id ="+idd ;
			   	       stmt.executeUpdate(update_quantity);	
			   	    
			   	       String stt = "select price from bookmanage where id = "+ idd;
			   	       ResultSet rss = stmt.executeQuery(stt);
			   	       float pric =0f;
			   	       while(rss.next())					
			   	    	   pric = (float)rss.getFloat(1);						
			   	       total_amount += pric*(float)quant;
			   	       final_price = pric;
			   	       final_quant = quant;
			   	    }
			   	    
			     	//Item it = new Item();
			   	    String boook_name=null;
		   	    	String authorr=null;
		   	    	String sttrr = "select bookname,author from bookmanage where id = "+ idd;
		   	        ResultSet rsstt = stmt.executeQuery(sttrr);
			   	    while(rsstt.next()) {					
			   	    	boook_name = rsstt.getString(1);
			   	    	authorr = rsstt.getString(2);
			   	    }
		   	    	it.add_Item(boook_name,authorr,final_price,final_quant);
		   	    	//System.out.println(boook_name+" "+authorr+" "+final_price+" "+final_quant);
			   	    System.out.println("If you purchase more book press '1' else '0'");
			   	    int press = sc.nextInt();
			   	    if(press == 1) {			   	    				   	    	
			   	    	continue;
			   	    }
			   	    else {
			   	    	//System.out.println("\n\nTotal Amount: "+ total_amount);	
			   	    	//Item it = new Item();			   	    	
						System.out.println("Type Name to generate the Bill: ");
						String customer_name = sc.next();
						sc.nextLine();
						System.out.println("Contact No. : ");
						String mob_no = sc.next();
						int bill_number = get_bill_No(stmt);
						it.generate_Bill(stmt,customer_name,bill_number,mob_no,cd,conn);
			   	    	break;
			   	    }
			}			
		  }
		  else {
			System.out.println("NotConnected");
		}
	   }catch(ClassNotFoundException e){
		   System.out.println("Driver class is not found! Exception occured");
	   }catch(SQLException se){
		   System.out.println("Exception occured while creating a connection");
	       System.out.println(se);
	   }
	   /*finally {
	   try {
		conn.close();
	   }
	   catch(SQLException se){
		   System.out.println("Exception occured while closing the connection!!");
	   }
	   }*/
	   sc.close();

	}

}
