package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by Dubov Aleksei
 * Date: Jun 30, 2007
 * Time: 8:38:51 PM
 * Company: EBS (c) 2007
 */
public class EWTUtils {
	/*widgets*/
	public static Widget removeStyles(Widget widget, String... styles) {
		int len = styles.length;
		for(int i = 0; i < len; i++) { widget.removeStyleName(styles[i]); }
		return widget;
	}

	public static Widget setStyles(Widget widget, String... styles) {
		widget.setStyleName(styles[0]);
		int len = styles.length;
		for(int i = 1; i < len; i++) { widget.addStyleName(styles[i]); }
		return widget;
	}

	public static Widget addStyles(Widget widget, String... styles) {
		int len = styles.length;
		for(int i = 0; i < len; i++) { widget.addStyleName(styles[i]); }
		return widget;
	}

	public static native String getBold(String text) /*-{
		return "<b>" + text + "</b>";
	}-*/;

	public static Html getBoldHTML(String text) {
		return new Html(getBold(text));
	}

	public static Html getClassStyledHTML(String text, String className) {
		return new Html(getClassStyledHTMLasString(text, className));
	}

	public static native String getClassStyledHTMLasString(String text, String className) /*-{
		return "<div class='" + className + "'>" + text + "</div>";
	}-*/;

	public static native String getStyledHTMLasString(String text, String style) /*-{
		return "<div style='" + style + "'>" + text + "</div>";
	}-*/;

	public static native String getIMGasString(String path, int w, int h) /*-{
		return "<img src='" + path + "' width='" + w + "' height='" + h + "'/>";
	}-*/;

	public static HorizontalPanel getHTMLInHorizontalPanel(String text) {
		HorizontalPanel panel = new HorizontalPanel();
		panel.add(new Html(text));
		return panel;
	}

	public static boolean setLocation(String page, String[] params, String[] vars) {
		StringBuilder location = new StringBuilder(GWT.getHostPageBaseURL()).append(page).append('?');

		if(params != null && vars != null) {
			if(params.length != vars.length) return false;

			int len = params.length;
			for(int i = 0; i < len; i++) {
				appendParamValue(location, params[i], vars[i]);
			}
		}

		Window.Location.assign(location.toString());
		return true;
	}

	public static StringBuilder appendParamValue(StringBuilder builder, String param, String value) {
		return builder.append(param).append('=').append(value).append('&');
	}
}
