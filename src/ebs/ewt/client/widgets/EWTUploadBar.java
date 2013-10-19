package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;

import static ebs.ewt.client.EWT.ewtConstants;

/**
 * Created by Dubov Aleksey
 * Date: Oct 23, 2009
 * Time: 1:50:30 PM
 * Company: EBS (c) 2009
 */

public class EWTUploadBar extends Window {
	public interface EWTUploadSubmitter {
		public Widget onAdd(FileUpload upload);

		public boolean canSubmit();

		public void onSubmitComplete();

		public void onSubmitError();

		public void onCancel();
	}

	private EWTUploadSubmitter submitter;

	private FormPanel formPanel;
	private String uid;
	private String uploadURL;
	private String progressURL;
	private int filesPerColumn;
	private int currentRow = 0;
	private int currentColumn = 0;

	private Grid uploadGrid;
	private VerticalPanel buttonPanel;

	private HTML waitHtml;
	private EWTCustomHTML addHtml = null;
	private HTML infoHtml;

	private ProgressBar progressBar;
	private Timer progressAskTimer;
	private RequestBuilder progressAskBuilder;

	public EWTUploadBar(String header, String progressURL, String uploadURL, boolean canAdd, boolean canCancel,
						int filesPerColumn, EWTUploadSubmitter uploadSubmitter) {
		super();
		setAutoHeight(true);
		setAutoWidth(true);
		setPlain(true);
		setModal(true);
		setHeading(header);
		setClosable(false);
		setLayout(new FillLayout());

		VerticalPanel rootPanel = new VerticalPanel();
		rootPanel.setSpacing(2);
		rootPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		this.uploadURL = uploadURL;
		this.progressURL = progressURL;
		this.filesPerColumn = filesPerColumn;
		this.submitter = uploadSubmitter;

		uploadGrid = new Grid();
		uploadGrid.setCellSpacing(4);

		formPanel = new FormPanel();
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setWidget(uploadGrid);
		formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
				setWait(false);
				progressAskTimer.cancel();
				if (event.getResults() != null && event.getResults().contains("ewtOk")) {
					submitter.onSubmitComplete();
					reset(uid);
				} else {
					submitter.onSubmitError();
				}
			}
		});

		waitHtml = new HTML();
		waitHtml.setStyleName("ewt-stat-loading");
		waitHtml.setVisible(false);

		buttonPanel = new VerticalPanel();
		buttonPanel.setSpacing(5);
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		buttonPanel.add(new Button(ewtConstants.upload(), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				submit();
			}
		}));

		if (canCancel) {
			buttonPanel.add(new Button(ewtConstants.cancel(), new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					submitter.onCancel();
				}
			}));
		}

		progressBar = new ProgressBar();
		progressBar.setWidth(250);
		progressBar.setVisible(false);

		infoHtml = new HTML();
		infoHtml.setVisible(false);

		HorizontalPanel panel = new HorizontalPanel();
		panel.add(formPanel);
		panel.add(waitHtml);
		panel.add(buttonPanel);
		panel.add(progressBar);

		if (canAdd) {
			addHtml = EWTBaseButton.getLink(ewtConstants.uploadOneMore(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					addFileUpload();
				}
			});

			rootPanel.add(addHtml);
		}
		rootPanel.add(panel);
		rootPanel.add(infoHtml);

		add(rootPanel);
	}

	public void setWait(boolean wait) {
		if (addHtml != null) {
			addHtml.setVisible(!wait);
		}
		setClosable(!wait);
		buttonPanel.setVisible(!wait);
		uploadGrid.setVisible(!wait);
		progressBar.setVisible(wait);
		waitHtml.setVisible(wait);
		infoHtml.setVisible(wait);
	}

	public void reset(String uid) {
		this.uid = uid;

		setWait(false);
		uploadGrid.clear();
		formPanel.setAction(uploadURL + uid);
		addFileUpload();
	}

	public void addFileUpload() {
		FileUpload upload = new FileUpload();
		upload.setName("upload");
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(2);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Widget widget = submitter.onAdd(upload);
		if (widget != null) {
			panel.add(widget);
		}
		panel.add(upload);

		if (currentRow == filesPerColumn) {
			currentRow = 0;
			currentColumn += 1;
		}

		if (currentColumn > 2) return;

		uploadGrid.resize(currentColumn > 0 ? filesPerColumn : currentRow + 1, currentColumn + 1);
		uploadGrid.setWidget(currentRow, currentColumn, panel);
		currentRow += 1;
	}

	public void submit() {
		if (submitter.canSubmit()) {
			setWait(true);
			formPanel.submit();

			progressAskBuilder = new RequestBuilder(RequestBuilder.POST, progressURL + uid);

			progressAskTimer = new Timer() {
				private Integer fileRead = 0;
				private NumberFormat format = NumberFormat.getDecimalFormat();

				@Override
				public void run() {
					try {
						progressAskBuilder.sendRequest("", new RequestCallback() {
							@Override
							public void onResponseReceived(Request request, Response response) {
								String[] result = response.getText().split("/");
								if (result.length > 0) {
									Integer fileSize = Integer.parseInt(result[0]);
									Integer nFileRead = Integer.parseInt(result[1]);

									progressBar.updateProgress(((float) nFileRead / (float) fileSize), null);

									infoHtml.setHTML(format.format((nFileRead - fileRead) / 1024f) + "kB/s");

									fileRead = nFileRead;
								}
							}

							@Override
							public void onError(Request request, Throwable exception) {
							}
						});
					} catch (RequestException e) {
						//ooops :)
					}
				}
			};
			progressAskTimer.scheduleRepeating(1000);
		}
	}
}
