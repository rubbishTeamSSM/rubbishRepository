package com.neusoft.sdd.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * 导入模板
 * @author work-admin
 *
 */
public class ExcelExportTemplate {
	
	private Workbook workbook;
	
	private HSSFSheet sheet;
	
	private HSSFSheet hidden;
	
	private int beginNum;
	
	private int sheetNum = 0;
	/**
	 * 写入文件
	 * @param xlsFile
	 * @throws IOException
	 */
	public void writeToFile(File xlsFile) throws IOException{
		FileOutputStream out = new FileOutputStream(xlsFile);
		
		try {
			workbook.write(out);
		} catch (IOException e) {
			out.close();
			throw new IOException("",e);
		}
		
		out.close();
		
	}

	public ExcelExportTemplate() {
		this.workbook =  new HSSFWorkbook();
	}
	
	/**
	 * 格式化单元格
	* 部门：软件开发事业部
	* 功能：
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 下午4:15:12
	 */
	public HSSFCellStyle getFormatter(String type)
	{
		//创建数字和日期类型的样式
		 HSSFCellStyle cellStyleData = (HSSFCellStyle) workbook.createCellStyle();
		 HSSFCellStyle cellStyleDate = (HSSFCellStyle) workbook.createCellStyle();
		// 用于格式化单元格的数据 
        HSSFDataFormat format = (HSSFDataFormat) workbook.createDataFormat();
        
        //数值类型
        if("1".equals(type)){
        	cellStyleData.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        	return cellStyleData;
        }else if("2".equals(type)){
        	//日期类型
        	cellStyleDate.setDataFormat(format.getFormat("yyyy年m月d日"));
        	return cellStyleDate;
        }else
        {
        	return null;
        }
	}
	
	/**
	* 部门：软件开发事业部
	* 功能：创建提示行
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 下午3:03:45
	 */
	public void createCommontRow(List<TitleRowCell> titleRowCells,String excelName){
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(80);//设置行高
		
		// 设置标题字体
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setFontHeightInPoints((short) 14); //字体高度
        font.setColor(HSSFColor.BLUE.index); //字体颜色
        font.setFontName("黑体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(true); //是否使用斜体
        HSSFCellStyle cellStyle = (HSSFCellStyle) workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中中      cellStyle.setWrapText(true);
        cellStyle.setWrapText(true);//回绕文本
       
       
       // 设置提示信息字体
       HSSFFont fontTS = (HSSFFont) workbook.createFont();
       fontTS.setFontName("黑体"); //字体
       fontTS.setColor(HSSFColor.GREEN.index); //字体颜色
       fontTS.setItalic(true); //是否使用斜体
       HSSFCellStyle cellStyleTS = (HSSFCellStyle) workbook.createCellStyle();
       cellStyleTS.setFont(fontTS);
       cellStyleTS.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中中      cellStyle.setWrapText(true);
       cellStyleTS.setWrapText(true);//回绕文本
	    
		String note = 
				"1、红色必须填写\n" +
						"2、黑色选择填写\n" + 
						"3、注意查看批注\n" +
						"4、日期必须以文本形式填入，即在开头会出一个绿色的小三角，否则会导致导入出错\n" +
						"5、数字 不能超过七位，否则系统会以科学计数法表示。如果确须超过七位可以以文本方式存入，这样不会被系统读成科学记数法。";
		
		//合并单元格
		if(titleRowCells.size()>1)
		{
			sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
			sheet.addMergedRegion(new Region(0,(short)2,0,(short)(titleRowCells.size()-1)));
		}else
		{
			sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
			sheet.addMergedRegion(new Region(0,(short)2,0,(short)(2)));
		}
		
		
		HSSFCell cell = row.createCell(0);
		
		cell.setCellStyle(cellStyleTS);
		cell.setCellValue(note);
		
        HSSFCell cellBT = row.createCell(2);
		
        cellBT.setCellStyle(cellStyle);
        cellBT.setCellValue(excelName);
        
	}
	
	/**
	* 部门：软件开发事业部
	* 功能：创建标题行
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 下午3:03:45
	 */
	public synchronized void  createTitleRow(List<TitleRowCell> titleRowCells,String sheetName){
		sheet =  (HSSFSheet) workbook.createSheet(sheetName);
		if(0 == sheetNum)
		{
			hidden = (HSSFSheet) workbook.createSheet("hidden");//创建隐藏的sheet
			sheetNum++;
			
			beginNum = 1;
		}
		
		
		createCommontRow(titleRowCells,sheetName);
		HSSFRow row = sheet.createRow(1);
		for(int i=0;i <titleRowCells.size();i++ ){
			HSSFCell cell = row.createCell(i);
			createTitleRowCell(titleRowCells.get(i),cell,i);
			//设置列宽
			sheet.setColumnWidth(i, 5000);
		}
		
		workbook.setSheetHidden(1,true);
	}
	
	/**
	 * 创建65533文本行
	* 部门：软件开发事业部
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2015-4-1 下午3:19:03
	 */
	public void ceateRows(int collen)
	{
		//新增的四句话，设置CELL格式为文本格式  
        HSSFCellStyle cellStyle2 = (HSSFCellStyle) workbook.createCellStyle();  
        HSSFDataFormat format = (HSSFDataFormat) workbook.createDataFormat();  
        cellStyle2.setDataFormat(format.getFormat("@"));  
        
		for (int i = 2; i < 5000; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < collen; j++) {
				HSSFCell cell= row.createCell(j);
				cell.setCellStyle(cellStyle2);  
		        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			}
		}
	}
	
	private void createTitleRowCell(TitleRowCell t,Cell cell,int coloum){
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(t.getTitleName());
		
		if(t.getSuggest().size() > 0){
			//createSuggest(cell,t.getSuggest());
			createHiddenSelect(cell, t.getSuggest(),t.getTitleName());
		}
		
		if (t.isRequire()) {
			createCellType(cell);
		}else
		{
			createCellTypeNreq(cell);
		}
		
		//加批注
		if(t.isRequire() && t.getSuggest().size() > 0)
		{
			createComment("必选项，代码表型，请选择！",1,coloum);
		}else if(t.isRequire() && t.getSuggest().size() <= 0){
			createComment("必填项，请填写！",1,coloum);
		}else if(!t.isRequire() && t.getSuggest().size() > 0){
			createComment("代码表型，请选择！",1,coloum);
		}
	}
	
	/**
	 * 定义注释
	* 部门：软件开发事业部
	* 功能：
	* 描述：content:批注内容  row:批注插入行号  coloum:批注插入列号  
	* 作成者：严惠惠
	* 作成时间：2014-12-19 上午11:17:16
	 */
	private void createComment(String content,int row,int coloum){
		 //创建绘图对象 
		  HSSFPatriarch p=sheet.createDrawingPatriarch(); 
		  //创建单元格对象,批注插入到4行,1列,B5单元格 
		  HSSFCell cell=sheet.getRow(row).getCell(coloum); 
		  //插入单元格内容 
		  //cell.setCellValue(new HSSFRichTextString("批注")); 
		  //获取批注对象 
		  //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2) 
		  //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小. 
		  HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6)); 
		  //输入批注信息 
		  comment.setString(new HSSFRichTextString(content)); 
		  //添加作者,选中B5单元格,看状态栏 
		 // comment.setAuthor("toad"); 
		  //将批注添加到单元格对象中 
		  cell.setCellComment(comment); 
	}
	
	/**
	 * 必填样式
	* 部门：软件开发事业部
	* 功能：
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 上午11:14:56
	 */
	private void createCellType(Cell cell) {
	    HSSFCellStyle style;
		style = (HSSFCellStyle) workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		defineStyle(style);
		cell.setCellStyle(style);
	}
	
	/**
	 * 非必填样式
	* 部门：软件开发事业部
	* 功能：
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 上午11:15:10
	 */
	private void createCellTypeNreq(Cell cell) {
		 HSSFCellStyle style_not_req;
		 style_not_req = (HSSFCellStyle) workbook.createCellStyle();
		 style_not_req.setFillForegroundColor(HSSFColor.YELLOW.index);
		 style_not_req.setFillPattern(CellStyle.SOLID_FOREGROUND);
		 style_not_req.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		 style_not_req.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		 style_not_req.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 style_not_req.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 style_not_req.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 cell.setCellStyle(style_not_req);
	}
	
	/**
	 * 设置标题如果是必填字体设置为红色
	* 部门：软件开发事业部
	* 功能：
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 上午10:18:31
	 */
	private void defineStyle(HSSFCellStyle style)
	{
		  // 生成一个字体
	      HSSFFont font = (HSSFFont) workbook.createFont();
	      font.setColor(HSSFColor.RED.index);
	      font.setFontHeightInPoints((short) 10);
	      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	      style.setFont(font);
	}

	/**
	 * 生成下拉选择
	* 部门：软件开发事业部
	* 功能：
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2014-12-19 上午10:22:08
	 */
	private void createSuggest(Cell cell,List<String> sugs){
		int index = cell.getColumnIndex();
		CellRangeAddressList  cellRangeAddressList = new CellRangeAddressList(2, 65535, index, index);
		
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(sugs.toArray(new String[]{}));
		
		HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList, constraint);
		
		sheet.addValidationData(dataValidation);
	
	}
	
	/**
	 * 解决下拉字符太长问题
	* 部门：软件开发事业部
	* 描述：略
	* 作成者：严惠惠
	* 作成时间：2015-2-7 下午8:19:30
	 */
	public void createHiddenSelect(Cell cell,List<String> sugs,String titleName){
		int index = cell.getColumnIndex();
		if(null == (HSSFName) workbook.getName(titleName))
		{
			String[] explicitListValues = sugs.toArray(new String[]{});
			for (int i = 0 ; i < explicitListValues.length; i++) {
				   String name = explicitListValues[i];
				   HSSFRow row = hidden.createRow(i+beginNum);
				   HSSFCell hiddencell = row.createCell(0);
				   hiddencell.setCellValue(name);
		   }
			int endNum = explicitListValues.length + beginNum;
				 HSSFName namedCell = (HSSFName) workbook.createName();
				 namedCell.setNameName(titleName);
				 namedCell.setRefersToFormula("hidden!$A$"+(beginNum+1)+":$A$" + endNum);
				 DVConstraint constraint = DVConstraint.createFormulaListConstraint(titleName);
				 CellRangeAddressList addressList = new CellRangeAddressList(2, 65535, index, index);
				 HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
				 sheet.addValidationData(validation);
			 beginNum = endNum;
		}else
		{
			DVConstraint constraint = DVConstraint.createFormulaListConstraint(titleName);
			 CellRangeAddressList addressList = new CellRangeAddressList(2, 65535, index, index);
			 HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
			 
			 sheet.addValidationData(validation);
		}
		
		workbook.setSheetHidden(1, true);
	}
	
	
	
	public static void main(String[] args) throws IOException {
//	    File file = new  File("F://a.xls");
//		
//		ExcelExportTemplate e = new ExcelExportTemplate();
//		List<TitleRowCell> titleRowCells = new ArrayList<TitleRowCell>();
//		
//		TitleRowCell t = new TitleRowCell("城市",true);
//		t.addSuggest("南京");
//		t.addSuggest("徐州");
//		titleRowCells.add(t);
//		
//		TitleRowCell tOne = new TitleRowCell("学历",false);
//		tOne.addSuggest("本科");
//		tOne.addSuggest("硕士");
//		titleRowCells.add(tOne);
//		
//		
//		TitleRowCell tOnet = new TitleRowCell("外语",false);
//		
//		titleRowCells.add(tOnet);
//		
//		
//		
//		TitleRowCell tOneQ = new TitleRowCell("年龄",true);
//		
//		titleRowCells.add(tOneQ);
//		
//		
//		
//		e.createTitleRow(titleRowCells,"测试sheet1");
//		e.ceateRows(4);
//		
//		
//		
//		
//        List<TitleRowCell> titleRowCells2 = new ArrayList<TitleRowCell>();
//		
//		TitleRowCell t2 = new TitleRowCell("城市2",true);
//		t2.addSuggest("南京2");
//		t2.addSuggest("徐州2");
//		titleRowCells2.add(t2);
//		
//		TitleRowCell tOne2 = new TitleRowCell("学历2",false);
//		tOne2.addSuggest("本科2");
//		tOne2.addSuggest("硕士2");
//		titleRowCells2.add(tOne2);
//		
//		
//		TitleRowCell tOnet2 = new TitleRowCell("外语2",false);
//		
//		titleRowCells2.add(tOnet2);
//		
//		
//		
//		TitleRowCell tOneQ2 = new TitleRowCell("年龄2",true);
//		
//		titleRowCells2.add(tOneQ2);
//		
//		e.createTitleRow(titleRowCells2,"测试sheet2");
//		e.ceateRows(4);
//		
//		e.writeToFile(file);
//		
//		
		
        /*String[] countryName = new String[]{" 分裂国家罪"," 煽动分裂国家罪"," 武装叛乱、暴乱罪"," 颠覆国家政权罪"," 煽动颠覆国家政权罪"," 资助危害国家安全犯罪活动罪"," 投敌叛变罪"," 叛逃罪"," 间谍罪"," 为境外窃取、刺探、收买、非法提供国家秘密、情报罪"," 资敌罪"," 危害公共安全罪"," 放火罪"," 决水罪"," 爆炸罪"," 投放危险物质罪"," 以危险方法危害公共安全罪"," 失火罪"," 过失决水罪"," 过失爆炸罪"," 过失投放危险物质罪"," 过失以危险方法危害公共安全罪"," 破坏交通工具罪"," 破坏交通设施罪"," 破坏电力设备罪"," 破坏易燃易爆设备罪"," 过失损坏交通工具罪"," 过失损坏交通设施罪"," 过失损坏电力设备罪"," 过失损坏易燃易爆设备罪"," 组织、领导、参加恐怖组织罪"," 资助恐怖活动罪"," 劫持航空器罪"," 劫持船只、汽车罪"," 暴力危及飞行安全罪"," 破坏广播电视设施、公用电信设施罪"," 过失损坏广播电视设施、公用电信设施罪"," 非法制造、买卖、运输、邮寄、储存枪支、弹药、爆炸物罪"," 非法制造、买卖、运输、储存危险物质罪"," 违规制造、销售枪支罪"," 盗窃、抢夺枪支、弹药、爆炸物、危险物质罪"," 抢劫枪支、弹药、爆炸物、危险物质罪"," 非法持有、私藏枪支、弹药罪"," 非法出租、出借枪支罪"," 丢失枪支不报罪"," 非法携带枪支、弹药、管制刀具、危险物品危及公共安全罪"," 重大飞行事故罪"," 铁路运营安全事故罪"," 交通肇事罪"," 重大责任事故罪"," 重大劳动安全事故罪"," 危险物品肇事罪"," 工程重大安全事故罪"," 教育设施重大安全事故罪"," 消防责任事故罪"," 强令违章冒险作业罪"," 大型群众性活动重大安全事故罪"," 不报、谎报安全事故罪"," 危险驾驶罪"," 破坏社会主义市场经济秩序罪"," 生产、销售伪劣商品罪"," 生产、销售伪劣产品罪"," 生产、销售假药罪"," 生产、销售劣药罪"," 生产、销售不符合安全标准的食品罪"," 生产、销售有毒、有害食品罪"," 生产、销售不符合标准的医用器材罪"," 生产、销售不符合安全标准的产品罪"," 生产、销售伪劣农药、兽药、化肥、种子罪"," 生产、销售不符合卫生标准的化妆品罪"," 走私罪"," 走私武器、弹药罪"," 走私核材料罪"," 走私假币罪"," 走私文物罪"," 走私贵重金属罪"," 走私珍贵动物、珍贵动物制品罪"," 走私国家禁止进出口的货物、物品罪"," 走私淫秽物品罪"," 由于一时激动犯罪"," 由于一时冲动犯罪"," 由于一时头昏犯罪"," 由于一时意志薄弱犯罪"," 由于一时贪财犯罪"," 走私普通货物、物品罪"," 走私废物罪"," 妨害对公司、企业的管理秩序罪"," 虚报注册资本罪"," 虚假出资、抽逃出资罪"," 欺诈发行股票、债券罪"," 违规披露、不披露重要信息罪"," 隐匿、故意销毁会计凭证、会计帐簿、财务会计报告罪"," 妨害清算罪"," 非国家工作人员受贿罪"," 对非国家工作人员行贿罪"," 非法经营同类营业罪"," 为亲友非法牟利罪"," 签订、履行合同失职被骗罪"," 国有公司、企业、事业单位人员失职罪"," 国有公司、企业、事业单位人员滥用职权罪"," 徇私舞弊低价折股、出售国有资产罪"," 虚假破产罪"," 背信损害上市公司利益罪"," 对外国公职人员、国际公共组织官员行贿罪"," 破坏金融管理秩序罪"," 伪造货币罪"," 出售、购买、运输假币罪"," 金融工作人员购买假币、以假币换取货币罪"," 持有、使用假币罪"," 变造货币罪"," 擅自设立金融机构罪"," 伪造、变造、转让金融机构经营许可证、批准文件罪"," 高利转贷罪"," 非法吸收公众存款罪"," 伪造、变造金融票证罪"," 伪造、变造国家有价证券罪"," 伪造、变造股票、公司、企业债券罪"," 擅自发行股票、公司、企业债券罪"," 内幕交易、泄露内幕信息罪"," 编造并传播证券、期货交易虚假信息罪"," 诱骗投资者买卖证券、期货合约罪"," 操纵证券、期货市场罪"," 违法发放贷款罪"," 吸收客户资金不入账罪"," 违规出具金融票证罪"," 对违法票据承兑、付款、保证罪"," 逃汇罪"," 洗钱罪"," 骗购外汇罪"," 骗取贷款、票据承兑、金融票证罪"," 妨害信用卡管理罪"," 窃取、收买、非法提供信用卡信息罪"," 背信运用受托财产罪"," 违法运用资金罪"," 利用未公开信息交易罪"," 金融诈骗罪"," 集资诈骗罪"," 贷款诈骗罪"," 票据诈骗罪"," 金融凭证诈骗罪"," 信用证诈骗罪"," 信用卡诈骗罪"," 有价证券诈骗罪"," 保险诈骗罪"," 危害税收征管罪"," 逃税罪"," 抗税罪"," 逃避追缴欠税罪"," 骗取出口退税罪"," 虚开增值税专用发票、用于骗取出口退税、抵扣税款发票罪"," 伪造、出售伪造的增值税专用发票罪"," 非法出售增值税专用发票罪"," 非法购买增值税专用发票、购买伪造的增值税专用发票罪"," 非法制造、出售非法制造的用于骗取出口退税、抵扣税款发票罪"," 非法制造、出售非法制造的发票罪"," 非法出售用于骗取出口退税、抵扣税款发票罪"," 非法出售发票罪"," 虚开发票罪"," 持有伪造的发票罪"," 侵犯知识产权罪"," 假冒注册商标罪"," 销售假冒注册商标的商品罪"," 非法制造、销售非法制造的注册商标标识罪"," 假冒专利罪"," 侵犯著作权罪"," 销售侵权复制品罪"," 侵犯商业秘密罪"," 扰乱市场秩序罪"," 损害商业信誉、商品声誉罪"," 虚假广告罪"," 串通投标罪"," 合同诈骗罪"," 非法经营罪"," 强迫交易罪"," 伪造、倒卖伪造的有价票证罪"," 倒卖车票、船票罪"," 非法转让、倒卖土地使用权罪"," 提供虚假证明文件罪"," 出具证明文件重大失实罪"," 逃避商检罪"," 组织、领导传销活动罪"," 侵犯公民人身权利、民主权利罪"," 故意杀人罪"," 过失致人死亡罪"," 故意伤害罪"," 过失致人重伤罪"," 强奸罪"," 强制猥亵、侮辱妇女罪"," 猥亵儿童罪"," 非法拘禁罪"," 绑架罪"," 拐卖妇女、儿童罪"," 收买被拐卖的妇女、儿童罪"," 聚众阻碍解救被收买的妇女、儿童罪"," 诬告陷害罪"," 强迫劳动罪"," 非法搜查罪"," 非法侵入住宅罪"," 侮辱罪"," 诽谤罪"," 刑讯逼供罪"," 暴力取证罪"," 虐待被监管人罪"," 煽动民族仇恨、民族歧视罪"," 出版歧视、侮辱少数民族作品罪"," 非法剥夺公民宗教信仰自由罪"," 侵犯少数民族风俗习惯罪"," 侵犯通信自由罪"," 私自开拆、隐匿、毁弃邮件、电报罪"," 报复陷害罪"," 打击报复会计、统计人员罪"," 破坏选举罪"," 暴力干涉婚姻自由罪"," 重婚罪"," 破坏军婚罪"," 虐待罪"," 遗弃罪"," 拐骗儿童罪"," 组织残疾人、儿童乞讨罪"," 出售、非法提供公民个人信息罪"," 非法获取公民个人信息罪"," 组织未成年人进行违反治安管理活动罪"," 组织出卖人体器官罪"," 雇用童工从事危重劳动罪"," 侵犯财产罪"," 抢劫罪"," 盗窃罪"," 诈骗罪"," 抢夺罪"," 聚众哄抢罪"," 侵占罪"," 职务侵占罪"," 挪用资金罪"," 挪用特定款物罪"," 敲诈勒索罪"," 故意毁坏财物罪"," 破坏生产经营罪"," 拒不支付劳动报酬罪"," 妨害社会管理秩序罪"," 扰乱公共秩序罪"," 妨害公务罪"," 煽动暴力抗拒法律实施罪"," 招摇撞骗罪"," 伪造、变造、买卖国家机关公文、证件、印章罪"," 盗窃、抢夺、毁灭国家机关公文、证件、印章罪"," 伪造公司、企业、事业单位、人民团体印章罪"," 伪造、变造居民身份证罪"," 非法生产、买卖警用装备罪"," 非法获取国家秘密罪"," 非法持有国家绝密、机密文件、资料、物品罪"," 非法生产、销售间谍专用器材罪"," 非法使用窃听、窃照专用器材罪"," 非法侵入计算机信息系统罪"," 破坏计算机信息系统罪"," 扰乱无线电通讯管理秩序罪"," 聚众扰乱社会秩序罪"," 聚众冲击国家机关罪"," 聚众扰乱公共场所秩序、交通秩序罪"," 投放虚假危险物质罪"," 编造、故意传播虚假恐怖信息罪"," 聚众斗殴罪"," 寻衅滋事罪"," 组织、领导、参加黑社会性质组织罪"," 入境发展黑社会组织罪"," 包庇、纵容黑社会性质组织罪"," 传授犯罪方法罪"," 非法集会、游行、示威罪"," 非法携带武器、管制刀具、爆炸物参加集会、游行、示威罪"," 破坏集会、游行、示威罪"," 侮辱国旗、国徽罪"," 组织、利用会道门、邪教组织、利用迷信破坏法律实施罪"," 组织、利用会道门、邪教组织、利用迷信致人死亡罪"," 聚众淫乱罪"," 引诱未成年人聚众淫乱罪"," 盗窃、侮辱尸体罪"," 赌博罪"," 故意延误投递邮件罪"," 开设赌场罪"," 非法获取计算机信息系统数据、非法控制计算机信息系统罪"," 提供侵入、非法控制计算机信息系统程序、工具罪"," 妨害司法罪"," 伪证罪"," 辩护人、诉讼代理人毁灭证据、伪造证据、妨害作证罪"," 妨害作证罪"," 帮助毁灭、伪造证据罪"," 打击报复证人罪"," 扰乱法庭秩序罪"," 窝藏、包庇罪"," 拒绝提供间谍犯罪证据罪"," 掩饰、隐瞒犯罪所得、犯罪所得收益罪"," 拒不执行判决、裁定罪"," 非法处置查封、扣押、冻结的财产罪"," 破坏监管秩序罪"," 脱逃罪"," 劫夺被押解人员罪"," 组织越狱罪"," 暴动越狱罪"," 聚众持械劫狱罪"," 妨害国边境管理罪"," 组织他人偷越国(边)境罪"," 骗取出境证件罪"," 提供伪造、变造的出入境证件罪"," 出售出入境证件罪"," 运送他人偷越国(边)境罪"," 偷越国(边)境罪"," 破坏界碑、界桩罪"," 破坏永久性测量标志罪"," 妨害文物管理罪"," 故意损毁文物罪"," 故意损毁名胜古迹罪"," 过失损毁文物罪"," 非法向外国人出售、赠送珍贵文物罪"," 倒卖文物罪"," 非法出售、私赠文物藏品罪"," 盗掘古文化遗址、古墓葬罪"," 盗掘古人类化石、古脊椎动物化石罪"," 抢夺、窃取国有档案罪"," 擅自出卖、转让国有档案罪"," 危害公共卫生罪"," 妨害传染病防治罪"," 传染病菌种、毒种扩散罪"," 妨害国境卫生检疫罪"," 非法组织卖血罪"," 强迫卖血罪"," 非法采集、供应血液、制作、供应血液制品罪"," 采集、供应血液、制作、供应血液制品事故罪"," 医疗事故罪"," 非法行医罪"," 非法进行节育手术罪"," 妨害动植物防疫、检疫罪"," 破坏环境资源保护罪"," 污染环境罪"," 非法处置进口的固体废物罪"," 擅自进口固体废物罪"," 非法捕捞水产品罪"," 非法猎捕、杀害珍贵、濒危野生动物罪"," 非法收购、运输、出售珍贵、濒危野生动物、制品罪"," 非法狩猎罪"," 非法占用农用地罪"," 非法采矿罪"," 破坏性采矿罪"," 非法采伐、毁坏国家重点保护植物罪"," 盗伐林木罪"," 滥伐林木罪"," 非法收购、运输盗伐、滥伐的林木罪"," 非法收购、运输、加工、出售国家重点保护植物、国家重点保护植物制品罪"," 走私、贩卖、运输、制造毒品罪"," 走私、贩卖、运输、制造毒品罪"," 非法持有毒品罪"," 包庇毒品犯罪分子罪"," 窝藏、转移、隐瞒毒品、毒赃罪"," 走私制毒物品罪"," 非法买卖制毒物品罪"," 非法种植毒品原植物罪"," 非法买卖、运输、携带、持有毒品原植物种子、幼苗罪"," 引诱、教唆、欺骗他人吸毒罪"," 强迫他人吸毒罪"," 容留他人吸毒罪"," 非法提供麻醉药品、精神药品罪"," 组织、强迫、引诱、容留、介绍卖淫罪"," 组织卖淫罪"," 强迫卖淫罪"," 协助组织卖淫罪"," 引诱、容留、介绍卖淫罪"," 引诱幼女卖淫罪"," 传播性病罪"," 嫖宿幼女罪"," 制作、贩卖、传播淫秽物品罪"," 制作、复制、出版、贩卖、传播淫秽物品牟利罪"," 为他人提供书号出版淫秽书刊罪"," 传播淫秽物品罪"," 组织播放淫秽音像制品罪"," 组织淫秽表演罪"," 危害国防利益罪"," 阻碍军人执行职务罪"," 阻碍军事行动罪"," 破坏武器装备、军事设施、军事通信罪"," 故意提供不合格武器装备、军事设施罪"," 过失提供不合格武器装备、军事设施罪"," 聚众冲击军事禁区罪"," 聚众扰乱军事管理区秩序罪"," 冒充军人招摇撞骗罪"," 煽动军人逃离部队罪"," 雇用逃离部队军人罪"," 接送不合格兵员罪"," 伪造、变造、买卖武装部队公文、证件、印章罪"," 盗窃、抢夺武装部队公文、证件、印章罪"," 非法生产、买卖武装部队制式服装罪"," 战时拒绝、逃避征召、军事训练罪"," 战时拒绝、逃避服役罪"," 战时故意提供虚假敌情罪"," 战时造谣扰乱军心罪"," 战时窝藏逃离部队军人罪"," 战时拒绝、故意延误军事订货罪"," 战时拒绝军事征用罪"," 过失损坏武器装备、军事设施、军事通信罪"," 伪造、盗窃、买卖、非法提供、非法使用武装部队专用标志罪"," 贪污贿赂罪"," 贪污罪"," 挪用公款罪"," 受贿罪"," 单位受贿罪"," 行贿罪"," 对单位行贿罪"," 介绍贿赂罪"," 单位行贿罪"," 巨额财产来源不明罪"," 隐瞒境外存款罪"," 私分国有资产罪"," 私分罚没财物罪"," 利用影响力受贿罪"," 渎职罪"," 滥用职权罪"," 玩忽职守罪"," 故意泄露国家秘密罪"," 过失泄露国家秘密罪"," 徇私枉法罪"," 民事、行政枉法裁判罪"," 私放在押人员罪"," 失职致使在押人员脱逃罪"," 徇私舞弊减刑、假释、暂予监外执行罪"," 徇私舞弊不移交刑事案件罪"," 滥用管理公司、证券职权罪"," 徇私舞弊不征、少征税款罪"," 徇私舞弊发售发票、抵扣税款、出口退税罪"," 国家机关工作人员签订、履行合同失职被骗罪"," 违法发放林木采伐许可证罪"," 环境监管失职罪"," 传染病防治失职罪"," 非法批准征用、占用土地罪"," 放纵走私罪"," 商检徇私舞弊罪"," 商检失职罪"," 动植物检疫徇私舞弊罪"," 动植物检疫失职罪"," 放纵制售伪劣商品犯罪行为罪"," 办理偷越国(边)境人员出入境证件罪"," 放行偷越国(边)境人员罪"," 不解救被拐卖、绑架妇女、儿童罪"," 阻碍解救被拐卖、绑架妇女、儿童罪"," 帮助犯罪分子逃避处罚罪"," 招收公务员、学生徇私舞弊罪"," 失职造成珍贵文物损毁、流失罪"," 违法提供出口退税凭证罪"," 非法低价出让国有土地使用权罪"," 枉法仲裁罪"," 食品监管渎职罪"," 执行判决、裁定失职罪"," 执行判决、裁定滥用职权罪"," 国家工作人员非法拘禁"," 国家工作人员非法搜查"," 国家工作人员刑讯逼供"," 暴力取证"," 报复陷害"," 虐待被监管人"," 破坏选举"," 利用职权实施的其他重大犯罪"," 军人违反职责罪"," 战时违抗命令罪"," 隐瞒、谎报军情罪"," 拒传、假传军令罪"," 投降罪"," 战时临阵脱逃罪"," 擅离、玩忽军事职守罪"," 阻碍执行军事职务罪"," 指使部属违反职责罪"," 违令作战消极罪"," 拒不救援友邻部队罪"," 军人叛逃罪"," 非法获取军事秘密罪"," 为境外窃取、刺探、收买、非法提供军事秘密罪"," 故意泄露军事秘密罪"," 过失泄露军事秘密罪"," 战时造谣惑众罪"," 战时自伤罪"," 逃离部队罪"," 武器装备肇事罪"," 擅自改变武器装备编配用途罪"," 盗窃、抢夺武器装备、军用物资罪"," 非法出卖、转让武器装备罪"," 遗弃武器装备罪"," 遗失武器装备罪"," 擅自出卖、转让军队房地产罪"," 虐待部属罪"," 遗弃伤病军人罪"," 战时拒不救治伤病军人罪"," 战时残害居民、掠夺居民财物罪"," 私放俘虏罪"," 虐待俘虏罪"};
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet realSheet = workbook.createSheet("Sheet xls");
		HSSFSheet hidden = workbook.createSheet("hidden");
		for (int i = 0, length= countryName.length; i < length; i++) {
		   String name = countryName[i];
		   HSSFRow row = hidden.createRow(i);
		   HSSFCell cell = row.createCell(0);
		   cell.setCellValue(name);
		 }
		 HSSFName namedCell = workbook.createName();
		 namedCell.setNameName("hidden");
		 namedCell.setRefersToFormula("hidden!$A$1:$A$" + countryName.length);
		 DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
		 CellRangeAddressList addressList = new CellRangeAddressList(0, 100, 0, 0);
		 HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
		 workbook.setSheetHidden(1, true);
		 realSheet.addValidationData(validation);
		 FileOutputStream stream = new FileOutputStream("c:\\range.xls");
		 workbook.write(stream);
		 stream.close();*/
	
	}
	
}
