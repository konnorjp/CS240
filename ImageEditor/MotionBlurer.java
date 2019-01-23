class MotionBlurer {
    public Image motionblur(Image image, int motionBlurLength) {
        String p3 = image.getP3();
        int width = image.getWidth();
        int height = image.getHeight();
        int maxColorValue = image.getMaxColorValue();
        Pixel[][] pixels = image.getPixels();

        Image img = new Image(p3, width, height, maxColorValue, pixels);

        Pixel[][] new_pixels = img.getPixels();
        Pixel[][] org_pixels = image.getPixels();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int newMotionBlurLength = motionBlurLength;
                int[] rgb = new_pixels[i][j].getRGB();
                int[] newRGB = new int[3];

                int redSum = rgb[0];
                int greenSum = rgb[1];
                int blueSum = rgb[2];

                if (newMotionBlurLength + j > width) {
                    newMotionBlurLength = width - j;
                }

                for (int k = 1; k < newMotionBlurLength; k++) {
                    int[] colors = org_pixels[i][j+k].getRGB();
                    redSum += colors[0];
                    greenSum += colors[1];
                    blueSum += colors[2];
                }

                newRGB[0] = redSum / newMotionBlurLength;
                newRGB[1] = greenSum / newMotionBlurLength;
                newRGB[2] = blueSum / newMotionBlurLength;

                new_pixels[i][j].setRGB(newRGB);
            }
        }

        return img;
    }
}
