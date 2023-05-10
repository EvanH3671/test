// Last updated: 05/07/23

import javax.swing.JOptionPane;

/** Class for Popups which appear outside of the main window. */
public class Popup {

    public static boolean confirmation(String text) {
        int response = JOptionPane.showConfirmDialog(
            null, text, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
        );
        return response == 0 ? true : false;
    }

    public static void invalidInput(String text) {
        JOptionPane.showMessageDialog(null, text, "okay", JOptionPane.WARNING_MESSAGE);
    }
} 