package ebs.ewt.client.ewt;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Map;

/**
 * Created by Dubov Aleksei
 * Date: Nov 2, 2008
 * Time: 8:33:44 PM
 * Company: EBS (c) 2008
 */

public interface EWTDataType {
	public String getID();
	public String getName();
	public String getSearchName();
	public int getValue();
	public void init(AsyncCallback<Map<Integer, String>> callback);
}
