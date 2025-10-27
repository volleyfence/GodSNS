<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>
<%@page import = "java.net.URLEncoder"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>

    <%
       try{
       String tmp;
       tmp = session.getAttribute("id").toString();
       String id = new String(tmp.getBytes("8859_1"),"UTF-8");
       if(Admin.adminCheck(id) == false){
       response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
       }else{
       %>

    <title>管理者画面/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">
  </head>
  <body>
    <center>
      <header>
	<h1>
	  <table border="0" style="font: 12px; width: 100%;">
	    <tr>
	      <td align="center"><input type="image" src="image/back.png" alt="前のページへ戻る" title="前のページへ戻る" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>
      <br>
      <div style="width:500px;">
	<h2>管理者画面</h2>
	<br>

	<a href="/godsns/GodServlet?command=toAdminCreate"><img height="100" onmouseover="this.src='image/create_account_after.png'" onmouseout="this.src='image/create_account.png'" alt="アカウント作成" title="アカウント作成" src="image/create_account.png"></a>
	<a href="/godsns/GodServlet?command=toAdminAccount"><img height="100" onmouseover="this.src='image/account_list_after.png'" onmouseout="this.src='image/account_list.png'" alt="アカウント一覧" title="アカウント一覧" src="image/account_list.png"></a>
	<a href="/godsns/GodServlet?command=toAdminMessage"><img height="100" onmouseover="this.src='image/message_list_after.png'" onmouseout="this.src='image/message_list.png'" alt="掲示板投稿一覧" title="掲示板投稿一覧" src="image/message_list.png"></a>
	<a href="/godsns/GodServlet?command=toAdminPicture"><img height="100" onmouseover="this.src='image/picture_list_after.png'" onmouseout="this.src='image/picture_list.png'" alt="写真一覧" title="写真一覧" src="image/picture_list.png"></a>
	<a href="/godsns/GodServlet?command=toAdminChangePassword"><img height="100" onmouseover="this.src='image/change_password_after.png'" onmouseout="this.src='image/change_password.png'" alt="パスワード変更" title="パスワード変更" src="image/change_password.png"></a>
	<a href="/godsns/GodServlet?command=toAdminReset"><img height="100" onmouseover="this.src='image/reset_after.png'" onmouseout="this.src='image/reset.png'" alt="初期化" title="初期化" src="image/reset.png"></a>
      </div>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
