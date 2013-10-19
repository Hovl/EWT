package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.data.BaseModelData;
import ebs.ewt.client.widgets.form.EWTDateRangeDTO;

/**
 * Created by Dubov Aleksei
 * Date: Nov 7, 2008
 * Time: 4:43:46 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTDataDTO extends BaseModelData implements EWTModelData {
	public <X> X get(String property) {
		return (X) super.get(property);
	}

	public <X> X set(String property, X o) {
		return (X) super.set(property, o);
	}

	public boolean containsKey(String property) {
		return map.containsKey(property);
	}

	public EWTDateRangeDTO checkEwtDateRangeDTO(String property) {
		return get(property) == null ? null : (EWTDateRangeDTO) get(property);
	}

	public String checkString(String property) {
		return get(property) == null ? "" : (String) get(property);
	}

	public Integer checkInteger(String property) {
		return get(property) == null ? -1 : (Integer) get(property);
	}

	public Boolean checkBoolean(String property) {
		return get(property) == null ? false : (Boolean) get(property);
	}

	public String checkToString(String property) {
		return get(property) == null ? "" : ((Object) get(property)).toString();
	}

	public static Float getFloat(Object o) {
		if(o instanceof Double) {
			return new Float((Double) o);
		} else {
			return (Float) o;
		}
	}
}
