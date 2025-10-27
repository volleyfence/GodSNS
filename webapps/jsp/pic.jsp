<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>

<!DOCTYPE html>
<html lang="ja">
  <head>

    <%
       try{
       String tmp = session.getAttribute("filename").toString();
       String filename = new String(tmp.getBytes("8859_1"),"UTF-8");
       tmp = session.getAttribute("id").toString();
       String id = new String(tmp.getBytes("8859_1"),"UTF-8");

       Picture picture[] = null;
       PictureDB pictureDB = new PictureDB();
       AccountDB accountDB = new AccountDB();
       picture = pictureDB.getPicture(filename);
       if(picture.length == 0){
       response.sendRedirect("../GodServlet?command=toHome");
       }else{
       String[] date = picture[0].getTime().split(" ");
       String[] date1 = date[0].split("/");
       String[] date2 = date[1].split(":");

       String body = picture[0].getBody();
       body = body.replaceAll("\n", "<br>");

       GodDB godDB = new GodDB();
       %>

    <meta charset="UTF-8">
    <title><%=accountDB.getName(picture[0].getId()) %>さんの投稿/GodSNS</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript">
      <!--
	 function disp(){
	 if(window.confirm('本当にこの画像を削除しますか？')){
	 location.href = "../GodServlet?command=pictureDelete&url=<%=filename %>";
	 }
	 else{
	 window.alert('キャンセルされました');
	 }
	 }
	 // -->
    </script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>

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
      <h2>
	<%=date1[0] %>年<%=Integer.parseInt(date1[1]) %>月<%=Integer.parseInt(date1[2]) %>日<%=Integer.parseInt(date2[0]) %>時<%=Integer.parseInt(date2[1]) %>分
	<br>
	<% if(picture[0].getId().equals(id) == true){ %>
	あなた
	<% }else{ %>
        <%=accountDB.getName(picture[0].getId()) %>さん
	<% } %>
	の投稿</h2>
      <img src="../GodServlet?command=showImage&filename=<%=picture[0].getName() %>" style="height: 230px;">
      <p style="width:50%;"><%=body %></p>
      <%
      	 if(picture[0].getId().equals(id) == false && !(id.equals("god_suzuki29"))){
	 if(godDB.search(id, filename)){ %>
      <p style="font-size: 30px;"><input type="image" src="image/god_after.png" onClick="location.href='../GodServlet?filename=<%=filename %>&command=godDelete'"><%=picture[0].getGod() %></p>
      <% }else{ %>
      <p style="font-size: 30px;"><input type="image" src="image/god.png" onClick="location.href='../GodServlet?filename=<%=filename %>&command=godAdd'"><%=picture[0].getGod() %></p>
      <% }
         }else{ %>
      <p style="font-size: 30px;"><img src="image/god_after.png"><%=picture[0].getGod() %></p>
      <input type="button" onClick="disp()" value="画像削除">
      <% } %>
    </center>
  </body>
</html>
<% }}catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
