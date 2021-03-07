package com.crud.simple.controller;

import com.crud.simple.service.ExportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@Controller
@Slf4j
public class ExportController {

    private final ExportService exportService;

    /**
     * Экспорт данных пользователей в xls
     */
    @GetMapping(value="/user/export")
    public void exportToFile(HttpServletResponse response) {
        try {
            log.info("Экспорт данных пользователей в xls");
            File file = exportService.exportUsersToFile();
            InputStream is = new FileInputStream(file);
            response.setContentType("application/xls");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}
