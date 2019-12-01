import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Customer_details {
	//boolean regular_customer_status = true;
	float amount_for_discount =0f;
	public void customer_data(Statement stmt,String customer_name,String mob_no,float totall,Connection conn) throws Exception {		
		  String st ="Select bought from Customer where mob_no = "+mob_no;
		  float new_totall = 0f;
		  //try {
		     ResultSet rs = stmt.executeQuery(st);		     
		     while(rs.next()) {
			     new_totall= rs.getFloat(1);
		     }
		     amount_for_discount = new_totall;
		     totall += new_totall; 
		     //update bought amount
		     String update_amount = "update Customer set bought= "+totall+ " where mob_no ="+mob_no ;
	   	     stmt.executeUpdate(update_amount);
		  //}
		  //catch(Exception e){
	   	  if(new_totall == 0) {
	   		  //regular_customer_status = false;
		      PreparedStatement pd = conn.prepareStatement("insert into Customer(name,mob_no,bought) values(?,?,?)");
	          pd.setString(1,customer_name);
	          pd.setString(2,mob_no);
	          pd.setFloat(3,totall);
	          pd.executeUpdate();
	   	  }
	   	  //return amount_for_discount;
		 // }
	}
	public int discount() {
		if(amount_for_discount >= 500000) {
			System.out.println("Platinum Customer! You got extra discount of 10%+20%. Thank You for Shopping!!!");
			return 30;
		}
		else if(amount_for_discount >= 10000 && amount_for_discount < 50000) {
			System.out.println("Silver Customer! You got extra discount of 10%+5%. Thank You for Shopping!!!");
			return 15;
		}
		else if(amount_for_discount >= 50000 && amount_for_discount < 100000) {
			System.out.println("Golden Customer! You got extra discount of 10%+10%. Thank You for Shopping!!!");
			return 20;
		}
		else if(amount_for_discount >= 100000 && amount_for_discount < 500000) {
			System.out.println("Diamond Customer! You got extra discount of 10%+15%. Thank You for Shopping!!!");
			return 25;
		}
		System.out.println("Thank You for Shopping!!! You got discount of 10%.");
		return 10;
	}
	
}
