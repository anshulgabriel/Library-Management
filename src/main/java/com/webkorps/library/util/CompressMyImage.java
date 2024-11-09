package com.webkorps.library.util;

import java.awt.image.BufferedImage;
import javax.servlet.http.Part;

public class CompressMyImage {
    
    public static BufferedImage compressImage(BufferedImage originalImage, Part bookImage) {
        if (bookImage.getSize() > 0) {
            // Example compression: Resize the image to a maximum width/height of 800 pixels
            int maxWidth = 800;
            int maxHeight = 800;
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            if (width > height) {
                if (width > maxWidth) {
                    height = (height * maxWidth) / width;
                    width = maxWidth;
                }
            } else {
                if (height > maxHeight) {
                    width = (width * maxHeight) / height;
                    height = maxHeight;
                }
            }

            BufferedImage compressedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            compressedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            return compressedImage;
        }
        return originalImage;
    }
    
}
