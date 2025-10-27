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
       tmp = session.getAttribute("year").toString();
       String year = new String(tmp.getBytes("8859_1"),"UTF-8");
       tmp = session.getAttribute("month").toString();
       String month = new String(tmp.getBytes("8859_1"),"UTF-8");
       tmp = session.getAttribute("day").toString();
       String day = new String(tmp.getBytes("8859_1"),"UTF-8");
       if(month.length() == 1){
       month = "0" + month;
       }
       if(day.length() == 1){
       day = "0" + day;
       }
       String date = year + "/" + month + "/" + day;
       %>

    <title><%= year + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日の写真" %>/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">
  </head>
  <body>
    <center>
      <header>
	<h1>
	  <table border="0" style="font: 12px; width: 100%;">
	    <tr>
	      <td align="center"><input type="image" src="image/back.png" alt="前のページへ戻る" title="前のページへ戻る" onclick="location.href='../GodServlet?command=back'"/></td>
	      <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>
      <br>
      <marquee behavior="alternate" direction="up" height="50"><marquee direction="right"><h2><%= year + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日の写真" %></h2></marquee></marquee>
      <p><a href="/godsns/GodServlet?command=toRank&year=<%=year %>&month=<%=month %>&day=<%=day %>"><img height="100" onmouseover="this.src='image/ranking_after.png'" onmouseout="this.src='image/ranking.png'" alt="<%= year + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日" %>のランキングへ" title="<%= year + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日" %>のランキングへ" src="image/ranking.png"></a></p>
      <p>写真をクリックして投稿の詳細を表示</p>
      <hr>
      <%
	 Picture[] picture = null;
	 PictureDB pictureDB = new PictureDB();
	 AccountDB accountDB = new AccountDB();
	 picture = pictureDB.getAllPicture("time", date);
	 if(picture.length == 0){ %>
      <p>今日の投稿はありません</p>
      <% }else{ %>
      <% for(int i = 0; i < picture.length; i++){ %>
	 <a href="../GodServlet?command=toPic&filename=<%=picture[i].getName() %>"><img src="../GodServlet?command=showImage&filename=<%=picture[i].getName() %>" alt="<%=accountDB.getName(picture[i].getId()) %>さん" width="150" height="150" border="1"></a>
	 <% }
	    } %>
    </center>
  </body>
</html>
<% }catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
