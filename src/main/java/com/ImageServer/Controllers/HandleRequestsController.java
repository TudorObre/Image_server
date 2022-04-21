package com.ImageServer.Controllers;


import com.ImageServer.Services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/images")
public class HandleRequestsController extends HttpServlet {

    @Autowired
    private ServerService serverService;

    @GetMapping(path = "/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void get_details(@PathVariable(required = false, name = "filename") String filename,
                            @RequestParam(required = false) Map<String, String> qparams,
                            HttpServletResponse response) throws IOException, InterruptedException {

        String size = qparams.values().toString();

        String to_serve = serverService.path_to_serve(filename, size);

        response.setContentType("image/jpeg");
        ServletOutputStream out;
        out = response.getOutputStream();
        FileInputStream fin = new FileInputStream(to_serve);

        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch = 0;

        while ((ch = bin.read()) != -1) {
            bout.write(ch);
        }
        bin.close();
        fin.close();
        bout.close();
        out.close();
    }


}