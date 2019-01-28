package com.example.demo.controller;

import com.example.demo.db1.entity.UsersEntity;
import com.example.demo.db1.repo.UsersRepo;
import com.example.demo.db2.entity.ReportsEntity;
import com.example.demo.db2.repo.ReportsRepo;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContrroller {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ReportsRepo reportsRepo;

    @RequestMapping("/")
    public String index() {

        JsonObject result = new JsonObject();

        UsersEntity usersEntity = usersRepo.findFirstByOrderById();

        JsonObject user = new JsonObject();
        user.addProperty("id", usersEntity.getId());
        user.addProperty("username", usersEntity.getUsername());
        user.addProperty("first_name", usersEntity.getFirstName());
        user.addProperty("last_name", usersEntity.getLastName());

        result.add("users", user);

        ReportsEntity reportsEntity = reportsRepo.findFirstByOrderById();

        JsonObject report = new JsonObject();
        report.addProperty ("id", reportsEntity.getId());
        report.addProperty("date", reportsEntity.getDate());
        report.addProperty("count", reportsEntity.getCount());

        result.add("reports", report);

        return result.toString();
    }


}
