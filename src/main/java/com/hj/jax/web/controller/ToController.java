package com.hj.jax.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ToController {

    @GetMapping("/api/to/login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/api/to/index")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/api/to/welcome")
    public String toWelcome() {
        return "welcome";
    }

    /**
     *     管理员 - 人员管理
     */
    @RequestMapping("/api/to/admin-user")
    public String toAdminUser() {
        return "admin-user";
    }
    @RequestMapping("/api/to/admin-user-add")
    public String toAdminAddUser() {
        return "admin-user-add";
    }
    @RequestMapping("/api/to/admin-user-update")
    public ModelAndView toAdminUpdateUser(@RequestParam Long userId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin-user-update");
        mv.addObject("userId", userId);
        return mv;
    }


    @RequestMapping("/api/to/admin-mark")
    public String toAdminSorder() {
        return "admin-mark";
    }

    @RequestMapping("/api/to/admin-engine")
    public String toAdminMarkEngine() {
        return "admin-engine";
    }


    /**
     *     管理员 - 报表管理
     */
    @RequestMapping("/api/to/admin-report")
    public String toAdminReport() {
        return "admin-report";
    }



}
