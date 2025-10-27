<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    <meta charset="UTF-8">
    <title>アカウント削除/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/change_info.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript">
      <!--
	 function submitCheck(){
	 if(document.forms[0].password.value == ""){
         alert("入力漏れがあります");
         return false;
	 }
	 if(window.confirm('投稿したものはすべて削除されます\nアカウントを削除しますか？')){
	 return true;
	 }
	 else{
	 window.alert('キャンセルされました');
	 return false;
	 }
	 }
	 // -->
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
      <br>
      <%="<p><font color=\"red\">" + message + "</font></p>" %>
      <h2>アカウント削除</h2>
            <p>アカウントを削除します</p>
      <hr>
      <form action="../GodServlet" method="POST" onSubmit="return submitCheck()">
	<table style="text-align:right;">
	  <tr>
	    <td>パスワード</td>
	    <td><input type="password" id="password" name="password" placeholder="パスワード"></td>
	  </tr>
	  <input type="hidden" id="command" name="command" value="accountDelete">
	  <tr>
	    <td></td>
	    <td style="text-align:center;"><input type="submit" value="削除"></td>
	  </tr>
	</table>
      </form>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
