package ebs.ewt.client.ewt;

/**
 * Created by Dubov Aleksey
 * Date: Sep 4, 2009
 * Time: 11:25:42 PM
 * Company: EBS (c) 2009
 */

public class EWTBlock {
	private boolean busy = false;

	public void setBusy() {
		busy = true;
	}

	public void setFree() {
		busy = false;
	}

	public boolean isBusy() {
		return busy;
	}
}
