package com.ImageServer.Controllers.Tryouts;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HTMLController {

    @GetMapping(value = "/welcome", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody

    public String welcomeAsHTML() {
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "<h1>" + "Hello world as HTML\n" + "</h1>" + "</body>\n" + "</html>";
    }

    @RequestMapping("/welcome12")
    public String getWelcomePage() {
        return "image.html";
    }

}
