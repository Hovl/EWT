package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.ewt.EWTComboFieldItem;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 27.04.12
 * Time: 18:38
 */

public abstract class EWTComboFieldAsync extends AdapterField{


	private ComboBox<EWTComboFieldItem> box;
	private EWTComboFieldItem defItem;
	private ListStore<EWTComboFieldItem> store;

	public EWTComboFieldAsync(String id, String name, String def, Integer minChars) {
		super(new ComboBox<EWTComboFieldItem>());
		super.setData("id", id);
		super.setFieldLabel(name);

		this.box = (ComboBox) super.getWidget();
		store = new ListStore<EWTComboFieldItem>(new BaseListLoader< BaseListLoadResult<EWTComboFieldItem>>(
				new RpcProxy<BaseListLoadResult<EWTComboFieldItem>>() {
					@Override
					protected void load(Object loadConfig, AsyncCallback<BaseListLoadResult<EWTComboFieldItem>> asyncCallback) {
						loadList(box.getRawValue(), asyncCallback);
					}
				}
		)
		);

		box.setDisplayField("value");
		box.setMinChars(minChars);
		box.setAllowBlank(false);
		box.setStore(store);
		box.setTypeAhead(true);
		box.setTriggerAction(ComboBox.TriggerAction.ALL);
		if (def!=null){
			this.defItem = new EWTComboFieldItem(0, def);
			box.setOriginalValue(defItem);
		}
	}



	public Integer getValue() {
		return box.getValue().getID();
	}

	public void reset() {
		store.removeAll();
	}

	protected abstract void loadList(String text, AsyncCallback<BaseListLoadResult<EWTComboFieldItem>> callback);

}
