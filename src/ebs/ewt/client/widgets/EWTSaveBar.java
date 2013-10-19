package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.Image;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Dubov Aleksei
 * Date: Apr 24, 2008
 * Time: 4:05:21 PM
 * Company: EBS (c) 2008
 */

public class EWTSaveBar extends HorizontalPanel {
	private Button saveButton;
	private Button addButton;
	private Button cancelButton;
	private Image waitImage;
	private boolean config;
	private boolean hideCancel = true;

	public EWTSaveBar(SelectionListener<ButtonEvent> saveListener,
					  SelectionListener<ButtonEvent> addListener,
					  SelectionListener<ButtonEvent> cancelListener)
	{
		super();
		setSpacing(2);

		config = false;

		waitImage = new Image("images/little-loading.gif");
		add(waitImage);

		if(saveListener != null) {
			saveButton = new Button(ewtConstants.save(), saveListener);
			add(saveButton);
		}

		if(addListener != null) {
			addButton = new Button(ewtConstants.add(), addListener);
			add(addButton);
		}

		if(cancelListener != null) {
			cancelButton = new Button(ewtConstants.cancel(), cancelListener);
			add(cancelButton);
			hideCancel = false;
		}

		if(addButton == null) {
			setSave();
		}

		if(saveButton == null) {
			setAdd();
		}
	}

	public void setSave() {
		setAction(false);
	}

	public void setAdd() {
		setAction(true);
	}

	private void setAction(boolean b) {
		config = b;

		if(saveButton != null) {
			saveButton.setVisible(!config);
		}

		if(addButton != null) {
			addButton.setVisible(config);
		}

		if(cancelButton != null) {
			cancelButton.setVisible(!hideCancel);
		}

		waitImage.setVisible(false);
	}

	public void setWait() {
		if(saveButton != null) {
			saveButton.setVisible(false);
		}

		if(addButton != null) {
			addButton.setVisible(false);
		}

		if(cancelButton != null) {
			cancelButton.setVisible(hideCancel);
		}

		waitImage.setVisible(true);
	}

	public void setWork() {
		setAction(config);
	}

	public void save() {
		saveButton.fireEvent(Events.Select);
	}

	public boolean getConfig() {
		return config;
	}

	public void hideCancelButton() {
		cancelButton.hide();
		hideCancel = true;
	}

	public void showCancelButton() {
		cancelButton.show();
		hideCancel = false;
	}
}
