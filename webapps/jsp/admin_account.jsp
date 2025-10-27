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

    <title>アカウント一覧/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript">
      <!--
         function disp(id){
	 if(window.confirm(id + 'のアカウントを削除しますか？')){
	 location.href = "../GodServlet?command=adminAccountDelete&id=" + id;
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
	 AccountDB accountDB = new AccountDB();
	 Account[] account = null;
	 account = accountDB.getAllAccount();
	 %>

      <h2>アカウント一覧</h2>
      <hr>
      <% if(account.length > 1){ %>
      <table border="1" cellspacing="0" style="text-align:center;">
	<tr bgcolor="#e6e6fa">
	  <td>id</td>
	  <td>name</td>
	  <td>firstLogin</td>
	  <td>lastLogin</td>
	  <td>loginCount</td>
	  <td></td>
	</tr>
	<% for(int i = 0; i < account.length; i++){
			      if(account[i].getId().equals("god_suzuki29") == false){%>
	   <tr bgcolor="<% if(i % 2 == 0){ %><%="#e0ffff" %><% }else{ %><%="#ffffff" %><% } %>">
	     <td><%=account[i].getId() %></td>
	     <td><%=account[i].getName() %></td>
	     <td><%=account[i].getFirstLogin() %></td>
	     <td><%=account[i].getLastLogin() %></td>
	     <td><%=account[i].getLoginCount() %></td>
	     <td><input type="button" onClick="disp('<%=account[i].getId() %>')" value="アカウント削除"></td>
	   </tr>
	   <% }
	      }%>
      </table>
      <% }else{%>
      <p>アカウントはありません</p>
      <% } %>

    </center>

  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
