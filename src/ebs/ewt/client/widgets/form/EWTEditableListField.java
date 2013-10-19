package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTDataType;
import ebs.ewt.client.ewt.EWTNotNullCallback;
import ebs.ewt.client.ewt.EWTWidget;
import ebs.ewt.client.widgets.EWTDummyWidget;
import ebs.ewt.client.widgets.EWTIconButton;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Aleksey Dubov.
 * Date: 2010-11-07
 * Time: 12:20
 * Copyright (c) 2010
 */
public class EWTEditableListField extends AdapterField {
	private EWTListField listField;
	private Window window = null;
	private EWTFormProvider provider;
	private EWTForm form;

	public interface EWTFormProvider {
		public EWTForm getForm(EWTWidget widget, AsyncCallback<Integer> callback);
	}

	public EWTEditableListField(EWTDataType type, boolean allowBlank, EWTFormProvider _provider) {
		this(type.getName(), type, allowBlank, _provider);
	}

	public EWTEditableListField(String def, EWTDataType type, boolean allowBlank, EWTFormProvider _provider) {
		super(new HorizontalPanel());

		this.provider = _provider;

		listField = new EWTListField(def, type, allowBlank);
		EWTIconButton<EWTDataType> addButton = new EWTIconButton<EWTDataType>("ewt-tool-add", ewtConstants.add(), type,
				new EWTIconButton.EWTIconButtonListener<EWTDataType>() {
					@Override
					public void onEvent(final EWTDataType type) {
						if(window == null) {
							window = new Window();
							window.setLayout(new FitLayout());
							window.setAutoWidth(true);
							window.setAutoHeight(true);
							window.setDraggable(false);
							window.setModal(true);
							window.setHeading(ewtConstants.add() + " " + listField.getTitle());

							form = provider.getForm(
									new EWTDummyWidget() {
										@Override
										public void redraw() {
											if(window.isVisible()) {
												window.hide();
											}
										}
									},
									new EWTNotNullCallback<Integer>() {
										@Override
										public void onNotNullSuccess(Integer result) {
											EWTListField.init(type, new EWTCommand<Integer>(result) {
												@Override
												public void execute(Integer integer) {
													window.hide();
													listField.setSelectedIndex(integer);
												}
											});
										}
							});

							window.add(form);
						}

						form.resetForm(true);
						window.show();
					}
				});

		HorizontalPanel mainPanel = (HorizontalPanel) super.getWidget();
		super.setData("id", listField.getId());
		super.setFieldLabel(listField.getFieldLabel());

		mainPanel.add(listField);
		mainPanel.add(addButton);
	}

	@Override
	public boolean isValid(boolean silent) {
		return listField.isValid(silent);
	}

	@Override
	public boolean validate(boolean preventMark) {
		return listField.validate(preventMark);
	}

	@Override
	protected boolean validateValue(String value) {
		return listField.validateValue(value);
	}

	@Override
	public boolean isValid() {
		return listField.isValid();
	}

	@Override
	public Integer getValue() {
		return listField.getValue();
	}

	@Override
	public void setValue(Object value) {
		listField.setValue(value);
	}

	@Override
	public void reset() {
		listField.reset();
	}
}
