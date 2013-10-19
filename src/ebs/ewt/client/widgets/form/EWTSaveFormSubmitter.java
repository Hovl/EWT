package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.data.ModelData;
import ebs.ewt.client.ewt.EWTModelData;

/**
 * Created by Dubov Aleksei
 * Date: Dec 10, 2008
 * Time: 6:51:05 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTSaveFormSubmitter<T extends EWTModelData & ModelData> implements EWTFormSubmitter<T> {
	public void add(T t) {
		save(t);
	}

	public void find(T t) { }

	public void cancel() { }
}
