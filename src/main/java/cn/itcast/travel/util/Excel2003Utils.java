package cn.itcast.travel.util;

import cn.itcast.travel.domain.ExamTheme;
import cn.itcast.travel.service.impl.ManagerServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class Excel2003Utils{
    public static List<ExamTheme> read(InputStream request) {
        List<ExamTheme> list=new ArrayList<>();
        try {
            HSSFWorkbook workbook=new HSSFWorkbook(request);
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            for(Row row:sheetAt) {
                if (row != null) {
                    List<String> contentRow = new ArrayList<>();
                    for (Cell cell : row) {
                        String stringCellValue = cell.getStringCellValue();
                        contentRow.add(stringCellValue);
                    }
                    list.add(new ExamTheme(contentRow.get(0),
                            contentRow.get(1),
                            contentRow.get(2),
                            contentRow.get(3),
                            contentRow.get(4),
                            contentRow.get(5),
                            contentRow.get(6)));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}



