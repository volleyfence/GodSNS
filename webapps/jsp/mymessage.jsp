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

    <title>掲示板投稿一覧/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript">
      <!--
         function disp(m_id){
	 if(window.confirm('投稿を削除しますか？')){
	 location.href = "../GodServlet?command=myMessageDelete&m_id=" + m_id;
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
	 MessageDB messageDB = new MessageDB();
	 Message[] messages = null;
	 messages = messageDB.getMyMessage(id);
	 %>

      <h2>掲示板投稿一覧</h2>
      <hr>
      <% if(messages.length != 0){ %>
      <table border="1" cellspacing="0" style="text-align:center;">
	<tr bgcolor="#e6e6fa">
	  <td>id</td>
	  <td>メッセージ</td>
	  <td>投稿時間</td>
	  <td>チャット名</td>
	  <td></td>
	</tr>
	<% for(int i = 0; i < messages.length; i++){ %>
	   <tr bgcolor="<% if(i % 2 == 0){ %><%="#e0ffff" %><% }else{ %><%="#ffffff" %><% } %>">
	     <td><%=messages[i].getId() %></td>
	     <td><%=messages[i].getBody().replaceAll("\n", "<br>") %></td>
	     <td><%=messages[i].getTime() %></td>
	     <% String select1 = "";
		String select2 = "";
		tmp = messages[i].getChatName();
		if(tmp.contains("_")){
		String[] select = tmp.split("_");
		select1 = select[0];
		select2 = select[1];
		}
		else{
		select1 = tmp;
		}
		ChatName chatName = new ChatName();
		%>
	     <td><%=chatName.getName(select1, select2) %></td>
	     <td><input type="button" onClick="disp('<%=messages[i].getMId() %>')" value="掲示板投稿削除"></td>
	   </tr>
	   <% } %>
      </table>
      <% }else{%>
      <p>掲示板投稿はありません</p>
      <% } %>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
