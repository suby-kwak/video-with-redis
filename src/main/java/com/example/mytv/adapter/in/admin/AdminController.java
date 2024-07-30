package com.example.mytv.adapter.in.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("greeting")
    public String index() {
        System.out.println("index");
        return "greeting";
    }
}
