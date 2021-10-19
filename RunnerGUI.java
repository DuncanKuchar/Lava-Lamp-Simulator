/**
 * Runs the program
 * 
 * @author Duncan Kuchar
 * @version 6/4/2021
 */
public class RunnerGUI {
    public static void main(String[] args) {
        // the JFrame size:
        int windowWidth = 950;
        int windowHeight = 700;

        final int DELAY = 80; // speed of graphics, lower number = faster (# milliseconds between updates)

        // make Frame, handing over Rabbit object to it
        Window win = new Window(windowWidth, windowHeight, DELAY);
        // begin animation
        win.startTimer();
    }
}
