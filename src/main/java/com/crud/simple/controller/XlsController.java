package com.crud.simple.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
@Slf4j
public class XlsController {
    /**
     * Выгрузка данных таблицы в xls
     * @return редирект на страницу списка пользователей
     */
    @GetMapping("/user/download")
    public String downloadDataBase() {
        log.info("Выгрузка данных таблицы в xls");
        return "redirect:/user";
    }
}
