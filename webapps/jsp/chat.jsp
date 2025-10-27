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
       tmp = session.getAttribute("select1").toString();
       String select1 = new String(tmp.getBytes("8859_1"),"UTF-8");
       String se1name = select1;
       tmp = session.getAttribute("select2").toString();
       String select2 = new String(tmp.getBytes("8859_1"),"UTF-8");
       String se2name = select2;

       String id = session.getAttribute("id").toString();
       String name = session.getAttribute("name").toString();

       ChatName chatname = new ChatName();
       %>

    <title><%=chatname.getName(select1, select2) + "掲示板"%>/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/chat.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script language="JavaScript">
      <!--
	 function submitCheck(){
	 if(document.forms[0].body.value == ""){
         alert("メッセージが入力されていません");
         return false;
	 }
	 return true;
	 }
	-->
    </script>

    <script type="text/javascript">
      <!--
         function disp(m_id){
	 if(window.confirm('本当にこの投稿を削除しますか？')){
	 location.href = "../GodServlet?command=messageDelete&m_id=" + m_id;
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
	      <td align="center"><input type="image" src="image/back.png" alt="前のページへ戻る" title="前のページへ戻る" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	      <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	    </tr>
	  </table>
	</h1>
      </header>
      <br>

      <%
	 Message[] message = null;
	 MessageDB messageDB = new MessageDB();
	 AccountDB accountDB = new AccountDB();
	 if(!(se2name.equals("")))
	 se1name += "_";
	 message = messageDB.getAllMessage(se1name + se2name);
	 String from = "";
	 %>

      <h2><%=chatname.getName(select1, select2) + "掲示板"%></h2>

    </center>

    <div id="chat-frame">
      <% for(int i = 0; i < message.length; i++){
			    if(id.equals(message[i].getId())){
			    from = " mytalk";
			    }
			    else{
			    from = "";
			    }
			    %>

	 <p class="chat-talk<%=from %>" <% if(i == message.length - 1){ %><%= "id=\"bottom\"" %> <% } %>>
	   <% if(!(id.equals(message[i].getId()))){ %>
           <%=accountDB.getName(message[i].getId()) + "さん" + "     " + message[i].getTime() %>
	   <% }else{ %>
	   <%=message[i].getTime() %>
	   <% } %>
	   <span class="talk-icon">
	     <br>
             <img src="${pageContext.request.contextPath}/jsp/image/<%=accountDB.getIcon(message[i].getId()) %>.png" alt="<%=accountDB.getName(message[i].getId()) + "さん" %>" width="50" height="50"/>
	   </span>
	   <%
	      String body = message[i].getBody();

	      body = body.replaceAll("\n", "<br>");
	      %>

	   <span class="talk-content"><%=body %></span>
	   <% if(id.equals(message[i].getId()) || id.equals("godsns")){ %>
	   <input type="button" onClick="disp(<%=message[i].getMId() %>)" value="投稿削除">
	   <% } %>

	 </p>
	 <br>
	 <% } %>
    </div>
    <br>

    <script src="http://code.jquery.com/jquery-1.12.3.min.js"></script>

    <script>
      $(function() {
      var m = $('#bottom');
      $(window)
      .scrollTop(m.offset().top)
      .scrollLeft(m.offset().left);
      });
    </script>
    <center>
      <div id="form">
	<hr>
	<p><form action="../GodServlet" method="GET" onSubmit="return submitCheck()">
	    <textarea id="body" name ="body" cols="40" rows="4" style="resize: none;" placeholder="メッセージ(500文字まで)" maxlength="500"></textarea>
	    <input type="hidden" id="select1" name="select1" value="<%=select1%>">
	    <input type="hidden" id="select2" name="select2" value="<%=select2%>">
	    <input type="hidden" id="command" name="command" value="messageAdd">
	    <input type="submit" value="送信">
            <input type="button" value="ページの更新" onclick="location.reload()">
	</form></p>
      </div>
    </center>
  </body>
</html>
<% }catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
