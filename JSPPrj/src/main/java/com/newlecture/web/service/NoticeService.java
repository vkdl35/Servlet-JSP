package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList() {
		
		return getNoticeList("title", "", 1);
	}
	
	public List<Notice> getNoticeList(int page) {
		
		return getNoticeList("title", "", page);
	}
	
	public List<Notice> getNoticeList(String field/*TITLE, WRITER_ID*/, String query/*A*/, int page) {

		List<Notice> list = new ArrayList<>();
		
		String sql = "SELECT * FROM ( "
				+ "SELECT ROWNUM NUM, N.* "
				+ "FROM (SELECT * FROM NOTICE WHERE TITLE LIKE ? ORDER BY REGDATE DESC) N "
				+ ") "
				+ "WHERE NUM BETWEEN ? AND ?";
		
		
		
		// 1, 11, 21, 31 .. -> an = 1+(page-1)*10
		// 10, 20, 30, 40 .. -> page*10 
		
		String url = "jdbc:oracle:thin:@//localhost:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"newlec","alsgml77");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()){		

				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				Notice notice = new Notice(
						id,
						title,
						writerId,
						regdate,
						hit,
						files,
						content
					);
				list.add(notice);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int getNoticeCount() {
		
		return getNoticeCount("title", ""); 
	}
	
	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM ( "
				+ "SELECT ROWNUM NUM, N.* "
				+ "FROM (SELECT * FROM NOTICE WHERE TITLE LIKE ? ORDER BY REGDATE DESC) N "
				+ ")";
		
		String url = "jdbc:oracle:thin:@//localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"newlec","alsgml77");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
				count = rs.getInt("count");

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		String url = "jdbc:oracle:thin:@//localhost:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"newlec","alsgml77");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){		
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(
						nid,
						title,
						writerId,
						regdate,
						hit,
						files,
						content
					);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notice;
	}
	
	public Notice getNextNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE "
				+ "    WHERE ID = ( "
				+ "        SELECT ID FROM NOTICE "
				+ "        WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=?) "
				+ "        AND ROWNUM = 1"
				+ "    )";
		
		String url = "jdbc:oracle:thin:@//localhost:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"newlec","alsgml77");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){		
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(
						nid,
						title,
						writerId,
						regdate,
						hit,
						files,
						content
					);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) "
				+ "WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?) "
				+ "AND ROWNUM = 1";
		
		String url = "jdbc:oracle:thin:@//localhost:1521/xepdb1";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url,"newlec","alsgml77");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if(rs.next()){		
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regdate = rs.getDate("REGDATE");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(
						nid,
						title,
						writerId,
						regdate,
						hit,
						files,
						content
					);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
}
