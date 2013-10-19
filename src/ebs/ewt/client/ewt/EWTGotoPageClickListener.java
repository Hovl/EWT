package ebs.ewt.client.ewt;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Created by Dubov Aleksei
 * Date: Dec 23, 2008
 * Time: 11:00:39 PM
 * Company: EBS (c) 2008
 */

public class EWTGotoPageClickListener implements ClickHandler {
	private String page;
	private String[] params;

	public EWTGotoPageClickListener(String page) {
		this.page = page;
	}

	public EWTGotoPageClickListener(String page, String... params) {
		this.page = page;
		this.params = params;
	}

	public void onClick(ClickEvent event) {
		EWTPages.gotoPage(page, params);
	}
}
