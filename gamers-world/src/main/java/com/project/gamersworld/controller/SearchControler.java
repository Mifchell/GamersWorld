package com.project.gamersworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.gamersworld.handlers.GroupHandler;
import com.project.gamersworld.models.Group;

@Controller
public class SearchControler {

    @Autowired
    GroupHandler groupHandler;

    // @GetMapping
    // public List<Group> searchGroup(@RequestParam(value = "filter") String filter)
    // {
    // List<Group> groups = groupHandler.groupSearch(filter);

    // // check if its empty => redirect to error group not found page
    // // redirect display of the groups

    // return groups;

    // }

}
