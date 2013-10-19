package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

/**
 * Created by Dubov Aleksey
 * Date: Feb 6, 2009
 * Time: 4:10:17 PM
 * Company: EBS (c) 2008
 */

public class EWTDateField extends AdapterField {
	private DateField field;

	public EWTDateField(String formatPattern) {
		super(new HorizontalPanel());

		field = new DateField();
		field.getPropertyEditor()
                .setFormat(DateTimeFormat.getFormat(formatPattern));
		field.setOriginalValue(new Date());

		((HorizontalPanel) getWidget()).add(field);
	}

	public EWTDateRangeDTO getValue() {
		return new EWTDateRangeDTO(0, field.getValue(), field.getValue());
	}

	public void setValue(EWTDateRangeDTO dto) {
		super.setValue(dto.getTo());
	}

	public void reset() {
		field.setValue(field.getOriginalValue());
	}

	@Override
	public boolean isValid() {
		return field.isValid();
	}
}
