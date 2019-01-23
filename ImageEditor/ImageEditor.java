
public class ImageEditor {

    public void run(String[] args) {
        if (args.length < 3) {
            System.out.println("USAGE: java ImageEditor inputfile outputfile (grayscale|invert|emboss|motionblur motion-blur-length)");
            return;
        }
        String inputfile = args[0];
        String outfile = args[1];
        String transform = args[2];
        int motionBlurLength = 0;
        if ("motionblur".equals(transform)) {
            if (args.length != 4) {
                System.out.println("USAGE: java ImageEditor inputfile outputfile (grayscale|invert|emboss|motionblur motion-blur-length)");
                return;
            }
            motionBlurLength = Integer.parseInt(args[3]);
            if (motionBlurLength < 1) {
                System.out.println("USAGE: java ImageEditor inputfile outputfile (grayscale|invert|emboss|motionblur motion-blur-length)");
                return;
            }
        }
        else if (args.length > 3) {
            System.out.println("USAGE: java ImageEditor inputfile outputfile (grayscale|invert|emboss|motionblur motion-blur-length)");
            return;
        }

        ImageIO imageio = new ImageIO();
        Image image = imageio.readImage(inputfile);
        if (image == null) {
            return;
        }

        Image outputImage;
        switch (args[2]) {
            case "invert":
                    Inverter inverter = new Inverter();
                    outputImage = inverter.invert(image);
                    break;
            case "grayscale":
                    GrayScaler grayScaler = new GrayScaler();
                    outputImage = grayScaler.grayscale(image);
                    break;
            case "emboss":
                    Embosser embosser = new Embosser();
                    outputImage = embosser.emboss(image);
                    break;
            case "motionblur":
                    MotionBlurer motionBlurer = new MotionBlurer();
                    outputImage = motionBlurer.motionblur(image, motionBlurLength);
                    break;
            default:
                System.out.println("USAGE: java ImageEditor inputfile outputfile (grayscale|invert|emboss|motionblur motion-blur-length)");
                return;
        }

        imageio.writeImage(outfile, outputImage);
    }

    public static void main(String[] args) {
        ImageEditor ie = new ImageEditor();
        ie.run(args);
    }
}
