	package net.timentraining.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.timentraining.core.mail.TableOperation;


public class Excel implements TableOperation {
	private String excelFilePath;
	@SuppressWarnings("unused")
	private String sheetname;
	private FileInputStream inputStream;
	private Workbook workbook;
	private Sheet sheet;
	@SuppressWarnings("unused")
	private Iterator<Row> iterator;
	private int header = 0;
	

	/***
	 * Construct your excel file. This constructor by default it will assume first row in your excel file is a header
	 * @param excelfile - excel file path
	 * @param sheetname - sheet name from the workbook
	 * @author rkarim
	 */
	public Excel(String excelfile, String sheetname) {
		this(excelfile, sheetname, true);
	}
	/***
	 * Construct your excel file. Using this constructor you have an option to declare whether 1st row is header or not. 
	 * @param excelfile - excel file path
	 * @param sheetname - sheet name from the workbook
	 * @param header - true if 1st row is header, false if first row is not a header
	 * @author rkarim
	 */
	public Excel(String excelfile, String sheetname, boolean header) {
		try {

			this.excelFilePath = excelfile;
			this.sheetname = sheetname;
			this.inputStream = new FileInputStream(new File(excelFilePath));
//			this.workbook = new HSSFWorkbook(inputStream);
			if(excelfile.endsWith(".xls")){
				this.workbook = new HSSFWorkbook(inputStream);
			}else if(excelfile.endsWith(".xlsx")){
				this.workbook =new XSSFWorkbook(inputStream);
			}
			this.sheet = workbook.getSheet(sheetname); // specify sheet to
			this.iterator = this.sheet.iterator();
			if (header==true && this.header==0) {
				this.header++;
			}
		} catch (Exception e) {			
			e.printStackTrace();
			System.err.println("There is an exception. Possible reasons: [invalid sheet name]. Please contact AutoBots");
		}

	}


@Override
public String getCellValue(int row, int col) {
	
	return getCellValueAsString(row, col);
	/*
	int type;
	String send = " ";
	Cell cell=sheet.getRow(row + header).getCell(col);
	type = cell.getCellType();
	switch (type) {
		case Cell.CELL_TYPE_STRING: send = cell.getStringCellValue();
				break;
		case Cell.CELL_TYPE_NUMERIC: send = "" + cell.getNumericCellValue();
				break;
		case Cell.CELL_TYPE_BOOLEAN: send = "" + cell.getBooleanCellValue();
				break;
			 
	}
	closeWorkbook();
	return send;
	*/
}









	private String getCellValueAsString(Cell cell) {
		String s;
		DataFormatter format = new DataFormatter();
		s= format.formatCellValue(cell);
		closeWorkbook();
		return s;
	}
	
//	public String[] getEachRowValuesForAcolumn(int col){
//		String[] s = new String[rowCount()]; 
//		for (int i = 0; i <= rowCount(); i++) {
//			s[i]=this.getCellValueAsString(i,col);
//		}
//		return s;
//	}
	
	
	@SuppressWarnings("unused")
	private Cell getCell(int row, int col){
		return getRow(row).getCell(getCol(col));
	}
	
	private Row getRow(int row) throws IllegalArgumentException{
		if(row!=0){
			row=row-1;
		}else{
			throw new IllegalArgumentException("Row should be starts with 1. Found 0");
		}
	return this.sheet.getRow(row+header);	
		
	}
	
	private int getCol(int col){
		if (col!=0){
			col=col-1;
		}else{
			throw new IllegalArgumentException("Column should be starts with 1. Found 0");
		}
		return col;
	}

	
	@Override
	public List<String> getAllRowValuesByColumn(int col) {
		List<String> res = new ArrayList<>();
		for(int i=1;i<=rowCount();i++){
			res.add(getCellValueAsString(i, col));
		}
		return res;
	}
	
	

	public String getCellValueAsString(int row, int col) {
		Cell cell = getRow(row).getCell(getCol(col));
		return getCellValueAsString(cell);
	}
	
	public String getCellValueAsString(int row, String column){
		return getCellValueAsString(row, getColumnIndex(column));
	}
	
    public List<Map<String, String>> getColumnAndRowsAsMap() {
        List<Map<String,String>> ret = new ArrayList<>();
        Map<String,String> colrows;
        
        for(int row=1;row<=rowCount();row++){
               colrows = new HashMap<>();
               for(int col=0;col<getColHeaders().size();col++){
                      
                      
                      colrows.put(getColHeaders().get(col), getCellValueAsString(row, getColHeaders().get(col)));
                      
                      
               }
               ret.add(colrows);
        }
        
        
        return ret;

 }

	
	@Override
	public boolean checkValueIsInCell(int row, int col, String dataToFind) {
		return getCellValueAsString(row, col).equals(dataToFind);
	}
	
	@Override
	public boolean checkValueContainsInCell(int row, int col, String dataToFind) {
		return getCellValueAsString(row, col).contains(dataToFind);
	}
	
	
	@Override
	public List<String> getAllColumnValueByRow(int row) {
		List<String> valuesI_in_rows=new ArrayList<>();
		List<String> headers=getColHeaders();
		for(String s:headers){
			valuesI_in_rows.add(getCellValueAsString(row, s));
		}
		
		return valuesI_in_rows;	
	}
	

	public String getValue(int rowNum, String colHeader){
		return getCellValueAsString(rowNum, colHeader);
	}
	/***
	 * This method will return the row number for given data in a column.
	 * Note that by default it will return the first row if duplicate data found in same column. 
	 * Similar method available ({@link Excel#getAllRowByData(String, String)}) which retrieve all the rows in array for all data occurrence in given column.
	 * @param colHeader
	 * @param dataToFind
	 * @return
	 */
	@Override
	public int getRowByData(String colHeader, String dataToFind){
		return getAllRowByData(colHeader, dataToFind)[0];
	}
	@Override
	public Integer[] getAllRowByData(String colHeader, String dataToFind){
		int colIndex=getColumnIndex(colHeader);
		List<Integer> res= new ArrayList<>();
		String val;
		for (int i = 1; i <= rowCount(); i++) {
			val=getCellValueAsString(i, colIndex);
			if(val.equalsIgnoreCase(dataToFind)){
				res.add(i);
			}
		}
		if(res.isEmpty())
			try {
				throw new Exception("Given data: ["+ dataToFind + "] in column ["+ colHeader + "] is not exist.");
			} catch (Exception e) {
					e.printStackTrace();
			}
		return res.toArray(new Integer[0]);
	}
	
/***
 * 
 * @param columnName - User defined column name in excel.
 * @return - index of column, returns null if no matching column name found.
 */
	public Integer getColumnIndex(String columnName) {
		Row row = this.sheet.getRow(0);
		Iterator<Cell> cell = row.cellIterator();
		int counter = 0;
		boolean found = false;
		while (cell.hasNext()) {
			Cell c = cell.next();
			if (getCellValueAsString(c).equalsIgnoreCase(columnName)) {
				found = true;
				break;
			}
			counter++;

		}
		closeWorkbook();
		if (found) {
			return counter+1;
		} else {
			return null;
		}
	}
	@Override
	public String getColumnNameByIndex(int col) {
		if(col<0){
			col=col-1;
		}else{
			throw new IllegalArgumentException("Column should be starts with 1. Found 0");
		}
		return getColHeaders().get(col)+1;
	}
	
	

	/***
	 * 
	 * @param columnName - User defined column name in excel.
	 * @return - All column names in List
	 */
@Override
public List<String> getColHeaders() {
	List<String> cols = new ArrayList<>();
	Row row = this.sheet.getRow(0);
	Iterator<Cell> cell = row.cellIterator();
	while (cell.hasNext()) {
		Cell c = cell.next();
		cols.add(getCellValueAsString(c));
	}
	closeWorkbook();
	return cols;
}



		
		@Override
	public int columnCount(){
		int res = 0;
		Iterator<Cell>cells=sheet.getRow(0).cellIterator();
		while (cells.hasNext()) {
			cells.next();
			res++;

		}
		closeWorkbook();
		return res;
	}
		

@Override
	public int rowCount() {	
		int res = 0;
		res=this.sheet.getLastRowNum();
		closeWorkbook();
		return res;
	}

	private void closeWorkbook() {
		try {
			this.inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	

	
	
}
