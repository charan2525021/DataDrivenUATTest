package utilities;


public class readDataFromExcel {


    public static final String SHEET_NAME = "TestDataOne";

    public void TestCaeone() {

        readExcelFile rd = new readExcelFile("E:\\Projects\\DataDrivenFramework\\src\\main\\TestData\\ReadData.xlsx");
        rd.addColumn(SHEET_NAME,"Git&GitHub");

    }
}
