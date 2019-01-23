class Inverter {

    public Image invert(Image image) {

        Pixel[][] pixels = image.getPixels();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] rgb = pixels[i][j].getRGB();
                int[] newRGB = new int[3];

                newRGB[0] = 255 - rgb[0];
                newRGB[1] = 255 - rgb[1];
                newRGB[2] = 255 - rgb[2];

                pixels[i][j].setRGB(newRGB);
            }
        }

        return image;
    }

}
