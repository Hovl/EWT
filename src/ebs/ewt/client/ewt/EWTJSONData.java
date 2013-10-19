package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import ebs.ewt.client.widgets.form.EWTDateRangeDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Dubov Aleksey
 * Date: Nov 10, 2009
 * Time: 4:59:39 PM
 * Company: EBS (c) 2009
 */

public abstract class EWTJSONData<T> implements EWTModelData, ModelData, Serializable {
	private transient FastMap<Object> map = new FastMap<Object>();

	public abstract T getObject(JSONObject json);

	public abstract JSONObject getJSONObject(T t);

	public <X> X get(String property) {
		return (X) map.get(property);
	}

	@Override
	public Map<String, Object> getProperties() {
		return map;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return map.keySet();
	}

	@Override
	public <X> X remove(String property) {
		return (X) map.remove(property);
	}

	@Override
	public <X> X set(String property, X value) {
		return (X) map.put(property, value);
	}

	public static JSONObject getJSONObject(String json) {
		return JSONParser.parseStrict(json).isObject();
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

	public static String getJSONString(JSONObject object, String property) {
		return object.get(property).isString().stringValue();
	}

	public static int getJSONInt(JSONObject object, String property) {
		return (int) object.get(property).isNumber().doubleValue();
	}

	public static boolean getJSONBoolean(JSONObject object, String property) {
		return object.get(property).isBoolean().booleanValue();
	}

	public List<T> getObjectsArray(String jsonArray) {
		JSONValue value = JSONParser.parseStrict(jsonArray);
		JSONArray array = value.isArray();
		if(array != null) {
			List<T> result = new ArrayList<T>(array.size());

			int len = array.size();
			for(int i = 0; i < len; i++) {
				JSONObject object = array.get(i).isObject();
				if(object == null) {
					continue;
				}

				result.add(getObject(object));
			}

			return result;
		}
		return null;
	}

	public String getJSONStringOfArray(List<T> list) {
		JSONArray array = new JSONArray();
		int len = list.size();
		for(int i = 0; i < len; i++) {
			array.set(i, getJSONObject(list.get(i)));
		}
		return array.toString();
	}
}
