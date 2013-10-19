package ebs.ewt.client.ewt;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by Dubov Aleksey
 * Date: Sep 4, 2009
 * Time: 11:20:53 PM
 * Company: EBS (c) 2009
 */

public class EWTBlockCallback<T> implements AsyncCallback<T> {
	private EWTBlock block;
	private AsyncCallback<T> callback;

	public EWTBlockCallback(EWTBlock block, AsyncCallback<T> callback) {
		this.block = block;
		this.callback = callback;
	}

	public void onFailure(Throwable caught) {
		block.setFree();
		callback.onFailure(caught);
	}

	public void onSuccess(T result) {
		block.setFree();
		callback.onSuccess(result);
	}
}
