package com.gtnals.book_information.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.gtnals.book_information.data.AdminVO;
import com.gtnals.book_information.service.AdminService;
import com.gtnals.book_information.service.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired DashboardService service;
    @Autowired AdminService admin_service;
    @GetMapping("/")
    public String getMain(Model model){
        service.getUpdate();
        model.addAttribute("cnt", service.getCounts());
        model.addAttribute("update", service.getUpdateDate());
        return "/index";
    }
    @GetMapping("/login")
    public String getLogin(){
        return "/login";
    }
    @PostMapping("/admin/login")
    public String postAdminLogin(String a_id, String a_pwd, HttpSession session){
        Map<String, Object> resultMap = admin_service.loginAdmin(a_id, a_pwd);
        AdminVO admin = (AdminVO)resultMap.get("admin");
        if(admin==null) {
            session.setAttribute("message", resultMap.get("message"));
            return "redirect:/login";
        }
        session.setAttribute("admin", admin);
        session.setAttribute("message", "");
        return "redirect:/";
    }
    @GetMapping("/admin/logout")
    public String getAdminLogout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
