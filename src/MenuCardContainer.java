// Last updated: 05/09/23

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**Class for the main menu panel, which uses a card layout to cycle between different panels. This panel is displayed
 * as soon as the code starts running. */
public final class MenuCardContainer extends JPanel {

    /**The {@code CardLayout} used by this {@code JPanel}.
     * 
     * @see java.awt.CardLayout
     */
    private CardLayout layout;

    /**Creates a new {@code MenuCardContainer}, which has 3 JPanels attached to it. It has a size equal to the size
     * of the JFrame it is contained in as uses a CardLayout.
     */
    public MenuCardContainer() {
        layout = new CardLayout();
        setLayout(layout);
        setSize(GameWindow.getDimension());

        add(new MainMenuPanel(), "Main Menu");
        add(new SettingsPanel(), "Settings");
        add(new SaveSelectPanel(), "Save Select");
    }

    /**Switches the panel which is currently visible.
     * 
     * @param name String containing the name of the chosen panel, which is defined in the constructor.
     * @see {@link #MenuCardContainer()}
     */
    public void switchPanel(String name) {
        layout.show(this, name);
    }

    /**Creates a generic back button. Functionality is not added with this method.
     * 
     * @return A JButton which looks like a back button.
     */
    public static JButton createBackButton() {
        JButton button = new JButton("< Back"); // Probably gonna use an image or something to make the arrow eventually
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setMargin(new Insets(2, 0, 2, 0));
        
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);

        return button;
    }

    /* Class for the main menu panel. */
    private final class MainMenuPanel extends JPanel implements ActionListener {
        private JButton playButton, settingsButton, exitButton;

        private HashMap<Object, Runnable> actionList;
    
        public MainMenuPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            Dimension titleSize = new Dimension(750, 90);

            JLabel titleLabel = new JLabel("WWE© 2K23© | EA Sports©", SwingConstants.CENTER);
            titleLabel.setMaximumSize(titleSize);
            titleLabel.setMinimumSize(titleSize);
            titleLabel.setPreferredSize(titleSize);
            
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 55)); // change fonts later obviously
            add(titleLabel);
            add(Box.createRigidArea(new Dimension(0, 220)));
            
            playButton = newMenuButton("Play");
            add(playButton);
            add(Box.createRigidArea(new Dimension(0, 12)));

            settingsButton = newMenuButton("Settings");
            add(settingsButton);
            add(Box.createRigidArea(new Dimension(0, 12)));

            exitButton = newMenuButton("Exit");
            add(exitButton);

            createActionList();
        }

        /* Creats a generic button to be used in the menu.
         * Input: text - Text which is displayed on the button.
         * Output: A new JButton.
         */
        private JButton newMenuButton(String text) {
            Dimension buttonSize = new Dimension(180, 35);
    
            JButton button = new JButton(text);
            button.setMaximumSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            button.setFont(new Font("Josefin Sans Regular", Font.PLAIN, 20));
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(4, 0, 0, 0)
            ));
    
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setFocusable(false);
            button.addActionListener(this);
    
            return button;
        }


        private void createActionList() {
            actionList = new HashMap<Object, Runnable>();

            actionList.put(playButton, () ->  {
                MenuCardContainer.this.switchPanel("Save Select");
            });
    
            actionList.put(settingsButton, () -> {
                MenuCardContainer.this.switchPanel("Settings");
            });
    
            actionList.put(exitButton, () -> {
                if (Popup.confirmation("Are you sure you want to exit?")) {
                    System.exit(0);
                }
            });
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            actionList.get(e.getSource()).run();
        }
    }
    
    private final class SettingsPanel extends JPanel implements ActionListener, ChangeListener {
        private JButton backButton;
        private JLabel testText;
        private JSlider testSlider;

        private HashMap<Object, Runnable> actionList;
    
        public SettingsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            addHeaderPanel();
            addBodyPanel();

            createActionList();
        }

        private void addHeaderPanel() {
            Dimension headerPanelSize = new Dimension(750, 80);

            JPanel headerPanel = new JPanel(null);
            headerPanel.setMaximumSize(headerPanelSize);
            headerPanel.setMinimumSize(headerPanelSize);
            headerPanel.setPreferredSize(headerPanelSize);

            JLayeredPane headerPane = new JLayeredPane();
            headerPane.setSize(750, 80);
            headerPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPane.setAlignmentY(Component.TOP_ALIGNMENT);

            backButton = createBackButton();
            backButton.setLocation((int) headerPane.getLocation().getX() + 15, (int) headerPane.getLocation().getY() + 15);
            backButton.setSize(50, 15);
            backButton.addActionListener(this);
            headerPane.add(backButton, 2);     
    
            JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
            titleLabel.setLocation(headerPane.getLocation());
            titleLabel.setSize(headerPanelSize);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
            headerPane.add(titleLabel, 1);
            headerPanel.add(headerPane);       
            
            add(headerPanel); 
        }

        private void addBodyPanel() {
            Dimension bodyPanelSize = new Dimension(750, 495);

            JPanel bodyPanel = new JPanel(new GridBagLayout());
            bodyPanel.setMaximumSize(bodyPanelSize);
            bodyPanel.setMinimumSize(bodyPanelSize);
            bodyPanel.setPreferredSize(bodyPanelSize);

            SettingsRecord settingsValues = null;
            try {
                settingsValues = SaveManager.getFileData(3);
            } catch (NoSuchFileException nsfe) {
                nsfe.printStackTrace();
            }

            testSlider = new JSlider(0, 100);
            testSlider.setSize(new Dimension(250, 50));

            testSlider.setOrientation(SwingConstants.HORIZONTAL);
            testSlider.setMinorTickSpacing(5);
            testSlider.setMajorTickSpacing(25);

            testSlider.setValue(settingsValues.number());
            testSlider.addChangeListener(this);
    
            testText = new JLabel("Number: " + testSlider.getValue());
    
            bodyPanel.add(testSlider);
            bodyPanel.add(testText);

            add(bodyPanel);
        }

        private void createActionList() {
            actionList = new HashMap<>();

            actionList.put(backButton, () -> {
                // Saving settings
                SettingsRecord settings = new SettingsRecord(testSlider.getValue());

                try {
                    SaveManager.setFileData(settings, 3);
                } catch (NoSuchFileException nsfe) {
                    nsfe.printStackTrace();
                }
                
                MenuCardContainer.this.switchPanel("Main Menu");
            });

            actionList.put(testSlider, () -> {
                testText.setText("Number: " + testSlider.getValue());
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionList.get(e.getSource()).run();
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            actionList.get(e.getSource()).run();
        }
    }

    /**Creates the panel used by the user to select a save file to load. Allows the user to create a new save if the
     * save doesn't exist, as well as delete existing save files.
     * Attributes:
     *     individualSavePanels - 
     *     newGameButtons - Buttons which allow the user to create a new save file. This button will appear in each 
     *       panel which does not have a corresponding save file.
     *     continueGameButtons - Buttons which allow the user to continue playing on an already-existing save file. 
     *       This button will appear in each panel which has a corresponding save file.
     *     deleteSaveButtons - Buttons which allow the user to delete their save. This button will appear in each panel 
     *       which has a corresponding save file.
     *     characterNames - Labels which display the name of the player stored in it's corresponding save file. This label
     *       whill appear in each panel which has a corresponding save file.
     *     backButton - Button which allows the user to go back to the main menu.
     *     actionList - A HashMap used by objects the user can interact with an ActionListener. Each key is an object,
     *       and the values are runnables.
     */
    private final class SaveSelectPanel extends JPanel implements ActionListener {
        /**Panels which house each set of compoments for one save file.
         * 
         */
        private JPanel[] individualSavePanels;
        private JButton[] newGameButtons, continueGameButtons, deleteSaveButtons;
        private JLabel[] characterNames;
        private JButton backButton;

        private HashMap<Object, Runnable> actionList;
        
        /* Constructor method with creates the save selection panel.
         * Output: The save selection panel.
         */
        public SaveSelectPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            addHeaderPanel();
            add(Box.createVerticalStrut(45));
            addBodyPanel();

            createActionList();
        }

        private void addHeaderPanel() {
            Dimension headerPanelSize = new Dimension(750, 80);

            JPanel headerPanel = new JPanel(null);
            headerPanel.setMaximumSize(headerPanelSize);
            headerPanel.setMinimumSize(headerPanelSize);
            headerPanel.setPreferredSize(headerPanelSize);

            JLayeredPane headerPane = new JLayeredPane();
            headerPane.setSize(750, 80);
            headerPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPane.setAlignmentY(Component.TOP_ALIGNMENT);

            backButton = createBackButton();
            backButton.setLocation((int) headerPane.getLocation().getX() + 15, (int) headerPane.getLocation().getY() + 15);
            backButton.setSize(50, 15);
            backButton.addActionListener(this);
            headerPane.add(backButton, 2);            

            JLabel titleLabel = new JLabel("Select a Save", SwingConstants.CENTER);
            titleLabel.setLocation(headerPane.getLocation());
            titleLabel.setSize(headerPanelSize);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
            headerPane.add(titleLabel, 1);

            headerPanel.add(headerPane);       
            
            add(headerPanel);          
        }

        private void addBodyPanel() {
            final int numOfSaves = 3;
            final int strutSize = 15;

            JPanel bodyPanel = new JPanel();
            bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
            bodyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            bodyPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            
            newGameButtons = new JButton[numOfSaves];
            continueGameButtons = new JButton[numOfSaves];
            deleteSaveButtons = new JButton[numOfSaves];
            characterNames = new JLabel[numOfSaves];
            individualSavePanels = new JPanel[numOfSaves];

            individualSavePanels[0] = createSavePanel(0);
            bodyPanel.add(individualSavePanels[0]);
            bodyPanel.add(Box.createHorizontalStrut(strutSize));

            individualSavePanels[1] = createSavePanel(1);
            bodyPanel.add(individualSavePanels[1]);
            bodyPanel.add(Box.createHorizontalStrut(strutSize));

            individualSavePanels[2] = createSavePanel(2);
            bodyPanel.add(individualSavePanels[2]);
            
            Dimension bodyPanelSize = new Dimension(
                individualSavePanels[0].getPreferredSize().width * numOfSaves + strutSize * (numOfSaves - 1), 
                individualSavePanels[0].getPreferredSize().height
            );

            bodyPanel.setMaximumSize(bodyPanelSize);
            bodyPanel.setMinimumSize(bodyPanelSize);
            bodyPanel.setPreferredSize(bodyPanelSize);

            add(bodyPanel);
        }

        /* Creates the actionList HashMap, which is used to link each button to an action.
         */
        private void createActionList() {
            actionList = new HashMap<>();
            int numOfSaves = individualSavePanels.length;

            actionList.put(backButton, () -> {
                MenuCardContainer.this.switchPanel("Main Menu");
            });

            for (int i1 = 0; i1 < numOfSaves; i1 ++) {
                final int index = i1;

                if (newGameButtons[i1] != null) {
                    actionList.put(newGameButtons[i1], () -> {
                        /* Initializing frame outside of this causes an error since it'd be called before the constructor
                           finishes running. */
                        GameWindow frame = (GameWindow) MenuCardContainer.this.getParent().getParent().getParent().getParent();
                        frame.newCharacterCreator(index);
                        // frame.displayPanel(GameWindow.CardPanel.CHARACTER_CREATOR, index);
                    });
                }

                if (continueGameButtons[i1] != null) {
                    actionList.put(continueGameButtons[i1], () -> {
                        Appearance player = null;
                        try {
                            player = ((PlayerStats) SaveManager.getFileData(index)).appearance();
                        } catch (NoSuchFileException nsfe) {
                            nsfe.printStackTrace();
                        }

                        System.out.println("Character Name: " + player.getFirstName() + " " + player.getLastName());

                        GameWindow frame = (GameWindow) MenuCardContainer.this.getParent().getParent().getParent().getParent();
                        frame.newGamePanel(index);
                    });
                }

                if (deleteSaveButtons[i1] != null) {
                    actionList.put(deleteSaveButtons[index], () -> {
                        if (!Popup.confirmation("Are you sure you want to delete this save?")) return;

                        try {
                            SaveManager.removeFile(index);
                        } catch (NoSuchFileException nsfe) {
                            nsfe.printStackTrace();
                        }

                        MenuCardContainer.this.remove(SaveSelectPanel.this);
                        MenuCardContainer.this.add(new SaveSelectPanel(), "Save Select");
                    });
                }
            }
        }

        /* Checks if the save file for a JPanel exists, and calls the method to create the needed JPanel.
         * Input: fileIndex - 
         * Output: A new JPanel object.
         */
        private JPanel createSavePanel(int fileIndex) {
            return SaveManager.isPath(fileIndex) ? createLoadSavePanel(fileIndex) : createNewSavePanel(fileIndex);
        }

        /* Creates a JPanel which allows the user to create a new save.
         * Input: fileIndex - 
         * Output: A new JPanel object.
         */
        private JPanel createNewSavePanel(int fileIndex) {
            Dimension panelSize = new Dimension(150, 350);

            JPanel panel = new JPanel();
            panel.setMinimumSize(panelSize);
            panel.setMaximumSize(panelSize);
            panel.setPreferredSize(panelSize);

            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

            newGameButtons[fileIndex] = new JButton("New Game");
            newGameButtons[fileIndex].addActionListener(this);
            panel.add(newGameButtons[fileIndex]);

            return panel;
        }

        /* Creates a JPanel for an already existing save.
         * Input: fileIndex - 
         * Output: A new JPanel object.
         */
        private JPanel createLoadSavePanel(int fileIndex) {
            Dimension panelSize = new Dimension(150, 350);
            
            JPanel panel = new JPanel();
            panel.setMaximumSize(panelSize);
            panel.setMinimumSize(panelSize);
            panel.setPreferredSize(panelSize);

            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

            // Gonna be player (fighter) object later
            Appearance player = null;
            try {
                player = ((PlayerStats) SaveManager.getFileData(fileIndex)).appearance();
            } catch (NoSuchFileException nsfe) {
                nsfe.printStackTrace();
            }

            characterNames[fileIndex] = new JLabel(player.getFirstName() + " " + player.getLastName());
            characterNames[fileIndex].setFont(new Font("Arial", Font.PLAIN, 18));
            panel.add(characterNames[fileIndex]);

            continueGameButtons[fileIndex] = new JButton("Continue Game");
            continueGameButtons[fileIndex].addActionListener(this);
            panel.add(continueGameButtons[fileIndex]);

            deleteSaveButtons[fileIndex] = new JButton("Delete Save");
            deleteSaveButtons[fileIndex].addActionListener(this);
            panel.add(deleteSaveButtons[fileIndex]);

            return panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionList.get(e.getSource()).run();
        }
    }
}