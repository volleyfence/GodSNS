<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    <meta charset="UTF-8">
    <title>登録情報変更/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/change_info.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">
    <script language="JavaScript">
      <!--
	 function submitCheck0(){
	 if(window.confirm('アイコンを変更しますか？')){
	 return true;
	 }
	 else{
	 window.alert('キャンセルされました');
	 return false;
	 }
	 }

	 function submitCheck1(){
	 if(document.forms[1].name.value == ""){
         alert("入力漏れがあります");
         return false;
	 }

	 if(window.confirm('名前を変更しますか？')){
	 return true;
	 }
	 else{
	 window.alert('キャンセルされました');
	 return false;
	 }
	 }

	 function submitCheck2(){
	 if(document.forms[2].password.value == "" || document.forms[2].password1.value == "" || document.forms[2].password2.value == ""){
         alert("入力漏れがあります");
         return false;
	 }
	 else if(document.forms[2].password1.value != document.forms[2].password2.value){
         alert("入力された新しいパスワードが一致していません");
         return false;
	 }

	 if(document.forms[2].password.value == document.forms[2].password1.value){
         alert("パスワードが変わっていません");
         return false;
	 }


	 if(window.confirm('パスワードを変更しますか？')){
	 return true;
	 }
	 else{
	 window.alert('キャンセルされました');
	 return false;
	 }

	 }
	-->
    </script>

    <%
       try{
       String tmp;
       String message = "";
       tmp = session.getAttribute("id").toString();
       String id = new String(tmp.getBytes("8859_1"),"UTF-8");
       if(id.equals("god_suzuki29") == true){
       response.sendRedirect("/godsns/jsp/admin.jsp");
       }else{
       tmp = request.getParameter("message");
       if(tmp != null){
       message = tmp;
       }

       AccountDB accountDB = new AccountDB();
       %>

  </head>
  <body>
    <center>
      <header>
	<h1>
          <table border="0" style="width: 100%;">
	    <tr>
	      <td align="center"><input type="image" src="image/menu.png" alt="メニューへ" title="メニューへ" onClick="location.href='../GodServlet?command=toMenu'"/></td>
	      <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>
      <h2>登録情報変更</h2>

      <%="<p><font color=\"red\">" + message + "</font></p>" %>

      <p>登録情報の変更ができます</p>
      <hr>

      <h3>アイコン変更</h3>
      <form action="../GodServlet" method="POST" onSubmit="return submitCheck0()">
	<div>
	  <label data-icon="1"><input type="radio" id="icon" name="icon" value="boy" <% if(accountDB.getIcon(id).equals("boy")){ %><%="checked" %><% } %>><img></label>
	  <label data-icon="2"><input type="radio" id="icon" name="icon" value="girl" <% if(accountDB.getIcon(id).equals("girl")){ %><%="checked" %><% } %>><img></label>
	</div>
	<input type="hidden" id="command" name="command" value="changeIcon">
	<input type="submit" value="変更">
      </form>
      <hr>

      <h3>名前変更</h3>
      <form action="../GodServlet" method="POST" onSubmit="return submitCheck1()">
	<table style="text-align:right;">
	  <tr>
	    <td>新しい名前(20文字まで)</td>
	    <td><input type="text" id="name" name="name" value="<%=accountDB.getName(id) %>" placeholder="新しい名前(20文字まで)" maxlength="20"></td>
	  </tr>
	  <input type="hidden" id="command" name="command" value="changeName">
	  <tr>
	    <td></td>
	    <td style="text-align:center;"><input type="submit" value="変更"></td>
	  </tr>
	</table>
      </form>
      <hr>

      <h3>パスワード変更</h3>
      <form action="../GodServlet" method="POST" onSubmit="return submitCheck2()">
	<table style="text-align:right;">
	  <tr>
	    <td>現在のパスワード</td>
	    <td><input type="password" id="password" name="password" placeholder="現在のパスワード"></td>
	  </tr>
	  <tr>
	    <td>新しいパスワード</td>
	    <td><input type="password" id="password1" name="password1" placeholder="新しいパスワード"></td>
	  </tr>
	  <tr>
	    <td>新しいパスワード(確認用)</td>
	    <td><input type="password" id="password2" name="password2" placeholder="新しいパスワード(確認用)"></td>
	  </tr>
	  <input type="hidden" id="command" name="command" value="changePassword">
	  <tr>
	    <td></td>
	    <td style="text-align:center;"><input type="submit" value="変更"></td>
	  </tr>
	</table>
      </form>
      <br>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
