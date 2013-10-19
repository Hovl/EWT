package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;

import java.util.Date;

import static ebs.ewt.client.EWT.ewtConstants;
import static ebs.ewt.client.widgets.form.EWTDateRangeField.EWTRangeType.*;

/**
 * Created by Dubov Aleksei
 * Date: Apr 29, 2008
 * Time: 3:02:54 PM
 * Company: EBS (c) 2008
 */

public class EWTDateRangeField extends AdapterField implements ChangeHandler {
	public enum EWTRangeType {
		TODAY,
		CURMONTH,
		LASTDAY,
		LASTMONTH
	}

	private DateField fromField;
	private DateField toField;
	private ListBox rangeBox;

	public EWTDateRangeField(EWTRangeType defaultValue, String formatPattern) {
		super(new HorizontalPanel());
		setHideLabel(true);

		setOriginalValue(defaultValue);

		rangeBox = new ListBox(false);
		rangeBox.addItem(ewtConstants.forToday(), TODAY.toString());
		rangeBox.addItem(ewtConstants.forCurMonth(), CURMONTH.toString());
		rangeBox.addItem(ewtConstants.forLastDay(), LASTDAY.toString());
		rangeBox.addItem(ewtConstants.forLastMonth(), LASTMONTH.toString());

		rangeBox.addChangeHandler(this);

		fromField = new DateField();
		fromField.setToolTip(ewtConstants.from());
		fromField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat(formatPattern));

		toField = new DateField();
		toField.setToolTip(ewtConstants.to());
		toField.getPropertyEditor().setFormat(
				DateTimeFormat.getFormat(formatPattern));

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(fromField);
		verticalPanel.add(toField);

		HorizontalPanel panel = (HorizontalPanel) getWidget();
		panel.setSpacing(5);
		panel.setVerticalAlign(Style.VerticalAlignment.MIDDLE);
		panel.add(new HTML(ewtConstants.range()));
		panel.add(rangeBox);
		panel.add(verticalPanel);
	}

	public EWTDateRangeDTO getValue() {
		return new EWTDateRangeDTO(rangeBox.getSelectedIndex(), getFromDate(), getToDate());
	}

	public void setValue(EWTDateRangeDTO dto) {
		reset(EWTRangeType.values()[dto.getSelection()]);
	}

	public void reset() {
		reset((EWTRangeType) getOriginalValue());
	}

	private void reset(EWTRangeType val) {
		rangeBox.setItemSelected(val.ordinal(), true);
		update();
	}

	public Date getFromDate() {
		return fromField.getValue();
	}

	public Date getToDate() {
		return toField.getValue();
	}

	public void onChange(ChangeEvent event) {
		update();
	}

	private void update() {
		DateWrapper curDate = new DateWrapper().clearTime();
		switch (EWTRangeType.valueOf(rangeBox.getValue(rangeBox.getSelectedIndex()))) {
			case TODAY:
				fromField.setValue(curDate.asDate());
				toField.setValue(curDate.addDays(1).asDate());
				break;
			case CURMONTH:
				fromField.setValue(curDate.getFirstDayOfMonth().asDate());
				toField.setValue(curDate.addDays(1).asDate());
				break;
			case LASTDAY:
				fromField.setValue(curDate.addDays(-1).asDate());
				toField.setValue(curDate.asDate());
				break;
			case LASTMONTH:
				fromField.setValue(curDate.addMonths(-1).getFirstDayOfMonth().asDate());
				toField.setValue(curDate.getFirstDayOfMonth().asDate());
				break;
			default:
				break;
		}
	}
}
