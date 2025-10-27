package godsns;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class GodDB{
    // 全てのidによる「神だね」を取得
    public God[] getAllGod(String id) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<God> godVector = new Vector<God>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM god Where id='" + id + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		godVector.add(new God(rs.getString("id"),
				      rs.getString("filename"),
				      rs.getString("time")));
	    }
	    God[] god = new God[godVector.size()];
	    god = godVector.toArray(god);
	    return god;
	} finally {
	    try{
		if(stmt!=null) stmt.close();
	    } catch(Exception e) {
		// ignore
	    }
	    try {
		if(conn!=null) conn.close();
	    } catch(Exception e) {
		// ignore
	    }
	}
    }

    // 「神だね」の登録
    public boolean add(String id, String filename) throws SQLException {
	Connection conn = null;
	Statement stmt = null;

	boolean bool;

	try{
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    String time = sdf1.format(calendar.getTime());

	    // 二重処理の防止
	    if(search(id, filename) == false){
		String sql = "INSERT INTO god (id,filename,time) VALUES ('" + id + "','" + filename + "','" + time + "')";
		int rs = stmt.executeUpdate(sql);
		bool = true;
	    }
	    else{
		bool = false;
	    }
	} finally {
	    try{
		if(stmt!=null) stmt.close();
	    } catch(Exception e) {
		// ignore
	    }
	    try {
		if(conn!=null) conn.close();
	    } catch(Exception e) {
		// ignore
	    }
	}

	return bool;
    }

    // 「神だね」の削除
    public boolean delete(String id, String filename) throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	boolean bool;
	try{
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    // 二重処理の防止
	    if(search(id, filename) == true){
		String sql = "DELETE FROM god WHERE id='" + id + "' AND filename='" + filename + "'";
		int rs = stmt.executeUpdate(sql);
		bool = true;
	    }
	    else{
		bool = false;
	    }
	} finally {
	    try{
		if(stmt!=null) stmt.close();
	    } catch(Exception e) {
		// ignore
	    }
	    try {
		if(conn!=null) conn.close();
	    } catch(Exception e) {
		// ignore
	    }
	}

	return bool;
    }

    // 「神だね」の全削除
    public void allDelete() throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	boolean bool;
	try{
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    String sql = "TRUNCATE TABLE god";
	    int rs = stmt.executeUpdate(sql);

	} finally {
	    try{
		if(stmt!=null) stmt.close();
	    } catch(Exception e) {
		// ignore
	    }
	    try {
		if(conn!=null) conn.close();
	    } catch(Exception e) {
		// ignore
	    }
	}

    }

    // 要求された「神だね」の全削除
    public void allDelete(String key, String command) throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	boolean bool;
	try{
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    String sql = "DELETE FROM god WHERE " + command + "='" + key + "'";
	    int rs = stmt.executeUpdate(sql);

	} finally {
	    try{
		if(stmt!=null) stmt.close();
	    } catch(Exception e) {
		// ignore
	    }
	    try {
		if(conn!=null) conn.close();
	    } catch(Exception e) {
		// ignore
	    }
	}

    }

    // 「神だね」の検索
    public boolean search(String id, String filename) throws SQLException {
	God[] god = null;
	god = getAllGod(id);
	for(int i = 0; i < god.length; i++){
	    if(god[i].getName().equals(filename))
		return true;
	}
	return false;
    }

}
