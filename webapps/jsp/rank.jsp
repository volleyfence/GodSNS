<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page	import = "godsns.*"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
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
    <meta charset="UTF-8">
    <title><%=Integer.parseInt(year) + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日のランキング" %>/GodSNS</title>
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
      <marquee behavior="alternate" direction="up" height="50"><marquee direction="right"><h2><%=Integer.parseInt(year) + "年" + Integer.parseInt(month) + "月" + Integer.parseInt(day) + "日のランキング" %></h2></marquee></marquee>
      <p>写真をクリックして投稿の詳細を表示</p>
      <hr>
      <%
	 Picture[] picture = null;
	 PictureDB pictureDB = new PictureDB();
	 AccountDB accountDB = new AccountDB();
	 picture = pictureDB.getAllPicture("god", date);
	 int rank = 0;
	 int god = -1;
	 if(picture.length == 0){ %>
      <p>今日の投稿はありません</p>
      <% }else{ %>
      <% for(int i = 0; i < picture.length; i++){
			    if(god != picture[i].getGod()){
			    god = picture[i].getGod();
			    rank++;
			    }
			    if(god == 0){
			    %>
	 <h2>-位</h2>
	 <% }else{ %>
	 <h2><%=rank %>位</h2>
	 <% } %>
	 <% if(god != 0 && 1 <= rank && rank <=3){ %>
				<img src="${pageContext.request.contextPath}/jsp/image/<%=rank %>.png" width="100">
				<% } %>
				<br>
				<br>
				<a href="../GodServlet?command=toPic&filename=<%=picture[i].getName() %>"><img src="../GodServlet?command=showImage&filename=<%=picture[i].getName() %>" alt="<%=accountDB.getName(picture[i].getId()) %>さん" height="200"></a>
				<hr>
				<% }
				   } %>
    </center>
  </body>
</html>
<% }catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
