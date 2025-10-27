package godsns;

public class ChatName{

    // チャット名の取得
    public String getName(String a, String b){
	String name = "";
	if(a.equals("school")){
	    name += "学校";
	}
	else if(a.equals("doboku") || a.equals("kennchiku") || a.equals("kikai") || a.equals("dennki") || a.equals("seimei") || a.equals("joho")){
	    if(a.equals("doboku")){
		name += "土木工学科・専攻";
	    }
	    else if(a.equals("kennchiku")){
		name += "建築学科・専攻";
	    }
	    else if(a.equals("kikai")){
		name += "機械工学科・専攻";
	    }
	    else if(a.equals("dennki")){
		name += "電気電子工学科・専攻";
	    }
	    else if(a.equals("seimei")){
		name += "生命応用化学科・専攻";
	    }
	    else if(a.equals("joho")){
		name += "情報工学科・専攻";
	    }
	    if(b.equals("all")){
		name += " ";
	    }
	    else if(b.equals("d1")){
		name += " 学部1年 ";
	    }
	    else if(b.equals("d2")){
		name += " 学部2年 ";
	    }
	    else if(b.equals("d3")){
		name += " 学部3年 ";
	    }
	    else if(b.equals("d4")){
		name += " 学部4年 ";
	    }
	    else if(b.equals("c")){
		name += " 大学院 ";
	    }
	}
	else if(a.equals("taiiku")){
	    if(b.equals("1")){
		name += "体育会 ";
	    }
	    else if(b.equals("2")){
		name += "應援團 ";
	    }
	    else if(b.equals("3")){
		name += "合気道部 ";
	    }
	    else if(b.equals("4")){
		name += "アメリカンフットボール部 ";
	    }
	    else if(b.equals("5")){
		name += "空手道部 ";
	    }
	    else if(b.equals("6")){
		name += "器械体操部 ";
	    }
	    else if(b.equals("7")){
		name += "弓道部 ";
	    }
	    else if(b.equals("8")){
	    name += "剣道部 ";
	    }
	    else if(b.equals("9")){
		name += "硬式ソフトボール部 ";
	    }
	    else if(b.equals("10")){
		name += "硬式庭球部 ";
	    }
	    else if(b.equals("11")){
		name += "硬式野球部 ";
	    }
	    else if(b.equals("12")){
		name += "ゴルフ部 ";
	    }
	    else if(b.equals("13")){
		name += "サッカー部 ";
	    }
	    else if(b.equals("14")){
	    name += "射撃部 ";
	    }
	    else if(b.equals("15")){
		name += "柔道部 ";
	    }
	    else if(b.equals("16")){
		name += "水泳部 ";
	    }
	    else if(b.equals("17")){
		name += "スキー部 ";
	    }
	    else if(b.equals("18")){
		name += "ソフトテニス部 ";
	    }
	    else if(b.equals("19")){
		name += "卓球部 ";
	    }
	    else if(b.equals("20")){
		name += "軟式野球部 ";
	    }
	    else if(b.equals("21")){
		name += "日本拳法部 ";
	    }
	    else if(b.equals("22")){
		name += "バスケットボール部 ";
	    }
	    else if(b.equals("23")){
		name += "バドミントン部 ";
	    }
	    else if(b.equals("24")){
		name += "バレーボール部 ";
	    }
	    else if(b.equals("25")){
		name += "ハンドボール部 ";
	    }
	    else if(b.equals("26")){
		name += "ボクシング部 ";
	    }
	    else if(b.equals("27")){
		name += "洋弓部 ";
	    }
	    else if(b.equals("28")){
		name += "ラグビー部 ";
	    }
	    else if(b.equals("29")){
		name += "ラクロス部 ";
	    }
	    else if(b.equals("30")){
		name += "陸上競技部 ";
	    }
	}
	else if(a.equals("bunnka")){
	    if(b.equals("1")){
		name += "学術文化サークル連合会 ";
	    }
	    else if(b.equals("2")){
		name += "囲碁・将棋部 ";
	    }
	    else if(b.equals("3")){
		name += "演劇部 ";
	    }
	    else if(b.equals("4")){
		name += "桜花一門YOSAKOI隊 ";
	    }
	    else if(b.equals("5")){
		name += "音楽研究会 ";
	    }
	    else if(b.equals("6")){
		name += "音響研究会 ";
	    }
	    else if(b.equals("7")){
		name += "滑空研究会 ";
	    }
	    else if(b.equals("8")){
		name += "管弦楽部 ";
	    }
	    else if(b.equals("9")){
		name += "機械研究会 ";
	    }
	    else if(b.equals("10")){
		name += "建築研究会 ";
	    }
	    else if(b.equals("11")){
		name += "サイクリング部 ";
	    }
	    else if(b.equals("12")){
		name += "茶道部 ";
	    }
	    else if(b.equals("13")){
		name += "自動車部 ";
	    }
	    else if(b.equals("14")){
		name += "写真部 ";
	    }
	    else if(b.equals("15")){
		name += "ジャグリング愛好会 ";
	    }
	    else if(b.equals("16")){
		name += "情報研究会 ";
	    }
	    else if(b.equals("17")){
		name += "吹奏楽部 ";
	    }
	    else if(b.equals("18")){
		name += "赤十字奉仕団 ";
	    }
	    else if(b.equals("19")){
		name += "鐵道研究会 ";
	    }
	    else if(b.equals("20")){
		name += "天文研究会 ";
	    }
	    else if(b.equals("21")){
		name += "動画漫画研究会 ";
	    }
	    else if(b.equals("22")){
		name += "美術部 ";
	    }
	    else if(b.equals("23")){
		name += "木匠塾 ";
	    }
	    else if(b.equals("24")){
		name += "モダンジャズ研究会 ";
	    }
	    else if(b.equals("25")){
		name += "ワンダーフォーゲル部 ";
	    }
	}
	else if(a.equals("none")){
	    if(b.equals("1")){
		name += "北桜祭実行委員会 ";
	    }
	    else if(b.equals("2")){
		name += "アルティメット同好会 ";
	    }
	    else if(b.equals("3")){
		name += "異文化コミュニケーション研究会 ";
	    }
	    else if(b.equals("4")){
		name += "カンジュースポーツ同好会 ";
	    }
	    else if(b.equals("5")){
		name += "基礎スキー研究同好会 ";
	    }
	    else if(b.equals("6")){
		name += "ゲレンデスキー同好会 ";
	    }
	    else if(b.equals("7")){
		name += "現代視覚文化研究同好会 ";
	    }
	    else if(b.equals("8")){
		name += "護身武道研究会 ";
	    }
	    else if(b.equals("9")){
		name += "書道会 ";
	    }
	    else if(b.equals("10")){
		name += "General Sports 同好会 ";
	    }
	    else if(b.equals("11")){
		name += "ジョイフルテニス同好会 ";
	    }
	    else if(b.equals("12")){
		name += "杖道同好会 ";
	    }
	    else if(b.equals("13")){
		name += "スクエアスポーツ同好会 ";
	    }
	    else if(b.equals("14")){
		name += "3on3 since1891 同好会 ";
	    }
	    else if(b.equals("15")){
		name += "創作研究同好会 ";
	    }
	    else if(b.equals("16")){
		name += "ダーツ同好会 ";
	    }
	    else if(b.equals("17")){
		name += "ダンス同好会 ";
	    }
	    else if(b.equals("18")){
		name += "地域連携活動研究会 ";
	    }
	    else if(b.equals("19")){
		name += "電気電子工学研究会 ";
	    }
	    else if(b.equals("20")){
		name += "土木女子の会 ";
	    }
	    else if(b.equals("21")){
		name += "トレーディングカードゲーム同好会 ";
	    }
	    else if(b.equals("22")){
		name += "日本大学工学部合唱団 ";
	    }
	    else if(b.equals("23")){
		name += "フォークソング同好会 ";
	    }
	    else if(b.equals("24")){
		name += "フットサル同好会 ";
	    }
	    else if(b.equals("25")){
		name += "文学同好会 ";
	    }
	    else if(b.equals("26")){
		name += "モーターサイクル同好会 ";
	    }
	    else if(b.equals("27")){
		name += "Racing Kart同好会 ";
	    }

	}
	return name;
    }
}
