package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.google.gwt.user.client.ui.ListBox;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTDataType;
import ebs.ewt.client.ewt.EWTNotNullCallback;
import ebs.ewt.client.ewt.EWTPages;

import java.util.*;

/**
 * Created by Dubov Aleksei
 * Date: Nov 2, 2008
 * Time: 8:34:29 PM
 * Company: EBS (c) 2008
 */

public class EWTListField extends AdapterField {
	private static Set<String> initSet = new HashSet<String>();
	private static Map<EWTDataType, List<EWTListField>> fieldsMap = new HashMap<EWTDataType, List<EWTListField>>();

	private ListBox box;
	private String def;
	private EWTDataType type;
	private boolean allowBlank;
	private Map<Integer, String> data;

	public EWTListField(EWTDataType type, boolean allowBlank) {
		this(type.getName(), type, allowBlank);
	}

	public EWTListField(String def, EWTDataType type, boolean allowBlank) {
		super(new ListBox(false));

		this.box = (ListBox) super.getWidget();
		this.def = def;
		this.type = type;
		this.allowBlank = allowBlank;

		super.setData("id", type.getID());
		super.setFieldLabel(type.getName());

		addField(type, this);

		box.addItem(def, Integer.toString(type.getValue()));

		if(EWTPages.dataCache.containsKey(type.toString())) {
			init((Map<Integer, String>) EWTPages.dataCache.get(type.toString()));
		}
	}

	public Integer getValue() {
		return Integer.parseInt(box.getValue(box.getSelectedIndex()));
	}

	public void setValue(Object value) {
		setSelectedIndex((Integer) value);
	}

	public void reset() {
		reset(false);
	}

	public void setSelectedIndex(Integer index) {
		if(data.isEmpty()) {
			box.setSelectedIndex(index);
			return;
		}

		int i = 0;
		Set<Integer> set = data.keySet();
		for (Integer integer : set) {
			i++;
			if (integer.equals(index)) {
				box.setSelectedIndex(i);
				return;
			}
		}

		box.setSelectedIndex(index);
	}

	public int getSelectedIndex() {
		return box.getSelectedIndex();
	}

	public String getItemText(int index) {
		return box.getItemText(index);
	}

	public String getDataValue(Integer id) {
		return data.containsKey(id) ? data.get(id) : "";
	}

	public void init(Map<Integer, String> map) {
		box.clear();
		box.addItem(def, Integer.toString(type.getValue()));

		data = map;
		Set<Map.Entry<Integer, String>> set = map.entrySet();
		for(Map.Entry<Integer, String> integerStringEntry : set) {
			box.addItem(integerStringEntry.getValue(), integerStringEntry.getKey().toString());
		}
	}

	public void reset(boolean force) {
		clearInvalid();
		if(box.getItemCount() != 0 && !force) {
			box.setSelectedIndex(0);
		} else {
			if(force || !isFilled(type)) {
				initSet.add(type.toString());
				init(type);
			} else {
				init((Map<Integer, String>) EWTPages.dataCache.get(type.toString()));
			}
		}
	}

	@Override
	public boolean isValid(boolean silent) {
		forceInvalidText = allowBlank || !getValue().equals(type.getValue()) ? null : "";
		return validateValue("");
	}

	@Override
	public boolean validate(boolean preventMark) {
		if (disabled) {
		  clearInvalid();
		  return true;
		}
		boolean restore = this.preventMark;
		this.preventMark = preventMark;
		boolean result = validateValue(getRawValue());
		this.preventMark = restore;
		if (result) {
		  clearInvalid();
		}
		return result;
	}

	@Override
	protected boolean validateValue(String value) {
		if (forceInvalidText != null) {
		  markInvalid(forceInvalidText);
		  return false;
		}
		return true;
	}

	public static String getDataValue(EWTDataType type, Integer id) {
		if(EWTPages.dataCache.containsKey(type.toString())) {
			Map<Integer, String> map = (Map<Integer, String>) EWTPages.dataCache.get(type.toString());
			if(map.containsKey(id)) return map.get(id);
		}
		init(type);
		return id.toString();
	}

	public static void init(final EWTDataType type) {
		initSet.add(type.toString());
		type.init(new EWTNotNullCallback<Map<Integer, String>>() {
			public void onNotNullSuccess(Map<Integer, String> result) {
				init(type, result);
			}
		});
	}

	public static void addField(EWTDataType type, EWTListField field) {
		if(!fieldsMap.containsKey(type)) {
			fieldsMap.put(type, new ArrayList<EWTListField>());
		}

		fieldsMap.get(type).add(field);
	}

	public static boolean isFilled(EWTDataType type) {
		return EWTPages.dataCache.containsKey(type.toString()) || initSet.contains(type.toString());
	}

	public static void addPreInit(EWTDataType type) {
		initSet.add(type.toString());
	}

	public static void removePreInit(EWTDataType type) {
		initSet.remove(type.toString());
	}

	public static void init(EWTDataType type, Map<Integer, String> map) {
		String name = type.toString();
		EWTPages.dataCache.put(name, map);

		if(fieldsMap.containsKey(type)) {
			List<EWTListField> list = fieldsMap.get(type);
			for(EWTListField field : list) {
				field.init(map);
			}
		}

		initSet.remove(name);
	}

	public static void init(final EWTDataType type, final EWTCommand<Integer> command) {
		initSet.add(type.toString());
		type.init(new EWTNotNullCallback<Map<Integer, String>>() {
			@Override
			public void onNotNullSuccess(Map<Integer, String> result) {
				init(type, result);
				command.execute();
			}
		});
	}
}
