package ebs.ewt.client.ewt;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dubov Aleksei
 * Date: Aug 13, 2007
 * Time: 2:54:16 PM
 * Company: EBS (c) 2007
 */
public class EWTPages {
	public static final String MARK = "/";

	public static HashMap<String, Object> dataCache = new HashMap<String, Object>();

	public interface EWTPager {
		public void getPage(String name, String[] params);
	}

	public interface EWTWidgetProvider {
		public void getWidget(String name, EWTCommand<EWTWidget> command);
	}

	private static EWTPager pager = null;
	private static EWTWidgetProvider provider = null;

	private static HashMap<String, List<String>> pages = new HashMap<String, List<String>>();

	private static HashMap<String, EWTWidget> widgets = new HashMap<String, EWTWidget>();
	private static List<EWTWidget> const_widgets = new ArrayList<EWTWidget>();

	private static String new_page = "";
	private static String cur_page = "";
	private static String old_page = "";

	private static String[] cur_params = null;

	public static void init(EWTPager ewtPager) {
		pager = ewtPager;

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String s = event.getValue();
				String pageID = getPageWothoutParams(s);
				cur_params = getParams(s);

				if(!pages.containsKey(pageID)) {
					pager.getPage(pageID, cur_params);
				} else {
					showPage(pageID);
				}
			}
		});
	}

	public static void setProvider(EWTWidgetProvider widgetProvider) {
		provider = widgetProvider;
	}

	public static String getNew_page() {
		return new_page;
	}

	public static String getCur_page() {
		return cur_page;
	}

	public static String getOld_page() {
		return old_page;
	}

	public static String[] getCur_params() {
		return cur_params;
	}

	public static String getCur_param(int pos) {
		if(cur_params != null && cur_params.length > pos) {
			return cur_params[pos];
		}
		return null;
	}

	public static void addConstWidget(EWTWidget widget) {
		if(!const_widgets.contains(widget)) {
			widget.init();
			const_widgets.add(widget);
		}
	}

	public static void addWidget(EWTWidget widget) {
		widgets.put(widget.getName(), widget);
	}

	private static void displayWidget(EWTWidget widget) {
		if(widget != null) {
			if(!widget.isInited()) {
				widget.init();
				widget.inited(new EWTCommand<EWTWidget>(widget) {
					@Override
					public void execute(EWTWidget widget) {
						resetWidget(widget);
					}
				});
			} else {
				resetWidget(widget);
			}
		}
	}

	private static void resetWidget(EWTWidget widget) {
		widget.wakeup();
		widget.reset();
		widget.redraw();
		widget.setresetted();
	}

	public static void addPage(String name, List<String> list) {
		if(!containsPage(name)) {
			pages.put(name, list);
		}
	}

	public static boolean containsPage(String pID) {
		return pages.containsKey(getPageWothoutParams(pID));
	}

	public static void gotoPage(String pageID, String... params) {
		String pID = getPageWithParams(pageID, params);
		if(History.getToken().equals(pID)) {
			History.fireCurrentHistoryState();
		} else {
			History.newItem(pID);
		}
	}

	private static void showPage(String pageID) {
		new_page = pageID;

		for (EWTWidget const_widget : const_widgets) {
			const_widget.reset();
		}

		List<String> new_widgets = pages.get(new_page);
		List<String> cur_widgets = (cur_page.length() == 0 ? new ArrayList<String>(0) : pages.get(cur_page));

		for(String cur_widget_name : cur_widgets) {
			widgets.get(cur_widget_name).sleep();
		}

		for(String new_widget_name : new_widgets) {
			getWidget(new_widget_name, new EWTCommand<EWTWidget>() {
				@Override
				public void execute(EWTWidget widget) {
					displayWidget(widget);
				}
			});
		}

		old_page = cur_page;
		cur_page = new_page;
	}

	private static void getWidget(String name, final EWTCommand<EWTWidget> command) {
		EWTWidget widget = widgets.get(name);
		if(widget == null && provider != null) {
			provider.getWidget(name, new EWTCommand<EWTWidget>() {
				@Override
				public void execute(EWTWidget ewtWidget) {
					EWTPages.addWidget(ewtWidget);
					command.execute(ewtWidget);
				}
			});
		}
		command.execute(widget);
	}

	private static String getPageWithParams(String pageID, String[] params) {
		if(params == null) return pageID;

		StringBuilder buffer = new StringBuilder(pageID).append(MARK);
		for(String param : params) {
			buffer.append(param).append(MARK);
		}
		buffer.deleteCharAt(buffer.length() - 1);

		return buffer.toString();
	}

	private static String getPageWothoutParams(String pageID) {
		int index = pageID.indexOf(MARK);
		if(index < 0) return pageID;
		return pageID.substring(0, index);
	}

	private static String[] getParams(String pageID) {
		int index = pageID.indexOf(MARK);
		if(index < 0) return null;
		return pageID.substring(index + 1, pageID.length()).split(MARK);
	}
}
