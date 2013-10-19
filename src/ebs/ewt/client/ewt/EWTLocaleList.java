package ebs.ewt.client.ewt;

import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import ebs.ewt.client.widgets.EWTIconButton;

/**
 * Created by Dubov Aleksey
 * Date: Jul 24, 2009
 * Time: 11:12:54 PM
 * Company: EBS (c) 2009-2010
 */

public class EWTLocaleList extends HorizontalPanel {
	private EWTLocaleChanger changer;

	private EWTLocale last;

	public EWTLocaleList(EWTLocaleChanger c, EWTLocale... locales) {
		this.changer = c;

		EWTIconButton.EWTIconButtonListener<EWTLocale> l = new EWTIconButton.EWTIconButtonListener<EWTLocale>() {
			public void onEvent(EWTLocale locale) {
				if(last != locale) {
					locale.setCurrentLocale();
					changer.change(locale);
				}
			}
		};

		String currentLocaleID = EWTLocale.getLocaleCookie();
		for(EWTLocale locale : locales) {
			if(locale != null) {
				add(new EWTIconButton<EWTLocale>(locale.getId(), locale.getName(), locale, l));
				if(locale.getId().equals(currentLocaleID)) {
					last = locale;
				}
			}
		}
	}
}
