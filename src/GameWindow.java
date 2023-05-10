// Last updated: 05/09/23

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/** Window */
public final class GameWindow extends JFrame {
    private final Component menu;
    private Component activeComponent;

    public GameWindow() {
        setSize(GameWindow.getDimension());
        setResizable(false);
        setTitle("WWE© 2K23© | EA Sports©");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        SaveManager.createSaveDirectory(); //! code breaks if card containers are initialized before this method runs

        menu = new MenuCardContainer();
        add(menu);
        activeComponent = menu;

        addWindowListener(new WindowAdapter() {
            
            public void windowClosing(WindowEvent e) {
                if (!Popup.confirmation("Are you sure you want to exit? Your data will not be saved.")) return;

                System.exit(0);
            }
        });

        setVisible(true);
    }

    /**Returns a {@code Dimension} object representing the dimnesions of the active {@code JFrame}.
     * @see java.awt.Dimension
     */
    public static Dimension getDimension() {
        return new Dimension(768, 576);
    }

    public void returnToMenu() {
        replaceActiveComponent(menu);
    }

    public void newCharacterCreator(int fileIndex) {
        replaceActiveComponent(new CharacterCreatorCardContainer(fileIndex));
    }

    public void newGamePanel(int fileIndex) {
        GamePanel gamePanel = new GamePanel(fileIndex);
        replaceActiveComponent(gamePanel);

        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }

    private void replaceActiveComponent(Component newActiveComponent) {
        remove(activeComponent);
        activeComponent = newActiveComponent;
        add(activeComponent);

        repaint(); // very important!!!
        revalidate();
    }
}