package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import ebs.ewt.client.ewt.EWTDataType;
import ebs.ewt.client.ewt.EWTPages;

import java.util.*;

/**
 * Created by Aleksey Dubov
 * Date: Sep 14, 2010
 * Time: 4:21:13 PM
 * Copyright (c) 2010-2012
 */
public class EWTCheckGroupField extends AdapterField {
	private CheckBoxGroup checkBoxGroup;
	private ListBox listBox;
	private boolean allowBlank;
	private Map<Integer, CheckBox> boxMap;
	private EWTDataType type;

	public EWTCheckGroupField(EWTDataType type, String id, String name, boolean allowBlank) {
		super(new VerticalPanel());
		super.setData("id", id);
		super.setFieldLabel(name);

		this.type = type;
		this.allowBlank = allowBlank;
		this.boxMap = new HashMap<Integer, CheckBox>();

		checkBoxGroup = new CheckBoxGroup(); //(CheckBoxGroup) super.getWidget();
		checkBoxGroup.setOrientation(Style.Orientation.VERTICAL);
		checkBoxGroup.setSpacing(0);

		listBox = new ListBox(false);
		listBox.addItem("", Integer.toString(0 - this.hashCode()));
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = listBox.getSelectedIndex();
				CheckBox checkBox = boxMap.get(Integer.parseInt(listBox.getValue(index)));
				checkBox.setValue(true);

				listBox.removeItem(index);
			}
		});

		VerticalPanel panel = (VerticalPanel) super.getWidget();
		panel.add(checkBoxGroup);
		panel.add(listBox);
	}

	@Override
	public boolean isValid(boolean silent) {
		return checkBoxGroup.isValid(silent);
	}

	@Override
	public boolean validate(boolean preventMark) {
		return checkBoxGroup.validate(preventMark);
	}

	@Override
	public boolean isValid() {
		checkBoxGroup.isValid();
		return allowBlank || !checkBoxGroup.getValues().isEmpty();
	}

	@Override
	public List<Integer> getValue() {
		List<Integer> list = new ArrayList<Integer>();

		List<CheckBox> boxs = checkBoxGroup.getValues();
		for (CheckBox box : boxs) {
			if (box.getValue()) {
				list.add(Integer.parseInt(box.getId()));
			}
		}

		return list;
	}

	@Override
	public void setValue(Object value) {
		List<Integer> list = (List<Integer>) value;

		checkBoxGroup.reset();
		for (Integer id : list) {
			boxMap.get(id).setValue(true);
		}
	}

	@Override
	public void reset() {
		checkBoxGroup.reset();

		if (boxMap.isEmpty()) {
			if (EWTPages.dataCache.containsKey(type.toString())) {
				Map<Integer, String> map = (Map<Integer, String>) EWTPages.dataCache.get(type.toString());
				Set<Map.Entry<Integer, String>> set = map.entrySet();
				for (Map.Entry<Integer, String> entry : set) {
					CheckBox checkBox = new CheckBox() {
						@Override
						public void setValue(Boolean value) {
							super.setValue(value);
							super.setVisible(value);

							if (!value) {
								Integer itemCount = listBox.getItemCount();
								for (int i = 0; i < itemCount; i++) {
									if (listBox.getValue(i).equals(super.getId())) {
										return;
									}
								}

								listBox.addItem(super.getBoxLabel(), super.getId());
							}
						}
					};

					checkBox.setId(entry.getKey().toString());
					checkBox.setBoxLabel(entry.getValue());
					boxMap.put(entry.getKey(), checkBox);

					checkBoxGroup.add(checkBox);

					listBox.addItem(entry.getValue(), entry.getKey().toString());
				}
			} else {
				EWTListField.init(type);
			}
		}
	}
}
