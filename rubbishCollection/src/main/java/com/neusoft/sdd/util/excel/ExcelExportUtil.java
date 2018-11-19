package com.neusoft.sdd.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelExportUtil {
	
	public static final int MAX_NUM = 65535;

	public static Workbook  exportExcel(String sheetName,String[] columnsTitle,List<?> data) throws Exception{
		Workbook  workbook = new HSSFWorkbook();
		int listSize = data.size();
		if( listSize> MAX_NUM){
			new ExcelExportServer(listSize,false).createSheet(workbook,sheetName,columnsTitle,data,0,MAX_NUM);
		}else{
			new ExcelExportServer(listSize,false).createSheet(workbook,sheetName,columnsTitle,data,0,listSize);
		}
		
		return workbook;
	}

	public static String exportExcelPath(String sheetName,String[] columnsTitle,List<?> data) throws Exception{
		
		//计算导出excel数量
		int listSize = data.size();
		int excelNum = listSize % MAX_NUM == 0 ? listSize / MAX_NUM : listSize / MAX_NUM + 1;
		
		File[] files = new File[excelNum];
		
		for(int i=0;i<excelNum;i++){
			try{
				files[i] = new File(System.getProperty("java.io.tmpdir"),UUID.randomUUID().toString() +".xls");
				Workbook  workbook = new HSSFWorkbook();
				
				int start = i*MAX_NUM ;
				int end = (start + MAX_NUM) > listSize ? listSize:(start + MAX_NUM) ;
				
				System.out.println("=====>start" + start + "end==>" + end);
				
				new ExcelExportServer(listSize,true).createSheet(workbook,sheetName,columnsTitle,data,start,end);
				
				FileOutputStream out = new FileOutputStream(files[i]);
				workbook.write(out);
				out.close();
			}catch(Exception e){
				deleteFiles(files);
				throw new Exception(e);
				
			}
			
		}
		File zipFile = new File(System.getProperty("java.io.tmpdir"),UUID.randomUUID().toString() + "test.zip");
		FileOutputStream out = new FileOutputStream(zipFile); 
		ZipOutputStream zipOutputStream = new ZipOutputStream(out);
		
		ZipEntry zipEntry = null;
		byte[] buffer = new byte[1024];
		int readtotal;
		
		for(int j=0;j<files.length;j++){
			zipEntry = new ZipEntry(files[j].getName());
			
			
			zipOutputStream.putNextEntry(zipEntry);
			
			FileInputStream in = new FileInputStream(files[j]);
			while((readtotal = in.read(buffer)) > 0){
				zipOutputStream.write(buffer, 0, readtotal);
			}
			in.close();
		}
		zipOutputStream.close();
		out.close();
		deleteFiles(files);
		return zipFile.toString();
	}
	
	public static String exportExcelPath(String sheetName,String[] columnsTitle,List<?> data, String[] fields) throws Exception{
		
		//计算导出excel数量
		int listSize = data.size();
		int excelNum = listSize % MAX_NUM == 0 ? listSize / MAX_NUM : listSize / MAX_NUM + 1;
		
		File[] files = new File[excelNum];
		
		for(int i=0;i<excelNum;i++){
			try{
				files[i] = new File(System.getProperty("java.io.tmpdir"), getDateString().toString() +".xls");
				Workbook  workbook = new HSSFWorkbook();
				
				int start = i*MAX_NUM ;
				int end = (start + MAX_NUM) > listSize ? listSize:(start + MAX_NUM) ;
				
				System.out.println("=====>start" + start + "end==>" + end);
				
				ExcelExportServer excelExportServer = new ExcelExportServer(listSize,true);
				excelExportServer.setfields(fields);
				excelExportServer.createSheet(workbook,sheetName,columnsTitle,data,start,end);
				
				FileOutputStream out = new FileOutputStream(files[i]);
				workbook.write(out);
				out.close();
			}catch(Exception e){
				deleteFiles(files);
				throw new Exception(e);
				
			}
			
		}
		File zipFile = new File(System.getProperty("java.io.tmpdir"),UUID.randomUUID().toString() + "test.zip");
		FileOutputStream out = new FileOutputStream(zipFile); 
		ZipOutputStream zipOutputStream = new ZipOutputStream(out);
		
		ZipEntry zipEntry = null;
		byte[] buffer = new byte[1024];
		int readtotal;
		
		for(int j=0;j<files.length;j++){
			zipEntry = new ZipEntry(files[j].getName());
			
			
			zipOutputStream.putNextEntry(zipEntry);
			
			FileInputStream in = new FileInputStream(files[j]);
			while((readtotal = in.read(buffer)) > 0){
				zipOutputStream.write(buffer, 0, readtotal);
			}
			in.close();
		}
		zipOutputStream.close();
		out.close();
		deleteFiles(files);
		return zipFile.toString();
	}
	public static void deleteFiles(File[] files){
		if(null != files){
			for(int i=0;i<files.length;i++){
				if(null != files[i]){
					 files[i].delete();
				}
			}
		}
	}

	private static  String getDateString() {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	}
	
	public static String ZcyexportExcelPaths(String[] columnsTitle,List<List<Map<String, String>>>  data,String[] fields) throws Exception{
		
		//计算导出excel数量
		int excelNum = data.size();
		
		File[] files = new File[excelNum];
		
		for(int i=0;i<excelNum;i++){
			try{
				List<Map<String, String>>  orderlist=data.get(i);
				Map<String, String> temp = orderlist.get(0);
				String sheetName = temp.get("sheetName");
				files[i] = new File(System.getProperty("java.io.tmpdir"), sheetName+"_"+getDateString().toString() +".xls");
				Workbook  workbook = new HSSFWorkbook();
				int listSize=orderlist.size();
				System.out.println("=====>start" + 0 + "end==>" +listSize);
				ExcelExportServer excelExportServer = new ExcelExportServer(listSize,true);
				excelExportServer.setfields(fields);
				excelExportServer.createSheet(workbook,sheetName+i,columnsTitle,orderlist,0,listSize);
				FileOutputStream out = new FileOutputStream(files[i]);
				workbook.write(out);
				out.close();
			}catch(Exception e){
				deleteFiles(files);
				throw new Exception(e);
			}
			
		}
		File zipFile = new File(System.getProperty("java.io.tmpdir"),UUID.randomUUID().toString() + "test.zip");
		FileOutputStream out = new FileOutputStream(zipFile); 
		ZipOutputStream zipOutputStream = new ZipOutputStream(out);
		
		ZipEntry zipEntry = null;
		byte[] buffer = new byte[1024];
		int readtotal;
		
		for(int j=0;j<files.length;j++){
			zipEntry = new ZipEntry(files[j].getName());
			
			
			zipOutputStream.putNextEntry(zipEntry);
			
			FileInputStream in = new FileInputStream(files[j]);
			while((readtotal = in.read(buffer)) > 0){
				zipOutputStream.write(buffer, 0, readtotal);
			}
			in.close();
		}
		zipOutputStream.close();
		out.close();
		deleteFiles(files);
		return zipFile.toString();
	}
}
