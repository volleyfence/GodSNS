<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>

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
       if(id.equals("god_suzuki29") == true){
       response.sendRedirect("/godsns/jsp/admin.jsp");
       }else{
       %>

    <title>メニュー/GodSNS</title>
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
      <h2>メニュー</h2>
      <br>
      <a href="/godsns/GodServlet?command=toMyMessage"><img height="100" onmouseover="this.src='image/mymessage_list_after.png'" onmouseout="this.src='image/mymessage_list.png'" alt="掲示板投稿一覧" title="掲示板投稿一覧" src="image/mymessage_list.png"></a>
      <a href="/godsns/GodServlet?command=toMyPicture"><img height="100" onmouseover="this.src='image/mypicture_list_after.png'" onmouseout="this.src='image/mypicture_list.png'" alt="写真投稿一覧" title="写真投稿一覧" src="image/mypicture_list.png"></a>
      <a href="/godsns/GodServlet?command=toMyGod"><img height="100" onmouseover="this.src='image/mygod_list_after.png'" onmouseout="this.src='image/mygod_list.png'" alt="神だね一覧" title="神だね一覧" src="image/mygod_list.png"></a>
      <a href="/godsns/GodServlet?command=toChange"><img height="100" onmouseover="this.src='image/change_info_after.png'" onmouseout="this.src='image/change_info.png'" alt="登録情報変更" title="登録情報変更" src="image/change_info.png"></a>
      <a href="/godsns/GodServlet?command=toAccountDelete"><img height="100" onmouseover="this.src='image/delete_account_after.png'" onmouseout="this.src='image/delete_account.png'" alt="アカウント削除" title="アカウント削除" src="image/delete_account.png"></a>
      </div>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
