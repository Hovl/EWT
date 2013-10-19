package ebs.ewt.client.ewt;

import com.google.gwt.user.client.Window;

import java.util.Date;

/**
 * Created by Dubov Aleksei
 * Date: Aug 31, 2007
 * Time: 4:10:40 PM
 * Company: EBS (c) 2007
 */
public class EWTProfiling {
	private static long curtime;
	private static String mark;

	public static void startProfile(String name) {
		mark = name;
		curtime = new Date().getTime();
	}

	public static void endProfile() {
		Window.alert(mark + ": " + (new Date().getTime() - curtime) + " ms");
	}
}
