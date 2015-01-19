package net.snails.web.util;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

/**
 * @author krisjin
 * @date 2014-2-1下午2:12:07
 */
public class HtmlUtil {
	private static final String REGXP_HTML_TAG = "<([^>]*)>";
	private static final String REGXP_SRCATR_VALUE = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";

	private HtmlUtil() {
	}

	public static String removeAllHtmlTag(String str) {
		Pattern pattern = Pattern.compile(REGXP_HTML_TAG);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String filterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s*+([^>]*)\\s*>|</" + tag + ">";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);

		return matcher.replaceAll("");
	}

	public static String filterHtmlTag(String str, String[] tags) {
		String ret = str;
		for (String tag : tags) {
			ret = filterHtmlTag(ret, tag);
		}
		return ret;
	}

	public static String replaceHtmlTag(String str, String beforeTag, String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

	public static String getImgSrcAtrVal(String input) {
		Pattern p = Pattern.compile(REGXP_SRCATR_VALUE);
		Matcher matcher = p.matcher(input);
		String retVal = null;
		boolean result = matcher.find();
		while (result) {
			retVal = matcher.group(1);
			result = matcher.find();
		}
		return retVal;
	}

	public static List<String> getImgSrcAtrVals(String str) {
		Pattern p = Pattern.compile(REGXP_SRCATR_VALUE);
		Matcher matcher = p.matcher(str);
		List<String> list = new ArrayList<String>();
		boolean flag = matcher.find();

		while (flag) {
			list.add(matcher.group(1));
			flag = matcher.find();
		}
		return list;
	}

	public static String interceptLen(String str, int len) {
		str = str.trim();
		int byteLen = 0;
		if (Strings.isNullOrEmpty(str))
			return "";

		byteLen = str.getBytes().length;
		if (byteLen < len)
			return str;

		for (int i = 0; i < len; i++) {
			String tmpStr = str.substring(i, i + 1);
			if (tmpStr.getBytes(Charsets.UTF_8).length >= 2)
				len = len - 1;
		}
		return str.substring(0, len);
	}

	public static String removeStyleTag(String str) {
		if (Strings.isNullOrEmpty(str)) {
			throw new InvalidParameterException("parameter is invalid");
		}
		String rex = "style=\"\\s*\\S*\"";
		String ret = "";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(str);
		ret = m.replaceAll("");
		return ret;
	}

}
