package com.ImageServer.Services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StringFormatService {

    public static String[] removeFirstandLast(String str) {

        StringBuilder sb = new StringBuilder(str);

        sb.deleteCharAt(str.length() - 1);
        sb.deleteCharAt(0);
        if (!sb.toString().contains("x")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Dimensions error, please specify qparams such as: ?size=WIDTHxHEIGHT; " +
                            "make sure width and height are positive whole numbers. 320 <= width <= 3840;" +
                            " 240 <= height <= 2160");
        }
        return splitmethod(sb.toString());

    }

    public static String[] splitmethod(String str) {
        String[] size = str.split("x");

        return size;
    }

}
