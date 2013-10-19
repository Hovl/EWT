package ebs.ewttest.client;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.EWT;
import ebs.ewt.client.ewt.EWTCommand;
import ebs.ewt.client.ewt.EWTWidget;
import ebs.ewt.client.widgets.EWTPage;
import ebs.ewt.client.widgets.form.EWTForm;
import ebs.ewt.client.widgets.form.EWTFormSubmitter;
import ebs.ewt.client.widgets.grid.EWTBaseDataRenderImpl;
import ebs.ewt.client.widgets.grid.EWTGridColumn;
import ebs.ewt.client.widgets.grid.EWTGridNavigation;
import ebs.ewt.client.widgets.grid.EWTPagedGrid;

import static ebs.ewt.client.EWT.ewtConstants;
import static ebs.ewttest.client.Ewttest.CENTERPANEL;
import static ebs.ewttest.client.TestDataTypeImpl.Level;
import static ebs.ewttest.client.TestDataTypeImpl.Owner;

/**
 * Created by Dubov Aleksey
 * Date: Oct 15, 2009
 * Time: 6:09:26 PM
 * Company: EBS (c) 2009
 */

public class PagedForm2Widget extends EWTPage {
	private static PagedForm2Widget instance = null;

	public static void getInstance(final EWTCommand<EWTWidget> command) {
		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onFailure(Throwable reason) {
				EWT.displayAlertWindow(
						ewtConstants.errorNullResultReceivedHeader(),
						ewtConstants.errorNullResultReceived());
			}

			@Override
			public void onSuccess() {
				if(instance == null) {
					instance = new PagedForm2Widget();
					instance.setRootPanel(CENTERPANEL);
				}

				command.execute(instance);
			}
		});
	}

	private boolean inited = false;

	private EWTPagedGrid<PersoneDTO> grid;
	private EWTForm<PersoneDTO> editForm;
	private EWTForm<PersoneDTO> findForm;

	@Override
	public String getName() {
		return "PagedForm2Widget";
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
		setHeading(getName());

		grid = new EWTPagedGrid<PersoneDTO>(getName(), 20,
				new EWTGridColumn[] {
						new EWTGridColumn("id", "ID", 0).setBooleans(true, true, false).setLabel(true),
						new EWTGridColumn("login", "login", 0).setBooleans(true, true, false),
						new EWTGridColumn("name", "name", 0).setBooleans(true, true, false),
						new EWTGridColumn(Owner, 0, Owner, true).setBooleans(true, true, false),
						new EWTGridColumn(Level, 0, Level, true).setBooleans(true, true, false),
						new EWTGridColumn("blocked", "blocked", 0, EWTBaseDataRenderImpl.BOOLEANWIDGET, true)
								.setBooleans(true, true, false).setBool(true)
				},
				new EWTGridNavigation<PersoneDTO>() {
					@Override
					public void load(int from, int to, int sortby, int order, AsyncCallback<PagingLoadResult<PersoneDTO>> pagingLoadResultAsyncCallback) {
						EWT.display("load call");
						pagingLoadResultAsyncCallback.onSuccess(new BasePagingLoadResult<PersoneDTO>(PersoneDTO.getPersones(from, to), from, 1005));
					}
				},
				new EWTFormSubmitter<PersoneDTO>() {
					@Override
					public void find(PersoneDTO personeDTO) {
						EWT.display(personeDTO.toString());
					}

					@Override
					public void cancel() {
						redraw();
					}

					@Override
					public void save(PersoneDTO personeDTO) {
						EWT.display(personeDTO.toString());
					}

					@Override
					public void add(PersoneDTO personeDTO) {
						EWT.display(personeDTO.toString());
					}
				}, this);
		grid.generateBar(false);

		editForm = grid.getEditForm();
		findForm = grid.getSearchForm();
		findForm.setObject(new PersoneDTO(), true);

		getTopPanel().add(findForm);
		getTopPanel().add(editForm);

		addPlusToolButton(new SelectionListener<IconButtonEvent>() {
			@Override
			public void componentSelected(IconButtonEvent ce) {
				findForm.hide();
				editForm.hideOrAdd(new PersoneDTO());
			}
		});

		addSearchToolButton(new SelectionListener<IconButtonEvent>() {
			@Override
			public void componentSelected(IconButtonEvent ce) {
				editForm.hide();
				findForm.switchVisibility();
			}
		});

		add(grid, new RowData(1, 1));
		
		layout();

		inited = true;
	}

	@Override
	public void reset() {
		EWT.display("reset call");

		editForm.resetForm();

		grid.load(true);
	}
}
