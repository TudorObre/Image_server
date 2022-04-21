package com.ImageServer.Controllers.Tryouts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InputController {

    @RequestMapping("/input")
    public String getInputPage() {
        System.out.println("/input was requested");
        return "input.html";
    }

}
