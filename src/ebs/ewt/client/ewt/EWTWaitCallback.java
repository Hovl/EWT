package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Dubov Aleksey
 * Date: Mar 26, 2010
 * Time: 6:07:15 PM
 * Company: EBS (c) 2009
 */

public class EWTWaitCallback<T> implements AsyncCallback<T> {
	private MessageBox box;
	private AsyncCallback<T> callback;

	public EWTWaitCallback(AsyncCallback<T> callback) {
		this.box = MessageBox.wait(
				ewtConstants.waitRequestCompletionHeader(),
				ewtConstants.waitRequestCompletion(),
				"");
		this.callback = callback;
	}

	public void onFailure(Throwable caught) {
		box.close();
		callback.onFailure(caught);
	}

	public void onSuccess(T result) {
		box.close();
		callback.onSuccess(result);
	}
}
