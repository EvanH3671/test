// Last updated: 05/09/23

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/** Character Creator */
public class CharacterCreatorCardContainer extends JPanel {
    private CardLayout layout;
    
    private Color[] outfitColours;
    private Integer saveFileIndex;

    public CharacterCreatorCardContainer(int saveFileIndex) {
        layout = new CardLayout();
        setLayout(layout);
        setSize(GameWindow.getDimension());

        add(new AppearanceSelectPanel(), "Appearance Select");
        add(new NameSelectPanel(), "Name Select");

        outfitColours = new Color[4];

        this.saveFileIndex = saveFileIndex;
    }

    // public static void setFileIndex(int index) {
    //     saveFileIndex = index;
    // }

    // gonna be different than menu one at some point (maybe)
    public static JButton createBackButton() {
        JButton button = new JButton("< Back");
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setMargin(new Insets(2, 0, 2, 0));
        
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);

        return button;
    }

    private static JButton createNextButton() {
        JButton button = new JButton("Next");

        button.setBounds(15, 15, 50, 20);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }
    
    public class AppearanceSelectPanel extends JPanel implements ActionListener {
        private JButton backButton, nextButton;
        private JRadioButton[] gloveColourButtons, pantsColourButtons, skinColourButtons, hairColourButtons;
        private ButtonGroup gloveButtons, pantsButtons, skinButtons, hairButtons;

        private HashMap<Object, Runnable> actionList;

        public AppearanceSelectPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            addHeaderPanel();
            add(Box.createVerticalStrut(20));
            addBodyPanel();

            nextButton = createNextButton();
            nextButton.addActionListener(this);
            add(nextButton);

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
    
            JLabel titleLabel = new JLabel("Choose your Appearance", SwingConstants.CENTER);
            titleLabel.setLocation(headerPane.getLocation());
            titleLabel.setSize(headerPanelSize);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
            headerPane.add(titleLabel, 1);
            headerPanel.add(headerPane); 

            add(headerPanel);
        }

        private void addBodyPanel() {
            Dimension bodyPanelSize = new Dimension(720, 350);

            JPanel bodyPanel = new JPanel();
            bodyPanel.setMaximumSize(bodyPanelSize);
            bodyPanel.setMinimumSize(bodyPanelSize);
            bodyPanel.setPreferredSize(bodyPanelSize);

            bodyPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2)); //TEMP
            bodyPanel.setLayout(new GridBagLayout());

            //TODO make this not suck
            // -> Gloves
            JPanel gloveButtonPanel = createSelectionPanel("Glove Colour", 200);

            gloveColourButtons = createRadioButtons(Appearance.getOutfitColourList());
            gloveButtons = new ButtonGroup();

            for (JRadioButton button : gloveColourButtons) {
                gloveButtons.add(button);
                gloveButtonPanel.add(button);
            }

            bodyPanel.add(gloveButtonPanel, gridLocation(0, 0));

            // -> Pants
            JPanel pantsButtonPanel = createSelectionPanel("Pants Colour", 200);

            pantsColourButtons = createRadioButtons(Appearance.getOutfitColourList());
            pantsButtons = new ButtonGroup();

            for (JRadioButton button : pantsColourButtons) {
                pantsButtons.add(button);
                pantsButtonPanel.add(button);
            }

            bodyPanel.add(pantsButtonPanel, gridLocation(2, 0));

            //-> Skin
            JPanel skinButtonPanel = createSelectionPanel("Skin Tone", 150);

            skinColourButtons = createRadioButtons(Appearance.getSkinColourList());
            skinButtons = new ButtonGroup();

            for (JRadioButton button : skinColourButtons) {
                skinButtons.add(button);
                skinButtonPanel.add(button);
            }

            bodyPanel.add(skinButtonPanel, gridLocation(0, 1));

            //-> Hair
            JPanel hairButtonPanel = createSelectionPanel("Hair Colour", 150);

            hairColourButtons = createRadioButtons(Appearance.getHairColourList());
            hairButtons = new ButtonGroup();

            for (JRadioButton button : hairColourButtons) {
                hairButtons.add(button);
                hairButtonPanel.add(button);
            }

            bodyPanel.add(hairButtonPanel, gridLocation(2, 1));

            //-> Character
            Dimension characterSize = new Dimension(240, 350);

            JPanel characterPanel = new JPanel();
            characterPanel.setMaximumSize(characterSize);
            characterPanel.setMinimumSize(characterSize);
            characterPanel.setPreferredSize(characterSize);

            characterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            GridBagConstraints characterConstraints = new GridBagConstraints(
                1, 0, 1, 2, 1, 1, 
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
                new Insets(0, 0, 0, 0), 0, 0
            );
            bodyPanel.add(characterPanel, characterConstraints);

            add(bodyPanel);
        }

        private JPanel createSelectionPanel(String title, int height) {
            Dimension panelSize = new Dimension(200, height);

            JPanel panel = new JPanel();
            panel.setMaximumSize(panelSize);
            panel.setMinimumSize(panelSize);
            panel.setPreferredSize(panelSize);
            panel.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 0), 3));

            Dimension labelSize = new Dimension(200, 25);

            JLabel label = new JLabel(title, SwingConstants.CENTER);
            label.setMaximumSize(labelSize);
            label.setMinimumSize(labelSize);
            label.setPreferredSize(labelSize);

            label.setFont(new Font("Arial", Font.PLAIN, 17));
            panel.add(label);

            return panel;
        }

        private GridBagConstraints gridLocation(int gridx, int gridy) {
            return new GridBagConstraints(
                gridx, gridy, 1, 1, 0, 0, 
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 
                new Insets(0, 0, 0, 0), 0, 0
            );
        }

        private JRadioButton[] createRadioButtons(Color[] possibleColours) {
            Dimension colourButtonSize = new Dimension(25, 25);
            Icon deselectedIcon = new ImageIcon("Images/colourNotSelected.png");
            Icon selectedIcon = new ImageIcon("Images/colourSelected.png");
            int numOfButtons = possibleColours.length;

            JRadioButton[] buttons = new JRadioButton[numOfButtons];
            for (int i1 = 0; i1 < numOfButtons; i1 ++) {
                buttons[i1] = new JRadioButton();
                buttons[i1].setMaximumSize(colourButtonSize);
                buttons[i1].setMinimumSize(colourButtonSize);
                buttons[i1].setPreferredSize(colourButtonSize);

                buttons[i1].setBackground(possibleColours[i1]);
                buttons[i1].setIcon(deselectedIcon);
                buttons[i1].setSelectedIcon(selectedIcon);
                buttons[i1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                buttons[i1].setBorder(null);

                buttons[i1].addActionListener(this);
            }

            return buttons;
        }

        private void createActionList() {
            actionList = new HashMap<>();

            actionList.put(backButton, () -> {
                GameWindow frame = ((GameWindow) CharacterCreatorCardContainer.this.getParent().getParent().getParent().getParent());
                frame.returnToMenu();
            });

            actionList.put(nextButton, () -> {

                for (Color colour : outfitColours) {
                    if (colour == null) {
                        Popup.invalidInput("One or more colours has not been selected.");
                        return;
                    }
                }

                layout.next(CharacterCreatorCardContainer.this);
            });

            for (int i1 = 0, n1 = gloveColourButtons.length; i1 < n1; i1 ++) {
                final int index = i1;

                actionList.put(gloveColourButtons[i1], () -> {
                    outfitColours[0] = gloveColourButtons[index].getBackground();
                });
            }

            for (int i1 = 0, n1 = pantsColourButtons.length; i1 < n1; i1 ++) {
                final int index = i1;

                actionList.put(pantsColourButtons[i1], () -> {
                    outfitColours[1] = pantsColourButtons[index].getBackground();
                });
            }

            for (int i1 = 0, n1 = skinColourButtons.length; i1 < n1; i1 ++) {
                final int index = i1;

                actionList.put(skinColourButtons[i1], () -> {
                    outfitColours[2] = skinColourButtons[index].getBackground();
                });
            }

            for (int i1 = 0, n1 = hairColourButtons.length; i1 < n1; i1 ++) {
                final int index = i1;

                actionList.put(hairColourButtons[i1], () -> {
                    outfitColours[3] = hairColourButtons[index].getBackground();
                });
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionList.get(e.getSource()).run();
        }
    }

    public class NameSelectPanel extends JPanel implements ActionListener {
        private JButton backButton, nextButton;
        private JComboBox<String> firstNameCBox, lastNameCBox;

        private HashMap<Object, Runnable> actionList;

        public NameSelectPanel() {
            backButton = createBackButton();
            nextButton = createNextButton();

            backButton.addActionListener(this);
            nextButton.addActionListener(this);

            firstNameCBox = new JComboBox<String>(Appearance.getFirstNameList());
            lastNameCBox = new JComboBox<String>(Appearance.getLastNameList());

            this.add(backButton);
            this.add(nextButton);
            this.add(firstNameCBox);
            this.add(lastNameCBox);

            createActionList();
        }

        private void createActionList() {
            actionList = new HashMap<>();

            actionList.put(backButton, () -> {
                layout.previous(CharacterCreatorCardContainer.this);
            });

            actionList.put(nextButton, () -> {
                // //TEMP all of this is temporary
                Appearance playerAppearance = new Appearance(
                    (String) firstNameCBox.getSelectedItem(), 
                    (String) lastNameCBox.getSelectedItem(), 
                    outfitColours
                );

                if (Popup.confirmation("Are you sure you want to create a new save?")) {
                    PlayerStats stats = new PlayerStats(playerAppearance);

                    try {
                        SaveManager.addFile(saveFileIndex);

                        SaveManager.setFileData(stats, saveFileIndex);
                        GameWindow frame = (GameWindow) CharacterCreatorCardContainer.this.getParent().getParent().getParent().getParent();
                        frame.newGamePanel(saveFileIndex);

                        saveFileIndex = null;
                    } catch (FileAlreadyExistsException faee) {
                        System.out.println("Invalid saveFileIndex.\n");

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            actionList.get(e.getSource()).run();
        }
    }

}