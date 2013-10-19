package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.widget.form.NumberField;

/**
 * Created by Dubov Aleksei
 * Date: Dec 23, 2008
 * Time: 7:17:01 PM
 * Company: EBS (c) 2008
 */

public class EWTIntegerField extends NumberField {
	public EWTIntegerField() {
		super();
		setAllowDecimals(false);
	}

	public Integer getValue() {
		return super.getValue().intValue();
	}
}
