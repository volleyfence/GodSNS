package godsns;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class MessageDB{
    // 全てのメッセージの取得
    public Message[] getAllMessage() throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<Message> messageVector = new Vector<Message>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM message ORDER BY time DESC";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		messageVector.add(new Message(rs.getString("id"),
					      rs.getString("body"),
					      rs.getString("time"),
					      rs.getString("m_id"),
					      rs.getString("chatname")));
	    }
	    Message[] message = new Message[messageVector.size()];
	    message = messageVector.toArray(message);
	    return message;
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

    // 要求された掲示板の全てのメッセージの取得
    public Message[] getAllMessage(String chatname) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<Message> messageVector = new Vector<Message>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM message WHERE chatname='" + chatname + "' ORDER BY time";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		messageVector.add(new Message(rs.getString("id"),
					      rs.getString("body"),
					      rs.getString("time"),
					      rs.getString("m_id"),
					      rs.getString("chatname")));
	    }
	    Message[] message = new Message[messageVector.size()];
	    message = messageVector.toArray(message);
	    return message;
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

    // 要求されたidの全てのメッセージの取得
    public Message[] getMyMessage(String id) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<Message> messageVector = new Vector<Message>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM message WHERE id='" + id + "' ORDER BY time DESC";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		messageVector.add(new Message(rs.getString("id"),
					      rs.getString("body"),
					      rs.getString("time"),
					      rs.getString("m_id"),
					      rs.getString("chatname")));
	    }
	    Message[] message = new Message[messageVector.size()];
	    message = messageVector.toArray(message);
	    return message;
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

    // 1つのメッセージの取得
    public Message[] getMessage(String m_id) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<Message> messageVector = new Vector<Message>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM message WHERE m_id=" + m_id;
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		messageVector.add(new Message(rs.getString("id"),
					      rs.getString("body"),
					      rs.getString("time"),
					      rs.getString("m_id"),
					      rs.getString("chatname")));
	    }
	    Message[] message = new Message[messageVector.size()];
	    message = messageVector.toArray(message);
	    return message;
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

    // メッセージの追加
    public void add(String id, String body, String chatname) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	try{
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    String time = sdf1.format(calendar.getTime());

	    String sql = "INSERT INTO message (id,body,time,chatname) VALUES ('"+id+"','"+body+"','"+time+"','"+chatname+"')";
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

    // メッセージの削除
    public int delete(String id, String m_id) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
	Message[] message = null;
	int result;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    message = getMessage(m_id);
	    if(message.length == 1 && (message[0].getId().equals(id) || id.equals("god_suzuki29"))){
		String sql = "DELETE FROM message WHERE m_id = " + m_id;
		int rs = stmt.executeUpdate(sql);
		result = 0;
	    }
	    else{
		result = -1;
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
	return result;
    }

    // メッセージの全削除
    public void allDelete() throws SQLException {
	Connection conn = null;
        Statement stmt = null;

        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    String sql = "TRUNCATE TABLE message";
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

    // 要求されたidのメッセージの全削除
    public void allDelete(String id) throws SQLException {
	Connection conn = null;
        Statement stmt = null;

        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    String sql = "DELETE FROM message WHERE id = '" + id + "'";
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

}
