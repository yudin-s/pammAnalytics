package org.client.grid.columns;

import java.util.ArrayList;
import java.util.List;

public class ColumnsFactory {

	public static List<IColumn> createFromColumnNameList(List<String> columnNameList) {
		return createFromColumnNameList(columnNameList.toArray(new String[0]));
	}
	
//	public static List<IColumn> createFromResourceId(IResources resource, List<String> resourceIdList) {
//		return createFromResourceId(resource, resourceIdList.toArray(new String[0]));
//	}

	public static List<IColumn> createFromColumnNameList(String[] columnNameArray) {
		List<IColumn> columnsList = new ArrayList<IColumn>();
		for (String columnName: columnNameArray) {
			columnsList.add(Column.createFromColumnName(columnName));
		}
		return columnsList;
	}
//	public static List<IColumn> createFromResourceId(IResources resource, String[] resourceIdArray) {
//		List<IColumn> columnsList = new ArrayList<IColumn>();
//		for (String resourceId: resourceIdArray) {
//			columnsList.add(Column.createFromResourceId(resource, resourceId));
//		}
//		return columnsList;
//	}
}
