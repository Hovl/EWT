package ebs.ewttest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import ebs.ewt.client.ewt.EWTDataType;
import ebs.ewt.client.ewt.EWTUtils;
import ebs.ewt.client.widgets.form.EWTListField;
import ebs.ewt.client.widgets.grid.EWTDataRender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dubov Aleksey
 * Date: Oct 15, 2009
 * Time: 5:18:05 PM
 * Company: EBS (c) 2009
 */

public enum TestDataTypeImpl implements EWTDataType, EWTDataRender {
	Level("level", "level", "searchLevel", 0),
	Owner("owner", "owner", "searchOwner", 0);

	private String id;
	private String name;
	private String searchName;
	private int value;

	TestDataTypeImpl(String id, String name, String searchName, int value) {
		this.id = id;
		this.name = name;
		this.searchName = searchName;
		this.value = value;
	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSearchName() {
		return this.searchName;
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public void init(AsyncCallback<Map<Integer, String>> callback) {
		Map<Integer, String> result = new HashMap<Integer, String>();
		for(int i = 0; i < 11; i++) {
			result.put(i, this.equals(Level) ? "level" + i : "owner" + i);
		}
		callback.onSuccess(result);
	}

	public Widget renderWidget(Object o) {
		return EWTUtils.getHTMLInHorizontalPanel(renderData(o));
	}

	public String renderData(Object o) {
		return EWTListField.getDataValue(this, (Integer) o);
	}
}
