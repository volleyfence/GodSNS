<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>
<%@page import = "java.net.URLEncoder"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    <meta charset="UTF-8">
    <title>パスワード変更/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/change_info.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">
    <script language="JavaScript">
      <!--
	 function submitCheck(){
	 if(document.forms[0].password.value == "" || document.forms[0].password1.value == "" || document.forms[0].password2.value == ""){
         alert("入力漏れがあります");
         return false;
	 }
	 else if(document.forms[0].password1.value != document.forms[0].password2.value){
         alert("入力された新しいパスワードが一致していません");
         return false;
	 }

	 if(document.forms[0].password.value == document.forms[0].password1.value){
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
       if(Admin.adminCheck(id) == false){
       response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
       }else{
       tmp = request.getParameter("message");
       if(tmp != null){
       message = tmp;
       }

       %>

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
      <h2>パスワード変更</h2>

      <%="<p><font color=\"red\">" + message + "</font></p>" %>

      <p>パスワードの変更ができます</p>
      <hr>

      <form action="../GodServlet" method="POST" onSubmit="return submitCheck()">
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
	  <input type="hidden" id="command" name="command" value="adminChangePassword">
	  <tr>
	    <td></td>
	    <td style="text-align:center;"><input type="submit" value="変更"></td>
	  </tr>
	</table>
      </form>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
