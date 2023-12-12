package com.example.SpringBootMongoDBAssessment.Controller;

import com.example.SpringBootMongoDBAssessment.Model.Admin;
import com.example.SpringBootMongoDBAssessment.Service.AdminService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    //Create Admin
    @PostMapping("/add-admin")
    public String signUp(@RequestBody Admin admin)
    {
        return adminService.signUp(admin);
    }

    //Login Admin
    @PostMapping("/login")
    public String login(@RequestBody Map<String,Object> map)
    {
        return adminService.login(map.get("email").toString(), map.get("password").toString());
    }

}