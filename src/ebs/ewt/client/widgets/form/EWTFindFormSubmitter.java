package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.data.ModelData;
import ebs.ewt.client.ewt.EWTDataDTO;
import ebs.ewt.client.ewt.EWTModelData;

/**
 * Created by Dubov Aleksei
 * Date: Dec 10, 2008
 * Time: 7:38:46 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTFindFormSubmitter<T extends EWTModelData & ModelData> implements EWTFormSubmitter<T> {
	public void add(T t) { }

	public void save(T t) { }

	public void cancel() { }
}
