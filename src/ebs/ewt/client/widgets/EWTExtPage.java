package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTWidget;

/**
 * Created by Dubov Aleksey
 * Date: Oct 28, 2009
 * Time: 3:58:25 PM
 * Company: EBS (c) 2009
 */

public class EWTExtPage extends ContentPanel implements EWTWidget {
	private boolean inited = false;

	private ContentPanel rootPanel;

	private String name;
	private String url;

	public EWTExtPage(String name, String url) {
		super();
		setHeaderVisible(false);
		setBorders(false);
		
		this.name = name;
		this.url = url;
	}

	public EWTExtPage setRootPanel(ContentPanel rootPanel) {
		this.rootPanel = rootPanel;
		return this;
	}

	@Override
	public String getName() {
		return name;
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
		setUrl(url);

		layout(true);

		inited = true;
	}

	@Override
	public void redraw() {
		rootPanel.layout(true);
	}

	@Override
	public void reset() { }

	public void sleep() {
		hide();
	}

	public void wakeup() {
		if(!isAttached()) {
			rootPanel.add(this);
		}
		show();
	}

	@Override
	public void setresetted() {

	}
}
