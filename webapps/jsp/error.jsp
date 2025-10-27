<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <title>ERROR/GodSNS</title>
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

  </head>
  <body>
    <%
       try{
       String error = request.getParameter("error");
       %>

    <center>
      <header>
	<h1>
	  <table border="0" style="font: 12px; width: 100%;">
	    <tr>
	      <td align="center"><input type="image" src="image/back.png" alt="前のページへ戻る" title="前のページへ戻る" onclick="history.back()"/></td>
	      <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>
      <h2 style="color:red;">ERROR</h2>
      <p><font size="2"></font><%=error%></p>
    </center>
  </body>
</html>
<% }catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
