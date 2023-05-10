// Last updated: 05/07/23

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

/* This class is used to set the appearance of the player. */
public class Appearance implements Serializable {

    private static final String[] firstNameList = {
        "Daniel", "Evan", "Tian"
    };
    private static final String[] lastNameList = {
        "Chen", "Cheng", "Hollander", "Leung"
    };
    private static final Color[] outfitColourList = {
        Color.BLACK, Color.WHITE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, 
        Color.GRAY, Color.MAGENTA, new Color(25, 67, 120), new Color(210, 165, 12)
    };
  
    private static final Color[] skinColourList = {
        new Color(236, 188, 180), new Color(84, 51, 40),  new Color(89, 47, 42),
        new Color(161, 102, 94), new Color(197, 140, 133), Color.RED, Color.GREEN
    };
  
    private static final Color[] hairColourList = {
        new Color(240, 226, 182), new Color(84, 51, 40), Color.BLACK, Color.RED, Color.GREEN
    };
  
    private static final Random rand = new Random();

    private final String firstName, lastName;
    private Color[] outfit;

    /* Constructor. */
    public Appearance(String firstName, String lastName, Color[] outfit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.outfit = outfit;
    }

    public Appearance() {
        this(
            firstNameList[rand.nextInt(3)], 
            lastNameList[rand.nextInt(4)], 
            getRandomizedAppearance()
        );
    }

    public static String[] getFirstNameList() {
        return firstNameList;
    }

    public static String[] getLastNameList() {
        return lastNameList;
    }

    public static Color[] getOutfitColourList() {
        return outfitColourList;
    }

    public static Color[] getSkinColourList() {
        return skinColourList;
    }

    public static Color[] getHairColourList() {
        return hairColourList;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Some more get functions
    public Color getGlovesColor(){
        return outfit[0];
    }
    public Color getShortsColor(){
        return outfit[1];
    }
    public Color getSkinColor(){
        return outfit[2];
    }
    public Color getHairColor(){
        return outfit[3];
    }

    /* This method is used to randomize the opponent's appearance. */
    public static Color[] getRandomizedAppearance() {
        return new Color[]{
            outfitColourList[rand.nextInt(outfitColourList.length)],
            outfitColourList[rand.nextInt(outfitColourList.length)],
            skinColourList[rand.nextInt(skinColourList.length)],
            hairColourList[rand.nextInt(hairColourList.length)]
        };
    }

    public void setOutfit(Color[] outfit){
        this.outfit = outfit;
    }

    /* Gets outfit. */
    public Color[] getOutfit(){
        return outfit;
    }
}