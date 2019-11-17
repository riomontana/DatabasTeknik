package helpers;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window {

    public static void center(JFrame frame) {
        Dimension window = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((window.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((window.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
