package com.example.orientation.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 照片打卡流程
 */
public class ReviewImageUtils {

    public static Double AutoReview(MultipartFile file1,String ImageName) {
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        double percentageDifference=100.0;

        try {
            image1 = ImageIO.read(file1.getInputStream());
            URL url = new URL(ImageName);
//            String basicImageUrl = url.getProtocol() + "://" + url.getHost() + url.getPath();
//            File file2 = new File(basicImageUrl);
//            image2 = ImageIO.read(file2);
            InputStream inputStream = url.openStream();
            image2 = ImageIO.read(inputStream);

            long difference = 0;

            int width1 = image1.getWidth();
            int height1 = image1.getHeight();
            int width2 = image2.getWidth();
            int height2 = image2.getHeight();

            if (width1 != width2 || height1 != height2) {
                System.out.println("警告：图像尺寸不同，平均差异仅供参考。");
            }

            for (int y = 0; y < height1; y++) {
                for (int x = 0; x < width1; x++) {
                    int rgb1 = image1.getRGB(x, y);
                    int rgb2 = image2.getRGB(x, y);
                    int r1 = (rgb1 >> 16) & 0xff;
                    int g1 = (rgb1 >> 8) & 0xff;
                    int b1 = (rgb1) & 0xff;
                    int r2 = (rgb2 >> 16) & 0xff;
                    int g2 = (rgb2 >> 8) & 0xff;
                    int b2 = (rgb2) & 0xff;
                    difference += Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                }
            }

            double totalPixels = width1 * height1 * 3.0; // Each pixel has three color channels (RGB)
            double avg = difference / totalPixels;
            System.out.println("平均差异: " + avg);
            percentageDifference = (avg / (255 * 3)) * 100;
            System.out.println("百分比差异: " + percentageDifference + "%");

            // 根据平均差异的具体值进行判断
            if (avg <= 1000) {
                System.out.println("平均差异不大于1000");
            } else {
                System.out.println("平均差异大于1000");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return percentageDifference;
    }
}