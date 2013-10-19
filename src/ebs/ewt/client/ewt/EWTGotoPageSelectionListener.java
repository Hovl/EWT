package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import ebs.ewt.client.widgets.EWTSelectionListener;

/**
 * Created by Dubov Aleksei
 * Date: Dec 24, 2008
 * Time: 11:36:40 PM
 * Company: EBS (c) 2008
 */

public class EWTGotoPageSelectionListener extends EWTSelectionListener<String> {
	private String[] params;

	public EWTGotoPageSelectionListener(String page, String... params) {
		super(page);
		this.params = params;
	}

	public void componentSelected(String s, IconButtonEvent ce) {
		EWTPages.gotoPage(s, params);
	}
}
