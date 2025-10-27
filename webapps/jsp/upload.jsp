<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "godsns.*"%>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>
    
    <title>画像アップロード/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">
    
    <script type="text/javascript"> 
      <!-- 
	 function check(){
	 if(window.confirm('以下の点に気を付けてください\n・肖像権を侵害していない\n・プロパティに個人情報は含まれていない\n送信してよろしいですか？')){
	 return true;
	 }
	 else{
	 window.alert('キャンセルされました'); 
	 return false;
	 }
	 }
	 // -->
    </script>
    
    <script type="text/javascript">
      <!--
	 window.onload = function() {
	 document.getElementById("button").disabled = true;
	 }
	 function selectFile() {
	 if (document.getElementById("datafile").value === "") {
	 document.getElementById("button").disabled = true;
	 }
	 else {
	 document.getElementById("button").disabled = false;
	 }
	 }
	 // -->
    </script>
    
    <%
       try{
       String tmp;
       tmp = session.getAttribute("id").toString();
       String id = new String(tmp.getBytes("8859_1"),"UTF-8");
       %>
    
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
      <h2>画像アップロード</h2>
      <form action="../GodServlet" method="POST" enctype="multipart/form-data" onSubmit="return check()">
	<p>画像を選択(3MB以内)：</p>
	<p><input type="file" id="datafile" name="datafile" onchange="selectFile()" accept="image/*"></p>
	<div class="preview"></div>
	<p>メッセージ：</p>
	<textarea id="body" name="body" cols="40" rows="4" placeholder="コメント(500文字まで)" maxlength="500"></textarea>
	<input type="hidden" id="command" name="command" value="pictureAdd">
	<p><input type="submit" id="button" value="送信する"></p>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery-2.1.0.js"></script>
	<script type="text/javascript">
	  $(function(){
	  $('form').on('change', 'input[type="file"]', function(e) {
	  var file = e.target.files[0],
          reader = new FileReader(),
          $preview = $(".preview");
          t = this;
	  if(file.type.indexOf("image") < 0){
					  return false;
					  }
					  reader.onload = (function(file) {
					  return function(e) {
					  $preview.empty();
					  $preview.append($('<img>').attr({
					  src: e.target.result,
					  width: "150px",
					  class: "preview",
					  title: file.name
					  }));
					  };
					  })(file);
					  
					  reader.readAsDataURL(file);
					  });
					  });
					  </script>
      </form>
    </center>
  </body>
</html>
<% }catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
