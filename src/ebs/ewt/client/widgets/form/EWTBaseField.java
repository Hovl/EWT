package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.widget.form.*;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

/**
 * Created by Dubov Aleksei
 * Date: Dec 2, 2008
 * Time: 8:43:57 PM
 * Company: EBS (c) 2008
 */

public class EWTBaseField {
	public static Field<Object> getLabelField(String id, String name) {
		return getField(id, name, new LabelField());
	}

	public static Field<Boolean> getBooleanField(String id, String name) {
		return getField(id, name, new CheckBox());
	}

	public static Field<String> getStringField(String id, String name, boolean allowBlank, String regex) {
		TextField<String> field = (TextField<String>) getField(id, name, new TextField<String>());
		field.setAllowBlank(allowBlank);
		field.setRegex(regex);
		return field;
	}

	public static Field<Number> getIntegerField(String id, String name, boolean allowBlank) {
		EWTIntegerField field = (EWTIntegerField) getField(id, name, new EWTIntegerField());
		field.setAllowBlank(allowBlank);
		return field;
	}

	public static Field<Number> getDecimalsField(String id, String name, boolean allowBlank) {
		Field<Number> field = getIntegerField(id, name, allowBlank);
		((NumberField) field).setAllowDecimals(true);
		return field;
	}

	public static DateField getDateField(String id, String name, boolean allowBlank) {
		DateField field = (DateField) getField(id, name, new DateField());
		field.setAllowBlank(allowBlank);
		field.setOriginalValue(new Date());
		field.getPropertyEditor()
				.setFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.YEAR_MONTH_NUM_DAY));
		return field;
	}

	public static <X> Field<X> getField(String id, String name, Field<X> field) {
		field.setData("id", id);
		field.setFieldLabel(name);
		return field;
	}
}
