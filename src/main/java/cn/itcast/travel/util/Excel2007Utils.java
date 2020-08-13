package cn.itcast.travel.util;

import cn.itcast.travel.domain.ExamTheme;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class Excel2007Utils {
    public static List<ExamTheme> read(InputStream request) {
        List<ExamTheme> list=new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(request);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int maxRow=sheetAt.getLastRowNum();
            for(int i=0;i<maxRow;i++)
            {
                list.add(new ExamTheme(sheetAt.getRow(i).getCell(0).getStringCellValue(),
                        sheetAt.getRow(i).getCell(1).getStringCellValue(),
                        sheetAt.getRow(i).getCell(2).getStringCellValue(),
                        sheetAt.getRow(i).getCell(3).getStringCellValue(),
                        sheetAt.getRow(i).getCell(4).getStringCellValue(),
                        sheetAt.getRow(i).getCell(5).getStringCellValue(),
                        sheetAt.getRow(i).getCell(6).getStringCellValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
