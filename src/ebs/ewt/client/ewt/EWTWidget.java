package ebs.ewt.client.ewt;

/**
 * Created by Dubov Aleksei
 * Date: Aug 13, 2007
 * Time: 2:57:53 PM
 * Company: EBS (c) 2007
 */
public interface EWTWidget {
	public String getName();

	public boolean isInited();

	public void inited(EWTCommand<EWTWidget> cmd);

	public void init();

	public void reset();

	public void redraw();

	public void sleep();

	public void wakeup();

	public void setresetted();
}
