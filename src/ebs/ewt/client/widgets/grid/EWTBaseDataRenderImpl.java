package ebs.ewt.client.widgets.grid;

import com.extjs.gxt.ui.client.widget.Html;
import com.google.gwt.user.client.ui.Widget;
import ebs.ewt.client.ewt.EWTUtils;
import ebs.ewt.client.widgets.EWTIconButton;

/**
 * Created by Dubov Aleksei
 * Date: Nov 2, 2008
 * Time: 10:41:51 PM
 * Company: EBS (c) 2008
 */

public enum EWTBaseDataRenderImpl implements EWTDataRender {
	SIMPLEMULTILINE,
	MULTILINE,
	BOOLEANWIDGET;

	public Widget renderWidget(Object o) {
		switch (this) {
			case SIMPLEMULTILINE:
			case MULTILINE: return EWTUtils.getHTMLInHorizontalPanel(o.toString());
			case BOOLEANWIDGET: return new EWTIconButton(((Boolean) o) ? "ewt-stat-ok" : "ewt-stat-blocked");
			default: return new Html(o.toString());
		}
	}

	public String renderData(Object o) {
		switch (this) {
			case SIMPLEMULTILINE: return o.toString().replaceAll("\n", "</br>");
			case MULTILINE: return o.toString().replaceAll(" ", "</br>");
			default: return o.toString();
		}
	}
}
