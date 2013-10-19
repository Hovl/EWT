package ebs.ewt.client;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.EntryPoint;
import ebs.ewt.client.localization.EWTConstants;

/**
 * Created by Dubov Aleksei
 * Date: Apr 10, 2008
 * Time: 3:28:39 PM
 * Company: EBS (c) 2008
 */

public class EWT implements EntryPoint {
	public static EWTConstants ewtConstants;
	public static boolean alertDisplayed = false;

	public static void initConstants(EWTConstants constants) {
		ewtConstants = constants;
	}

	public void onModuleLoad() { }

	public static void display(String text) {
		Info.display("DEBUG", text);
	}

	public static void displayAlertWindow(String header, String text) {
		if(!alertDisplayed) {
			alertDisplayed = true;
			MessageBox.alert(header, text, new Listener<MessageBoxEvent>() {
				@Override
				public void handleEvent(MessageBoxEvent be) {
					alertDisplayed = false;
				}
			});
		}
	}
}
