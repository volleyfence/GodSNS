package godsns;

public class MyCalendar{
    private int year;
    private int month;

    // インスタンス変数の初期化
    public MyCalendar(int year, int month){
		this.year = year;
		this.month = month;
    }

    // その月が何日まであるか
    public int getLastDay(){
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
		    return 31;
		}
		else if(month == 2){
		    if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)){
			return 29;
		    }
		    else{
			return 28;

		    }
		}
		else{
		    return 30;
		}
    }

    // 1日が何曜日から始まるか
    public int getOffset(){
		// 1月と2月はそれぞれ前年の13月、14月として計算する
		if(month == 1 || month == 2)
		    return (((year - 1) + (year - 1) / 4 - (year - 1) / 100 + (year - 1) / 400 + (13 * (month + 12) + 8)/5 + 1) % 7);

		else
		    return ((year + year / 4 - year / 100 + year / 400 + (13 * month + 8) / 5 + 1) % 7);
    }

}
