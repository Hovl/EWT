package ebs.ewt.client.widgets;

import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTWidget;

/**
 * Created by Aleksey Dubov.
 * Date: 2010-11-07
 * Time: 14:07
 * Copyright (c) 2010
 */
public class EWTDummyWidget implements EWTWidget {
	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean isInited() {
		return false;
	}

	@Override
	public void inited(EWTCommand<EWTWidget> cmd) {
	}

	@Override
	public void init() {
	}

	@Override
	public void reset() {
	}

	@Override
	public void redraw() {
	}

	@Override
	public void sleep() {
	}

	@Override
	public void wakeup() {
	}

	@Override
	public void setresetted() {
	}
}
