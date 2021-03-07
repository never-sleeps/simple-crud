package com.crud.simple.service.impl;

import com.crud.simple.model.User;
import com.crud.simple.service.ExportService;
import com.crud.simple.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ExportServiceImpl implements ExportService {

    private final String FILE_NAME = "download.xls";
    private final UserService userService;

    @Override
    public File exportUsersToFile() {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("USERS");

        generateHeadRow(sheet);
        generateDataRows(sheet);
        setAutoSizeColumn(sheet);

        writeWorkbookIntoFile(hssfWorkbook);
        return new File(FILE_NAME);
    }

    /**
     * Устанавливаем автоматическое определение ширины столбцов
     * @param sheet лист
     */
    private void setAutoSizeColumn(HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(0);
        for(int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    /**
     * Генерируем данные из таблицы USERS
     * @param sheet лист
     */
    private void generateDataRows(HSSFSheet sheet) {
        List<User> users = userService.findAllUsers();
        int rowNumber = 1;
        for (User user : users) {
            HSSFRow row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getBirthday());
            row.createCell(3).setCellValue(user.getMail());
            rowNumber++;
        }
    }

    /**
     * Генерируем строку-заголовок
     * @param sheet лист
     */
    private void generateHeadRow(HSSFSheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);

        HSSFRow headRow = sheet.createRow(0);

        Cell idCell = headRow.createCell(0);
        Cell nameCell = headRow.createCell(1);
        Cell birthdayCell = headRow.createCell(2);
        Cell mailCell = headRow.createCell(3);

        idCell.setCellStyle(cellStyle);
        nameCell.setCellStyle(cellStyle);
        birthdayCell.setCellStyle(cellStyle);
        mailCell.setCellStyle(cellStyle);

        idCell.setCellValue("id");
        nameCell.setCellValue("Name");
        birthdayCell.setCellValue("Birthday");
        mailCell.setCellValue("contact mail");
    }

    /**
     * Записываем Workbook в файл
     * @param hssfWorkbook объект Workbook
     */
    private void writeWorkbookIntoFile(HSSFWorkbook hssfWorkbook) {
        try {
            FileOutputStream fileOut =  new FileOutputStream(FILE_NAME);
            hssfWorkbook.write(fileOut);
            fileOut.close();
            log.info("Excel file has been generated!");
        } catch (IOException e) {
            log.error("File generation error!");
            e.printStackTrace();
        }
    }
}
