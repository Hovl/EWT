package ebs.ewttest.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import ebs.ewt.client.EWT;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTPages;
import ebs.ewt.client.ewt.EWTWidget;
import ebs.ewt.client.localization.EWTConstants;
import ebs.ewt.client.widgets.EWTExtPage;
import ebs.ewt.client.widgets.form.EWTListField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dubov Aleksey
 * Date: Oct 15, 2009
 * Time: 4:44:24 AM
 * Company: EBS (c) 2009
 */

public class Ewttest extends Viewport implements EntryPoint {
	public static ContentPanel TOPPANEL = getPanel(Style.Orientation.HORIZONTAL);
	public static ContentPanel CENTERPANEL = getPanel(Style.Orientation.VERTICAL);
	public static ContentPanel LEFTPANEL = getPanel(Style.Orientation.VERTICAL);

	public void onModuleLoad() {
		EWT.initConstants(new EWTConstants() {
			@Override
			public String search() {
				return "search";
			}

			@Override
			public String add() {
				return "add";
			}

			@Override
			public String refresh() {
				return "refresh";
			}

			@Override
			public String save() {
				return "save";
			}

			@Override
			public String cancel() {
				return "cancel";
			}

			@Override
			public String report() {
				return "report";
			}

			@Override
			public String edit() {
				return "edit";
			}

			@Override
			public String from() {
				return "from";
			}

			@Override
			public String forLastMonth() {
				return "for last month";
			}

			@Override
			public String find() {
				return "find";
			}

			@Override
			public String forCurMonth() {
				return "for current month";
			}

			@Override
			public String forLastDay() {
				return "for last day";
			}

			@Override
			public String to() {
				return "to";
			}

			@Override
			public String forToday() {
				return "for today";
			}

			@Override
			public String range() {
				return "range";
			}

			@Override
			public String upload() {
				return "upload";
			}

			@Override
			public String uploadOneMore() {
				return "Upload one more file...";
			}

			@Override
			public String errorNullResultReceived() {
				return "No result received. Try to make a new request later...";
			}

			@Override
			public String errorNullResultReceivedHeader() {
				return "No result";
			}

			@Override
			public String errorReceived() {
				return "Your request finished with error on server. Try to reload a page.";
			}

			@Override
			public String errorReceivedHeader() {
				return "Error received.";
			}

			@Override
			public String waitRequestCompletionHeader() {
				return "Wait";
			}

			@Override
			public String waitRequestCompletion() {
				return "Wait for your request be completed.";
			}

			@Override
			public String hide() {
				return "Hide";
			}

			@Override
			public String mismatch() {
				return "Mismatch";
			}
		});
		GXT.init();

		BorderLayoutData topData = new BorderLayoutData(Style.LayoutRegion.NORTH, 45);
		topData.setMargins(new Margins(2, 2, 2, 2));

		BorderLayoutData centerData = new BorderLayoutData(Style.LayoutRegion.CENTER);
		centerData.setMargins(new Margins(0, 2, 2, 0));

		BorderLayoutData leftData = new BorderLayoutData(Style.LayoutRegion.WEST, 150);
		leftData.setMargins(new Margins(0, 2, 2, 2));
		leftData.setCollapsible(true);
		leftData.setFloatable(true);
		leftData.setSplit(true);

		BorderLayoutData bottomData = new BorderLayoutData(Style.LayoutRegion.SOUTH, 25);
		bottomData.setMargins(new Margins(0, 2, 2, 2));

		TOPPANEL.setLayout(new RowLayout(Style.Orientation.HORIZONTAL));
		CENTERPANEL.setLayout(new FitLayout());

		setBorders(false);
		setLayout(new BorderLayout());
		add(TOPPANEL, topData);
		add(CENTERPANEL, centerData);
		add(LEFTPANEL, leftData);

		RootPanel.get().add(this);

		layout();

		EWTPages.init(new EWTPages.EWTPager() {
			@Override
			public void getPage(String name, String[] params) {
				EWT.display("Page " + name + " not found!");
			}
		});

		EWTPages.setProvider(new EWTPages.EWTWidgetProvider() {
			@Override
			public void getWidget(String name, EWTCommand<EWTWidget> command) {
				EWT.display("Trying to load Page " + name + "...");
				if("PagedForm1Widget".equals(name)) {
					PagedForm1Widget.getInstance(command);
					return;
				} else if("PagedForm2Widget".equals(name)) {
					PagedForm2Widget.getInstance(command);
					return;
				}
				EWT.display("Can not load Page " + name + "!");
			}
		});

//		EWTPages.addWidget(new PagedForm1Widget().setRootPanel(CENTERPANEL));
//		EWTPages.addWidget(new PagedForm2Widget().setRootPanel(CENTERPANEL));
		EWTPages.addWidget(new EWTExtPage("ExtPage", GWT.getModuleBaseURL() + "ewtexttest.html").setRootPanel(CENTERPANEL));
		EWTPages.addWidget(new UploadPage().setRootPanel(CENTERPANEL));

		EWTPages.addConstWidget(new topPanelWidget());

		List<String> list1 = new ArrayList<String>();
		list1.add("PagedForm1Widget");
		EWTPages.addPage("test1", list1);

		List<String> list2 = new ArrayList<String>();
		list2.add("PagedForm2Widget");
		EWTPages.addPage("test2", list2);

		List<String> list3 = new ArrayList<String>();
		list3.add("ExtPage");
		EWTPages.addPage("test3", list3);

		List<String> list4 = new ArrayList<String>();
		list3.add("UploadPage");
		EWTPages.addPage("test4", list3);

		EWTListField.init(TestDataTypeImpl.Level);
		EWTListField.init(TestDataTypeImpl.Owner);

		if(History.getToken().length() == 0) {
			EWTPages.gotoPage("test1");
		} else {
			History.fireCurrentHistoryState();
		}
		GXT.hideLoadingPanel("loading");
	}

	private static ContentPanel getPanel(Style.Orientation orientation) {
		ContentPanel panel = new ContentPanel(new RowLayout(orientation));
		panel.setHeaderVisible(false);
		panel.setBorders(false);
		return panel;
	}
}
