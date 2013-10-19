package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.widget.form.TextField;
import ebs.ewt.client.ewt.EWTBlock;
import ebs.ewt.client.ewt.EWTCommand;

/**
 * Created by Dubov Aleksey
 * Date: Sep 4, 2009
 * Time: 11:05:53 PM
 * Company: EBS (c) 2009
 */

public class EWTSearchField extends TextField<String> {
	private EWTBlock block;
	private EWTCommand<String> searchAction;

	public EWTSearchField(String text, EWTCommand<String> ewtCommand, EWTBlock ewtBlock) {
		super();
		setFieldLabel(text);

		this.searchAction = ewtCommand;
		this.block = ewtBlock;

		addKeyListener(new KeyListener() {
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if(!block.isBusy()) {
					block.setBusy();
					searchAction.execute(getValue());
				}
			}
		});
	}
}
