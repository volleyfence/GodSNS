function logoutCheck(){
    
    if(window.confirm('ログアウトしますか？')){
	
	location.href = "../GodServlet?command=logout";
    }
    else{
	window.alert('キャンセルされました');
    }
    
}
