package ebs.ewt.client.widgets.grid;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.ewt.EWTDataDTO;
import ebs.ewt.client.ewt.EWTModelData;

/**
 * Created by Dubov Aleksey
 * Date: Jul 4, 2009
 * Time: 7:34:51 PM
 * Company: EBS (c) 2009
 */

public interface EWTGridNavigation<T extends EWTModelData & ModelData> {
	void load(int from, int to, int sortby, int order, AsyncCallback<PagingLoadResult<T>> callback);
}
