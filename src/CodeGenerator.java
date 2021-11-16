import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Rectangle;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

import javax.sound.midi.Soundbank;

/**
 * Diese Anwendung generiert ein zufälliges Pixelmuster. Dazu werden über ein zwei-dimensionales
 * Array zufällige boolean-Werte erzeugt. Für jede Stelle mit einem "true"-Wert wird ein
 * farbiges Rechteck erstellt. Dessen Position ergibt sich aus den "Koordinaten" des boolean-Arrays.
 *
 * Für eine "schönere" Darstellung werden die Pixel durch Rechtecke repräsentiert, die jeweils
 * mehrere Pixel breit sind.
 *
 * Achtung: Das Erstellen der Pixel-Rechtecke zu Beginn kann ein paar Sekunden dauern, währenddessen
 * werden Sie nichts im Anwendungsfenster sehen.
 */
public class CodeGenerator extends GraphicsApp {

    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)

    // Breite des Anwendungsfensters
    private static final int WINDOW_WIDTH = 500;
    // Höhe des Anwendungsfensters
    private static final int WINDOW_HEIGHT = 500;
    // Hintergrundfarbe
    private static final Color BACKGROUND_COLOR = GREY;
    // Farbe der "Pixel"
    private static final Color PIXEL_COLOR = YELLOW;
    // Breite und Höhe der "Pixel"
    private static final int PIXEL_SIZE = 10;

    // Array für alle Pixel des Musters (wird zu Beginn befüllt und in jedem Frame gezeichnet)
    private Rectangle[] pixels;

    @Override
    public void initialize() {
        // Wir reduzieren die "Frame Rate" auf 1 Bild pro Sekunde, da wir nur statische Inhalte anzeigen
        setFrameRate(1);
        setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        /*
         * Wir erstellen ein "Array of Arrays", das für jeden potenziellen, sichtbaren Pixel einen
         * Wahrheitswert (true oder false) enthält. Die Werte werden zufällig bestimmt. Dieses Array
         * dient uns als Maske für das Erstellen des eigentlichen Pixelmusters. Für jede Stelle, an
         * der true eingetragen ist, wird an der korrespondierenden Position im GraphicsApp-Koordinatensystem
         * ein farbiges Rechteck eingezeichnet.
         *
         * createPixelMask: Erstellt die Maske mit den boolean-Werten
         * createPixels: Erstellt auf Basis der Maske die notwendigen Rechtecke (Pixel)
         */
        boolean[][] pixelMask = createPixelMask(getWidth(), getHeight());
        pixels = createPixels(pixelMask);
    }

    /**
     * Die Methode gibt ein Array of Arrays mit zufällig ausgewählten boolean-Werten zurück. Die
     * Dimensionen der Datenstruktur werden über die übergebenen Parameter bestimmt.
     *
     * @param width Länge der inneren Arrays
     * @param height Länge des umschließenden, äußeren Arrays
     * @return Ein Array mit "height" boolean-Arrays der Länge "width", befüllt mit zufälligen Werten
     */
    private boolean[][] createPixelMask(int width, int height) {
        // Wir erstellen das "zwei-dimensionale" Array und verwenden die Parameter für die Längenangaben
        boolean[][] pixelMask = new boolean[height][width];
        // Wir iterieren über das äußere Array ...
        for (int y = 0; y < pixelMask.length; y++) {
            // ... und mit jeder Iteration einmal vollständig über das aktuelle, innere Array ...
            for (int x = 0; x < pixelMask[0].length; x++) {
                /*
                 * ... um damit die notwendigen Indizes für jede Zelle zu berechnen und diese mit ein
                 * einem zufällig bestimmten Boolean-Wert zu belegen. Für den Zufallswert generieren wir
                 * mithilfe der Math.random()-Funktion einen zufälligen Wert zwischen 0 und 1 und prüfen,
                 * ob dieser Wert größer als 0.5 ist. Das Ergebnis dieses Vergleichs ist ein Wahrheitswert,
                 * also entweder true oder false. Diesen Wert speichern wir in die aktuelle Zelle des
                 * Arrays (das wir ja explizit für boolean-Werte erstellt haben). Die Wahrscheinlichkeit für
                 * den Wert "true" beträgt ~ 0.5.
                 */
                pixelMask[y][x] = (Math.random() > 0.5);
            }
        }
        return pixelMask;
    }

    /**
     * Die Methode erstellt auf Basis der übergebenen boolean-Maske, ein eindimensionales Array,
     * das für jede als "true"-gekennzeichnete Stelle der Maske ein Rechteck an der korrespondierenden
     * Stelle im Koordinatensystem der GraphicsApp enthält.
     *
     * @param pixelMask Das "Array of Arrays" mit den Wahrheitswerten für die zu füllenden Stellen
     * @return Ein "normales" Array mit allen Rechtecken zur Darstellung der sichtbaren Pixel
     */
    private Rectangle[] createPixels(boolean[][] pixelMask) {
        // Wir erstellen ein leeres Array der Länge 0 (weil wir noch nicht wissen, wie viele sichtbaren Pixel in der Maske definiert sind)
        Rectangle[] pixels = new Rectangle[0];
        // Wir iterieren mit zwei Schleifen ...
        for (int y = 0; y < pixelMask.length; y++) {
            // ... über alle Zellen des übergebenen Arrays ...
            for (int x = 0; x < pixelMask[0].length; x++) {
                // ... und lesen den gespeicherten Wahrheitswert aus.
                boolean shouldDrawPixel = pixelMask[y][x];
                // Wenn in der Maske "true" steht, erweitern wir das Pixel-Array um einen neuen Eintrag ...
                if (shouldDrawPixel) {
                    /*
                     * ... und nutzen die Zählervariablen der beiden Schleifen um die x- und y-Position des neuen Rechtecks zu bestimmten.
                     * Dabei überschreiben wir das Array in pixels mit dem neuen Array, das als Rückgabe von addNewPixels zurückgegeben
                     * wird. Das Erstellen eines neuen (längeren) Arrays in der Methode ist notwendig, da die Länge eines Arrays nach
                     * dem Erstellen nicht mehr verändert werden kann. Da wir zu Beginn (Erstes Erstellen eines Arrays in pixels, Zeile 90)
                     * nicht wissen, wie viele Rechtecke am Ende in dem Array gespeichert sein müssen, tasten wir uns so Schritt für Schritt
                     * heran: Für jedes neue Rechteck erstellen wir ein neues Array mit einem Platz mehr und speichern an dieser letzten Stelle
                     * das neue Rechteck.
                     */
                    pixels = addNewPixelsTo(pixels, x * PIXEL_SIZE, y * PIXEL_SIZE);
                }
            }
        }
        return pixels;
    }

    /**
     * Diese Methode erzeugt ein neues Rechteck an der übergebenen Bildschirmposition und gibt dieses, zusammen
     * mit allen bereits vorhandenen Rechtecken aus dem als Parameter übergebenen Array, in einem neuen Array
     * zurück. Das neue, zurückgegebene Array hat einen Wert mehr als das ursprünglich übergebene Array.
     *
     * @param pixels Das Array mit allen bis jetzt erstellten Rechtecken
     * @param xPos Die x-Position des neuen Rechtecks
     * @param yPos Die y-Position des neuen Rechtecks
     * @return Ein neues Array mit allen bereits bekannten sowie dem neu erstellten Rechteck
     */
    private Rectangle[] addNewPixelsTo(Rectangle[] pixels, int xPos, int yPos) {
        // Wir erstellen ein neues Array, das genau eine Zelle mehr hat als das übergebene Array
        Rectangle[] expandedArray = new Rectangle[pixels.length + 1];
        // Wir "kopieren" (hier eigentlich "übertragen") alle Rechtecke aus dem ursprünglichen Array ins neue
        System.arraycopy(pixels, 0, expandedArray, 0, pixels.length);
        // Wir erstellen ein neues Rechteck ...
        Rectangle pixel = new Rectangle(xPos, yPos, PIXEL_SIZE, PIXEL_SIZE, PIXEL_COLOR);
        // ... modifizieren die Darstellung der Umrandung ...
        pixel.setBorder(PIXEL_COLOR, 1);
        // ... und speichern es an der letzten Stelle des neuen Arrays
        expandedArray[expandedArray.length - 1] = pixel;
        // Das neue Array mit dem neuen Rechteck an letzter Stelle wird zurückgegeben
        return expandedArray;
    }

    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
        // In jedem Frame werden alle Rechtecke neu gezeichnet
        drawPixels(pixels);
    }

    private void drawPixels(Rectangle[] pixels) {
        /*
         * Beim Zeichnen der Rechtecke kürzen wir das Iterieren über alle Einträge des Arrays durch
         * die for-each-Schleife ab. Intern wird über jede Zelle des Arrays iteriert. Im Rumpf der
         * Schleife können wir über die im Kopf angelegte Variable pixel jeweils auf das Rechteck an
         * der aktuellen Stelle des Arrays zugreifen und es, wie hier, dann zeichnen.
         */
        for (Rectangle pixel : pixels) {
            pixel.draw();
        }
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("CodeGenerator");
    }
}
