<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>
<%@page import = "java.net.URLEncoder"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>

    <meta charset="UTF-8">

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

    <title>写真投稿一覧/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript">
      <!--
         function disp(filename){
	 if(window.confirm('写真を削除しますか？')){
	 location.href = "../GodServlet?command=adminPictureDelete&filename=" + filename;
	 }
	 else{
	 window.alert('キャンセルされました');
	 }
	 }
	 // -->
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
      <br>
      <%="<p><font color=\"red\">" + message + "</font></p>" %>
      <%
	 PictureDB pictureDB = new PictureDB();
	 Picture[] picture = null;
	 picture = pictureDB.getAllPicture();
	 %>

      <h2>写真投稿一覧</h2>
      <hr>
      <% if(picture.length > 0){ %>
      <table border="1" cellspacing="0" style="text-align:center;">
	<tr bgcolor="#e6e6fa">
	  <td>picture</td>
	  <td>id</td>
	  <td>filename</td>
	  <td>body</td>
	  <td>time</td>
	  <td>god</td>
	  <td></td>
	</tr>
	<% for(int i = 0; i < picture.length; i++){ %>
	   <tr bgcolor="<% if(i % 2 == 0){ %><%="#e0ffff" %><% }else{ %><%="#ffffff" %><% } %>">
	     <td><a href="../GodServlet?command=toPic&filename=<%=picture[i].getName() %>"><img src="../GodServlet?command=showImage&filename=<%=picture[i].getName() %>" width="70" height="70"></a></td>
	     <td><%=picture[i].getId() %></td>
	     <td><%=picture[i].getName() %></td>
	     <td><%=picture[i].getBody() %></td>
	     <td><%=picture[i].getTime() %></td>
	     <td><%=picture[i].getGod() %></td>
	     <td><input type="button" onClick="disp('<%=picture[i].getName() %>')" value="写真削除"></td>
	   </tr>
	   <% } %>
      </table>
      <% }else{%>
      <p>写真投稿はありません</p>
      <% } %>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
