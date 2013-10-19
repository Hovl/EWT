package ebs.ewt.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import ebs.ewt.client.ewt.EWTGotoPageClickListener;

/**
 * Created by Dubov Aleksei
 * Date: Dec 23, 2008
 * Time: 10:32:05 PM
 * Company: EBS (c) 2008
 */

public class EWTBaseButton {
	public static EWTCustomHTML getGotoPageLink(String text, String page, String... params) {
		return new EWTCustomHTML(text, "ewt-link", "ewt-link-select", new EWTGotoPageClickListener(page, params));
	}

	public static EWTCustomHTML getLink(String text, ClickHandler handler) {
		return new EWTCustomHTML(text, "ewt-link", "ewt-link-select", handler);
	}
}
