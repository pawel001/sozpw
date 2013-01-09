package pl.edu.pw.mini.sozpw.webinterface.utils;

import com.google.gwt.regexp.shared.RegExp;

public class Validator {

	public static boolean verifyMail(String mail) {
		RegExp regExp = RegExp
				.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");

		return regExp.test(mail);
	}

	public static boolean verifyPass(String pass, String passConf) {
		if(pass.length() < 6 || !pass.equals(passConf))
			return false;

		return true;
	}

	public static boolean verifyName(String username) {
		
		if(username.length() < 5)
			return false;
		
		RegExp regExp = RegExp
				.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*");

		return regExp.test(username);
	}

}
