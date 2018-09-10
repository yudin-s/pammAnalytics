package org.client.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridEditor;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import org.client.grid.columns.CheckedColumn;
import org.client.grid.columns.ColumnSortOrder;
import org.client.grid.columns.IColumn;
import org.client.grid.editors.Editor;
import org.client.grid.listeners.EventData;
import org.client.grid.listeners.IGridListener;
import org.client.grid.nodes.Node;
import org.client.grid.nodes.NodeFactory;
import org.client.grid.nodes.NodeComparator;
import org.client.grid.nodes.NodeProperty;
import org.client.grid.properties.EditorProperty;
import org.client.grid.properties.Property;

/**
 * Wrapper class for Nebula Grid.
 * It
 *
 */
public class Grid {
/*
 * Implementing hints:
 * 1. Font and tooltip for row headers can't be specified.
 * 2. When the height is specified for each row separately then the first line height is displayed incorrectly. It's fixed only after table is sorted.
 * 3. Color can't be specified for column headers.
 * 4. grid.setAutoHeight(true) and grid.setAutoWidth(true) don't work when column.setWordWrap(false). Please use item.pack()
 * 5. column.pack() - specify column width, grid.recalculateHeader() - specify column height.
 * 6. column.setWordWrap(true) work correctly when column.pack() is absent;
 * 7. If the column at the given index is not checkable then individual cell will not be checkable regardless.
 */
	/**
	 * Place holder for fields with null values.
	 */
	public static final String EMPTY_FIELD = "-";
	
	/**
	 * Sort order for column which isn't sorted before.
	 */
	public static final ColumnSortOrder DEFAULT_COLUMN_SORT_ORDER = ColumnSortOrder.DOWN;
	
	/**
	 * Default style for created grid.
	 * @see SWT#BORDER
	 * @see SWT#V_SCROLL
	 * @see SWT#H_SCROLL
	 */
	public static final int DEFAULT_STYLE = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL;
	
	/**
	 * Style for grid which can have several selected rows.
	 * @see Grid#DEFAULT_STYLE
	 * @see SWT#MULTI
	 */
	public static final int MULTISELECT_STYLE = SWT.MULTI | DEFAULT_STYLE;

	/**
	 * Nebula grid which are used by this wrapper.
	 */
	private org.eclipse.nebula.widgets.grid.Grid grid;
	
	/**
	 * Factory which creates nodes for grid.
	 */
	private NodeFactory nodeFactory;
	
	/**
	 * List of grid columns.
	 */
	private List<GridColumn> columns;
	
	/**
	 * List of cell editors in grid. Those editors are permanent.
	 */
	private List<GridEditor> gridEditors;
	
	/**
	 * List of added to grid editors. Those editors are dispose when any column is sorting.
	 */
	private List<GridEditor> addedGridEditors;
	
	/**
	 * If true than height of row calculate automatically.
	 * If you are used setItemHeight() than used height from it.
	 * Default value is true.
	 */
	private boolean itemAutoHeight = true;
	
	/**
	 * If true than column width adjusts to a maximum length of a table row.
	 * If false use width from column properties.
	 * Default value is true.
	 */
	private boolean columnAutoWidth = true;
	
	/**
	 * Mouse listener. User can't directly set it.
	 * Used by setListener() to provide IGridListener.
	 */
	private MouseListener mouseListener;
	
	/**
	 * Selection listener. User can't directly set it.
	 * Used by setListener() to provide IGridListener.
	 */
	private SelectionListener selectionListener;
	
	/**
	 * List of grid nodes.
	 */
	private List<Node> nodes;

    /**
     * Constructs a new instance of this class given its parent, a style
     * value describing its behavior and appearance, factory which creates
     * nodes from objects that have to be displayed and list of columns.
     * 
     * @param parent a composite control which will be the parent of the new
     * instance (cannot be null)
     * @param style the style of control to construct
     * @param nodeFactory factory which creates nodes
     * @param columnsList list of columns. Can't be null
     * @throws IllegalArgumentException
     * <ul>
     * <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
     * </ul>
     * @throws org.eclipse.swt.SWTException
     * <ul>
     * <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that
     * created the parent</li>
     * </ul>
     * @see Grid#DEFAULT_STYLE
     * @see Grid#MULTISELECT_STYLE
     */
	public Grid(Composite parent, int style, NodeFactory nodeFactory, List<IColumn> columnsList) {
		grid = new org.eclipse.nebula.widgets.grid.Grid(parent, style);
		setDefaultGridProperties();
		createColumns(columnsList);
		setNodeFactory(nodeFactory);
	}

	public NodeFactory getNodeFactory() {
		return nodeFactory;
	}

	public void setNodeFactory(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
		this.nodeFactory.setGrid(this);
	}

	public void setLayoutData(Object layoutData) {
		grid.setLayoutData(layoutData);
	}

	public void layout() {
		grid.layout();
	}

	private void setDefaultGridProperties() {
		grid.setHeaderVisible(true);
	}

	private void createColumns(List<IColumn> columnsList) {
		if (columns != null) {
			for (GridColumn column: columns) {
				column.dispose();
			}
		}
		columns = new ArrayList<GridColumn>();
		for (IColumn column : columnsList) {
			final GridColumn currentGridColumn;
			if (column instanceof CheckedColumn) {
				currentGridColumn = new GridColumn(grid, column.getStyle().getSWTStyle() | SWT.CHECK);
				currentGridColumn.setCheckable(((CheckedColumn) column).isCheckable());
			} else {
				currentGridColumn = new GridColumn(grid, column.getStyle().getSWTStyle());
			}
			currentGridColumn.setText(column.getText());
			currentGridColumn.setImage(column.getImage());
			currentGridColumn.setHeaderTooltip(column.getHeaderTooltip());
			currentGridColumn.setHeaderFont(column.getHeaderFont());
			currentGridColumn.setWidth(column.getWidth());
			currentGridColumn.setMoveable(column.isMoveable());
			currentGridColumn.setWordWrap(column.isWordWrap());
			currentGridColumn.setResizeable(column.isResizeable());

			// Resize item heights
			// Function setAutoHeight(true) does't work
			if ((column.isWordWrap()) && (column.isResizeable())) {
				currentGridColumn.addControlListener(new ControlListener() {

					@Override
					public void controlMoved(ControlEvent e) {
					}

					@Override
					public void controlResized(ControlEvent e) {
						autoResizeItems();
					}

				});
			}
			currentGridColumn.setVisible(column.isVisible());
			if (column.isSortable()) {
				currentGridColumn.addSelectionListener(new SelectionListener() {
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					public void widgetSelected(SelectionEvent e) {
						clearSortLabels(currentGridColumn);
						removeAllAddedEditors();
						if (currentGridColumn.getSort() == SWT.UP) {
							currentGridColumn.setSort(SWT.DOWN);
						} else {
							currentGridColumn.setSort(SWT.UP);
						}
						sortGrid(new NodeComparator<Node>(currentGridColumn.getSort(), currentGridColumn.getText()));
					}
				});
			}
			currentGridColumn.setData(column);
			columns.add(currentGridColumn);
		}
		grid.setAutoWidth(columnAutoWidth);
		grid.recalculateHeader();
	}

	private void clearSortLabels() {
		clearSortLabels(null);
	}

	private void clearSortLabels(final GridColumn excludingColumn) {
		for (GridColumn gridColumn : columns) {
			if (gridColumn != excludingColumn) {
				gridColumn.setSort(SWT.NONE);
			}
		}
	}

	public void orderBy(String column) {
		orderBy(column, DEFAULT_COLUMN_SORT_ORDER);
	}

	public void orderBy(String column, ColumnSortOrder order) {
		for (GridColumn gridColumn : columns) {
			if (gridColumn.getText().equals(column)) {
				gridColumn.setSort(order.getSWTOrder());
				sortGrid(new NodeComparator<Node>(gridColumn.getSort(), gridColumn.getText()));
				break;
			}
		}
	}

	public void removeAll() {
		grid.removeAll();
		removeEditors();
		removeAllAddedEditors();
		grid.layout();
	}

	private void removeEditors() {
		if (gridEditors != null) {
			for (GridEditor gridEditor : gridEditors) {
				Control editor = gridEditor.getEditor();
				if (editor != null) {
					editor.dispose();
				}
				gridEditor.dispose();
			}
		}
		gridEditors = null;
	}

	public void refreshValues() {
		fillGrid();
	}

	private void fillGrid() {
		gridEditors = new ArrayList<GridEditor>();
		addedGridEditors = new ArrayList<GridEditor>();
		nodes = nodeFactory.getNodes();
		clearSortLabels();
		sortGrid(new NodeComparator<Node>());
	}

	private void sortGrid(NodeComparator<Node> nodeComparator) {
		grid.removeAll();
		for (GridEditor editor : gridEditors) {
			editor.dispose();
		}
		gridEditors.clear();
		Collections.sort(nodes, nodeComparator);
		for (Node node : nodes) {
			GridItem item = new GridItem(grid, SWT.NONE);
			node.setGridItem(item);
			fillItem(node);
		}
		autoResizeItems();
		autoResizeColumns();
	}

	private void fillItem(Node node) {
		GridItem item = node.getGridItem();
		// Set focus to last added item.
		// Exception is thrown after sorting grid and click left top corner to
		// select all items before this line was added
		grid.setFocusItem(item);

		Map<String, Property> values = node.getValues();
		setItemPropertiesFromNode(node);
		item.setData(node);
		NodeProperty nodeProperty = node.getNodeProperty();
		if (nodeProperty != null) {
			item.setHeaderText(nodeProperty.getText());
			item.setHeaderImage(nodeProperty.getImage());
			item.setHeaderForeground(nodeProperty.getTextColor());
			item.setHeaderBackground(nodeProperty.getBackgroundColor());
		}
		int i = 0;
		for (GridColumn column : columns) {
			Property property;
			if (values != null) {
				property = ((IColumn) column.getData()).processProperty(values.get(column.getText()));
			} else {
				property = new Property();
			}
			fillCell(node, item, i, (IColumn) column.getData(), property);
			i++;
		}
	}

	/**
	 * Recreate node using it's representedObject.
	 * Using when representedObject of node is changed.
	 * @param node changing node
	 * @throws Exception when node is null
	 */
	public void refreshNode(Node node) throws Exception {
		if (node != null) {
			changeNode(node, node.getRepresentedObject());
		} else {
			throw new Exception("Node is null");
		}
	}

	/**
	 * Recreate node using value.
	 * @param node changing node
	 * @param value new representedObject of node
	 * @throws Exception when node or value is null
	 */
	public void changeNode(Node node, Object value) throws Exception {
		if (node == null) {
			throw new Exception("Node is null");
		}
		if (value == null) {
			throw new Exception("Value is null");
		}
		nodeFactory.setNodeData(node, value);
		GridItem item = node.getGridItem();
		node.setGridItem(item);
		node.setRepresentedObject(value);
		fillItem(node);
		autoResizeItems();
		autoResizeColumns();
	}

	public void addNode(Object value) {
		if (value == null) {
			return;
		}
		Node node = nodeFactory.getNodeFromObject(value);
		node.setRepresentedObject(value);
		GridItem item = new GridItem(grid, SWT.NONE);
		node.setGridItem(item);
		fillItem(node);
		autoResizeItems();
		autoResizeColumns();
		nodes.add(node);
	}

	/**
	 * Remove node from grid.
	 * @param node
	 */
	public void removeNode(Node node) {
		if (node == null) {
			return;
		}
		node.getGridItem().dispose();
		nodes.remove(node);
		autoResizeItems();
		autoResizeColumns();
		grid.redraw();
	}

	private void fillCell(Node node, GridItem item, int i, IColumn column, Property property) {
		item.setData(Integer.toString(i), property.getValue());

		item.setImage(i, property.getImage());
		item.setText(i, property.getText());
		item.setToolTipText(i, getTooltip(node, column, property));
		item.setForeground(i, getForeground(node, column, property));
		item.setBackground(i, getBackground(node, column, property));
		item.setFont(i, getFont(node, column, property));

		if (column.getClass() == CheckedColumn.class) {
			Boolean isChecked = property.isChecked();
			if (isChecked == null) {
				isChecked = ((CheckedColumn)column).isChecked();
			}
			item.setChecked(i, isChecked);

			Boolean isGrayed = property.isGrayed();
			if (isGrayed == null) {
				isGrayed = ((CheckedColumn)column).isGrayed();
			}
			item.setGrayed(i, isGrayed);

			Boolean isCheckable = property.isCheckable();
			if (isCheckable == null) {
				isCheckable = ((CheckedColumn)column).isCheckable();
			}
			item.setCheckable(i, isCheckable);
		}

		if (property instanceof EditorProperty) {
			GridEditor gridEditor = new GridEditor(grid);
			gridEditor.horizontalAlignment = ((EditorProperty) property).getHorizontalAlignment();
			gridEditor.verticalAlignment = ((EditorProperty) property).getVerticalAlignment();
			gridEditor.grabHorizontal = ((EditorProperty) property).isGrabHorizontal();
			gridEditor.grabVertical = ((EditorProperty) property).isGrabVertical();
			gridEditor.minimumWidth = ((EditorProperty) property).getMinimumWidth();
			gridEditor.minimumHeight = ((EditorProperty) property).getMinimumHeight();
			Editor editor = ((EditorProperty) property).getEditor();
			editor.setGrid(this);
			editor.setNode(node);
			Control control = editor.getControl();
			control.setParent(grid);
			gridEditor.setEditor(control, item, i);
			gridEditors.add(gridEditor);
		}
	}

	/**
	 * If {@link Grid#columnAutoWidth} is true than resize columns by width.
	 * Code of this function is copied from GridColumn.pack() and modified.
	 * column.pack() can't be used because it doesn't use width of sort label and editors in grids.
	 */
	public void autoResizeColumns() {
		if (columnAutoWidth) {
			for (GridColumn column : columns) {
				if (((IColumn)column.getData()).isAutoResizeable()) {
					GC gc = new GC(grid);
					int newWidth = column.getHeaderRenderer().computeSize(gc, SWT.DEFAULT, SWT.DEFAULT, column).x;
					if (column.getSort() != SWT.NONE) {
						newWidth += 15;
					}
					for (Node node : nodes) {
						GridItem item = node.getGridItem();
						column.getCellRenderer().setColumn(grid.indexOf(column));
						newWidth = Math.max(newWidth, column.getCellRenderer().computeSize(gc, SWT.DEFAULT, SWT.DEFAULT, item).x);
						Property property = node.getValues().get(column.getText());
						if (property instanceof EditorProperty) {
							newWidth = Math.max(newWidth, ((EditorProperty) property).getEditor().getControl().getBounds().width);
						}
					}
					gc.dispose();
					column.setWidth(newWidth);
				}
			}
			grid.redraw();
		}
	}

	/**
	 * If {@link Grid#itemAutoHeight} is true than resize rows by height.
	 */
	public void autoResizeItems() {
		if (itemAutoHeight) {
			for (GridItem item : grid.getItems()) {
				item.pack();
			}
			grid.redraw();
		}
	}

	private Font getFont(Node node, IColumn column, Property property) {
		Font font;
		if (property.getFont() != null) {
			font = property.getFont();
		} else if (column.getFont() != null) {
			font = column.getFont();
		} else {
			font = node.getFont();
		}
		return font;
	}

	private Color getBackground(Node node, IColumn column, Property property) {
		Color background;
		if (property.getBackgroundColor() != null) {
			background = property.getBackgroundColor();
		} else if (column.getBackgroundColor() != null) {
			background = column.getBackgroundColor();
		} else {
			background = node.getBackgroundColor();
		}
		return background;
	}

	private Color getForeground(Node node, IColumn column, Property property) {
		Color foreground;
		if (property.getTextColor() != null) {
			foreground = property.getTextColor();
		} else if (column.getTextColor() != null) {
			foreground = column.getTextColor();
		} else {
			foreground = node.getTextColor();
		}
		return foreground;
	}

	private String getTooltip(Node node, IColumn column, Property property) {
		String tooltip;
		if (property.getToolTip() != null) {
			tooltip = property.getToolTip();
		} else if (column.getTooltip() != null) {
			tooltip = column.getTooltip();
		} else {
			tooltip = node.getTooltip();
		}
		return tooltip;
	}

	private void setItemPropertiesFromNode(Node node) {
		GridItem item = node.getGridItem();
		item.setForeground(node.getTextColor());
		item.setBackground(node.getBackgroundColor());
		item.setFont(node.getFont());
		if (node.getHeight() > 0) {
			item.setHeight(node.getHeight());
		}
	}

	/**
	 * Set data which can be represented by grid.
	 * @param data List of objects which have to represented by grid.
	 */
	@SuppressWarnings({ "rawtypes" })
	public void setData(List data) {
		if (data != null) {
			nodeFactory.setData(data);
			removeAll();
			fillGrid();
			grid.redraw();
		}
	}

	public void setHeaderVisible(boolean visible) {
		grid.setHeaderVisible(visible);
	}

	public void setRowHeaderVisible(boolean rowHeaderVisible) {
		grid.setRowHeaderVisible(rowHeaderVisible);
	}

	public void setRowsResizeable(boolean rowsResizeable) {
		grid.setRowsResizeable(rowsResizeable);
	}

    /**
     * Sets the order that the items in the receiver should be displayed in to
     * the given argument which is described in terms of the zero-relative
     * ordering of when the items were added.
     *
     * @param order the new order to display the items
     * @throws org.eclipse.swt.SWTException
     * <ul>
     * <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
     * <li>ERROR_THREAD_INVALID_ACCESS -if not called from the thread that
     * created the receiver</li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     * <li>ERROR_NULL_ARGUMENT - if the item order is null</li>
     * <li>ERROR_INVALID_ARGUMENT - if the order is not the same length as the
     * number of items, or if an item is listed twice, or if the order splits a
     * column group</li>
     * </ul>
     */
	public void setColumnOrder(int[] order) {
		grid.setColumnOrder(order);
	}

	public void setListener(final IGridListener gridListener) throws SWTException {
		removeListeners();
		mouseListener = new MouseListener() {
			private boolean doubleClick;

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				doubleClick = true;
				gridListener.doubleClick(getThisGrid(), getEventDataFromMouseEvent(e));
			}

			@Override
			public void mouseDown(final MouseEvent e) {
				doubleClick = false;
				if (e.button == 3) {
					gridListener.rightClick(getThisGrid(), getEventDataFromMouseEvent(e));
				} else {
					Display.getDefault().timerExec(Display.getDefault().getDoubleClickTime(), new Runnable() {
						public void run() {
							if (!doubleClick) {
								gridListener.leftClick(getThisGrid(), getEventDataFromMouseEvent(e));
							}
						}
					});
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		};
		selectionListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				gridListener.selection(getThisGrid(), getEventDataFromSelectionEvent(e));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		grid.addMouseListener(mouseListener);
		grid.addSelectionListener(selectionListener);
	}

	private EventData getEventDataFromMouseEvent(MouseEvent event) {
		Point point = new Point(event.x, event.y);
		boolean isHeaderClick = false;
		if (grid.getHeaderVisible()) {
			if (point.y <= grid.getHeaderHeight()) {
				isHeaderClick = true;
			}
		}
		GridColumn gridColumn = grid.getColumn(point);
		IColumn column = null;
		if (gridColumn != null) {
			column = (IColumn) (gridColumn.getData());
		}
		GridItem item = grid.getItem(point);
		Node node = null;
		if (item != null) {
			node = (Node) item.getData();
		}
		return new EventData(column, node, isHeaderClick);
	}

	private EventData getEventDataFromSelectionEvent(SelectionEvent event) {
		GridItem item = (GridItem) event.item;
		Node node = null;
		if (item != null) {
			node = (Node) item.getData();
		}
		return new EventData(null, node);
	}

	private Grid getThisGrid() {
		return this;
	}

	public void removeListeners() throws SWTException {
		if (mouseListener != null) {
			grid.removeMouseListener(mouseListener);
		}
		if (selectionListener != null) {
			grid.removeSelectionListener(selectionListener);
		}
	}

	public void setColumnsList(List<IColumn> columnsList) {
		removeAll();
		createColumns(columnsList);
	}

	public void setItemHeight(int height) {
		grid.setItemHeight(height);
		itemAutoHeight = false;
	}

	public void setItemAutoHeight(boolean itemAutoHeight) {
		this.itemAutoHeight = itemAutoHeight;
		grid.setAutoHeight(itemAutoHeight);
	}

	public void setColumnAutoWidth(boolean columnAutoWidth) {
		this.columnAutoWidth = columnAutoWidth;
		grid.setAutoWidth(columnAutoWidth);
		autoResizeColumns();
	}

	public List<IColumn> getColumns() {
		List<IColumn> columnsList = new ArrayList<IColumn>();
		for (GridColumn column : columns) {
			columnsList.add((IColumn) column.getData());
		}
		return columnsList;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Node> getSelectedNodes() {
		List<Node> selectedNodes = new ArrayList<Node>();
		for (GridItem item : grid.getSelection()) {
			selectedNodes.add((Node) item.getData());
		}
		return selectedNodes;
	}

	public Control getControl() {
		return this.grid;
	}

	public Composite getComposite() {
		return this.grid;
	}

	public void select(Node node) {
		GridItem[] items = grid.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[0] == node.getGridItem()) {
				grid.select(i);
				break;
			}
		}
	}

	public void deselect(Node node) {
		GridItem[] items = grid.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[0] == node.getGridItem()) {
				grid.deselect(i);
				break;
			}
		}
	}

	public void select(List<Node> nodes) {
		grid.select(getNodeNumbers(nodes));
	}

	public void deselect(List<Node> nodes) {
		grid.deselect(getNodeNumbers(nodes));
	}

	private int[] getNodeNumbers(List<Node> nodes) {
		GridItem[] gridItems = grid.getItems();
		Map<GridItem, Integer> gridItemsNumbers = new HashMap<GridItem, Integer>();
		for (int i = 0; i < gridItems.length; i++) {
			gridItemsNumbers.put(gridItems[i], i);
		}

		int[] selectedItemsNumbers = new int[nodes.size()];
		int i = 0;
		for (Node node : nodes) {
			selectedItemsNumbers[i] = gridItemsNumbers.get(node.getGridItem());
			i++;
		}
		return selectedItemsNumbers;
	}

	public void selectAll() {
		grid.selectAll();
	}

	public void deselectAll() {
		grid.deselectAll();
	}

	public void addEditor(Node node, IColumn column, Editor editor) {
		GridEditor gridEditor = new GridEditor(grid);
		gridEditor.grabHorizontal = true;
		editor.setGrid(this);
		editor.setNode(node);
		Control control = editor.getControl();
		control.setParent(grid);
		gridEditor.setEditor(control, node.getGridItem(), getColumnIndex(column));
		addedGridEditors.add(gridEditor);
	}
	
	public int getColumnIndex(IColumn column) {
		for (int i = 0; i < columns.size(); i++) {
			GridColumn gridColumn = columns.get(i);
			if (gridColumn.getText().equals(column.getText())) {
				return i;
			}
		}
		return -1;
	}

	public void removeAllAddedEditors() {
		if (addedGridEditors != null) {
			for (GridEditor editor : addedGridEditors) {
				editor.getEditor().dispose();
				editor.dispose();
			}
			addedGridEditors.clear();
		}
	}

	public void removeAddedEditor(Editor editor) {
		if (addedGridEditors != null) {
			for (GridEditor gridEditor : addedGridEditors) {
				if (gridEditor.getEditor() == editor.getControl()) {
					gridEditor.dispose();
					addedGridEditors.remove(gridEditor);
					break;
				}
			}
		}
	}
}
