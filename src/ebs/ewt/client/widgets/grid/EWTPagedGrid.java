package ebs.ewt.client.widgets.grid;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.LiveGridView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LiveToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ebs.ewt.client.ewt.EWTCallback;
import ebs.ewt.client.ewt.EWTModelData;
import ebs.ewt.client.widgets.EWTPage;
import ebs.ewt.client.widgets.form.EWTForm;
import ebs.ewt.client.widgets.form.EWTFormSubmitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dubov Aleksey
 * Date: Jul 4, 2009
 * Time: 7:16:13 PM
 * Company: EBS (c) 2009
 */

public class EWTPagedGrid<T extends EWTModelData & ModelData> extends ContentPanel {
	private EWTGridColumn[] columns;
	private EWTGridNavigation<T> nav;

	private PagingLoadConfig config;
	private ColumnModel cm;
	private PagingLoader<PagingLoadResult<T>> loader;

	private Grid<T> grid;

	private EWTFormSubmitter<T> submitter;
	private EWTPage page;

	public EWTPagedGrid(String id, int perpage, EWTGridColumn[] columns, EWTGridNavigation<T> navigation,
						EWTFormSubmitter<T> submitter, EWTPage page)
	{
		this(id, perpage, columns, navigation);
		this.submitter = submitter;
		this.page = page;
	}

	public EWTPagedGrid(String id, int perpage, EWTGridColumn[] columns, EWTGridNavigation<T> navigation) {
		super(new FitLayout());
		setHeaderVisible(false);
		setBorders(false);

		this.nav = navigation;

		setColumns(columns, false);

		this.config = new BasePagingLoadConfig(0, perpage);
		config.setSortField("1");
		config.setSortDir(Style.SortDir.ASC);
		config.setOffset(0);
		config.setLimit(perpage);

		loader = new BasePagingLoader<PagingLoadResult<T>>(
				new RpcProxy<PagingLoadResult<T>>() {
					protected void load(Object loadConfig, AsyncCallback<PagingLoadResult<T>> callback) {
						PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
						if(pagingLoadConfig.getSortField() == null) {
							pagingLoadConfig.setSortField(config.getSortField());
							pagingLoadConfig.setSortDir(config.getSortDir());
						}
						config = pagingLoadConfig;
						
						nav.load(config.getOffset(), config.getOffset() + config.getLimit(),
								Integer.parseInt(config.getSortField()), config.getSortDir().ordinal(), callback);
					}
				}
		);
		loader.setRemoteSort(true);

		grid = new Grid<T>(new ListStore<T>(loader), cm);
		grid.getView().setAutoFill(true);
		grid.getView().setForceFit(true);
		grid.setStripeRows(true);
		grid.disableTextSelection(false);
		grid.setStateId(id);
		grid.setLoadMask(true);

		add(grid);
	}

	public Grid<T> getGrid() {
		return grid;
	}

	public EWTPagedGrid<T> generateBar(boolean live) {
		if(live) {
			LiveGridView view = new LiveGridView();
			view.setCacheSize(config.getLimit() * 2);
			grid.setView(view);
		}

		ToolBar bar;
		if(!live) {
			bar = new PagingToolBar(config.getLimit());
			((PagingToolBar)bar).bind(loader);
		} else {
			bar = new ToolBar();
			bar.add(new FillToolItem());

			LiveToolItem item = new LiveToolItem();
			item.bindGrid(grid);
			bar.add(item);
		}
		setTopComponent(bar);
		return this;
	}

	public EWTPagedGrid<T> setSortDir(Style.SortDir dir) {
		config.setSortDir(dir);
		return this;
	}

	public void load(boolean renew) {
		if(renew) {
			config.setOffset(0);
			loader.setOffset(0);
		}
		loader.load(config);
	}

	public EWTCallback<Void> getRefreshCallback() {
		return new EWTCallback<Void>() {
			public void onSuccess(Void result) {
				load(false);
			}
		};
	}

	public String[] getHeaders() {
		String[] headers = new String[columns.length];
		for(int i = 0; i < columns.length; i++) {
			headers[i] = columns[i].getHeader();
		}
		return headers;
	}

	public void setColumns(EWTGridColumn[] columns, boolean reconfig) {
		this.columns = columns;

		List<ColumnConfig> listColumns = new ArrayList<ColumnConfig>(columns.length);
		listColumns.addAll(Arrays.asList(columns));
		cm = new ColumnModel(listColumns);

		if(reconfig) grid.reconfigure(new ListStore<T>(loader), cm);
	}

	public EWTForm<T> getEditForm() {
		return getForm(null, null, false);
	}

	public EWTForm<T> getEditForm(Field[] before, Field[] after) {
		return getForm(before, after, false);
	}

	public EWTForm<T> getSearchForm() {
		return getForm(null, null, true);
	}

	public EWTForm<T> getSearchForm(Field[] before, Field[] after) {
		return getForm(before, after, true);
	}

	private EWTForm<T> getForm(Field[] before, Field[] after, boolean search) {
		List<Field> fields = new ArrayList<Field>();

		if(before != null) {
			fields.addAll(Arrays.asList(before));
		}

		for(EWTGridColumn column : columns) {
			if(search) {
				if(column.isSearch()) {
					fields.add(column.getSearchField());
				}
			} else {
				if(column.isEdit()) {
					fields.add(column.getEditField());
				}
			}
		}

		if(after != null) {
			fields.addAll(Arrays.asList(after));
		}

		return search ?
				new EWTForm<T>(fields, submitter, page, true) :
				new EWTForm<T>(fields, submitter, page, false);
	}

	public EWTGridColumn getColumn(String id) {
		for(EWTGridColumn column : columns) {
			if(column.getId().equals(id)) return column;
		}
		return null;
	}
}
