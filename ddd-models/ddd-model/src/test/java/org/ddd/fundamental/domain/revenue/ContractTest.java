package org.ddd.fundamental.domain.revenue;

import org.ddd.fundamental.helper.DateHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ContractTest {

    private Product wordProduct = Product.newWordProcessor("文字处理软件");

    private Product excelProduct = Product.newExcelProcessor("Excel处理软件");

    private Product dataBaseProduct = Product.newDatabaseProcessor("数据库处理软件");

    @Test
    public void testCalculateRevenueRecognitionWord(){
        Date date = new Date();
        Contract contract = new Contract(wordProduct,1050, date);
        contract.calculateRecognitions();
        double result = contract.recognizeRevenue(date);
        Assert.assertEquals(1050.0,result,0.0);
    }
    @Test
    public void testCalculateRevenueRecognitionExcel(){
        Date date = new Date();
        Date beforeDate = DateHelper.addInterval(-61,date);
        Contract contract = new Contract(excelProduct,1080, beforeDate);
        contract.calculateRecognitions();
        double result = contract.recognizeRevenue(date);
        Assert.assertEquals(720.0,result,0.0);
    }
    @Test
    public void testCalculateRevenueRecognitionDatabase(){
        Date date = new Date();
        Date beforeDate = DateHelper.addInterval(-50,date);
        Contract contract = new Contract(dataBaseProduct,1110, beforeDate);
        contract.calculateRecognitions();
        double result = contract.recognizeRevenue(date);
        Assert.assertEquals(740.0,result,0.0);
    }
}
