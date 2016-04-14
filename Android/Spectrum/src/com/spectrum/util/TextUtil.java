package com.spectrum.util;

import android.text.TextUtils;

public class TextUtil {

	// 获取列表数据
	public static String getURL(String url, String uid) {
		return url + "?uid=" + uid;
	}

	// 登录
	public static String getURL(String url, String phone, String password) {
		return url + "?phone=" + phone + "&password=" + password;
	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNum(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、178、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}
	/**
	 * 验证密码格式
	 */
	public static boolean isPassword(String mobiles) {
		if (mobiles.length()<6)
			return false;
		else
			return true;
	}
}
