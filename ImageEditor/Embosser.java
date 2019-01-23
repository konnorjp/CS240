class Embosser {
    public Image emboss(Image image) {
        String p3 = image.getP3();
        int width = image.getWidth();
        int height = image.getHeight();
        //int maxColorValue = image.getMaxColorValue();
        //Pixel[][] pixels = image.getPixels();

        // I'm referencing the same pixels!
        //Image img = new Image(p3, width, height, maxColorValue, pixels);

        //Pixel[][] new_pixels = img.getPixels();
        Pixel[][] org_pixels = image.getPixels();

        for (int i = height - 1; i > -1; i--) {
            for (int j = width - 1; j > -1; j--) {
                int[] newRGB = new int[3];

                if (i-1 < 0 || j-1 < 0) {
                    newRGB[0] = 128;
                    newRGB[1] = 128;
                    newRGB[2] = 128;
                }
                else {
                    int[] rgb = org_pixels[i][j].getRGB();
                    int[] upper_rgb = org_pixels[i-1][j-1].getRGB();
                    int redDiff = rgb[0] - upper_rgb[0];
                    int greenDiff = rgb[1] - upper_rgb[1];
                    int blueDiff = rgb[2] - upper_rgb[2];

                    int bigDiff = (Math.abs(redDiff) >= Math.abs(greenDiff)) ? redDiff : greenDiff;
                    bigDiff = (Math.abs(bigDiff) >= Math.abs(blueDiff)) ? bigDiff : blueDiff;

                    int v = 128 + bigDiff;
                    /*if (v < 0) {
                        v = 0;
                    } else if (v > 255) {
                        v = 255;
                    }*/
                    v = (v < 0) ? 0 : (v > 255) ? 255 : v;

                    newRGB[0] = v;
                    newRGB[1] = v;
                    newRGB[2] = v;
                }

                org_pixels[i][j].setRGB(newRGB);
            }
        }

        return image;
    }
}
