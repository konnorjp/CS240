class Pixel {

    private int red;
    private int green;
    private int blue;

    Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int[] getRGB() {
        int[] rgb = {red, green, blue};
        return rgb;
    }

    public void setRGB(int[] rgb) {
        red = rgb[0];
        green = rgb[1];
        blue = rgb[2];
    }

}
