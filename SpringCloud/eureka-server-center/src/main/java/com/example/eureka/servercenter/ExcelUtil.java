package com.example.eureka.servercenter;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.awt.Color;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * <p>
 * 导出Excel工具类
 * </p>
 *
 * @author gaox
 * @since 2020/3/23
 */
public class ExcelUtil {

    private static final String suffix = ".xlsx";

    /**
     * 使用浏览器选择路径下载
     *
     * @param data
     * @throws Exception
     */
    @Deprecated
    public static ResponseEntity<byte[]> exportExcel(ExcelData data) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(data.getFileName() + ".xlsx", "utf-8"));
            generateExcel(data).write(bout);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<byte[]>(bout.toByteArray(), headers, HttpStatus.CREATED);
    }

    /**
     * 直接下载到本地
     *
     * @param excelData
     * @return
     * @throws Exception
     */
    public static void write2Local(ExcelData excelData) throws Exception {
        File f = new File(excelData.getSavePath() + excelData.getFileName() + suffix);
        FileOutputStream out = new FileOutputStream(f);
        generateExcel(excelData).write(out);
    }

    public static byte[] readDataAsByteArray(ExcelData data) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            if (data != null) {
                generateExcel(data).write(bout);
            }
            bytes = bout.toByteArray();
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static XSSFWorkbook generateExcel(ExcelData data) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        int rowIndex = 0;
        String sheetName = data.getSheetName();
        if (null == sheetName) {
            sheetName = "";
        }
        XSSFSheet sheet = wb.createSheet(sheetName);
        rowIndex = writeExcel(wb, sheet, data);
        return wb;
    }

    /**
     * 表显示字段
     *
     * @param wb
     * @param sheet
     * @param data
     * @return
     */
    private static int writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {
        int rowIndex = 0;
        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        rowIndex = writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().size() + 1);
        return rowIndex;
    }

    /**
     * 设置表头
     *
     * @param wb
     * @param sheet
     * @param titles
     * @return
     */
    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;
        Font titleFont = wb.createFont();
        //设置字体
        titleFont.setFontName("simsun");
        //设置粗体
        titleFont.setBold(true);
        //设置字号
        titleFont.setFontHeightInPoints((short) 14);
        //设置颜色
        titleFont.setColor(IndexedColors.BLACK.index);
        XSSFCellStyle titleStyle = wb.createCellStyle();
        //水平居中
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置图案颜色
        Integer grey = 888888;
        byte[] greys = ByteBuffer.allocate(Integer.BYTES).putInt(grey).array();
        CTColor greyColor = CTColor.Factory.newInstance();
        greyColor.setRgb(greys);
        titleStyle.setFillForegroundColor(XSSFColor.from(greyColor, new DefaultIndexedColorMap()));

        //设置图案样式
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));
        Row titleRow = sheet.createRow(rowIndex);
        titleRow.setHeightInPoints(25);
        colIndex = 0;
        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }
        rowIndex++;
        return rowIndex;
    }

    /**
     * 设置内容
     *
     * @param wb
     * @param sheet
     * @param rows
     * @param rowIndex
     * @return
     */
    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex;
        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));
        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            dataRow.setHeightInPoints(25);
            colIndex = 0;
            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    /**
     * 自动调整列宽
     *
     * @param sheet
     * @param columnNumber
     */
    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
//                if(newWidth>255*256){//Max width
                if (newWidth > 12000) {//为了美观
                    sheet.setColumnWidth(i, 12000);
                } else {
                    sheet.setColumnWidth(i, newWidth);
                }

            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    /**
     * 设置边框
     *
     * @param style
     * @param border
     * @param color
     */
    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(BorderSide.TOP, color);
        style.setBorderColor(BorderSide.LEFT, color);
        style.setBorderColor(BorderSide.RIGHT, color);
        style.setBorderColor(BorderSide.BOTTOM, color);
    }
}
