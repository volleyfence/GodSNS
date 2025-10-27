<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>
<%@page import = "java.net.URLEncoder"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    <meta charset="UTF-8">
    <title>初期化/GodSNS</title>
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
	 if(window.confirm('SNS内の全てのデータを削除します\nSNSを初期化しますか？')){
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
      <br>
      <%="<p><font color=\"red\">" + message + "</font></p>" %>
      <h2>初期化</h2>
      <p>SNSを初期化します</p>
      <hr>
      <form action="../GodServlet" method="POST" onSubmit="return submitCheck()">
	<table style="text-align:right;">
	  <tr>
	    <td>パスワード</td>
	    <td><input type="password" id="password" name="password" placeholder="パスワード"></td>
	  </tr>
	  <input type="hidden" id="command" name="command" value="adminReset">
	  <tr>
	    <td></td>
	    <td style="text-align:center;"><input type="submit" value="初期化"></td>
	  </tr>
	</table>
      </form>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
