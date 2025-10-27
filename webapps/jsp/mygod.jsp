<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>

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
       if(id.equals("god_suzuki29") == true){
       response.sendRedirect("/godsns/jsp/admin.jsp");
       }else{
       tmp = request.getParameter("message");
       if(tmp != null){
       message = tmp;
       }
       %>

    <title>神だね一覧/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript">
      <!--
         function disp(filename){
	 if(window.confirm('「神だね」を削除しますか？')){
	 location.href = "../GodServlet?command=myGodDelete&filename=" + filename;
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
      <%
	 PictureDB pictureDB = new PictureDB();
	 GodDB godDB = new GodDB();
	 God[] god = godDB.getAllGod(id);
	 %>

      <h2>神だね一覧</h2>
      <hr>
      <% if(god.length > 0){ %>
      <table border="1" cellspacing="0" style="text-align:center;">
	<tr bgcolor="#e6e6fa">
	  <td>写真</td>
	  <td>時間</td>
	  <td></td>
	</tr>
	<% for(int i = 0; i < god.length; i++){ %>
	   <tr bgcolor="<% if(i % 2 == 0){ %><%="#e0ffff" %><% }else{ %><%="#ffffff" %><% } %>">
	     <td><a href="../GodServlet?command=toPic&filename=<%=god[i].getName() %>"><img src="../GodServlet?command=showImage&filename=<%=god[i].getName() %>" width="70" height="70"></a></td>
	     <td><%=god[i].getTime() %></td>
	     <td><input type="button" onClick="disp('<%=god[i].getName() %>')" value="神だね削除"></td>
	   </tr>
	   <% } %>
      </table>
      <% }else{%>
      <p>「神だね」はありません</p>
      <% } %>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
