package utilities;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class readExcelFile {

    public String path;
    public XSSFWorkbook workBook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    public FileInputStream fis = null;
    public FileOutputStream fos = null;

    //         #Constructor
    public readExcelFile(String path) {
        this.path = path;
        try {
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //     #Find Now of Row exists in Excel File
    public int getRowCount(String sheetName) {
        try {
            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return 0;
            sheet = workBook.getSheetAt(index);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return sheet.getLastRowNum() + 1;
    }

//  #Get Cell Date Using Column Name and Row Number

    public String getCellData(String sheetName, String colName, int rowNum) {

        try {
            if (rowNum <= 0)
                return "Invalid Row Number";

            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return sheetName + " not exist in Provided excel";
            sheet = workBook.getSheetAt(index);
            int colNum = -1;
            int totalRows = getRowCount(sheetName) - 1;
            if (colName != null) {
                for (int i = 0; i <= totalRows; i++) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j <= row.getLastCellNum(); j++) {
                            if (row.getCell(j) != null) {
                                if (row.getCell(j).getStringCellValue().trim().equals(colName.trim())) {
                                    colNum = j;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else
                return "Cell Value is " + colName;
            if (colNum == -1)
                return "Column Name doest not exist in Provided Excel";

            sheet = workBook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            cell = row.getCell(colNum);

            if (cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

                    // System.out.println(cellText);

                }
                return cellText;
            } else if (cell.getCellType() == CellType.BLANK)
                return "There is no Data in Provided Cell is (" + "Row Number :" + rowNum + "Column Number:" + colNum + ")";
            else
                return String.valueOf(cell.getBooleanCellValue());

        } catch (Exception e) {
            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }

//    #Get Cell Date Using Column Number and Row number

    public String getCellData(String sheetName, int colNum, int rowNum) {

        try {
            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return sheetName + " doesn't exist in provided excel";
            if (colNum < 0 || rowNum - 1 < 0)
                return "Invalid Column Number: " + colNum + ", OR Invalid Row Number: " + rowNum;

            sheet = workBook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "Row is Null";
            cell = row.getCell(colNum);
            if (cell == null)
                return "Cell is Null";
            if (cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
                return String.valueOf(cell.getNumericCellValue());
            else if (cell.getCellType() == CellType.BLANK)
                return "Cell is Null";
            else
                return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
        }

    }

//    #Add Sheet to the Excel

    public boolean addSheet(String sheetName) {

        try {
            fos = new FileOutputStream(path);
            workBook.createSheet(sheetName);
            workBook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    #Remove Sheet from Excel

    public boolean removeSheet(String sheetName) {

        int index = workBook.getSheetIndex(sheetName);

        if (index == -1)
            return false;

        try {
            fos = new FileOutputStream(path);
            workBook.removeSheetAt(index);
            workBook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

//    #Add Data to the cell using columnName and Row Number

    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {


            if (rowNum <= 0)
                return false;

            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return false;
            sheet = workBook.getSheetAt(index);
            int colNum = -1;
            int totalRows = getRowCount(sheetName) - 1;
            if (colName != null) {
                for (int i = 0; i <= totalRows; i++) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j <= row.getLastCellNum(); j++) {
                            if (row.getCell(j) != null) {
                                if (row.getCell(j).getStringCellValue().trim().equals(colName.trim())) {
                                    colNum = j;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (colNum == -1)
                return false;

            sheet = workBook.getSheetAt(index);
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            CellStyle cs = workBook.createCellStyle();
            cs.setWrapText(true);
            cell.setCellStyle(cs);
            cell.setCellValue(data);

            fos = new FileOutputStream(path);
            workBook.write(fos);
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean setCellData(String sheetName, int colNum, int rowNum, String data) {
        try {
            if (rowNum <= 0 || colNum < 0)
                return false;
            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return false;
            sheet = workBook.getSheetAt(index);
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);
            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);


            CellStyle cs = workBook.createCellStyle();
            cs.setWrapText(true);
            cell.setCellStyle(cs);
            cell.setCellValue(data);
            fos = new FileOutputStream(path);
            workBook.write(fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addColumn(String sheetName, String colName) {
        try {
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);
            int index = workBook.getSheetIndex(sheetName);
            if (index == -1)
                return false;
            sheet = workBook.getSheetAt(index);

            row = sheet.getRow(0);
            if (row == null)
                row = sheet.createRow(0);

            if (row.getLastCellNum() == -1)
                cell = row.createCell(0);
            else
                cell = row.createCell(row.getLastCellNum());


            XSSFCellStyle style = workBook.createCellStyle();
            style.setWrapText(true);
            style.setFillBackgroundColor(IndexedColors.ROSE.index);
            style.setFillForegroundColor(IndexedColors.BLUE1.index);
            style.setFillPattern(FillPatternType.SQUARES);
            cell.setCellStyle(style);
            cell.setCellValue(colName);
           fos = new FileOutputStream(path);
            workBook.write(fos);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }


    /****************** Removes a column and all the contents ***********************/
    public boolean removeColumn(String sheetName, int colNum) {
        try {
            if (!isSheetExist(sheetName))
                return false;
            fis = new FileInputStream(path);
            workBook = new XSSFWorkbook(fis);
            sheet = workBook.getSheet(sheetName);
            XSSFCellStyle style = workBook.createCellStyle();
            style.setFillForegroundColor(new XSSFColor(Color.lightGray));
            XSSFCreationHelper createHelper = workBook.getCreationHelper();


            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    cell = row.getCell(colNum);
                    if (cell != null) {
                        cell.setCellStyle(style);
                        row.removeCell(cell);
                    }
                }
            }
            fos = new FileOutputStream(path);
            workBook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /****************** Find whether sheets exists ***********************/
    public boolean isSheetExist(String sheetName) {
        int index = workBook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workBook.getSheetIndex(sheetName.toUpperCase());
            if (index == -1)
                return false;
            else
                return true;
        } else
            return true;
    }


    /****************** Returns number of columns in a sheet ***********************/
    public int getColumnCount(String sheetName) {
        // check if sheet exists
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workBook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null)
            return -1;

        return row.getLastCellNum();

    }


/*	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {
		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;
		sheet = workBook.getSheet(sheetName);
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData_By_ColumnNumber_RowNumber(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}
		return true;
	}*/

    /****************** Returns Cell Row Number ***********************/
    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;

    }

}

