import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.StringBuilder;

public class ImageIO {
    public Image readImage(String filename) {
        String p3 = "";
        int width = 0;
        int height = 0;
        int maxColorValue = 0;
        Pixel[][] pixels;
        try {
            Scanner scanner = new Scanner(new File(filename)).useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

            if (scanner.hasNext()) {
                 p3 = scanner.next();
            }
            if (scanner.hasNext()) {
                 width = scanner.nextInt();
            }
            if (scanner.hasNext()) {
                 height = scanner.nextInt();
            }
            if (scanner.hasNext()) {
                 maxColorValue = scanner.nextInt();
            }

            pixels = new Pixel[height][width];
            int column = 0;
            int row = -1;
            while(scanner.hasNext()) {
                if (column % width == 0) {
                    row += 1;
                    column = 0;
                }

                int red = scanner.nextInt();
                int green = scanner.nextInt();
                int blue = scanner.nextInt();

                pixels[row][column] = new Pixel(red, green, blue);
                column += 1;
            }

            return new Image(p3, width, height, maxColorValue, pixels);
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void writeImage(String filename, Image image) {
        String p3 = image.getP3();
        int width = image.getWidth();
        int height = image.getHeight();
        int maxColorValue = image.getMaxColorValue();
        Pixel[][] pixels = image.getPixels();

        StringBuilder sb = new StringBuilder();
        sb.append(p3 + '\n');
        sb.append(Integer.toString(width) + " ");
        sb.append(Integer.toString(height) + '\n');
        sb.append(Integer.toString(maxColorValue) + '\n');
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int[] rgb = pixels[i][j].getRGB();
                if (j > 0) {
                    sb.append(" ");
                }
                sb.append(Integer.toString(rgb[0]) + " ");
                sb.append(Integer.toString(rgb[1]) + " ");
                sb.append(Integer.toString(rgb[2]));
            }
            sb.append('\n');
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.append(sb);
            bw.flush();
            bw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
