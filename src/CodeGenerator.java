import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

public class CodeGenerator extends GraphicsApp {


    private static final Color YELLOW = new Color(234, 182, 56); // "Selbstgemischter" RGB-Farbe (gelb)
    private static final Color GREY = new Color(47, 61, 76); // "Selbstgemischter" RGB-Farbe (grau)

    @Override
    public void initialize() {
        setCanvasSize(500, 500);
    }

    @Override
    public void draw() {
        drawBackground(GREY);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("CodeGenerator");
    }
}
