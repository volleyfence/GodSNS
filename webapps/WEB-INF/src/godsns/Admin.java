package godsns;

public class Admin {
	private static String adminId = "god_suzuki29";
	public static boolean adminCheck(String id) {
		if(id.equals(adminId)) {
			return true;
		}
		else {
			return false;
		}
	}
}
