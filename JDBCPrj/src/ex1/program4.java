package ex1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.xdevapi.PreparableStatement;

public class program4 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		int id = 13;
		
		String url = "jdbc:oracle:thin:@//localhost:1521/xepdb1";
		//String sql = "SELECT * FROM NOTICE";
		String sql = "DELETE NOTICE WHERE ID =?";

		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url,"newlec","alsgml77");
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		
		int result = st.executeUpdate();
		
		System.out.println(result);
		
		st.close();
		con.close();

	}

}
