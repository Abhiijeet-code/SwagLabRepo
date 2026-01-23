import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class Xls_Reader {

    private String path;
    private XSSFWorkbook workbook;

    // ================= CONSTRUCTOR =================
    public Xls_Reader(String path) {
        this.path = path;
        try (FileInputStream fis = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= ROW COUNT =================
    public int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) return 0;
        return workbook.getSheetAt(index).getLastRowNum() + 1;
    }

    // ================= COLUMN COUNT =================
    public int getColumnCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return -1;
        Row row = sheet.getRow(0);
        return row == null ? -1 : row.getLastCellNum();
    }

    // ================= GET CELL DATA (BY COLUMN NAME) =================
    public String getCellData(String sheetName, String colName, int rowNum) {

        if (rowNum <= 0) return "";

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return "";

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return "";

        int colNum = -1;
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (headerRow.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName)) {
                colNum = i;
                break;
            }
        }

        if (colNum == -1) return "";

        Row row = sheet.getRow(rowNum - 1);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        return getCellValue(cell);
    }

    // ================= GET CELL DATA (BY COLUMN NUMBER) =================
    public String getCellData(String sheetName, int colNum, int rowNum) {

        if (rowNum <= 0) return "";

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return "";

        Row row = sheet.getRow(rowNum - 1);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        return getCellValue(cell);
    }

    // ================= COMMON CELL VALUE HANDLER =================
    private String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return new SimpleDateFormat("dd/MM/yyyy").format(date);
                }
                return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case BLANK:
            default:
                return "";
        }
    }

    // ================= SET CELL DATA =================
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {

        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null || rowNum <= 0) return false;

            Row headerRow = sheet.getRow(0);
            int colNum = -1;

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(colName)) {
                    colNum = i;
                    break;
                }
            }

            if (colNum == -1) return false;

            Row row = sheet.getRow(rowNum - 1);
            if (row == null) row = sheet.createRow(rowNum - 1);

            Cell cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);

            cell.setCellValue(data);

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= ADD SHEET =================
    public boolean addSheet(String sheetName) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.createSheet(sheetName);
            workbook.write(fos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= REMOVE SHEET =================
    public boolean removeSheet(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) return false;

        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.removeSheetAt(index);
            workbook.write(fos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= ADD COLUMN =================
    public boolean addColumn(String sheetName, String colName) {

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return false;

        Row row = sheet.getRow(0);
        if (row == null) row = sheet.createRow(0);

        int colNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
        Cell cell = row.createCell(colNum);
        cell.setCellValue(colName);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        try (FileOutputStream fos = new FileOutputStream(path)) {
            workbook.write(fos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= ADD HYPERLINK =================
    public boolean addHyperLink(String sheetName, String colName, int rowNum,
                                String linkText, String url) {

        try {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return false;

            Row row = sheet.getRow(rowNum - 1);
            if (row == null) row = sheet.createRow(rowNum - 1);

            Row header = sheet.getRow(0);
            int colNum = -1;

            for (int i = 0; i < header.getLastCellNum(); i++) {
                if (header.getCell(i).getStringCellValue().equalsIgnoreCase(colName)) {
                    colNum = i;
                    break;
                }
            }

            if (colNum == -1) return false;

            Cell cell = row.createCell(colNum);
            cell.setCellValue(linkText);

            CreationHelper helper = workbook.getCreationHelper();
            Hyperlink link = helper.createHyperlink(HyperlinkType.FILE);
            link.setAddress(url.replace("\\", "/"));
            cell.setHyperlink(link);

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setUnderline(Font.U_SINGLE);
            font.setColor(IndexedColors.BLUE.getIndex());
            style.setFont(font);
            cell.setCellStyle(style);

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
