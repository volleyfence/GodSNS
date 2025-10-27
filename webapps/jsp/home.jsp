<%@page contentType = "text/html; charset = UTF-8"  pageEncoding="UTF-8" %>
<%@page import = "java.sql.*"%>
<%@page import = "godsns.*"%>

<!DOCTYPE html>
<html lang="ja">
  <head>

    <meta charset="UTF-8">
    <title>ホーム/GodSNS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/home.css">
    <link rel="icon" href="${pageContext.request.contextPath}/jsp/image/favicon.ico">

    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery-1.3.2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery.dependent.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/god.js"></script>

    <script type="text/javascript">
      $(document).ready(function(){
      $('#id2').dependent({
      parent:'id',//親のid名
      group: 'selectable'
      });
      });
    </script>

    <%
       try{
       String tmp;
       tmp = session.getAttribute("id").toString();
       String id = new String(tmp.getBytes("8859_1"),"UTF-8");

       int year = Integer.parseInt(session.getAttribute("year").toString());
       int month = Integer.parseInt(session.getAttribute("month").toString());
       if(month < 1){
		  month = 1;
		  }
		  if(month > 12){
       month = 12;
       }

       MyCalendar myCalendar = new MyCalendar(year, month);
       int last = myCalendar.getLastDay();
       int offset = myCalendar.getOffset();

       String today_y = session.getAttribute("today_y").toString();
       String today_m = session.getAttribute("today_m").toString();
       String today_d = session.getAttribute("today_d").toString();

       String today = today_y + "/" + today_m + "/" + today_d;
       AccountDB accountDB = new AccountDB();
       Picture[] picture = null;
       PictureDB pictureDB = new PictureDB();
       %>

  </head>

  <body>

    <header>
      <h1>
	<table border="0" style="width: 100%;">
          <tr>
	    <% if(id.equals("god_suzuki29")){ %><td align="center"><input type="image" src="image/admin.png" alt="管理者ページへ" title="管理者ページへ" onClick="location.href='../GodServlet?command=toAdmin'"/></td><% }else{ %>
	    <td align="center"><input type="image" src="image/menu.png" alt="メニューへ" title="メニューへ" onClick="location.href='../GodServlet?command=toMenu'"/></td><% } %>
	    <td align="center"><input type="image" src="image/logo.png" alt="ホームへ" title="ホームへ" onclick="location.href='../GodServlet?command=toHome'"/></td>
	    <td align="center"><input type="image" src="image/logout.png" alt="ログアウト" title="ログアウト" onclick="logoutCheck()"/></td>
	  </tr>
	</table>
      </h1>

    </header>
    <br>
    <div class="home_wrap">
      <div id="picture" class="home_top">
	<section>
	  <center>
	    <h2>写真投稿</h2>
	    <table border="0" cellspacing="1" cellpadding="1" bgcolor="#CCCCCC" style="font: 12px; color: #666666;">
	      <tr>
		<td align="center" colspan="7" bgcolor="#EEEEEE" height="18" style="color: #666666;"><input type="image" src="${pageContext.request.contextPath}/jsp/image/pre.gif" title="前の月のカレンダーに変更" onClick="location.href='/godsns/GodServlet?command=changeMonth&year=<%=year %>&month=<%=month %>&add=-1'"/>  <%=year %>年<% if(month / 10.0 < 1.0){ %>0<% }%><%=month %>月  <input type="image" src="${pageContext.request.contextPath}/jsp/image/next.gif" title="次の月のカレンダーに変更" onClick="location.href='/godsns/GodServlet?command=changeMonth&year=<%=year %>&month=<%=month %>&add=1'"/></td>
	      </tr>
	      <tr>
		<td align="center" width="20" height="18" bgcolor="#FF3300" style="color: #FFFFFF;">日</td>
		<td align="center" width="20" bgcolor="#C7D8ED" style="color: #666666;">月</td>
		<td align="center" width="20" bgcolor="#C7D8ED" style="color: #666666;">火</td>
		<td align="center" width="20" bgcolor="#C7D8ED" style="color: #666666;">水</td>
		<td align="center" width="20" bgcolor="#C7D8ED" style="color: #666666;">木</td>
		<td align="center" width="20" bgcolor="#C7D8ED" style="color: #666666;">金</td>
		<td align="center" width="20" bgcolor="#0000FF" style="color: #FFFFFF;">土</td>
	      </tr>
	      <%
		 int day = -offset + 1;
		 while(day <= last){
			      %>
		 <tr>
		   <%
		      for(int j = 1; j <= 7; j++){
					  String date, year_tmp, month_tmp, day_tmp;
					  year_tmp = Integer.toString(year);
					  month_tmp = Integer.toString(month);
					  day_tmp = Integer.toString(day);
					  if(month_tmp.length() == 1)
					  month_tmp = "0" + month;
					  if(day_tmp.length() == 1)
					  day_tmp = "0" + day;
					  date = year_tmp + "/" + month_tmp + "/" + day_tmp;

					  if(j == 1){
					  if(0 < day && day <= last){
							      picture = pictureDB.getAllPicture("time", date);
							      if(picture.length > 0){
						 %>
      					  <td align="center" height="18" bgcolor="<% if(year == Integer.parseInt(today_y) && month == Integer.parseInt(today_m) && day == Integer.parseInt(today_d)){ %>#00ff00<% }else{ %>#FFCC99<% } %>"><a href="/godsns/GodServlet?command=toPicList&year=<%=year %>&month=<%=month %>&day=<%=day %>" style="color: #FF0000;" title="<%= year + "年" + month + "月" + day + "日の投稿へ\n" + picture.length + "件の投稿" %>"><%=day %></a></td>
					  <% }else{ %>
					  <td align="center" height="18" bgcolor="<% if(year == Integer.parseInt(today_y) && month == Integer.parseInt(today_m) && day == Integer.parseInt(today_d)){ %>#00ff00<% }else{ %>#FFCC99<% } %>"><font color="#FF0000" title="<%= year + "年" + month + "月" + day + "日の投稿はありません" %>"><%=day %></font></td>
					  <% }}else{ %>
					  <td align="center" height="18" bgcolor="#FFCC99"></td>

					  <%
					     }}else if(j == 2 || j == 3 || j == 4 || j == 5 || j == 6){
					     if(0 < day && day <= last){
								 picture = pictureDB.getAllPicture("time", date);
								 if(picture.length > 0){
						    %>
					     <td align="center" bgcolor="<% if(year == Integer.parseInt(today_y) && month == Integer.parseInt(today_m) && day == Integer.parseInt(today_d)){ %>#00ff00<% }else{ %>#FFFFFF<% } %>"><a href="/godsns/GodServlet?command=toPicList&year=<%=year %>&month=<%=month %>&day=<%=day %>" style="color: #666666;" title="<%= year + "年" + month + "月" + day + "日の投稿へ\n" + picture.length + "件の投稿" %>"><%=day %></a></td>
					     <% }else{ %>
					     <td align="center" height="18" bgcolor="<% if(year == Integer.parseInt(today_y) && month == Integer.parseInt(today_m) && day == Integer.parseInt(today_d)){ %>#00ff00<% }else{ %>#FFFFFF<% } %>"><font color="#666666" title="<%= year + "年" + month + "月" + day + "日の投稿はありません" %>"><%=day %></font></td>
					     <% }}else{ %>
					     <td align="center" height="18" bgcolor="#FFFFFF"></td>

					     <%
						}}else if(j == 7){
						if(0 < day && day <= last){
								    picture = pictureDB.getAllPicture("time", date);
								    if(picture.length > 0){
						       %>
						<td align="center" bgcolor="<% if(year == Integer.parseInt(today_y) && month == Integer.parseInt(today_m) && day == Integer.parseInt(today_d)){ %>#00ff00<% }else{ %>#A6C0E1<% } %>"><a href="/godsns/GodServlet?command=toPicList&year=<%=year %>&month=<%=month %>&day=<%=day %>" style="color: #0000FF;" title="<%= year + "年" + month + "月" + day + "日の投稿へ\n" + picture.length + "件の投稿" %>"><%=day %></a></td>
						<% }else{ %>
						<td align="center" height="18" bgcolor="<% if(year == Integer.parseInt(today_y) && month == Integer.parseInt(today_m) && day == Integer.parseInt(today_d)){ %>#00ff00<% }else{ %>#A6C0E1<% } %>"><font color="#0000FF" title="<%= year + "年" + month + "月" + day + "日の投稿はありません" %>"><%=day %></font></td>
						<% }}else{ %>
						<td align="center" height="18" bgcolor="#A6C0E1"></td>

						<% }} %>
						<% day++;} %>
		 </tr>
		 <% } %>

	    </table>
	    <br>

	    <%
	       picture = pictureDB.getAllPicture("god", today);
	       int rank = 0;
	       int god = -1;
	       %>

	    <div class="ranking_wrap">
	      <a href="/godsns/GodServlet?command=toRank&year=<%=today_y %>&month=<%=today_m %>&day=<%=today_d %>" title="今日のランキングへ">今日のランキング</a>
	      <div class="ranking">
		<% if(picture.length == 0){ %>
		<p>今日の投稿はありません</p>
		<% } else{ %>
		<%
	           for(int i = 0; i < picture.length && i < 10; i++){
				      if(god != picture[i].getGod()){
				      rank++;
				      god = picture[i].getGod();
				      }
				      if(god == 0){
				      %>
		   <p>-位</p>
		   <% }else{ %>
		   <p><%=rank %>位</p>
		   <% } %>
                   <a href="../GodServlet?command=toPic&filename=<%=picture[i].getName() %>"><img src="../GodServlet?command=showImage&filename=<%=picture[i].getName() %>" alt="<%=accountDB.getName(picture[i].getId()) %>さん" width="150" height="150" border="1"></a>
		   <hr>
		   <% }
		      } %>
	      </div>
	    </div>

	    <a href="/godsns/GodServlet?command=toUpload" title="写真投稿ページへ">写真を投稿する</a>

	  </center>
	</section>
      </div>

      <div id="chat" class="home_top">
	<section>
	  <center>
	    <h2>学生掲示板</h2>

	    <div class="out">
	      <div class="box">
		<h2>全校掲示板</h2>
		<form action="/godsns/GodServlet" method="GET" id="all">
		  <select name="select1">
		    <option value="school">学校掲示板</option>
		  </select>
		  <input type="hidden" name="select2" value="">
		  <input type="hidden" name="command" value="toChat">
		  <p><input type="submit" value="GO"></p>
		</form>
	      </div>
	      <hr>
	      <div class="box">
		<h2>学科掲示板</h2>
		<form action="/godsns/GodServlet" method="GET" id="cource">
		  <select name="select1">
		    <option value="doboku">土木工学科・専攻</option>
		    <option value="kennchiku">建築学科・専攻</option>
		    <option value="kikai">機械工学科・専攻</option>
		    <option value="dennki">電気電子工学科・専攻</option>
		    <option value="seimei">生命応用化学科・専攻</option>
		    <option value="joho">情報工学科・専攻</option>
		  </select>
		  <select name="select2">
		    <option value="all">全体</option>
		    <option value="d1">学部1年</option>
		    <option value="d2">学部2年</option>
		    <option value="d3">学部3年</option>
		    <option value="d4">学部4年</option>
		    <option value="c">大学院</option>
		  </select>
		  <input type="hidden" name="command" value="toChat">
		  <p><input type="submit" value="GO"></p>
		</form>
	      </div>
	      <hr>
	      <div class="box">
		<h2>サークル掲示板</h2>
		<form action="/godsns/GodServlet" method="GET" id="club">
		  <select id="id" name="select1" class="selectable">
		    <option value="taiiku" title="体育会">体育会</option>
		    <option value="bunnka" title="学術文化サークル連合会">学術文化サークル連合会</option>
		    <option value="none" title="連合体未加入団体">連合体未加入団体</option>
		  </select>
		  <select id="id2" name="select2" class="selectable">
		    <option value="1" class="体育会">体育会</option>
		    <option value="2" class="体育会">應援團</option>
		    <option value="3" class="体育会">合気道部</option>
		    <option value="4" class="体育会">アメリカンフットボール部</option>
		    <option value="5" class="体育会">空手道部</option>
		    <option value="6" class="体育会">器械体操部</option>
		    <option value="7" class="体育会">弓道部</option>
		    <option value="8" class="体育会">剣道部</option>
		    <option value="9" class="体育会">硬式ソフトボール部</option>
		    <option value="10" class="体育会">硬式庭球部</option>
		    <option value="11" class="体育会">硬式野球部</option>
		    <option value="12" class="体育会">ゴルフ部</option>
		    <option value="13" class="体育会">サッカー部</option>
		    <option value="14" class="体育会">射撃部</option>
		    <option value="15" class="体育会">柔道部</option>
		    <option value="16" class="体育会">水泳部</option>
		    <option value="17" class="体育会">スキー部</option>
		    <option value="18" class="体育会">ソフトテニス部</option>
		    <option value="19" class="体育会">卓球部</option>
		    <option value="20" class="体育会">軟式野球部</option>
		    <option value="21" class="体育会">日本拳法部</option>
		    <option value="22" class="体育会">バスケットボール部</option>
		    <option value="23" class="体育会">バドミントン部</option>
		    <option value="24" class="体育会">バレーボール部</option>
		    <option value="25" class="体育会">ハンドボール部</option>
		    <option value="26" class="体育会">ボクシング部</option>
		    <option value="27" class="体育会">洋弓部</option>
		    <option value="28" class="体育会">ラグビー部</option>
		    <option value="29" class="体育会">ラクロス部</option>
		    <option value="30" class="体育会">陸上競技部</option>

		    <option value="1" class="学術文化サークル連合会">学術文化サークル連合会</option>
		    <option value="2" class="学術文化サークル連合会">囲碁・将棋部</option>
		    <option value="3" class="学術文化サークル連合会">演劇部</option>
		    <option value="4" class="学術文化サークル連合会">桜花一門YOSAKOI隊</option>
		    <option value="5" class="学術文化サークル連合会">音楽研究会</option>
		    <option value="6" class="学術文化サークル連合会">音響研究会</option>
		    <option value="7" class="学術文化サークル連合会">滑空研究会</option>
		    <option value="8" class="学術文化サークル連合会">管弦楽部</option>
		    <option value="9" class="学術文化サークル連合会">機械研究会</option>
		    <option value="10" class="学術文化サークル連合会">建築研究会</option>
		    <option value="11" class="学術文化サークル連合会">サイクリング部</option>
		    <option value="12" class="学術文化サークル連合会">茶道部</option>
		    <option value="13" class="学術文化サークル連合会">自動車部</option>
		    <option value="14" class="学術文化サークル連合会">写真部</option>
		    <option value="15" class="学術文化サークル連合会">ジャグリング愛好会</option>
		    <option value="16" class="学術文化サークル連合会">情報研究会</option>
		    <option value="17" class="学術文化サークル連合会">吹奏楽部</option>
		    <option value="18" class="学術文化サークル連合会">赤十字奉仕団</option>
		    <option value="19" class="学術文化サークル連合会">鐵道研究会</option>
		    <option value="20" class="学術文化サークル連合会">天文研究会</option>
		    <option value="21" class="学術文化サークル連合会">動画漫画研究会</option>
		    <option value="22" class="学術文化サークル連合会">美術部</option>
		    <option value="23" class="学術文化サークル連合会">木匠塾</option>
		    <option value="24" class="学術文化サークル連合会">モダンジャズ研究会</option>
		    <option value="25" class="学術文化サークル連合会">ワンダーフォーゲル部</option>

		    <option value="1" class="連合体未加入団体">北桜祭実行委員会</option>
		    <option value="2" class="連合体未加入団体">アルティメット同好会</option>
		    <option value="3" class="連合体未加入団体">異文化コミュニケーション研究会</option>
		    <option value="4" class="連合体未加入団体">カンジュースポーツ同好会</option>
		    <option value="5" class="連合体未加入団体">基礎スキー研究同好会</option>
		    <option value="6" class="連合体未加入団体">ゲレンデスキー同好会</option>
		    <option value="7" class="連合体未加入団体">現代視覚文化研究同好会</option>
		    <option value="8" class="連合体未加入団体">護身武道研究会</option>
		    <option value="9" class="連合体未加入団体">書道会</option>
		    <option value="10" class="連合体未加入団体">General Sports 同好会</option>
		    <option value="11" class="連合体未加入団体">ジョイフルテニス同好会</option>
		    <option value="12" class="連合体未加入団体">杖道同好会</option>
		    <option value="13" class="連合体未加入団体">スクエアスポーツ同好会</option>
		    <option value="14" class="連合体未加入団体">3on3 since1891 同好会</option>
		    <option value="15" class="連合体未加入団体">創作研究同好会</option>
		    <option value="16" class="連合体未加入団体">ダーツ同好会</option>
		    <option value="17" class="連合体未加入団体">ダンス同好会</option>
		    <option value="18" class="連合体未加入団体">地域連携活動研究会</option>
		    <option value="19" class="連合体未加入団体">電気電子工学研究会</option>
		    <option value="20" class="連合体未加入団体">土木女子の会</option>
		    <option value="21" class="連合体未加入団体">トレーディングカードゲーム同好会</option>
		    <option value="22" class="連合体未加入団体">日本大学工学部合唱団</option>
		    <option value="23" class="連合体未加入団体">フォークソング同好会</option>
		    <option value="24" class="連合体未加入団体">フットサル同好会</option>
		    <option value="25" class="連合体未加入団体">文学同好会</option>
		    <option value="26" class="連合体未加入団体">モーターサイクル同好会</option>
		    <option value="27" class="連合体未加入団体">Racing Kart同好会</option>
		  </select>

		  <input type="hidden" name="command" value="toChat">
		  <p><input type="submit" value="GO"></p>
		</form>

	      </div>

	    </div>
	  </center>
          <br>
	</section>
      </div>
    </div>
  </body>
</html>
<% }catch(Exception e){
   response.sendRedirect("../GodServlet?command=timeout");
   } %>
