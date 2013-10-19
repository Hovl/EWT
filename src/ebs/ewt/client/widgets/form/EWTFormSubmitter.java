package ebs.ewt.client.widgets.form;

import com.extjs.gxt.ui.client.data.ModelData;
import ebs.ewt.client.ewt.EWTModelData;

/**
 * Created by Dubov Aleksei
 * Date: Dec 2, 2008
 * Time: 9:27:20 PM
 * Company: EBS (c) 2008
 */

public interface EWTFormSubmitter<T extends EWTModelData & ModelData> {
	public void add(T t);
	public void save(T t);
	public void cancel();
	public void find(T t);
}
