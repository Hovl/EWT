package ebs.ewtexttest.client;

import com.extjs.gxt.ui.client.widget.Html;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Created by Dubov Aleksey
 * Date: Oct 28, 2009
 * Time: 4:36:01 PM
 * Company: EBS (c) 2009
 */

public class EwtExtTest implements EntryPoint {
//	private EWTPage page = new EWTPage() {
//		@Override
//		public void reset() {
//		}
//
//		@Override
//		public String getName() {
//			return null;
//		}
//
//		@Override
//		public boolean isInited() {
//			return false;
//		}
//
//		@Override
//		public void init() {
//			setHeading("ext page");
//
//			add(new Html("nothing here =)"), new RowData(1, 1));
//
//			layout();
//		}
//	};

	public void onModuleLoad() {
//		page.init();
		RootPanel.get().add(/*page*/new Html("nothing here =)"));
	}
}
