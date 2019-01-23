class Image {

    private String p3;
    private int width;
    private int height;
    private int maxColorValue;
    private Pixel[][] pixels;

    Image(String p3, int width, int height, int maxColorValue, Pixel[][] pixels) {
        this.p3 = p3;
        this.width = width;
        this.height = height;
        this.maxColorValue = maxColorValue;
        this.pixels = pixels;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public String getP3() {
        return p3;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxColorValue() {
        return maxColorValue;
    }
}
