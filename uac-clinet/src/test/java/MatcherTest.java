import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MatcherTest {
	@Test
	public void testMatch() {
		Pattern pat = Pattern.compile(".*fbrole\\.proper\\.loginmh\\.do");
		System.out.println(ignoreUrl(
				"http://10.52.18.239:7001/fbrole.proper.loginMH.do", pat));

	}

	@Test
	public void testMatch2() {
		Pattern pat = Pattern.compile(".*uac_logout");
		System.out.println(ignoreUrl(
				"http://10.52.16.124:8889/INAS4/uac_logout", pat));

	}
	@Test
	public void testMatch3() {
		Pattern pat = Pattern.compile(".*login\\.jsp");
		System.out.println(ignoreUrl(
				"http://10.52.16.124:8889/INAS4/Login.jsp", pat));
		
	}

	private boolean ignoreUrl(String url, Pattern pattern) {
		url = url.toLowerCase();
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	@Test
	public void testFormat() {
		String s = MessageFormat.format("\\S'{'{0},'}'", "10");
		System.out.println(s);

	}

}
