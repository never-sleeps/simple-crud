package com.crud.simple.controller;

import com.crud.simple.model.User;
import com.crud.simple.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import javax.validation.Valid;

@AllArgsConstructor
@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * Получение списка всех пользователей
     * @param model модель страницы
     * @return модель страницы с параметром users, хранящим в себе список пользователей
     */
    @GetMapping({"/", "/user"})
    public String getUsers(Model model) {
        log.info("Получение списка пользователей");
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    /**
     * Получение пользователя по id
     * @param userId id пользователя
     * @param model модель страницы
     * @return модель страницы с параметром user, хранящим в себе пользователя
     */
    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable("userId") Long userId, Model model) {
        log.info("Получение пользователя с id: " + userId);
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user";
    }

    /**
     * Добавление пользователя: первоначальная инициализация объекта User
     * @param model модель страницы
     * @return модель страницы с параметром user, хранящим в себе пустой объект User
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("add", true);
        return "edit";
    }

    /**
     * Сохранение пользователя
     * @param user объект User
     * @param result BindingResult
     * @param model модель страницы
     * @return модель страницы с параметрами: user - сохранённый объект User, add - режим добавления
     */
    @PostMapping("/user/add")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            log.info("Ошибка при попытке сохранения пользователя: " + user);
            model.addAttribute("add", true);
            model.addAttribute("user", user);
            return "edit";
        }
        log.info("Сохранение пользователя: " + user);
        User savedUser = userService.saveUser(user);
        model.addAttribute("user", savedUser);
        return "user";
    }

    /**
     * Сохранение пользователя с определенным id
     * @param userId id пользователя
     * @param user объект User
     * @param result BindingResult
     * @param model модель страницы
     * @return модель страницы с параметрами: user - сохранённый объект User, add - режим добавления
     */
    @PostMapping("/user/{userId}/update")
    public String updateUser(@PathVariable Long userId,
                             @ModelAttribute("book") @Valid User user,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            log.info("Ошибка при попытке сохранения пользователя: " + user);
            model.addAttribute("add", true);
            model.addAttribute("user", user);
            return "edit";
        }
        log.info("Сохранение пользователя с id: " + userId);
        user.setId(userId);
        User bookResult = userService.saveUser(user);
        model.addAttribute("user", bookResult);
        return "user";
    }

    /**
     * Получение пользователя по id
     * @param userId id пользователя
     * @param model модель страницы
     * @return модель страницы с параметром user - сохранённый объект User
     */
    @GetMapping("/user/{userId}/edit")
    public String editUser(@PathVariable("userId") Long userId, Model model) {
        log.info("Получение пользователя с id: " + userId);
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "edit";
    }

    /**
     * Удаление пользователя по id
     * @param userId id пользователя
     * @return редирект на страницу списка пользователей
     */
    @GetMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId) {
        log.info("Удаление пользователя с id: " + userId);
        userService.deleteUser(userId);
        return "redirect:/user";
    }
}
