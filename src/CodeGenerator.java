import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Rectangle;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

/**
 * Diese Anwendung generiert ein zufälliges Pixelmuster. Dazu werden über ein zwei-dimensionales
 * Array zufällige boolean-Werte erzeugt. Für jede Stelle mit einem "true"-Wert wird ein
 * farbiges Rechteck erstellt. Dessen Position ergibt sich aus den "Koordinaten" des boolean-Arrays.
 *
 * Für eine "schönere" Darstellung werden die Pixel durch Rechtecke repräsentiert, die jeweils
 * mehrere Pixel breit sind.
 */
public class CodeGenerator extends GraphicsApp {

    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final Color BACKGROUND_COLOR = GREY;
    private static final Color PIXEL_COLOR = YELLOW;
    private static final int PIXEL_SIZE = 10; // Breite und Höhe der "Pixel"

    private Rectangle[] pixels;

    @Override
    public void initialize() {
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        boolean[][] pixelMask = createPixelMask(getWidth(), getHeight());
        pixels = createPixels(pixelMask);
    }

    private boolean[][] createPixelMask(int width, int height) {
        boolean[][] pixelMask = new boolean[height][width];
        for (int y = 0; y < pixelMask.length; y++) {
            for (int x = 0; x < pixelMask[0].length; x++) {
                pixelMask[y][x] = (Math.random() > 0.5);
            }
        }
        return pixelMask;
    }

    private Rectangle[] createPixels(boolean[][] pixelMask) {
        Rectangle[] pixels = new Rectangle[0];
        for (int y = 0; y < pixelMask.length; y++) {
            for (int x = 0; x < pixelMask[0].length; x++) {
                boolean shouldDrawPixel = pixelMask[y][x];
                if (shouldDrawPixel) {
                    pixels = addNewPixelsTo(pixels, x * PIXEL_SIZE, y * PIXEL_SIZE);
                }
            }
        }
        return pixels;
    }

    private Rectangle[] addNewPixelsTo(Rectangle[] pixels, int xPos, int yPos) {
        Rectangle[] expandedArray = new Rectangle[pixels.length + 1];
        System.arraycopy(pixels, 0, expandedArray, 0, pixels.length);
        Rectangle pixel = new Rectangle(xPos, yPos, PIXEL_SIZE, PIXEL_SIZE, PIXEL_COLOR);
        pixel.setBorder(PIXEL_COLOR, 1);
        expandedArray[expandedArray.length - 1] = pixel;
        return expandedArray;
    }

    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
        drawPixels(pixels);
    }

    private void drawPixels(Rectangle[] pixels) {
        for (Rectangle pixel : pixels) {
            pixel.draw();
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("CodeGenerator");
    }
}
