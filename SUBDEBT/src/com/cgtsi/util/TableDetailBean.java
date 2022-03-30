/** Desc      : colom definition
 *  updated by: DKR
 */
package com.cgtsi.util;

public class TableDetailBean {
	String ColumnName="";
	String ColumnDataType="";
	int ColumnLength=0;
	int max_table_id=0;
	String FirstColumnName="";
   
	int columnPrecision =0;
	int columnScale =0;
	String DisplayColumnName="";
	
	
	
	
	public int getColumnPrecision() {
		return columnPrecision;
	}
	public void setColumnPrecision(int columnPrecision) {
		this.columnPrecision = columnPrecision;
	}
	public int getColumnScale() {
		return columnScale;
	}
	public void setColumnScale(int columnScale) {
		this.columnScale = columnScale;
	}
	public String getDisplayColumnName() {
		return DisplayColumnName;
	}
	public void setDisplayColumnName(String displayColumnName) {
		DisplayColumnName = displayColumnName;
	}
	public String getFirstColumnName() {
		return FirstColumnName;
	}
	public void setFirstColumnName(String firstColumnName) {
		FirstColumnName = firstColumnName;
	}
	public int getMax_table_id() {
		return max_table_id;
	}
	public void setMax_table_id(int max_table_id) {
		this.max_table_id = max_table_id;
	}
	String ColumnAllowNullFlag="";
	
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}
	public String getColumnDataType() {
		return ColumnDataType;
	}
	public void setColumnDataType(String columnDataType) {
		ColumnDataType = columnDataType;
	}
	 
	public String getColumnAllowNullFlag() {
		return ColumnAllowNullFlag;
	}
	public void setColumnAllowNullFlag(String columnAllowNullFlag) {
		ColumnAllowNullFlag = columnAllowNullFlag;
	}
	public int getColumnLength() {
		return ColumnLength;
	}
	public void setColumnLength(int columnLength) {
		ColumnLength = columnLength;
	}
	@Override
	public String toString() {
		return "TableDetailBean [ColumnName=" + ColumnName
				+ ", ColumnDataType=" + ColumnDataType + ", ColumnLength="
				+ ColumnLength + ", max_table_id=" + max_table_id
				+ ", FirstColumnName=" + FirstColumnName + ", columnPrecision="
				+ columnPrecision + ", columnScale=" + columnScale
				+ ", DisplayColumnName=" + DisplayColumnName
				+ ", ColumnAllowNullFlag=" + ColumnAllowNullFlag + "]";
	}
	
}
