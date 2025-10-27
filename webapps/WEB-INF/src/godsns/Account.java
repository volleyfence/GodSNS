package godsns;

public class Account{

    private String id;
    private String name;
    private String password;
    private String icon;
    private String firstLogin;
    private String lastLogin;
    private String loginCount;

    // インスタンス変数の初期化
    public Account(String id, String name, String password, String icon, String firstLogin, String lastLogin, String loginCount) {
	this.id = escape(id);
	this.name = escape(name);
	this.password = escape(password);
	this.icon = escape(icon);
	this.firstLogin = escape(firstLogin);
	this.lastLogin = escape(lastLogin);
	this.loginCount = escape(loginCount);
    }

    // idの取得
    public String getId() {
	return id;
    }

    // 名前の取得
    public String getName() {
	return name;
    }

    // パスワードの取得
    public String getPassword() {
	return password;
    }

    // アイコン情報の取得
    public String getIcon() {
	return icon;
    }

    // 初ログイン時間の取得
    public String getFirstLogin() {
	return firstLogin;
    }

    // ラストログイン時間の取得
    public String getLastLogin() {
	return lastLogin;
    }

    // ログイン回数の取得
    public String getLoginCount() {
	return loginCount;
    }

    // HTMLタグの無効化
    private String escape(String para){
	para = para.replaceAll("<", "＜");
	para = para.replaceAll(">", "＞");
	return para;
    }


}