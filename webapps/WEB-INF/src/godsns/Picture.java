package godsns;

public class Picture{

    private String id;
    private String body;
    private String time;
    private String filename;
    private int god;

    // インスタンス変数の取得
    public Picture(String id, String body, String time, String filename, int god) {
	this.id = escape(id);
	this.body = escape(body);
	this.time = escape(time);
	this.filename = escape(filename);
	this.god = god;
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

    // filenameの取得
    public String getName(){
	return filename;
    }

    // 「神だね」数の取得
    public int getGod(){
	return god;
    }

    // HTMLタグの無効化
    private String escape(String para){
	para = para.replaceAll("<", "＜");
	para = para.replaceAll(">", "＞");
	return para;
    }

}
