<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>
<%@page import = "java.net.URLEncoder"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    <meta charset="UTF-8">
    <title>アカウント作成/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/change_info.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <%
       try{
       String tmp;
       String message = "";
       tmp = session.getAttribute("id").toString();
       String id = new String(tmp.getBytes("8859_1"),"UTF-8");
       if(Admin.adminCheck(id) == false){
       response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
       }else{
       tmp = request.getParameter("message");
       if(tmp != null){
       message = tmp;
       }

       AccountDB accountDB = new AccountDB();

       %>

    <script language="JavaScript">
      <!--
	 function submitCheck(){
	 if(document.forms[0].id.value == "" || document.forms[0].name.value == "" || document.forms[0].password1.value == "" || document.forms[0].password2.value == "" || document.forms[0].icon.value == ""){
         alert("入力漏れがあります");
         return false;
	 }
	 else if(document.forms[0].password1.value != document.forms[0].password2.value){
         alert("入力されたパスワードが一致していません");
         return false;
	 }

	 if(document.forms[0].id.value == document.forms[0].password1.value){
         alert("idとパスワードが同じなのは危険です\n違うパスワードを設定してください");
         document.forms[0].password1.value = "";
         document.forms[0].password2.value = "";

         return false;
	 }

	 return true;
	 }
	-->
    </script>

  </head>
  <body>
    <center>
      <header>
	<h1>
          <table border="0" style="width: 100%;">
	    <tr>
	      <td align="center"><input type="image" src="image/admin.png" alt="管理者ページへ" title="管理者ページへ" onClick="location.href='../GodServlet?command=toAdmin'"/></td>
	      <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>
      <%="<p><font color=\"red\">" + message + "</font></p>" %>
      <h2>アカウント作成</h2>

      <p>学生番号の制約を無視してアカウントを作成します</p>
      <hr>

      <form action="../GodServlet" method="POST" onSubmit="return submitCheck()">

	<p>お好きなアイコンをクリックして下さい</p>
	<div>
	  <label data-icon="1"><input type="radio" id="icon" name="icon" value="boy" checked><img></label>
	  <label data-icon="2"><input type="radio" id="icon" name="icon" value="girl"><img></label>
	</div>
	<table style="text-align:right;">
	  <tr>
	    <td>id(半角英数字のみ)</td>
	    <td><input type="text" id="id" name="id" placeholder="id" maxlength="20"></td>
	  </tr>
	  <tr>
	    <td>名前(20文字まで)</td>
	    <td><input type="text" id="name" name="name" placeholder="名前(20文字まで)" maxlength="20"></td>
	  </tr>
	  <tr>
	    <td>パスワード</td>
	    <td><input type="password" id="password1" name="password1" placeholder="パスワード"></td>
	  </tr>
	  <tr>
	    <td>パスワード(確認用)</td>
	    <td><input type="password" id="password2" name="password2" placeholder="パスワード(確認用)"></td>
	  </tr>
	  <input type="hidden" id="command" name="command" value="adminCreate">
	  <tr>
	    <td></td>
	    <td style="text-align:center;"><input type="submit" value="登録"></td>
	  </tr>
	</table>
      </form>

    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
