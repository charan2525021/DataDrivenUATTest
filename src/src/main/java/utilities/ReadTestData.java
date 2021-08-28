package utilities;

import java.util.Hashtable;


public class ReadTestData {
//
//    public String sheetName = "TestDatas";
//    public String testCaseName = "TestCaseOne";
//
//    @Test(dataProvider = "getTestData")
//    public void sampleTestData(Hashtable<String, String> htable) {
//
//      System.out.println(htable.get("Column8"));
//    }
//
//    @DataProvider
    public static Object[][] getTestData(String dataFileName,String sheetName, String testCaseName) {
        readExcelFile rd = new readExcelFile(System.getProperty("user.dir")+"\\src\\test\\TestData\\"+dataFileName);
        int startRowNum = 0;

//        Get Row Number for TesCase Name
        while (!rd.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testCaseName)) {
            startRowNum++;
        }

        int startTestColumn = startRowNum + 1;
        int startTestRow = startRowNum + 2;

//      Get No of Rows Test data exsit in

        int row = 0;

        while (!rd.getCellData(sheetName, 0, startTestRow + row).trim().equalsIgnoreCase("Row is Null".trim())) {
            row++;
        }


//        Get No of Column Name Test data exsit in
        int clm = 0;
        while (!rd.getCellData(sheetName, clm, startTestColumn).trim().equalsIgnoreCase("Cell is Null".trim())) {
            clm++;
        }

        Object[][] dataSet = new Object[row][1];
        Hashtable<String, String> htable = null;
        int dataRownumber = 0;
        for (int noOfRows = startTestRow; noOfRows <= startTestColumn + row; noOfRows++) {
            htable = new Hashtable<String, String>();
            for (int noOfColumns = 0; noOfColumns < clm; noOfColumns++) {
                String key = rd.getCellData(sheetName, noOfColumns, startTestColumn);
                String value = rd.getCellData(sheetName, noOfColumns, noOfRows);
                htable.put(key, value);
            }
            dataSet[dataRownumber][0] = htable;
            dataRownumber++;
        }


        return dataSet;
    }


}

