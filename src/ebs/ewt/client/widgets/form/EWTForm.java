package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.event.dom.client.KeyCodes;
import static ebs.ewt.client.EWT.ewtConstants;

import ebs.ewt.client.ewt.EWTModelData;
import ebs.ewt.client.ewt.EWTWidget;
import ebs.ewt.client.widgets.EWTIconButton;
import ebs.ewt.client.widgets.EWTPage;
import ebs.ewt.client.widgets.EWTSaveBar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dubov Aleksei
 * Date: Dec 2, 2008
 * Time: 8:08:20 PM
 * Company: EBS (c) 2008
 */

public class EWTForm<T extends EWTModelData & ModelData> extends HorizontalPanel {
	private FormPanel form;
	private EWTSaveBar saveBar;
	private List<Field> formFields;
	private EWTFormSubmitter<T> formSubmitter;
	private Html msgHtml;

	private EWTIconButton.EWTIconButtonListener<T> editListener;

	private T curT;

	private EWTWidget widget;

	public EWTForm(Field[] fields, EWTFormSubmitter<T> submitter, EWTWidget widget) {
		this(Arrays.asList(fields), submitter, widget, false);
	}

	public EWTForm(List<Field> fields, EWTFormSubmitter<T> submitter, EWTWidget widget) {
		this(fields, submitter, widget, false);
	}

	public EWTForm(Field[] fields, EWTFormSubmitter<T> submitter, EWTWidget widget, boolean findEnabled) {
		this(Arrays.asList(fields), submitter, widget, findEnabled);
	}

	public EWTForm(List<Field> fields, EWTFormSubmitter<T> submitter, EWTWidget ewtWidget, boolean findEnabled) {
		super();
		setVisible(false);
		setVerticalAlign(Style.VerticalAlignment.BOTTOM);
		setSpacing(2);

		this.widget = ewtWidget;

		formFields = fields;
		formSubmitter = submitter;

		editListener = new EWTIconButton.EWTIconButtonListener<T>() {
			public void onEvent(T t) {
				setEdit(t);
			}
		};

		if(!findEnabled) {
			saveBar = new EWTSaveBar(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					action(false);
				}
			}, new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					action(true);
				}
			}, new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					resetForm();
					formSubmitter.cancel();
					widget.redraw();
				}
			});
		}

		form = new FormPanel();
		form.setHeaderVisible(false);
		form.setLabelWidth(150);
		form.setLabelAlign(FormPanel.LabelAlign.RIGHT);
		form.setFieldWidth(200);
		form.setBodyBorder(false);
		form.setPadding(2);

		KeyListener keyListener = new KeyListener() {
			@Override
			public void componentKeyDown(ComponentEvent event) {
				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					action(saveBar == null || saveBar.getConfig());
				}
			}
		};

		for(Field f : fields) {
			form.add(f);
			f.addKeyListener(keyListener);
		}

		msgHtml = new Html();
		msgHtml.setStyleName("ewt-form-msg");
		msgHtml.hide();

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(msgHtml);
		vPanel.add(form);

		add(vPanel);
		if(findEnabled) {
			add(new Button(ewtConstants.find(), new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					action(false);
				}
			}));
		} else {
			add(saveBar);
		}
	}

	public T getCurrentObject() {
		submit();
		curT.submit();
		return curT;
	}

	public void resetFields() {
		for(Field field : formFields) {
			field.reset();
		}
	}

	public void setObject(T t, boolean clean) {
		curT = t;

		resetFields();

		if(!clean) {
			for(Field f : formFields) {
				f.setValue(curT.get((String) f.getData("id")));
			}
		}
	}

	public void setEdit(T t) {
		setObject(t, false);
		if(saveBar != null) saveBar.setSave();
		show();
		widget.redraw();
	}

	public void setAdd(T t) {
		setObject(t, true);
		if(saveBar != null) saveBar.setAdd();
		show();
		widget.redraw();
	}

	private boolean submit() {
		boolean valid = true;
		for(Field f : formFields) {
			curT.set((String) f.getData("id"), f.getValue());
			if(!f.isValid()) {
				valid = false;
			}
		}
		return valid;
	}

	private void action(boolean add) {
		if(!submit()) return;

		curT.submit();
		if(saveBar != null) {
			saveBar.setWait();
			form.setReadOnly(true);
			if(add) formSubmitter.add(curT); else formSubmitter.save(curT);
		} else {
			formSubmitter.find(curT);
		}
	}

	public void resetForm() {
		resetForm(false);
	}

	public void resetForm(boolean visible) {
		hideMsg();
		resetFields();

		if(saveBar != null) {
			saveBar.setWork();
			form.setReadOnly(false);
			setVisible(visible);
		}
		layout();
	}

	public Field getField(String id) {
		for(Field field : formFields) {
			if(id.equals(field.getData("id"))) return field;
		}
		return null;
	}

	public EWTIconButton<T> getEditButton(String style, T t) {
		return new EWTIconButton<T>(style, ewtConstants.edit(), t, editListener);
	}

	public void switchVisibility() {
		setVisible(!isVisible());
		widget.redraw();
	}

	public void hideOrAdd(T t) {
		if(isVisible()) {
			hide();
			widget.redraw();
		} else {
			setAdd(t);
		}
	}

	public void showMsg(String msg) {
		msgHtml.setHtml(msg);
		msgHtml.show();

		if(saveBar != null) {
			saveBar.setWork();
			form.setReadOnly(false);
		}

		layout();
	}

	public Html getMsgHtml() {
		return msgHtml;
	}

	public void hideMsg() {
		msgHtml.hide();
	}

	public void hideCancelButton() {
		if(saveBar != null) {
			saveBar.hideCancelButton();
		}
	}

	public void showCancelButton() {
		if(saveBar != null) {
			saveBar.showCancelButton();
		}
	}
}
