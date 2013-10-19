package ebs.ewt.client.ewt;

import ebs.ewt.client.EWT;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Dubov Aleksei
 * Date: Feb 19, 2008
 * Time: 12:53:34 AM
 * Company: EBS (c) 2007
 */
public abstract class EWTNotNullCallback<T> extends EWTCallback<T> {
	public abstract void onNotNullSuccess(T result);

	public void onSuccess(T result) {
		if(result != null) {
			onNotNullSuccess(result);
		} else {
			EWT.displayAlertWindow(
					ewtConstants.errorNullResultReceivedHeader(),
					ewtConstants.errorNullResultReceived());
		}
	}
}
