package com.xuebusi.xbs.common.utils.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *  生成Excel的工具类
 *  String reportName = "供应商商家记录";
 *  String[] titles = new String[]{"商家编号", "商家名称", "入驻类型"};
 *  String[] fieldName = new String[]{"sellerCode", "sellerName", "settledType"};
 *  导出数据
 *  PoiExcelUtils.exportExcel(seller, reportName, titles, fieldName, response.getOutputStream(), null);
 *
 */
public class PoiExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(PoiExcelUtils.class);

    /**
     * 添加列表信息
     * sheet excelSheet
     * list 导出主要信息
     * fieldName 属性名称>数组对于表头 扩展属性格式extra.key
     * contextStyle 内容样式
     * isHaveSerial 是否添加序号
     */
    public static <T> void addContextByList(HSSFSheet sheet, List<T> list,
                                            String[] fieldName, HSSFCellStyle contextStyle, boolean isHaveSerial, int fromRowIndex) {

        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            if (list != null) {
                List<T> tList = (List<T>) list;
                T t = null;
                String value = "";
                for (int i = 0; i < list.size(); i++) {
                    row = sheet.createRow(i + fromRowIndex);
                    for (int j = 0; j < fieldName.length; j++) {
                        
                        t = tList.get(i);
                        value = objectToString(getMapValueByKey(fieldName[j], t));
                        if(isHaveSerial){
                            //首列加序号
                            if(row.getCell(0)!=null && row.getCell(0).getStringCellValue()!=null){
                                cell = row.createCell(0);
                                cell.setCellValue(""+i);
                            }
                            cell = row.createCell(j+1);
                            cell.setCellValue(value);    
                        }else{
                            cell = row.createCell(j);
                            cell.setCellValue(value);    
                        }
                        cell.setCellStyle(contextStyle);
                    }
                }
                int curColWidth = 0;
                for (int j = 0; j < fieldName.length; j++) {
                	sheet.autoSizeColumn(j,true); // 单元格宽度 以最大的为准
                	/* 实际宽度 */  
                	curColWidth = sheet.getColumnWidth(j);
                	
                	if (curColWidth < fieldName[j].getBytes().length*2*256+124) {  
                		sheet.setColumnWidth(j,fieldName[j].getBytes().length*2*256+124);
                    }  
       
                }
                
            } else {
                row = sheet.createRow(fromRowIndex);
                cell = row.createCell(0);
            }
        } catch (Throwable e) {
            logger.error("填充内容出现错误：" + e.getMessage(), e);
        }
    }
    
    /**
     * <P>Object转成String类型，便于填充单元格</P>
     * */
    public static String objectToString(Object object){
        String str = "";
        if(object==null){
        }else if(object instanceof Date){
                DateFormat from_type = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                Date date = (Date)object;
                str = from_type.format(date);
        }else if(object instanceof String){
            str = (String)object;
        }else if(object instanceof Integer){
            str = ((Integer)object).intValue()+"";
        }else if(object instanceof Double){
            str = ((Double)object).doubleValue()+"";
        }else if(object instanceof Long){
            str = Long.toString(((Long)object).longValue());
        }else if(object instanceof Float){
            str = Float.toHexString(((Float)object).floatValue());
        }else if(object instanceof Boolean){
            str = Boolean.toString((Boolean)object);
        }else if(object instanceof Short){
            str = Short.toString((Short)object);
        }
        return str;
    }
    
    /**
     * 添加标题(第一行)与表头(第二行)
     * 
     * @param 
     * sheet excelSheet
     * assettitle 表头>数组
     * titleName 标题 
     * headerStyle 标题样式
     * contextStyle  表头样式
     */ 
    public static void addTitle(HSSFSheet sheet, String[] assettitle, String titleName,
            HSSFCellStyle headerStyle, HSSFCellStyle contextStyle) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, assettitle.length - 1));
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(titleName);
        cell.setCellStyle(headerStyle);
        row = sheet.createRow(1);
        for (int i = 0; i < assettitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(assettitle[i]);
            cell.setCellStyle(contextStyle);
        }
    }
    
    
    public static void addMergeTitle(HSSFSheet sheet, String[] firstColumnName, String[] secondColumnName,String titleName,
            HSSFCellStyle headerStyle, HSSFCellStyle contextStyle,List<CellRangeAddress> regionList) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, firstColumnName.length - 1));
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(titleName);
        cell.setCellStyle(headerStyle);
        
        row = sheet.createRow(1);
        for (int i = 0; i < firstColumnName.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(firstColumnName[i]);
            cell.setCellStyle(contextStyle);
        }
        
        row = sheet.createRow(2);
        for (int i = 0; i < secondColumnName.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(secondColumnName[i]);
            cell.setCellStyle(contextStyle);
        }
        
        regionList.forEach(r->{
        	sheet.addMergedRegion(r);
        });
    }
    
    public static void addFooter(HSSFSheet sheet, String[] resultList,int rowIndex,HSSFCellStyle contextStyle) {
        //sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, resultList.length - 1));
        HSSFRow row = sheet.createRow(rowIndex);
        HSSFCell cell;
        for (int i = 0; i < resultList.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(resultList[i]);
            cell.setCellStyle(contextStyle);
        }
    }
    /**
     * <p>
     * 根据属性名获取属性值
     * </p>
     * fieldName 属性名 object 属性所属对象
     * 支持Map扩展属性, 不支持List类型属性，
     * return 属性值
     */
    @SuppressWarnings("unchecked")
    public static Object getFieldValueByName(String fieldName, Object object) {
        try {
            Object fieldValue = null;
            if (StringUtils.hasLength(fieldName) && object != null) {
                String firstLetter = ""; // 首字母
                String getter = ""; // get方法
                Method method = null; // 方法
                String extraKey = null;
                // 处理扩展属性 extraData.key
                if (fieldName.indexOf(".") > 0) {
                    String[] extra = fieldName.split("\\.");
                    fieldName = extra[0];
                    extraKey = extra[1];
                }
                firstLetter = fieldName.substring(0, 1).toUpperCase();
                getter = "get" + firstLetter + fieldName.substring(1);
                method = object.getClass().getMethod(getter, new Class[] {});
                fieldValue = method.invoke(object, new Object[] {});
                if (extraKey != null) {
                    Map<String, Object> map = (Map<String, Object>) fieldValue;
                    fieldValue = map==null ? "":map.get(extraKey);
                }
            }
            return fieldValue;
        } catch (Throwable e) {
            logger.error("获取属性值出现异常：" + e.getMessage(), e);
            return null;
        }
    }
    
    public static Object getMapValueByKey(String fieldName, Object object) {
        try {
        	 Map<String, Object> map = (Map<String, Object>) object;
            return map.get(fieldName);
        } catch (Throwable e) {
            logger.error("获取属性值出现异常：" + e.getMessage(), e);
            return null;
        }
    }
    
    
    public static void exportExcel(List<Map<String, Object>> list,String reportName, String[] titles,String[] fieldNames, OutputStream outputStream,String[] resultFooter) {
		// 产生工作簿对象  
        HSSFWorkbook workbook = new HSSFWorkbook();
        //产生工作表对象  
        HSSFSheet sheet = workbook.createSheet();  
        
        setColNum(titles.length, sheet);
        
        addTitle(sheet, titles, reportName, getHeader(workbook), getContext(workbook));
        addContextByList(sheet, list, fieldNames, getContext(workbook), false,2);

        if(resultFooter!=null)
        {
        	addFooter(sheet,resultFooter,list.size()+2,getContext(workbook));
        }
        try {
			workbook.write(outputStream);
		}
        catch (UnsupportedEncodingException e1) {
        	 logger.error(e1.getMessage(), e1.fillInStackTrace());
	    }  
        catch (IOException e) {
        	logger.error(e.getMessage(), e.fillInStackTrace());
		} 
        finally  
	     {  
	         try  
	         {  
	        	 outputStream.flush();  
	        	 outputStream.close();  
	         }  
	         catch (IOException e)  
	         {}  
	     }  
	}

    
    
    public static void exportExcel(List<Map<String, Object>> list,String reportName,
    		String[] firstColumnName,String[] secondColumnName,String[] fieldNames, 
    		OutputStream outputStream,
    		String[] resultFooter,List<CellRangeAddress> regionList) {
		// 产生工作簿对象  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        //产生工作表对象  
        HSSFSheet sheet = workbook.createSheet();  
        setColNum(secondColumnName.length, sheet);
        
        addMergeTitle(sheet,firstColumnName, secondColumnName,reportName,
        		getHeader(workbook), getContext(workbook), regionList);
        
        addContextByList(sheet, list, fieldNames, getContext(workbook), false,3);
        
        if(resultFooter!=null)
        {
        	addFooter(sheet,resultFooter,list.size()+2,getContext(workbook));
        }
        try {
			workbook.write(outputStream);
		}
        catch (UnsupportedEncodingException e1) {
        	 logger.error(e1.getMessage(), e1.fillInStackTrace());
	    }  
        catch (IOException e) {
        	logger.error(e.getMessage(), e.fillInStackTrace());
		} 
        finally  
	     {  
	         try  
	         {  
	        	 outputStream.flush();  
	        	 outputStream.close();  
	         }  
	         catch (IOException e)  
	         {}  
	     }  
	}
    
    
    
    
    /**
     * 设置列的宽度
     * @param titles
     * @param sheet
     */
	private static void setColNum(int columnNum, HSSFSheet sheet) {
		// 让列宽随着导出的列长自动适应
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(colNum) != null) {
					HSSFCell currentCell = currentRow.getCell(colNum);
					if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			if (colNum == 0) {
				sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
			} else {
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}
		}
	}
	

    //标题样式
    public static HSSFCellStyle getHeader(HSSFWorkbook workbook){
    	HSSFCellStyle format = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  //加粗
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)11);
        format.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        format.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        format.setFont(font);
        return format;
     }
        
    //内容样式
    public static HSSFCellStyle getContext(HSSFWorkbook workbook){
    	HSSFCellStyle format = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        format.setWrapText(true);  
        font.setFontName("宋体");
        format.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        format.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        format.setFont(font);
        return format;
    }
}