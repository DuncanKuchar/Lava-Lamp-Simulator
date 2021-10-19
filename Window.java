import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Provides a window for buttons, entry boxes, sliders, and displaying of
 * grahpics
 * 
 * @author Duncan Kuchar
 * @version 6/7/2021
 */
public class Window extends JFrame implements ActionListener {
    int TIMER_DELAY; // milliseconds for updating graphics/motion
    Timer t;
    Display display;

    int WIDTH, HEIGHT; // of window

    int movedX; // New X of bubble
    int movedY; // New Y of bubble

    // Starting color for rainbow (red)
    int red = 255;
    int green = 0;
    int blue = 0;
    int rainbowTimer = 0; // For rainbow loop

    boolean rainbowActive = false;

    Color c;

    JSlider colorR, colorB, colorG; // Sliders for R, G, B
    JLabel R, G, B; // Labels for sliders
    JPanel rightPanel; // Houses sliders, labels, and rinbow button
    JButton rainbow;

    /**
     * Constructor for Window
     * 
     * @param width
     * @param height
     * @param delay
     */
    public Window(int width, int height, int delay) {
        super("Lava Lamp --- Duncan Kuchar"); // Name of window
        TIMER_DELAY = delay;

        // save instance variables:
        WIDTH = width;
        HEIGHT = height;

        // Button to set colors to rainbow
        rainbow = new JButton("Rainbow");
        rainbow.addActionListener(this);
        rainbow.setActionCommand("rainbowButton");

        // 3 Sliders for R,G,B. Set to origional color.
        colorR = new JSlider(JSlider.VERTICAL, 0, 510, 200);
        colorG = new JSlider(JSlider.VERTICAL, 0, 510, 40);
        colorB = new JSlider(JSlider.VERTICAL, 0, 510, 400);
        // Labels for Sliders
        R = new JLabel("Red");
        G = new JLabel("Green");
        B = new JLabel("Blue");

        rightPanel = new JPanel();

        this.setLayout(new BorderLayout());
        // make a display object that will show board and motion:
        display = new Display(WIDTH, HEIGHT - 40);

        // add the display to this JFrame:
        this.add(display, BorderLayout.CENTER);

        // R, G, B sliders and labels
        this.add(rightPanel, BorderLayout.EAST);
        rightPanel.add(R, BorderLayout.WEST);
        rightPanel.add(colorR, BorderLayout.WEST);
        rightPanel.add(G, BorderLayout.SOUTH);
        rightPanel.add(colorG, BorderLayout.SOUTH);
        rightPanel.add(B, BorderLayout.EAST);
        rightPanel.add(colorB, BorderLayout.EAST);
        rightPanel.add(rainbow, BorderLayout.NORTH);
        // final setup
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Creates a Timer object and starts timer
     */
    public void startTimer() {
        t = new Timer(TIMER_DELAY, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    /**
     * Called automatically when a button is pressed
     * 
     * @param e this contains information about the button that was pressed and is
     *          send automatically
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) // timer has fired
        {
            updateAll(); // does all motion, checking, and logic (below)
            display.repaint(); // calls paintComponent to redraw everything
        }
        if (e.getActionCommand().equals("rainbowButton")) // timer has fired
        {
            // Turns rainbow on if it was off, and off if it was on
            if (rainbowActive == true) {
                rainbowActive = false;
            } else if (rainbowActive == false) {
                rainbowActive = true;
            }
        }
    }

    /**
     * Called by timer Moves all bubbles, changes colors, and alters bubble speed
     * randomly
     */
    public void updateAll() {
        // Looks through every bubble, changing its location based on its timer
        for (int i = 0; i < display.bubbles.size(); i++) {
            Bubble b = display.bubbles.get(i); // Current bubble
            int speed = b.speed; // Bubble speed alters the timer and movement
            // Sets the new X and Y to the current X and Y (temp)
            movedX = b.bubbleMiddleX;
            movedY = b.bubbleMiddleY;
            // offset from center vertically
            int o = b.offSet;

            // *New Y value* (Timer is distance traveled + the bubble's origional offset
            // (down is 2* origional offset))

            // Moving UP
            if (b.moveTimer < 425 + o) {
                movedY -= speed;
                b.moveTimer += speed;
                // Stays at TOP
            } else if (b.moveTimer < 600 + o) {
                b.moveTimer += speed;
                speed = (int) (Math.random() * 2) + 1;
                // Moves DOWN
            } else if (b.moveTimer < 1025 + 2 * o) {
                movedY += speed;
                b.moveTimer += speed;
            } else if (b.moveTimer < 1200 + 2 * o) {
                // Stays at BOTTOM
                b.moveTimer += speed;
                speed = (int) (Math.random() * 2) + 1;
            } else
                b.moveTimer = 0; // Resets loop

            // *New Width* (Bubbles occilate by 10 pixels)

            int width = b.bubbleWidth;
            if (b.light = false) {
                // Increases by 10
                if (b.bubbleTimer < 10) {
                    b.bubbleWidth++;
                    width = b.bubbleWidth;
                    b.bubbleTimer++;
                    // Decreases by 10
                } else if (b.bubbleTimer < 20) {
                    b.bubbleWidth--;
                    width = b.bubbleWidth;
                    b.bubbleTimer++;
                } else
                    b.bubbleTimer = 0; // Resets loop
            } else // Different type of occilation for bottom light bubble
            {
                // Increases by 5
                if (b.bubbleTimer < 5) {
                    b.bubbleWidth++;
                    width = b.bubbleWidth;
                    b.bubbleTimer++;
                    // Noting
                } else if (b.bubbleTimer < 10) {
                    b.bubbleTimer++;
                    // Decreases by 5
                } else if (b.bubbleTimer < 15) {
                    b.bubbleWidth--;
                    width = b.bubbleWidth;
                    b.bubbleTimer++;
                    // Nothing
                } else if (b.bubbleTimer < 20) {
                    b.bubbleTimer++;
                } else
                    b.bubbleTimer = 0; // Resets loop
            }
            // New Height
            int height = b.total - width;

            this.colorChange(rainbowActive); // Changes color (different if rainbow is active)
            this.move(movedX, movedY, width, height, speed, i, b.moveTimer, b.bubbleTimer, b.offSet, b.total, b.light,
                    c); // See Below
        }
    }

    // Makes a new bubble at the updated location, with a new size, and new color
    // (can be altered for future use such as merging)
    public void move(int newX, int newY, int newWidth, int newHeight, int speed3, int index, int mTimer, int bTimer,
            int o, int wh, boolean isLight, Color bColor) {
        display.bubbles.set(index,
                new Bubble(newX, newY, newWidth, newHeight, speed3, mTimer, bTimer, o, wh, isLight, bColor));
    }

    // If rainbow is NOT active: Changes color depending on sliders
    // If rainbow is active: Changes color depending on rainbow loop
    public void colorChange(boolean rainbow) {
        if (rainbow == false) {
            int R = colorR.getValue() / 2;
            int G = colorG.getValue() / 2;
            int B = colorB.getValue() / 2;
            c = new Color(R, G, B);
        }
        // Changes color depending on rainbowTimer R, G, B increase or decrease
        // occording to pattern for rainbow movement (can be visualized using google
        // color picker)
        else if (rainbow == true) {
            if (rainbowTimer < 255) {
                green++;
                rainbowTimer++;
            } else if (rainbowTimer < 510) {
                red--;
                rainbowTimer++;
            } else if (rainbowTimer < 765) {
                blue++;
                rainbowTimer++;
            } else if (rainbowTimer < 1020) {
                green--;
                rainbowTimer++;
            } else if (rainbowTimer < 1275) {
                red++;
                rainbowTimer++;
            } else if (rainbowTimer < 1530) {
                blue--;
                rainbowTimer++;
            } else {
                rainbowTimer = 0;
            }
            c = new Color(red, green, blue);
        }
    }
}
