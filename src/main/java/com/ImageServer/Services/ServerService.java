package com.ImageServer.Services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class ServerService {

    public ServerService() {

    }

    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        System.out.println("entered resize method");

        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public String path_to_serve(String filename, String size) throws IOException {
        List<String> available = Arrays.asList("bmw.jpeg", "mercedes.jpeg", "dacia.jpeg", "porsche.jpeg", "audi.jpeg");

        String pathh = System.getProperty("user.dir");

        String static_path = pathh + "\\src\\main\\resources\\static";

        String path = static_path + "\\" + filename;


        if (available.contains(filename)) {

            String[] dimensions = StringFormatService.removeFirstandLast(size);
            if (!dimensions[0].matches("-?(0|[1-9]\\d*)")
                    || !dimensions[1].matches("-?(0|[1-9]\\d*)")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dimensions error, please specify qparams such as: ?size=WIDTHxHEIGHT;" +
                                " make sure width and height are positive whole numbers. 320 <= width <= 3840;" +
                                " 240 <= height <= 2160");
            }
            int desired_width = Integer.parseInt(dimensions[0]);
            int desired_height = Integer.parseInt(dimensions[1]);

            BufferedImage image = ImageIO.read(new File(path));
            int actual_width = image.getWidth();
            int actual_height = image.getHeight();

            if (desired_width > 3840 || desired_width < 320 || desired_height > 2160 || desired_height < 240) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Dimensions error, please specify qparams such as: ?size=WIDTHxHEIGHT;" +
                                " make sure width and height are positive whole numbers. 320 <= width <= 3840;" +
                                " 240 <= height <= 2160");
            }

            if (desired_height == actual_height && desired_width == actual_width) {
                return path;
            } else {
                String resized_path = static_path + "\\serve.jpeg";
                resize(path, resized_path, desired_width, desired_height);

                return resized_path;
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");

        }
    }
}
