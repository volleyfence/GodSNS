package godsns;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class AccountDB{
    // 全てのアカウントの取得
    public Account[] getAllAccount() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		Vector<Account> accountVector = new Vector<Account>();

		try{
		    conn = SQLConnection.getConnection();
		    stmt = conn.createStatement();
	        String sql = "SELECT * FROM account WHERE id != 'god_suzuki29' ORDER BY id";
		    ResultSet rs = stmt.executeQuery(sql);
		    while(rs.next()) {
				accountVector.add(new Account(rs.getString("id"),
							      rs.getString("name"),
							      rs.getString("password"),
							      rs.getString("icon"),
							      rs.getString("firstLogin"),
							      rs.getString("lastLogin"),
							      rs.getString("loginCount")));
		    }
		    Account[] account = new Account[accountVector.size()];
		    account = accountVector.toArray(account);

		    return account;
		}
		finally{
		    try{
			if(stmt!=null) stmt.close();
		    }
		    catch(Exception e) {
			// ignore
		    }
		    try{
			if(conn!=null) conn.close();
		    }
		    catch(Exception e) {
			// ignore
		    }
		}
    }

    // アカウントの取得
    public Account[] getAccount(String id) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		Vector<Account> accountVector = new Vector<Account>();
		try{
		    conn = SQLConnection.getConnection();
		    stmt = conn.createStatement();
	        String sql = "SELECT * FROM account WHERE id = '" + id + "'";
		    ResultSet rs = stmt.executeQuery(sql);
		    while(rs.next()) {
			accountVector.add(new Account(rs.getString("id"),
						      rs.getString("name"),
						      rs.getString("password"),
						      rs.getString("icon"),
						      rs.getString("firstLogin"),
						      rs.getString("lastLogin"),
						      rs.getString("loginCount")));
		    }
		    Account[] account = new Account[accountVector.size()];
		    account = accountVector.toArray(account);
		    return account;
		}
		finally{
		    try{
		    	if(stmt!=null) stmt.close();
		    }
		    catch(Exception e) {
		    	// ignore
		    }
		    try{
		    	if(conn!=null) conn.close();
		    }
		    catch(Exception e) {
		    	// ignore
		    }
		}
    }

    // ログイン機能
    public int login(String id, String password) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		Account[] account = this.getAccount(id);
		password = this.crypt(password);
		if(account.length == 1){
		    // パスワードが一致
		    if(account[0].getPassword().equals(password)){
			try{
			    conn = SQLConnection.getConnection();
			    stmt = conn.createStatement();

			    Calendar calendar = Calendar.getInstance();
			    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    String time = sdf1.format(calendar.getTime());
			    String sql = "UPDATE account SET lastLogin='" + time + "', loginCount=loginCount+1 WHERE id='" + id + "'";
			    int rs = stmt.executeUpdate(sql);

			}finally{
			    try{
			    	if(stmt!=null) stmt.close();
			    }
			    catch(Exception e) {
				// ignore
			    }
			    try {
			    	if(conn!=null) conn.close();
			    }
			    catch(Exception e) {
				// ignore
			    }
			}
				return 0;
		    }
		    // パスワードが一致しない
		    else{
		    	return -1;
		    }
		}
		// アカウントが見つからない
		else{
		    return -1;
		}
    }

    // 新規登録機能
    public int create(String id, String name, String password, String icon, String admin) throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	int loginCount = 1;

	try{
	    password = this.crypt(password);
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    String time = sdf1.format(calendar.getTime());

	    Account[] account = this.getAccount(id);
	    if(account.length == 0){
		if(wordCheck(id) == false){
		    return -2;
		}

		// 管理者権限なら全てのidを許可する
		if(idCheck(id) == false && admin.equals("admin") == false){
		    return -3;
		}

		if(wordCheck(password) == false){
		    return -2;
		}

		// 管理者権限ならログインカウントは0に設定
		if(admin.equals("admin")){
		    loginCount = 0;
		}

		String sql = "INSERT INTO account (id, name, password, icon, firstLogin, lastLogin, loginCount) VALUES ('" + id + "','" + name + "','" + password + "','" + icon + "','" + time + "','" + time + "'," + loginCount + ")";
		int rs = stmt.executeUpdate(sql);

		return 0;
	    }
	    else{
		return -1;
	    }
	}
	finally{
	    try{
		if(stmt!=null) stmt.close();
	    }
	    catch(Exception e) {
		// ignore
	    }
	    try {
		if(conn!=null) conn.close();
	    }
	    catch(Exception e) {
		// ignore
	    }
	}
    }

    // アカウントの削除
    public int delete(String id, String password) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    password = this.crypt(password);
	    Account[] account = this.getAccount(id);
	    if(account.length == 1){
		if(password.equals(account[0].getPassword()) && id.equals("god_suzuki29") == false){

		    String sql = "DELETE FROM account WHERE id = '" + id + "' ";
		    int rs = stmt.executeUpdate(sql);
		    return 0;
		}
		else{
		    return -1;
		}
	    }
	    else{
		return -1;
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
    }

    // アカウントの全削除
    public int allDelete(String admin, String password) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    password = this.crypt(password);
	    Account[] account = this.getAccount(admin);
	    if(account.length == 1){
		if(password.equals(account[0].getPassword())){
		    String sql = "DELETE FROM account WHERE id != '" + admin + "' ";
		    int rs = stmt.executeUpdate(sql);
		    return 0;
		}
		else{
		    return -1;
		}
	    }
	    else{
		return -1;
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
    }

    // 1つのアカウントの削除(管理者用)
    public void adminDelete(String id) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    String sql = "DELETE FROM account WHERE id = '" + id + "' ";
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

    // アイコンの変更
    public int changeIcon(String id, String icon) throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	Account[] account = this.getAccount(id);
	if(account.length == 1){
	    try{
		conn = SQLConnection.getConnection();
		stmt = conn.createStatement();

		if(account[0].getIcon().equals(icon)){
		    return -2;
		}

		String sql = "UPDATE account SET icon='" + icon + "' WHERE id='" + id + "'";
		int rs = stmt.executeUpdate(sql);

	    }finally{
		try{
		    if(stmt!=null) stmt.close();
		}
		catch(Exception e) {
		    // ignore
		}
		try {
		    if(conn!=null) conn.close();
		}
		catch(Exception e) {
		    // ignore
		}
	    }
	    return 0;
	}
	// アカウントが見つからない
	else{
	    return -1;
	}
    }

    // 名前の変更
    public int changeName(String id, String name) throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	Account[] account = this.getAccount(id);
	if(account.length == 1){
	    try{
		conn = SQLConnection.getConnection();
		stmt = conn.createStatement();

		if(account[0].getName().equals(name)){
		    return -2;
		}

		String sql = "UPDATE account SET name='" + name + "' WHERE id='" + id + "'";
		int rs = stmt.executeUpdate(sql);

	    }finally{
		try{
		    if(stmt!=null) stmt.close();
		}
		catch(Exception e) {
		    // ignore
		}
		try {
		    if(conn!=null) conn.close();
		}
		catch(Exception e) {
		    // ignore
		}
	    }
	    return 0;
	}
	// アカウントが見つからない
	else{
	    return -1;
	}
    }

    // パスワードの変更
    public int changePassword(String id, String password, String password1) throws SQLException{
	Connection conn = null;
	Statement stmt = null;
	Account[] account = this.getAccount(id);
	password = this.crypt(password);
	if(account.length == 1){
	    // パスワードが一致
	    if(account[0].getPassword().equals(password)){
		try{
		    conn = SQLConnection.getConnection();
		    stmt = conn.createStatement();
		    password1 = this.crypt(password1);

		    if(password1.equals(password)){
			return -3;
		    }

		    if(wordCheck(password) == false){
			return -2;
		    }

		    String sql = "UPDATE account SET password='" + password1 + "' WHERE id='" + id + "'";
		    int rs = stmt.executeUpdate(sql);

		}finally{
		    try{
			if(stmt!=null) stmt.close();
		    }
		    catch(Exception e) {
			// ignore
		    }
		    try {
			if(conn!=null) conn.close();
		    }
		    catch(Exception e) {
			// ignore
		    }
		}
		return 0;
	    }
	    // パスワードが一致しない
	    else{
		return -1;
	    }
	}
	// アカウントが見つからない
	else{
	    return -1;
	}
    }

    // 名前の取得
    public String getName(String id) throws SQLException{
	Account[] account = this.getAccount(id);
	if(account != null){
	    return account[0].getName();
	}
	else{
	    return "error";
	}
    }

    // アイコン情報の取得
    public String getIcon(String id) throws SQLException{
	Account[] account = this.getAccount(id);
	if(account != null){
	    return account[0].getIcon();
	}
	else{
	    return "error";
	}
    }

    // 入力情報のチェック
    private boolean wordCheck(String a){
	int i;
	char c;
	for(i = 0; i < a.length(); i++) {
	    c = a.charAt(i);
	    if((c < '0' || c > '9') && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z')){
		return false;
	    }
	}

	return true;
    }

    // idの制約チェック
    private boolean idCheck(String a){
	int i;
	int id;
	char c = a.charAt(0);
	String s;

        // uから始まり6桁の数字の判定
	if(c == 'u'){
	    s = a.substring(1);
	    if(s.length() != 6){
		return false;
	    }
	    else{
		for(i = 1; i < 7; i++){
		    c = a.charAt(i);
		    if(c < '0' || c > '9')
			return false;
		}
	    }
	    c = a.charAt(3);
	    if(c < '1' || c > '6')
		return false;
	    id = Integer.parseInt(a.substring(4));

	    // 大まかに存在判定
	    if(id > 250 || id == 0)
		return false;
	}
	// gから始まり5桁の数字の判定
	else if(c == 'g'){
	    s = a.substring(1);
	    if(s.length() != 5){
		return false;
	    }
	    else{
                for(i = 1; i < 6; i++){
                    c = a.charAt(i);
                    if(c < '0' || c > '9')
                        return false;
                }
            }
	}
	else{
	    return false;
	}

	return true;
    }

    // 暗号化
    public String crypt(String a){
	MessageDigest md = null;
	StringBuilder sb = null;
	try{
	    // SHA-1で暗号化
	    md = MessageDigest.getInstance("SHA-1");
	    md.update(a.getBytes());
	    sb = new StringBuilder();
	    for (byte b : md.digest()) {
		String hex = String.format("%02x", b);
		sb.append(hex);
	    }
	}
	catch(Exception e){
	    // ignore
	}
	return sb.toString();

    }
}
