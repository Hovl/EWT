package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import ebs.ewt.client.ewt.EWTCommand;

/**
 * Created by Dubov Aleksei
 * Date: Sep 29, 2008
 * Time: 4:10:42 PM
 * Company: EBS (c) 2008
 */

public class EWTMessageBox {
	public static MessageBox confirm(String confirm, String body, final EWTCommand command) {
		return MessageBox.confirm(confirm, body,
				new Listener<MessageBoxEvent>() {
					public void handleEvent(MessageBoxEvent event) {
						Button button = event.getButtonClicked();
						if(button.getText().equalsIgnoreCase("yes") || button.getText().equalsIgnoreCase("да")) {
							command.execute(command.getObj());
						}
					}
				});
	}
}
