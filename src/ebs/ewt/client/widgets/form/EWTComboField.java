package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import ebs.ewt.client.ewt.EWTComboFieldItem;
import ebs.ewt.client.ewt.EWTDataType;
import ebs.ewt.client.ewt.EWTPages;

import java.util.Map;
import java.util.Set;

/**
 * Created by Dubov Aleksey
 * Date: Oct 9, 2009
 * Time: 5:10:21 PM
 * Company: EBS (c) 2009
 */

public class EWTComboField extends AdapterField {

	private ComboBox<EWTComboFieldItem> box;
	private EWTComboFieldItem defItem;
	private EWTDataType type;
	private ListStore<EWTComboFieldItem> store;

	public EWTComboField(String def, EWTDataType type) {
		super(new ComboBox<EWTComboFieldItem>());

		this.box = (ComboBox) super.getWidget();
		this.defItem = new EWTComboFieldItem(type.getValue(), def);
		this.type = type;

		super.setData("id", type.getID());
		super.setFieldLabel(type.getName());

		store = new ListStore<EWTComboFieldItem>();

		box.setDisplayField("value");
		box.setStore(store);
		box.setTypeAhead(true);
		box.setTriggerAction(ComboBox.TriggerAction.ALL);
		box.setOriginalValue(defItem);

		if(EWTPages.dataCache.containsKey(type.toString())) {
			init((Map<Integer, String>) EWTPages.dataCache.get(type.toString()));
		}
	}

	public Integer getValue() {
		return box.getValue().getID();
	}

	public void reset() {
		init((Map<Integer, String>) EWTPages.dataCache.get(type.toString()));
	}

	public void init(Map<Integer, String> map) {
		store.removeAll();
		store.add(defItem);

		Set<Map.Entry<Integer, String>> set = map.entrySet();
		for(Map.Entry<Integer, String> integerStringEntry : set) {
			store.add(new EWTComboFieldItem(integerStringEntry.getKey(), integerStringEntry.getValue()));
		}
	}
}
