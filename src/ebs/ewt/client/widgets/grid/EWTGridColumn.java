package ebs.ewt.client.widgets.grid;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import ebs.ewt.client.ewt.EWTDataType;
import ebs.ewt.client.widgets.form.EWTBaseField;
import ebs.ewt.client.widgets.form.EWTEditableListField;
import ebs.ewt.client.widgets.form.EWTListField;

/**
 * Created by Dubov Aleksey
 * Date: Jul 4, 2009
 * Time: 7:18:29 PM
 * Company: EBS (c) 2009-2011
 */

public class EWTGridColumn extends ColumnConfig {
	private EWTDataRender render;
	private boolean widget;

	private EWTDataType type = null;

	private boolean isEdit = false;
	private boolean isSearch = false;
	private boolean allowBlank = false;
	private boolean isBool = false;
	private boolean isLabel = false;

	private String regex = null;

	private Field editField;
	private Field searchField;

	public EWTGridColumn(String id, String name, int size) {
		super(id, name, size);
		setToolTip(name);
		setMenuDisabled(true);
		setSortable(false);
		setStyle("vertical-align:middle;");
		if(size > 0) {
			setFixed(true);
		} else {
			setWidth(150);
		}
		setResizable(false);
	}

	public EWTGridColumn(String id, String name) {
		this(id, name, 0);
	}

	public EWTGridColumn(String id, String name, int size, EWTDataRender dataRender, boolean isWidget) {
		this(id, name, size);

		setRenderer(new GridCellRenderer() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex,
								 ListStore listStore, Grid grid)
			{
				Object o = property.length() == 0 ? model : model.get(property);
				if(widget) {
					return render.renderWidget(o);
				} else {
					return render.renderData(o);
				}
			}
		});

		this.render = dataRender;
		this.widget = isWidget;
	}

	public EWTGridColumn(EWTDataType type, int size, EWTDataRender dataRender, boolean isWidget) {
		this(type.getID(), type.getName(), size, dataRender, isWidget);
		this.type = type;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public boolean isSearch() {
		return isSearch;
	}

	public EWTGridColumn setBooleans(boolean isEdit, boolean isSearch, boolean canBeFree) {
		this.isEdit = isEdit;
		this.isSearch = isSearch;
		this.allowBlank = canBeFree;
		return this;
	}

	public boolean isBool() {
		return isBool;
	}

	public EWTGridColumn setBool(boolean bool) {
		isBool = bool;
		editField = EWTBaseField.getBooleanField(getId(), getHeader());
		searchField = EWTBaseField.getBooleanField(getId(), getHeader());
		return this;
	}

	public boolean isLabel() {
		return isLabel;
	}

	public EWTGridColumn setLabel(boolean label) {
		isLabel = label;
		searchField = EWTBaseField.getLabelField(getId(), getHeader());
		editField = EWTBaseField.getLabelField(getId(), getHeader());
		return this;
	}

	public EWTGridColumn setRegex(String regex) {
		this.regex = regex;
		return this;
	}

	public EWTGridColumn setEditableField(boolean editable, EWTEditableListField.EWTFormProvider provider) {
		if(editable && type != null) {
			editField = EWTBaseField.getField(type.getID(), type.getName(),
					new EWTEditableListField(type, allowBlank, provider));
			searchField = generateField(true);
		} else {
			editField = null;
		}
		return this;
	}

	public EWTGridColumn setEditField(Field editField) {
		this.editField = editField;
		return this;
	}

	public EWTGridColumn setSearchField(Field searchField) {
		this.searchField = searchField;
		return this;
	}

	public Field getEditField() {
		return getField(false);
	}

	public Field getSearchField() {
		return getField(true);
	}

	private Field getField(boolean search) {
		if(!search && editField != null) {
			return editField;
		} else if(search && searchField != null) {
			return searchField;
		}

		return generateField(search);
	}

	private Field generateField(boolean search) {
		if(type != null) {
			return EWTBaseField.getField(type.getID(), type.getName(),
					search ?
							new EWTListField(type.getSearchName(), type, true) :
							new EWTListField(type, allowBlank));
		} else {
			return EWTBaseField.getStringField(getId(), getHeader(), search || allowBlank, regex);
		}
	}
}
