package godsns;

public class God{

    private String id;
    private String filename;
    private String time;

    // インスタンス変数の初期化
    public God(String id, String filename, String time){
	this.id = id;
	this.filename = filename;
	this.time = time;
    }

    // idの取得
    public String getId() {
	return id;
    }

    // filenameの取得
    public String getName() {
	return filename;
    }

    // timeの取得
    public String getTime() {
	return time;
    }
}
