package godsns;

public class Message{

    private String id;
    private String body;
    private String time;
    private String m_id;
    private String chatname;

    // インスタンス変数の初期化
    public Message(String id, String body, String time, String m_id, String chatname) {
	this.id = escape(id);
	this.body = escape(body);
	this.time = escape(time);
	this.m_id = escape(m_id);
	this.chatname = escape(chatname);
    }

    // idの取得
    public String getId() {
	return id;
    }

    // メッセージの取得
    public String getBody() {
	return body;
    }

    // 時間の取得
    public String getTime() {
	return time;
    }

    // m_idの取得
    public String getMId() {
	return m_id;
    }

    // chatnameの取得
    public String getChatName() {
	return chatname;
    }

    // HTMLタグの無効化
    private String escape(String para){
	para = para.replaceAll("<", "＜");
	para = para.replaceAll(">", "＞");
	return para;
    }

}
