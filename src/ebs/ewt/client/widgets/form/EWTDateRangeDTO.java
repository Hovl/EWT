package ebs.ewt.client.widgets.form;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dubov Aleksei
 * Date: Dec 10, 2008
 * Time: 5:07:16 PM
 * Company: EBS (c) 2008
 */

public class EWTDateRangeDTO implements Serializable {
	private int selection;
	private Date from;
	private Date to;

	public EWTDateRangeDTO() {
	}

	public EWTDateRangeDTO(int selection, Date from, Date to) {
		this.selection = selection;
		this.from = from;
		this.to = to;
	}

	public int getSelection() {
		return selection;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}
}
