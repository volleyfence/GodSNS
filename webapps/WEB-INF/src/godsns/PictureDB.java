package godsns;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PictureDB{
    // 全ての写真の取得
    public Picture[] getAllPicture() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		Vector<Picture> pictureVector = new Vector<Picture>();

		try {
		    conn = SQLConnection.getConnection();
		    stmt = conn.createStatement();
	            String sql = "SELECT * FROM picture ORDER BY time DESC";
		    ResultSet rs = stmt.executeQuery(sql);
		    while(rs.next()) {
			pictureVector.add(new Picture(rs.getString("id"),
						      rs.getString("body"),
						      rs.getString("time"),
						      rs.getString("filename"),
						      rs.getInt("god")));
		    }
		    Picture[] picture = new Picture[pictureVector.size()];
		    picture = pictureVector.toArray(picture);
		    return picture;
		} finally {
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

    // 要求された表示順でその日付の全ての写真を取得
    public Picture[] getAllPicture(String command, String date) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		Vector<Picture> pictureVector = new Vector<Picture>();

		try {
		    conn = SQLConnection.getConnection();
		    stmt = conn.createStatement();
	        String sql = "SELECT * FROM picture WHERE time LIKE '" + date + "%'  ORDER BY " + command + " DESC";
		    ResultSet rs = stmt.executeQuery(sql);
		    while(rs.next()) {
		    	pictureVector.add(new Picture(rs.getString("id"),
						      				  rs.getString("body"),
						      				  rs.getString("time"),
						      				  rs.getString("filename"),
						      				  rs.getInt("god")));
		    }
		    Picture[] picture = new Picture[pictureVector.size()];
		    picture = pictureVector.toArray(picture);
		    return picture;
		} finally {
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

    // 要求されたidの全ての写真を取得
    public Picture[] getMyPicture(String id) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<Picture> pictureVector = new Vector<Picture>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM picture WHERE id='" + id + "' ORDER BY time";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		pictureVector.add(new Picture(rs.getString("id"),
					      rs.getString("body"),
					      rs.getString("time"),
					      rs.getString("filename"),
					      rs.getInt("god")));
	    }
	    Picture[] picture = new Picture[pictureVector.size()];
	    picture = pictureVector.toArray(picture);
	    return picture;
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

    // 要求されたfilenameの情報を取得
    public Picture[] getPicture(String filename) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	Vector<Picture> pictureVector = new Vector<Picture>();

	try {
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();
            String sql = "SELECT * FROM picture WHERE filename='" + filename + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()) {
		pictureVector.add(new Picture(rs.getString("id"),
					      rs.getString("body"),
					      rs.getString("time"),
					      rs.getString("filename"),
					      rs.getInt("god")));
	    }
	    Picture[] picture = new Picture[pictureVector.size()];
	    picture = pictureVector.toArray(picture);
	    return picture;
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


    // 写真投稿の追加
    public void add(String id, String filename, String body, String time) throws SQLException {
	Connection conn = null;
	Statement stmt = null;
	try{
	    conn = SQLConnection.getConnection();
	    stmt = conn.createStatement();

	    String sql = "INSERT INTO picture (id, body,time,filename) VALUES ('" + id + "','" + body + "','" + time + "','" + filename + "')";
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

    // 画像の削除
    public int delete(String id, String filename) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
	Picture[] picture = null;
	int result;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    picture = getPicture(filename);
	    if(picture.length == 1 && (picture[0].getId().equals(id) || id.equals("god_suzuki29"))){
		String sql = "DELETE FROM picture WHERE filename = '" + filename + "' ";
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

    // 画像の全削除
    public void allDelete() throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    String sql = "TRUNCATE TABLE picture";
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

    // 要求されたidの画像の全削除
    public void allDelete(String id) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

	    String sql = "DELETE FROM picture WHERE id = '" + id + "' ";
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

    // 「神だね」の追加
    public void godAdd(String filename) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

            String sql = "UPDATE picture SET god = god + 1 WHERE filename = '" + filename + "' ";
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

    // 「神だね」の削除
    public void godDelete(String filename) throws SQLException {
	Connection conn = null;
        Statement stmt = null;
        try{
            conn = SQLConnection.getConnection();
            stmt = conn.createStatement();

            String sql = "UPDATE picture SET god = god - 1 WHERE filename = '" + filename + "' ";
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
