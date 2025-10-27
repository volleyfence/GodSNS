import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import godsns.AccountDB;
import godsns.Admin;
import godsns.GodDB;
import godsns.MessageDB;
import godsns.PictureDB;

@WebServlet("/GodServlet")
@MultipartConfig(location="C:\\Users\\Masato\\program(Java)\\godsns\\webapps\\upload\\tmp" , maxFileSize=3145728)
public class GodServlet extends HttpServlet{
	private final int historySize = 5;

	// GETの取得
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		doAction(request, response);
	}

	// POSTの取得
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		doAction(request, response);
	}

	// 要求されたコマンドの判別
	private void doAction(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;

		String command = null;
		ServletContext sc = getServletContext();

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		try{
			tmp = request.getParameter("command");
			command = new String(tmp.getBytes("8859_1"),"UTF-8");

			// コマンドにより要求の判別
			if(command.equals("login"))
				login(request, response);
			else if(command.equals("create"))
				create(request, response);
			else if(command.equals("messageAdd"))
				messageAdd(request, response);
			else if(command.equals("messageDelete"))
				messageDelete(request, response);
			else if(command.equals("myMessageDelete"))
				myMessageDelete(request, response);
			else if(command.equals("pictureAdd"))
				pictureAdd(request, response);
			else if(command.equals("pictureDelete"))
				pictureDelete(request, response);
			else if(command.equals("myPictureDelete"))
				myPictureDelete(request, response);
			else if(command.equals("godAdd"))
				godAdd(request,response);
			else if(command.equals("godDelete"))
				godDelete(request,response);
			else if(command.equals("myGodDelete"))
				myGodDelete(request, response);
			else if(command.equals("changeIcon"))
				changeIcon(request, response);
			else if(command.equals("changeName"))
				changeName(request, response);
			else if(command.equals("changePassword"))
				changePassword(request, response);
			else if(command.equals("accountDelete"))
				accountDelete(request, response);
			else if(command.equals("adminCreate"))
				adminCreate(request, response);
			else if(command.equals("adminAccountDelete"))
				adminAccountDelete(request, response);
			else if(command.equals("adminMessageDelete"))
				adminMessageDelete(request, response);
			else if(command.equals("adminPictureDelete"))
				adminPictureDelete(request, response);
			else if(command.equals("adminChangePassword"))
				adminChangePassword(request, response);
			else if(command.equals("adminReset"))
				adminReset(request, response);
			else if(command.equals("changeMonth"))
				changeMonth(request,response);
			else if(command.equals("showImage"))
				showImage(request,response);
			else if(command.equals("logout"))
				logout(request,response);
			else if(command.equals("timeout"))
				timeout(request,response);
			else if(command.equals("toHome"))
				toHome(request,response);
			else if(command.equals("toPicList"))
				toPicList(request,response);
			else if(command.equals("toPic"))
				toPic(request,response);
			else if(command.equals("toRank"))
				toRank(request,response);
			else if(command.equals("toUpload"))
				toUpload(request,response);
			else if(command.equals("toChat"))
				toChat(request,response);
			else if(command.equals("toMenu"))
				toMenu(request,response);
			else if(command.equals("toMyMessage"))
				toMyMessage(request,response);
			else if(command.equals("toMyPicture"))
				toMyPicture(request,response);
			else if(command.equals("toMyGod"))
				toMyGod(request,response);
			else if(command.equals("toChange"))
				toChange(request,response);
			else if(command.equals("toAccountDelete"))
				toAccountDelete(request,response);
			else if(command.equals("toAdmin"))
				toAdmin(request,response);
			else if(command.equals("toAdminCreate"))
				toAdminCreate(request,response);
			else if(command.equals("toAdminAccount"))
				toAdminAccount(request,response);
			else if(command.equals("toAdminMessage"))
				toAdminMessage(request,response);
			else if(command.equals("toAdminPicture"))
				toAdminPicture(request,response);
			else if(command.equals("toAdminChangePassword"))
				toAdminChangePassword(request,response);
			else if(command.equals("toAdminReset"))
				toAdminReset(request,response);
			else if(command.equals("back"))
				back(request, response);
			else{
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("コマンドエラー","UTF-8"));
			}

		}
		catch(Exception e){
			response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
		}
	}

	// ログイン機能
	private void login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String password = null;
		int result;

		try{
			tmp = request.getParameter("id");
			id = new String(tmp.getBytes("8859_1"),"UTF-8");
			tmp = request.getParameter("password");
			password = new String(tmp.getBytes("8859_1"),"UTF-8");

			AccountDB accountDB = new AccountDB();
			result = accountDB.login(id, password);
			if(result == 0){
				HttpSession session = request.getSession(true);
				session.setAttribute("id", id);
				session.setAttribute("name", accountDB.getName(id));
				session.setAttribute("icon", accountDB.getIcon(id));
				session.setAttribute("login", "ok");
				session.setAttribute("index", "-1");
				toHome(request, response);
			}
			else{
				response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("学生番号もしくはパスワードが違います","UTF-8"));
			}
		}
		catch(Exception e){
			response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
		}
	}

	// 新規登録機能
	private void create(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String name = null;
		String password = null;
		String icon = null;
		int result;

		ServletContext sc = getServletContext();
		try{
			tmp = request.getParameter("id");
			id = new String(tmp.getBytes("8859_1"),"UTF-8");
			name = request.getParameter("name");
			tmp = request.getParameter("password1");
			password = new String(tmp.getBytes("8859_1"),"UTF-8");
			tmp = request.getParameter("icon");
			icon = new String(tmp.getBytes("8859_1"),"UTF-8");

			if(!(name.trim().equals("") || name.replaceAll("　", "").equals(""))){

				AccountDB accountDB = new AccountDB();
				result = accountDB.create(id, name, password, icon, "user");
				if(result == 0){
					HttpSession session = request.getSession(true);
					session.setAttribute("id", id);
					session.setAttribute("name", name);
					session.setAttribute("icon", icon);
					session.setAttribute("login", "ok");
					session.setAttribute("index", "-1");
					toHome(request, response);
				}
				else if(result == -1){
					response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("既にアカウントが存在しています","UTF-8"));
				}
				else if(result == -2){
					response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("学生番号とパスワードに使用できるのは半角英数字のみです","UTF-8"));
				}
				else if(result == -3){
					response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("学生番号はuの後に6桁の数字またはgの後に5桁の数字です","UTF-8"));
				}
			}
			else{
				response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("名前が空白のみです","UTF-8"));
			}
		}
		catch(Exception e){
			response.sendRedirect("/godsns/jsp/login_error.jsp?error="+URLEncoder.encode("エラーが発生しました<br>原因として以下のことが考えられます<br><ul style=\"list-style: none;\"><li>・名前に絵文字を使用している</li></ul>","UTF-8"));
		}
	}

	// 掲示板投稿機能
	private void messageAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String body = null;
		String select1 = null;
		String select2 = null;
		String se1name = null;
		String se2name = null;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				body = request.getParameter("body");
				tmp = request.getParameter("select1");
				select1 = new String(tmp.getBytes("8859_1"),"UTF-8");
				se1name = select1;
				tmp = request.getParameter("select2");
				select2 = new String(tmp.getBytes("8859_1"),"UTF-8");
				se2name = select2;
				if(!(se2name.equals("")))
					se1name += "_";
				if(!(body.trim().equals("") || body.replaceAll("　", "").equals(""))){
					MessageDB messageDB = new MessageDB();
					messageDB.add(id, body.trim(), se1name + se2name);
				}

				session.setAttribute("select1", select1);
				session.setAttribute("select2", select2);

				response.sendRedirect("/godsns/jsp/chat.jsp");
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました<br>原因として以下のことが考えられます<br><ul style=\"list-style: none;\"><li>・メッセージに絵文字を使用している</li></ul>","UTF-8"));
			}
		}
	}

	// メッセージ削除機能
	private void messageDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String m_id;
		String id;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");

				tmp = request.getParameter("m_id");
				m_id = new String(tmp.getBytes("8859_1"),"UTF-8");

				MessageDB messageDB = new MessageDB();
				result = messageDB.delete(id, m_id);
				if(result == 0){
					response.sendRedirect("/godsns/jsp/chat.jsp");
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたはこの投稿を削除できません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// メッセージ削除機能
	private void myMessageDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String m_id;
		String id;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");

				tmp = request.getParameter("m_id");
				m_id = new String(tmp.getBytes("8859_1"),"UTF-8");

				MessageDB messageDB = new MessageDB();
				result = messageDB.delete(id, m_id);
				if(result == 0){
					response.sendRedirect("/godsns/jsp/mymessage.jsp?mesage="+URLEncoder.encode("投稿を削除しました","UTF-8"));
				}
				else{
					response.sendRedirect("/godsns/jsp/mymessage.jsp?message="+URLEncoder.encode("投稿を削除できませんでした","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 画像投稿機能
	private void pictureAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String time1 = sdf1.format(calendar.getTime());
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time2 = sdf2.format(calendar.getTime());
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");
		String year = sdf3.format(calendar.getTime());
		SimpleDateFormat sdf4 = new SimpleDateFormat("MM");
		String month = sdf4.format(calendar.getTime());
		SimpleDateFormat sdf5 = new SimpleDateFormat("dd");
		String day = sdf5.format(calendar.getTime());
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				Part part = request.getPart("datafile");
				String type = this.getFileType(part);

				if(type.equals(".png") || type.equals(".jpeg") || type.equals(".jpg") || type.equals(".gif") || type.equals(".bmp") || type.equals(".PNG") || type.equals(".JPEG") || type.equals(".JPG") || type.equals(".GIF") || type.equals(".BMP")){

					String tmp = session.getAttribute("id").toString();
					String id = new String(tmp.getBytes("8859_1"),"UTF-8");
					String body = request.getParameter("body");
					ServletContext sc = getServletContext();
					String filename = id + "_" + time1 + type;

					// ディレクトリに保存
					part.write("C:\\Users\\Masato\\program(Java)\\godsns\\webapps\\upload\\upload\\" + filename);

					// データベースに登録
					PictureDB pictureDB = new PictureDB();
					pictureDB.add(id, filename, body.trim(), time2);

					session.setAttribute("year", year);
					session.setAttribute("month", month);
					session.setAttribute("day", day);

					session.setAttribute("beforePage", session.getAttribute("afterPage").toString());
					session.setAttribute("afterPage", "pic_list.jsp");
					response.sendRedirect("/godsns/jsp/pic_list.jsp");
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("画像ファイルをアップロードしてください","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました<br>原因として以下のことが考えられます<br><ul style=\"list-style: none;\"><li>・画像のサイズが3MBを超えている</li><li>・コメントに絵文字を使用している</li></ul>","UTF-8"));
			}
		}
	}

	// 画像削除機能
	private void pictureDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String filename;
		String id;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");

				tmp = request.getParameter("url");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");

				PictureDB pictureDB = new PictureDB();
				result = pictureDB.delete(id, filename);
				if(result == 0){
					GodDB godDB = new GodDB();
					godDB.allDelete(filename, "filename");

					session.setAttribute("afterPage", "home.jsp");
					toHome(request, response);
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたはこの画像を削除できません","UTF-8"));
				}
				/*
				  削除できない
				  File imageFile = new File(sc.getRealPath("webapps") + "/upload/upload/" + url);
				  imageFile.delete();
				 */
			}
			catch(Exception e){
				System.out.println(e);
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 写真投稿削除機能
	private void myPictureDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String filename;
		String id;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");

				tmp = request.getParameter("filename");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");

				PictureDB pictureDB = new PictureDB();
				result = pictureDB.delete(id, filename);
				if(result == 0){
					GodDB godDB = new GodDB();
					godDB.allDelete(filename, "filename");
					response.sendRedirect("/godsns/jsp/mypicture.jsp?message="+URLEncoder.encode("写真を削除しました","UTF-8"));
				}
				else{
					response.sendRedirect("/godsns/jsp/mypicture.jsp?message="+URLEncoder.encode("写真を削除できませんでした","UTF-8"));
				}
				/*
				  削除できない
				  File imageFile = new File(sc.getRealPath("webapps") + "/upload/upload/" + url);
				  imageFile.delete();
				 */
			}catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 「神だね」の登録
	private void godAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String filename = null;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("filename");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");

				GodDB godDB = new GodDB();
				if(godDB.add(id, filename) == true){
					PictureDB pictureDB = new PictureDB();
					pictureDB.godAdd(filename);
				}

				response.sendRedirect("/godsns/jsp/pic.jsp");
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 「神だね」の削除
	private void godDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String filename = null;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("filename");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");

				GodDB godDB = new GodDB();
				if(godDB.delete(id, filename) == true){
					PictureDB pictureDB = new PictureDB();
					pictureDB.godDelete(filename);
				}

				response.sendRedirect("/godsns/jsp/pic.jsp");
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 「神だね」の削除
	private void myGodDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String filename = null;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("filename");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");

				GodDB godDB = new GodDB();
				if(godDB.delete(id, filename) == true){
					PictureDB pictureDB = new PictureDB();
					pictureDB.godDelete(filename);
					response.sendRedirect("/godsns/jsp/mygod.jsp?message="+URLEncoder.encode("削除しました","UTF-8"));
				}
				else{
					response.sendRedirect("/godsns/jsp/mygod.jsp?message="+URLEncoder.encode("削除できませんでした","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// アイコンの変更
	private void changeIcon(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String icon = null;
		int result;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("icon");
				icon = new String(tmp.getBytes("8859_1"),"UTF-8");

				AccountDB accountDB = new AccountDB();
				result = accountDB.changeIcon(id, icon);
				if(result == 0){
					response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("アイコンを変更しました！","UTF-8"));
				}
				else if(result == -1){
					response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("アイコンを変更できませんでした","UTF-8"));
				}
				else if(result == -2){
					response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("アイコンの変更はありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 名前の変更
	private void changeName(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String name = null;
		int result;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				name = request.getParameter("name");

				if(!(name.trim().equals("") || name.replaceAll("　", "").equals(""))){

					AccountDB accountDB = new AccountDB();
					result = accountDB.changeName(id, name);
					if(result == 0){
						response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("名前を変更しました！","UTF-8"));
					}
					else if(result == -1){
						response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("名前を変更できませんでした","UTF-8"));
					}
					else if(result == -2){
						response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("名前の変更はありません","UTF-8"));
					}
				}
				else{
					response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("名前を変更できませんでした","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました<br>原因として以下のことが考えられます<br><ul style=\"list-style: none;\"><li>・名前に絵文字を使用している</li></ul>","UTF-8"));
			}
		}
	}

	// パスワードの変更
	private void changePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String password = null;
		String password1;
		int result;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("password");
				password = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("password1");
				password1 = new String(tmp.getBytes("8859_1"),"UTF-8");

				AccountDB accountDB = new AccountDB();
				result = accountDB.changePassword(id, password, password1);
				if(result == 0){
					response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("パスワードを変更しました！","UTF-8"));
				}
				else if(result == -1){
					response.sendRedirect("/godsns/jsp/change_info.jsp?message="+URLEncoder.encode("現在のパスワードが間違っています","UTF-8"));
				}
				else if(result == -2){
					response.sendRedirect("/godsns/jsp/admin_password.jsp?message="+URLEncoder.encode("パスワードに使用できるのは半角の英数字のみです","UTF-8"));
				}
				else if(result == -3){
					response.sendRedirect("/godsns/jsp/admin_password.jsp?message="+URLEncoder.encode("パスワードの変更はありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// アカウント削除機能
	private void accountDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String id;
		String password;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("password").toString();
				password = new String(tmp.getBytes("8859_1"),"UTF-8");

				AccountDB accountDB = new AccountDB();

				result = accountDB.delete(id, password);

				if(result == 0){
					MessageDB messageDB = new MessageDB();
					messageDB.allDelete(id);

					PictureDB pictureDB = new PictureDB();
					pictureDB.allDelete(id);

					GodDB godDB = new GodDB();
					godDB.allDelete(id, "id");

					response.sendRedirect("/godsns/index.html");
				}
				else{
					response.sendRedirect("/godsns/jsp/delete_account.jsp?message="+URLEncoder.encode("パスワードが違います","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 新規登録機能(管理者権限)
	private void adminCreate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String id = null;
		String name = null;
		String password = null;
		String icon = null;
		String admin;
		int result;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			ServletContext sc = getServletContext();
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){

					tmp = request.getParameter("id");
					id = new String(tmp.getBytes("8859_1"),"UTF-8");
					name = request.getParameter("name");

					tmp = request.getParameter("password1");
					password = new String(tmp.getBytes("8859_1"),"UTF-8");
					tmp = request.getParameter("icon");
					icon = new String(tmp.getBytes("8859_1"),"UTF-8");

					AccountDB accountDB = new AccountDB();
					result = accountDB.create(id, name, password, icon, "admin");
					if(result == 0){
						response.sendRedirect("/godsns/jsp/admin_create.jsp?message="+URLEncoder.encode(id + "のアカウントを作成しました","UTF-8"));
					}
					else{
						response.sendRedirect("/godsns/jsp/admin_create.jsp?message="+URLEncoder.encode("アカウントを作成できませんでした","UTF-8"));
					}
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// アカウント削除機能(管理者用)
	private void adminAccountDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String id;
		String admin;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("id");
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					MessageDB messageDB = new MessageDB();
					messageDB.allDelete(id);

					PictureDB pictureDB = new PictureDB();
					pictureDB.allDelete(id);

					GodDB godDB = new GodDB();
					godDB.allDelete(id, "id");

					AccountDB accountDB = new AccountDB();
					accountDB.adminDelete(id);
					response.sendRedirect("/godsns/jsp/admin_account.jsp?message="+URLEncoder.encode(id + "のアカウントを削除しました","UTF-8"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 掲示板削除機能(管理者用)
	private void adminMessageDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String m_id;
		String admin;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");

				tmp = request.getParameter("m_id");
				m_id = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					MessageDB messageDB = new MessageDB();
					messageDB.delete(admin, m_id);

					response.sendRedirect("/godsns/jsp/admin_message.jsp?message="+URLEncoder.encode("投稿を削除しました","UTF-8"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 写真投稿削除機能(管理者用)
	private void adminPictureDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String filename;
		String admin;
		int result;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");

				tmp = request.getParameter("filename");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					PictureDB pictureDB = new PictureDB();
					pictureDB.delete(admin, filename);

					GodDB godDB = new GodDB();
					godDB.allDelete(filename, "filename");

					response.sendRedirect("/godsns/jsp/admin_picture.jsp?message="+URLEncoder.encode("投稿を削除しました","UTF-8"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// パスワードの変更(管理者用)
	private void adminChangePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String admin = null;
		String password = null;
		String password1;
		int result;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("password");
				password = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("password1");
				password1 = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					AccountDB accountDB = new AccountDB();
					result = accountDB.changePassword(admin, password, password1);
					if(result == 0){
						response.sendRedirect("/godsns/jsp/admin_password.jsp?message="+URLEncoder.encode("パスワードを変更しました！","UTF-8"));
					}
					else if(result == -1){
						response.sendRedirect("/godsns/jsp/admin_password.jsp?message="+URLEncoder.encode("現在のパスワードが間違っています","UTF-8"));
					}
					else if(result == -2){
						response.sendRedirect("/godsns/jsp/admin_password.jsp?message="+URLEncoder.encode("パスワードに使用できるのは半角の英数字のみです","UTF-8"));
					}
					else if(result == -3){
						response.sendRedirect("/godsns/jsp/admin_password.jsp?message="+URLEncoder.encode("パスワードの変更はありません","UTF-8"));
					}
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// SNSの初期化(管理者用)
	private void adminReset(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String admin = null;
		String password = null;
		String password1;
		int result;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					tmp = request.getParameter("password").toString();
					password = new String(tmp.getBytes("8859_1"),"UTF-8");
					AccountDB accountDB = new AccountDB();

					result = accountDB.allDelete(admin, password);

					if(result == 0){
						MessageDB messageDB = new MessageDB();
						messageDB.allDelete();

						PictureDB pictureDB = new PictureDB();
						pictureDB.allDelete();

						GodDB godDB = new GodDB();
						godDB.allDelete();

						response.sendRedirect("/godsns/jsp/admin_reset.jsp?message="+URLEncoder.encode("初期化しました","UTF-8"));
					}
					else{
						response.sendRedirect("/godsns/jsp/admin_reset.jsp?message="+URLEncoder.encode("パスワードが違います","UTF-8"));
					}
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// カレンダーの月変更
	private void changeMonth(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		int year;
		int month;
		int add = 0;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				year = Integer.parseInt(request.getParameter("year").toString());
				month = Integer.parseInt(request.getParameter("month").toString());
				try{
					add = Integer.parseInt(request.getParameter("add").toString());
				}catch(NumberFormatException e){
					response.sendRedirect("/godsns/jsp/home.jsp");
				}

				month += add;
				if(month == 13){
					year++;
					month = 1;
				}
				if(month == 0){
					year--;
					month = 12;
				}

				// グレゴリオ暦(1582年10月15日制定)の範囲外
				if(year <= 1582){
					year = 1583;
					month = 1;
				}

				session.setAttribute("year", year);
				session.setAttribute("month", month);
				response.sendRedirect("/godsns/jsp/home.jsp");
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 写真の表示
	private void showImage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		try{
			String dir = "C:\\Users\\Masato\\program(Java)\\godsns\\webapps\\upload\\upload\\";
			String tmp = request.getParameter("filename");
			String  filename = new String(tmp.getBytes("8859_1"),"UTF-8");
			int iData = 0;

			//ServletのOutputStream取得
			ServletOutputStream out = response.getOutputStream();

			//画像ファイルをBufferedInputStreamを使用して読み取る
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(dir + filename));

			//画像を書き出す
			while((iData = in.read()) != -1){
				out.write(iData);
			}

			in.close();
			out.close();
		}catch(Exception e){
			response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
		}
	}

	// ログアウト
	private void logout(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		try{
			HttpSession session = request.getSession(true);
			session.invalidate();
			response.sendRedirect("/godsns/index.html");
		}catch(Exception e){
			response.sendRedirect("/godsns/index.html");
		}
	}

	// セッションタイムアウト
	private void timeout(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		response.sendRedirect("/godsns/timeout.html");
	}

	// ホーム画面へ
	private void toHome(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		String year = sdf1.format(calendar.getTime());
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		String month = sdf2.format(calendar.getTime());
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		String day = sdf3.format(calendar.getTime());
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);

		if(session == null){
			timeout(request, response);
		}
		else{
			session.setAttribute("year", year);
			session.setAttribute("month", month);
			session.setAttribute("today_y", year);
			session.setAttribute("today_m", month);
			session.setAttribute("today_d", day);
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "home.jsp"));
		}
	}

	// 画像一覧へ
	private void toPicList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String year = null;
		String month = null;
		String day = null;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = request.getParameter("year");
				year = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("month");
				month = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("day");
				day = new String(tmp.getBytes("8859_1"),"UTF-8");
				session.setAttribute("year", year);
				session.setAttribute("month", month);
				session.setAttribute("day", day);

				response.sendRedirect("/godsns/jsp/" + addHistory(session, "pic_list.jsp"));
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 画像詳細画面へ
	private void toPic(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String filename = null;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = request.getParameter("filename");
				filename = new String(tmp.getBytes("8859_1"),"UTF-8");
				session.setAttribute("filename", filename);

				response.sendRedirect("/godsns/jsp/" + addHistory(session, "pic.jsp"));
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// ランキング画面へ
	private void toRank(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String year = null;
		String month = null;
		String day = null;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = request.getParameter("year");
				year = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("month");
				month = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("day");
				day = new String(tmp.getBytes("8859_1"),"UTF-8");
				session.setAttribute("year", year);
				session.setAttribute("month", month);
				session.setAttribute("day", day);

				response.sendRedirect("/godsns/jsp/" + addHistory(session, "rank.jsp"));
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 写真投稿画面へ
	private void toUpload(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "upload.jsp"));
		}
	}

	// 掲示板へ
	private void toChat(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp = null;
		String select1 = null;
		String select2 = null;
		boolean flag = false;
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = request.getParameter("select1");
				select1 = new String(tmp.getBytes("8859_1"),"UTF-8");
				tmp = request.getParameter("select2");
				select2 = new String(tmp.getBytes("8859_1"),"UTF-8");
				session.setAttribute("select1", select1);
				session.setAttribute("select2", select2);
				if(select1.equals("taiiku") || select1.equals("bunnka") || select1.equals("none")){
					if(select2.equals("")){
						toHome(request, response);
					}
					else{
						flag = true;
					}
				}
				else{
					flag = true;
				}

				if(flag) {
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "chat.jsp"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// メニュー画面へ
	private void toMenu(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "menu.jsp"));
		}
	}

	// 掲示板投稿一覧画面へ
	private void toMyMessage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "mymessage.jsp"));
		}
	}

	// 写真投稿一覧画面へ
	private void toMyPicture(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "mypicture.jsp"));
		}
	}

	// 神だね一覧画面へ
	private void toMyGod(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "mygod.jsp"));
		}
	}

	// 登録情報変更画面へ
	private void toChange(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "change_info.jsp"));
		}
	}

	// アカウント削除画面へ
	private void toAccountDelete(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + addHistory(session, "delete_account.jsp"));
		}
	}

	// 管理者画面へ
	private void toAdmin(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String id;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(id)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 管理者権限でのアカウント作成画面へ
	private void toAdminCreate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String admin;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin_create.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// アカウント一覧画面へ
	private void toAdminAccount(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String id;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				id = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(id)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin_account.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 掲示板投稿一覧画面へ
	private void toAdminMessage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String admin;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin_message.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 写真投稿管理画面へ
	private void toAdminPicture(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String admin;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin_picture.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("エラーが発生しました","UTF-8"));
			}
		}
	}

	// 管理者用パスワード変更画面へ
	private void toAdminChangePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String admin;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin_password.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+e.toString());
			}
		}
	}

	// 管理者用SNS初期化画面へ
	private void toAdminReset(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String tmp;
		String admin;
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			try{
				tmp = session.getAttribute("id").toString();
				admin = new String(tmp.getBytes("8859_1"),"UTF-8");
				if(Admin.adminCheck(admin)){
					response.sendRedirect("/godsns/jsp/" + addHistory(session, "admin_reset.jsp"));
				}
				else{
					response.sendRedirect("/godsns/jsp/error.jsp?error="+URLEncoder.encode("あなたは管理者ではありません","UTF-8"));
				}
			}
			catch(Exception e){
				response.sendRedirect("/godsns/jsp/error.jsp?error="+e.toString());
			}
		}
	}

	// 前のページに戻る
	private void back(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		if(session == null){
			timeout(request, response);
		}
		else{
			response.sendRedirect("/godsns/jsp/" + getPreHistory(session));
		}
	}

	// 投稿されたファイルの拡張子の取得
	private String getFileType(Part part) {
		String name = null;
		String type = null;
		for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
			if (dispotion.trim().startsWith("filename")) {
				name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
				name = name.substring(name.lastIndexOf("\\") + 1);
				break;
			}
		}

		type = name.substring(name.lastIndexOf("."));

		return type;
	}

	// ページ履歴の追加
	private String addHistory(HttpSession session, String history) {
		int index = 0;

		try {
			index = Integer.parseInt(session.getAttribute("index").toString()) + 1;
			if(index == historySize) {
				index = 0;
			}
			session.setAttribute("index", index);
		}
		catch(Exception e) {
			session.setAttribute("index", -1);
		}
		session.setAttribute("history" + index, history);

		return history;
	}

	// 前のページ履歴
	private String getPreHistory(HttpSession session){
		int index = 0;
		index = Integer.parseInt(session.getAttribute("index").toString()) - 1;
		if(index == -1) {
			index = historySize - 1;
		}
		session.setAttribute("index", index);

		return session.getAttribute("history" + index).toString();
	}
}
