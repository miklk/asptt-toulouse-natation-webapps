package com.asptttoulousenatation.shared.util;

public final class HTMLUtils {

	public static final String escapeHTML(String s) {
		StringBuilder lSb = new StringBuilder();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '<':
				lSb.append("&lt;");
				break;
			case '>':
				lSb.append("&gt;");
				break;
			case '&':
				lSb.append("&amp;");
				break;
			case '"':
				lSb.append("&quot;");
				break;
			case 'à':
				lSb.append("&agrave;");
				break;
			case 'À':
				lSb.append("&Agrave;");
				break;
			case 'â':
				lSb.append("&acirc;");
				break;
			case 'Â':
				lSb.append("&Acirc;");
				break;
			case 'ä':
				lSb.append("&auml;");
				break;
			case 'Ä':
				lSb.append("&Auml;");
				break;
			case 'å':
				lSb.append("&aring;");
				break;
			case 'Å':
				lSb.append("&Aring;");
				break;
			case 'æ':
				lSb.append("&aelig;");
				break;
			case 'Æ':
				lSb.append("&AElig;");
				break;
			case 'ç':
				lSb.append("&ccedil;");
				break;
			case 'Ç':
				lSb.append("&Ccedil;");
				break;
			case 'é':
				lSb.append("&eacute;");
				break;
			case 'É':
				lSb.append("&Eacute;");
				break;
			case 'è':
				lSb.append("&egrave;");
				break;
			case 'È':
				lSb.append("&Egrave;");
				break;
			case 'ê':
				lSb.append("&ecirc;");
				break;
			case 'Ê':
				lSb.append("&Ecirc;");
				break;
			case 'ë':
				lSb.append("&euml;");
				break;
			case 'Ë':
				lSb.append("&Euml;");
				break;
			case 'ï':
				lSb.append("&iuml;");
				break;
			case 'Ï':
				lSb.append("&Iuml;");
				break;
			case 'ô':
				lSb.append("&ocirc;");
				break;
			case 'Ô':
				lSb.append("&Ocirc;");
				break;
			case 'ö':
				lSb.append("&ouml;");
				break;
			case 'Ö':
				lSb.append("&Ouml;");
				break;
			case 'ø':
				lSb.append("&oslash;");
				break;
			case 'Ø':
				lSb.append("&Oslash;");
				break;
			case 'ß':
				lSb.append("&szlig;");
				break;
			case 'ù':
				lSb.append("&ugrave;");
				break;
			case 'Ù':
				lSb.append("&Ugrave;");
				break;
			case 'û':
				lSb.append("&ucirc;");
				break;
			case 'Û':
				lSb.append("&Ucirc;");
				break;
			case 'ü':
				lSb.append("&uuml;");
				break;
			case 'Ü':
				lSb.append("&Uuml;");
				break;
			case '®':
				lSb.append("&reg;");
				break;
			case '©':
				lSb.append("&copy;");
				break;
			case '€':
				lSb.append("&euro;");
				break;
			// be carefull with this one (non-breaking whitee space)
			case ' ':
				lSb.append("&nbsp;");
				break;

			default:
				lSb.append(c);
				break;
			}
		}
		return lSb.toString();
	}
}
