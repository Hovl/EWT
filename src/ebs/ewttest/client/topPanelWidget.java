package ebs.ewttest.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTPages;
import ebs.ewt.client.ewt.EWTWidget;

/**
 * Created by Dubov Aleksey
 * Date: Oct 15, 2009
 * Time: 6:12:09 PM
 * Company: EBS (c) 2009
 */

public class topPanelWidget implements EWTWidget {
	private boolean inited = false;

	@Override
	public String getName() {
		return "topPanel";
	}

	@Override
	public boolean isInited() {
		return inited;
	}

	@Override
	public void inited(EWTCommand<EWTWidget> cmd) {
		cmd.execute();
	}

	@Override
	public void init() {
		ButtonBar bar = new ButtonBar();
		bar.add(new Button("testPage1", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				EWTPages.gotoPage("test1");
			}
		}));
		bar.add(new Button("testPage2", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				EWTPages.gotoPage("test2");
			}
		}));
		bar.add(new Button("extPage", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				EWTPages.gotoPage("test3");
			}
		}));
		bar.setAlignment(Style.HorizontalAlignment.CENTER);

		Ewttest.TOPPANEL.add(bar);
		Ewttest.TOPPANEL.layout();

		inited = true;
	}

	@Override
	public void reset() {
	}

	@Override
	public void redraw() {
	}

	@Override
	public void sleep() {
	}

	@Override
	public void wakeup() {
	}

	@Override
	public void setresetted() {

	}
}
