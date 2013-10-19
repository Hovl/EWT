package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.data.BaseModelData;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 27.04.12
 * Time: 21:08
 */

public class EWTComboFieldItem extends BaseModelData {

	public EWTComboFieldItem() {

	}

	public EWTComboFieldItem(Integer id, String value) {
		set("id", id);
		set("value", value);
	}

	public Integer getID() {
		return get("id");
	}

	public String getValue() {
		return get("value");
	}
}
