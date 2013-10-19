package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.TextField;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Aleksey Dubov.
 * Date: Aug 5, 2010
 * Time: 8:19:05 PM
 * Copyright (c) 2010-2011
 */
public class EWTPasswordField extends AdapterField {
	private TextField<String> passField1;
	private TextField<String> passField2;

	public EWTPasswordField(String id, String name, String regex, boolean allowBlank) {
		super(new HorizontalPanel());

		HorizontalPanel mainPanel = (HorizontalPanel) super.getWidget();
		super.setData("id", id);
		super.setFieldLabel(name);

		passField1 = new TextField<String>() {
			@Override
			protected void onKeyUp(FieldEvent fe) {
				super.onKeyUp(fe);

				validateAndMark();
			}
		};
		passField1.setPassword(true);
		passField1.setRegex(regex);
		passField1.setAllowBlank(allowBlank);

		passField2 = new TextField<String>() {
			@Override
			protected void onKeyUp(FieldEvent fe) {
				super.onKeyUp(fe);

				validateAndMark();
			}
		};
		passField2.setPassword(true);
		passField2.setRegex(regex);

		VerticalPanel panel = new VerticalPanel();
		panel.add(passField1);
		panel.add(passField2);

		mainPanel.add(panel);
	}

	private void validateAndMark() {
		if(!isValid()) {
			passField1.markInvalid(ewtConstants.mismatch());
			passField2.markInvalid(ewtConstants.mismatch());
		} else {
			passField1.clearInvalid();
			passField2.clearInvalid();
		}
	}

	@Override
	public boolean isValid(boolean silent) {
		return isValid();
	}

	@Override
	public boolean validate(boolean preventMark) {
		return isValid();
	}

	@Override
	protected boolean validateValue(String value) {
		return isValid();
	}

	@Override
	public boolean isValid() {
		return passField1.getAllowBlank() && passField1.getValue() == null && passField2.getValue() == null ||
				passField1.getValue().equals(passField2.getValue());

	}

	@Override
	public String getValue() {
		return passField1.getValue();
	}

	@Override
	public void reset() {
		passField1.reset();
		passField2.reset();
	}
}
