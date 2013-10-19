package ebs.ewt.client.ewt;

import com.google.gwt.user.client.Command;

/**
 * Created by Dubov Aleksei
 * Date: Sep 29, 2008
 * Time: 4:57:40 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTCommand<T> implements Command {
	private T obj = null;

	public EWTCommand() {
	}

	public EWTCommand(T obj) {
		this.obj = obj;
	}

	public T getObj() {
		return obj;
	}

	public abstract void execute(T t);

	@Override
	public void execute() {
		execute(obj);
	}
}
