package net.timentraining.core.mail;

import java.util.List;

public interface TableOperation {
	 int columnCount();
	 int rowCount();
	 Integer[] getAllRowByData(String colHeader, String dataToFind);
	 int getRowByData(String colHeader, String dataToFind);
	 String getCellValue(int row, int col);
	 String getCellValueAsString(int row, int col);
	 String getCellValueAsString(int row, String colHeader);
	 Integer getColumnIndex(String colHeader);
	 String getColumnNameByIndex(int col);
	 List<String> getAllColumnValueByRow(int row);
	 List<String> getAllRowValuesByColumn(int col);
	 List<String> getColHeaders();
	 boolean checkValueIsInCell(int row, int col,String dataToFind);
	 boolean checkValueContainsInCell(int row, int col,String dataToFind);

}
