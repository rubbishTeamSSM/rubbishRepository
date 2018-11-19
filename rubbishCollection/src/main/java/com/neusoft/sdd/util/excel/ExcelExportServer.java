package com.neusoft.sdd.util.excel;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class ExcelExportServer {
	
	private int total;
	private boolean isOneSheet;
	
	private String[] fields;
	private String[] mapfields;
	
	public ExcelExportServer(int total,boolean isOneSheet) {
		
		this.total = total;
		this.isOneSheet = isOneSheet;
		
		System.out.println("合计条数:" +this.total);
	}
	
	/**
	 * 导出的字段
	 * @param fields
	 */
	public void setfields(String[] fields){
		this.fields = fields;
		mapfields=fields;
	}
	
	private Field[] getClassField(Class target) throws SecurityException, NoSuchFieldException{
		
		Field[] targetFields = new Field[fields.length];
		
		for(int i=0;i<fields.length;i++){	
			targetFields[i] = target.getDeclaredField(fields[i]);
		}
		return targetFields;
	}





	public void createSheet(Workbook workbook, String sheetName,
			String[] columnsTitle, List<?> data,int start,int end) throws Exception {
		
		System.out.println("start:" +start+"　end:"+end);
		Sheet sheet = null;
		
		try{
			sheet = workbook.createSheet(sheetName);

		}catch(Exception e){
			sheet = workbook.createSheet();
		}
		
		//创建标题	
		Row titleRow = sheet.createRow(0);
		
		createSheetTitle(titleRow,columnsTitle);
		
		//创建内容	
		Row bodyRow = null;
		Field[] fields = null;
		for(int j =start;j<end;j++ ){
			//System.out.println("＝＝＝＝＝"+(j+1-start));

			 bodyRow = sheet.createRow(j+1-start);
			 
			 //设置行高
			/// bodyRow.setHeightInPoints(25f);
			
			Object tmp = data.get(j); 
			Class c = tmp.getClass();//////////////
			String cname=c.toString();
			//导出数据不存在固定model 数据格式：List<Map<string,object>>
			if ("class java.util.HashMap".equals(cname)) {
				HashMap<String,Object> datamap=(HashMap<String,Object>)data.get(j);
				Cell cell = null;
				for (int i = 0; i < mapfields.length; i++) {
					String dataString =checknull(datamap.get(mapfields[i])).toString();
					cell =  bodyRow.createCell(i);					
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(dataString);					
				}	
			}else {
				//导出数据存在model,数据格式：List<model>
				if(fields == null){
					fields = getClassField(c);
				}
				
				Cell cell = null;
				for(int k=0;k<fields.length;k++){
					cell =  bodyRow.createCell(k);
		
					Field field = fields[k];
					dealWithCell(cell,field,tmp);
				}
			}
		}
		
		
		if(end<total && isOneSheet == false){
			int nextStart = end + 1;
			int nextEnd = end+ ExcelExportUtil.MAX_NUM > total ? total:end+ ExcelExportUtil.MAX_NUM +1;
	
			createSheet(workbook,sheetName,columnsTitle,data,nextStart,nextEnd);
		}

	}
	
    public Object checknull(Object val){
    	if (val==null) {
			return "";
		}else {			
			return val;
		}
    }
	
	private void createSheetTitle(Row titleRow, String[] columnsTitle) {
		
		//设置标题行高
		
		//titleRow.setHeightInPoints(25f);
		
		Cell cell  = null;
		for(int i=0;i<columnsTitle.length;i++){
			cell = titleRow.createCell(i);
			cell.setCellValue(columnsTitle[i]);
		}
		
	}





	private void dealWithCell(Cell cell, Field field,Object tmp) throws Exception {
		
		field.setAccessible(true);
		String fileType = field.getType().getName();
		
		if(fileType.equals(String.class.getName())){
			try {
				String nullString =  (String) field.get(tmp);
				if(nullString==null || "".equals(nullString)){
					nullString = "暂无";
				}
				String strResult = nullString.toString();
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(strResult);
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		}
		
		else if(fileType.equals("int")){
			try {
				int intResult = field.getInt(tmp);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(intResult);
			} catch (IllegalArgumentException e) {
			
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		
		else if(fileType.equals("float")){
			try {
				float floatResult = field.getFloat(tmp);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(floatResult);
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		}
		
		
		else if(fileType.equals("double")){
			try {
				double doubleResult = field.getDouble(tmp);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(doubleResult);
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		}
		
		
		else if(fileType.equals(Date.class.getName())){
			try {
				Date dateResult = (Date) field.get(tmp);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(formatDate(dateResult));
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		}
		
		else if(fileType.equals("long")){
			try {
				long longResult = field.getLong(tmp);
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(longResult);
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		}
		
		else{
			throw new Exception("类型不支持");
		}

		
	}





	/**
	 * 格式化导出时间格式
	 * @param date
	 * @return
	 */
	private String formatDate(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return format.format(date);
	}
	


}