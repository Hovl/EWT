package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.google.gwt.user.client.Cookies;

/**
 * Created by Dubov Aleksey
 * Date: Jul 24, 2009
 * Time: 11:23:30 PM
 * Company: EBS (c) 2009
 */

public class EWTLocale extends BaseModelData {
	private static final String COOKIE_LOCALE_NAME = "ewtLocale";

	public EWTLocale(String id, String name) {
		set("id", id);
		set("name", name);
	}

	public String getId() {
		return get("id");
	}

	public String getName() {
		return get("name");
	}

	public void setCurrentLocale() {
		Cookies.setCookie(COOKIE_LOCALE_NAME, getId(), new DateWrapper().addMonths(6).asDate());
	}

	public static String getLocaleCookie() {
		return Cookies.getCookie(COOKIE_LOCALE_NAME);
	}
}
