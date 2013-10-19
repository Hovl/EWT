package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.fx.BaseEffect;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.ewt.EWTCallback;
import ebs.ewt.client.ewt.EWTWidget;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Dubov Aleksey
 * Date: Feb 29, 2008
 * Time: 8:23:15 PM
 * Company: EBS (c) 2007
 */
public abstract class EWTPage extends ContentPanel implements EWTWidget {
	private LayoutContainer rootPanel;

	private Html msgHtml;
	private HorizontalPanel msgPanel;

	private VerticalPanel topPanel;

	private Timer timer;
	private int ms;
	private Command timerCommand;

	private boolean reset = true;

	public EWTPage() {
		super(new RowLayout(Style.Orientation.VERTICAL));
		setBorders(false);

		msgHtml = new Html();

		msgPanel = new HorizontalPanel();
		msgPanel.add(msgHtml);
		msgPanel.setVerticalAlign(Style.VerticalAlignment.MIDDLE);
		msgPanel.add(new EWTIconButton<Object>("ewt-tool-block", ewtConstants.hide(),
				new EWTSelectionListener<Object>(null) {
					@Override
					public void componentSelected(Object o, IconButtonEvent ce) {
						hideMsgPanel();
					}
				}));
		msgPanel.setStyleName("ewt-msg-alert");
		msgPanel.hide();

		getHeader().addTool(msgPanel);

		addToolButton("x-tool-refresh", ewtConstants.refresh(),
				new SelectionListener<IconButtonEvent>() {
					public void componentSelected(IconButtonEvent event) {
						reset();
						redraw();
					}
				});

		topPanel = new VerticalPanel();
		topPanel.setTableWidth("100%");
		topPanel.setHorizontalAlign(Style.HorizontalAlignment.CENTER);

		add(topPanel);
	}

	public EWTPage setRootPanel(LayoutContainer rootPanel) {
		this.rootPanel = rootPanel;
		return this;
	}

	public void sleep() {
		stopTimer();
		rootPanel.removeAll();
	}

	public void wakeup() {
		Window.setTitle(getHeading());
		startTimer(ms);
		setReset(true);
		rootPanel.add(this);
	}

	public VerticalPanel getTopPanel() {
		return topPanel;
	}

	public void hideMsgPanel() {
		BaseEffect.fadeOut(msgPanel.el(), new FxConfig(2000));
	}

	public void showMsgPanel(String msg) {
		msgHtml.setHtml(msg);
		msgPanel.show();
		BaseEffect.fadeIn(msgPanel.el(), new FxConfig(2000));
		new Timer() {
			@Override
			public void run() {
				hideMsgPanel();
			}
		}.schedule(5000);
	}

	public void hideTopPanel() {
		topPanel.hide();
	}

	public void showTopPanel() {
		topPanel.show();
	}

	protected ToolButton addPlusToolButton(SelectionListener<IconButtonEvent> listener) {
		return addToolButton("x-tool-plus", ewtConstants.add(), listener);
	}

	protected ToolButton addSaveToolButton(SelectionListener<IconButtonEvent> listener) {
		return addToolButton("x-tool-save", ewtConstants.save(), listener);
	}

	protected ToolButton addSearchToolButton(SelectionListener<IconButtonEvent> listener) {
		return addToolButton("x-tool-search", ewtConstants.search(), listener);
	}

	protected ToolButton addToolButton(String style, String tip, SelectionListener<IconButtonEvent> listener) {
		ToolButton toolButton = new ToolButton(style);
		toolButton.setToolTip(tip);
		toolButton.addSelectionListener(listener);
		getHeader().addTool(toolButton);
		return toolButton;
	}

	public void redraw() {
		rootPanel.layout(true);
	}

	protected void initTimer(Command command, int ms) {
		timerCommand = command;
		this.ms = ms;

		stopTimer();

		timer = new Timer() {
			@Override
			public void run() {
				timerCommand.execute();
			}
		};
	}

	private void startTimer(int ms) {
		if(timer != null) timer.scheduleRepeating(ms);
	}

	private void stopTimer() {
		if(timer != null) timer.cancel();
	}

	protected AsyncCallback<Void> getResetCallback() {
		return new EWTCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				reset();
			}
		};
	}

	protected AsyncCallback<Boolean> getResetCallback(final String OkMsg, final String BadMsg) {
		return new EWTCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				reset();

				showMsgPanel(result ? OkMsg : BadMsg);
			}
		};
	}

	protected AsyncCallback<Boolean> getRedrawCallback(final String OkMsg, final String BadMsg) {
		return new EWTCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				reset();

				redraw();

				showMsgPanel(result ? OkMsg : BadMsg);
			}
		};
	}

	protected AsyncCallback<Void> getRedrawCallback() {
		return new EWTCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				reset();

				redraw();
			}
		};
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	@Override
	public void setresetted() {
		setReset(false);
	}
}
