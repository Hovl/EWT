package ebs.ewt.client.widgets;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by Dubov Aleksei
 * Date: Jun 30, 2007
 * Time: 8:04:05 PM
 * Company: EBS (c) 2007
 */
public class EWTCustomHTML<T> extends HTML {
	private String name;
	private String normstyle;
	private T data;

	public EWTCustomHTML(String text, String normstyle) {
		super(text);
		this.normstyle = normstyle;
		this.setStyleName(normstyle);
	}

	public EWTCustomHTML(String text, String normstyle, ClickHandler handler) {
		this(text, normstyle);
		this.addClickHandler(handler);
	}

	public EWTCustomHTML(String text, String normstyle, ClickHandler handler, T data) {
		this(text, normstyle, handler);
		this.data = data;
	}

	public EWTCustomHTML(String text, String normstyle, String onmousestyle, ClickHandler handler, T data) {
		this(text, normstyle, onmousestyle, handler);
		this.data = data;
	}

	public EWTCustomHTML(String text, final String normstyle, final String onmousestyle) {
		this(text, normstyle);

		addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Widget) event.getSource()).setStyleName(onmousestyle);
			}
		});
		addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Widget) event.getSource()).setStyleName(normstyle);
			}
		});
	}

	public EWTCustomHTML(String name, String text, String normstyle, String onmousestyle) {
		this(text, normstyle, onmousestyle);
		this.name = name;
	}

	public EWTCustomHTML(String text, String normstyle, String onmousestyle, ClickHandler handler) {
		this(text, normstyle, onmousestyle);
		this.addClickHandler(handler);
	}

	public EWTCustomHTML(String name, String text, String normstyle, String onmousestyle, ClickHandler handler) {
		this(name, text, normstyle, onmousestyle);
		this.addClickHandler(handler);
	}

	public EWTCustomHTML(String name, String text, String normstyle, String onmousestyle, ClickHandler handler,
						 T data)
	{
		this(name, text, normstyle, onmousestyle, handler);
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public String getNormstyle() {
		return normstyle;
	}

	public T getObject() {
		return data;
	}
}