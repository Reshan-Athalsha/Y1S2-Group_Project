package com.wedding.weddingplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController serves as the central hub for the Wedding Planner Application.
 * It strictly returns the landing page view containing the navigation grid.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
