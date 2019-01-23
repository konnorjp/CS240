class GrayScaler {

    public Image grayscale(Image image) {

        Pixel[][] pixels = image.getPixels();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] rgb = pixels[i][j].getRGB();
                int[] newRGB = new int[3];

                int newColor = (rgb[0] + rgb[1] + rgb[2]) / 3;
                newRGB[0] = newColor;
                newRGB[1] = newColor;
                newRGB[2] = newColor;

                pixels[i][j].setRGB(newRGB);
            }
        }

        return image;
    }

}
