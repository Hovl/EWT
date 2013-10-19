package ebs.ewttest.client;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Widget;
import ebs.ewt.client.EWT;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTWidget;
import ebs.ewt.client.widgets.EWTPage;
import ebs.ewt.client.widgets.EWTUploadBar;

/**
 * Created by Aleksey Dubov.
 * Date: 2011-04-24
 * Time: 12:33
 * Copyright (c) 2011
 */
public class UploadPage extends EWTPage {
	private boolean inited = false;
	private EWTUploadBar uploadBar;

	@Override
	public String getName() {
		return "UploadPage";
	}

	@Override
	public boolean isInited() {
		return inited;
	}

	@Override
	public void inited(EWTCommand<EWTWidget> cmd) {
		cmd.execute();
	}

	@Override
	public void init() {
		uploadBar = new EWTUploadBar("Upload test", "/no_progress?", "/no_upload", true, false, 5,
				new EWTUploadBar.EWTUploadSubmitter() {
			@Override
			public Widget onAdd(FileUpload upload) {
				return new TextField<String>();
			}

			@Override
			public boolean canSubmit() {
				return false;
			}

			@Override
			public void onSubmitComplete() {
				EWT.display("submit complete");
			}

			@Override
			public void onSubmitError() {
				EWT.display("submit error");
			}

			@Override
			public void onCancel() {
				EWT.display("submit canceled");
			}
		});

		uploadBar.show();
		layout();

		inited = true;
	}

	@Override
	public void reset() {
		uploadBar.reset("uid");
	}
}
