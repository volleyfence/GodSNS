<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>

<html>
  <head>
    <title>ERROR/GodSNS</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">
  </head>
  <body>
    <%
       String error = request.getParameter("error");
       %>
    <center>
      <header>
	<h1>
	  <table border="0" style="font: 12px; width: 70%;">
	    <tr>
	      <td align="center"><input type="image" src="image/back.png" value="前のページへ戻る" onclick="history.back()"/></td>
	      <td align="center"><img src="image/logo.png"></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>
      <h2 style="color:red;">ERROR</h2>
      <p><%=error%></p>
    </center>
  </body>
</html>
