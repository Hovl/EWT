package ebs.ewt.client.ewt;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.EWT;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Dubov Aleksei
 * Date: Nov 3, 2008
 * Time: 3:52:42 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTCallback<T> implements AsyncCallback<T> {
	public void onFailure(Throwable caught) {
		EWT.display(ewtConstants.errorReceived() + "</br>" + caught.getMessage());
	}

	public abstract void onSuccess(T result);
}
