
// Last updated: 05/10/23

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
/* Fighter */

public class Fighter {

    public static final int HEAD = 0;
    public static final int BODY = 1;
    public static final int FIST_L = 2;
    public static final int FIST_R = 3;
    public static final int LEG = 4;
    public static final int HAIR = 5;

    private int strength, health, speed, fame, money;
    private Rectangle[] fighterRect;
    private final Point[] partOffset;
    private boolean direction;

    /* Constructor
     */
    public Fighter() {
        
        strength = health = speed = 100; // Default values mean the fighter is at max stats
        fame = money = 0; // Default values mean the fighter is broke

        fighterRect = new Rectangle[]{
            new Rectangle(12, -24, 24, 24), // Head
            new Rectangle(0, 0, 48, 48), // Body
            new Rectangle(-10, -10, 16, 16), // Left fist (WIP) (for now just using this fist)
            new Rectangle(-10, -10, 16, 16), // Right fist (WIP)
            new Rectangle(0, 24, 48, 24), // Leg
            new Rectangle(12, -24, 24, 6) // Hair
        };

        partOffset = new Point[fighterRect.length];
        for (int i1 = 0, n1 = fighterRect.length; i1 < n1; i1 ++) {
            partOffset[i1] = fighterRect[i1].getLocation();
        }
    }

  // Some functions
    public Dimension getPartSize(int part) {
        return fighterRect[part].getSize();
    }

    public Point getPartLocation(int part) {
        return fighterRect[part].getLocation();
    }

    public Point getPartOffset(int part) {
        return partOffset[part];
    }

    public Rectangle getPartRectangle(int part) {
        return fighterRect[part];
    }

    public void setFighterLocation(Integer x, Integer y) {
        for (int i1 = 0, n1 = fighterRect.length; i1 < n1; i1 ++) {
            if (x != null) fighterRect[i1].x = x + partOffset[i1].x;
            if (y != null) fighterRect[i1].y = y + partOffset[i1].y;
        }
    }

    public void changeFighterLocation(int disX, int disY) {
        for (int i1 = 0, n1 = fighterRect.length; i1 < n1; i1 ++) {
            fighterRect[i1].x += disX;
            fighterRect[i1].y += disY;
        }
    }

    public int getStrength() {
        return strength;
    }

    public int getHealth() {
        return health;
    }

    public int getFame() {
        return fame;
    }

    public int getMoney() {
        return money;
    }

    public int getSpeed() {
        return speed;
    }


    //! dont use these methods
    public int getFistHeight(){
        return fighterRect[Fighter.FIST_L].height;
    }

    public int getFistWidth(){
        return fighterRect[Fighter.FIST_L].width;
    }

    public int getLegWidth(){
        return fighterRect[Fighter.LEG].width;
    }
    public int getLegHeight(){
        return fighterRect[Fighter.LEG].height;
    }

    public int getHeadWidth(){
        return fighterRect[Fighter.HEAD].width;
    }
    public int getHeadHeight(){
        return fighterRect[Fighter.HEAD].height;
    }

    public int getBodyWidth(){
        return fighterRect[Fighter.BODY].width;
    }
    public int getBodyHeight(){
        return fighterRect[Fighter.BODY].height;
    }
    public int getHairWidth(){
        return fighterRect[Fighter.HAIR].width;
    }

    public int getHairHeight(){
        return fighterRect[Fighter.HAIR].height;
    }
    // Movement/positioning
    public int getHeadPositionX(){
        return fighterRect[Fighter.HEAD].x;
    }
    public int getHeadPositionY(){
        return fighterRect[Fighter.HEAD].y;
    }
    public int getBodyPositionX(){
        return fighterRect[Fighter.BODY].x;
    }
    public int getBodyPositionY(){
        return fighterRect[Fighter.BODY].y;
    }
    public int getFistPositionX(){
        return fighterRect[Fighter.FIST_L].x;
    }
    public int getFistPositionY(){
        return fighterRect[Fighter.FIST_L].y;
    }
    public int getLegPositionX(){
        return fighterRect[Fighter.LEG].x;
    }
    public int getLegPositionY(){
        return fighterRect[Fighter.LEG].y;
    }
    public int getHairPositionX(){
        return fighterRect[Fighter.HAIR].x;
    }
    public int getHairPositionY(){
        return fighterRect[Fighter.HAIR].y;
    }

    // Set methods
    public void setHeadPositionX(int headPositionX){
        fighterRect[Fighter.HEAD].x = headPositionX;
    }
    public void setHeadPositionY(int headPositionY){
        fighterRect[Fighter.HEAD].y = headPositionY;
    }
    public void setBodyPositionX(int bodyPositionX){
        fighterRect[Fighter.BODY].x = bodyPositionX;
    }
    public void setBodyPositionY(int bodyPositionY){
        fighterRect[Fighter.BODY].y = bodyPositionY;
    }
    public void setFistPositionX(int fistPositionX){
        fighterRect[Fighter.FIST_L].x = fistPositionX;
    }
    public void setFistPositionY(int fistPositionY){
        fighterRect[Fighter.FIST_L].y = fistPositionY;
    }
    public void setLegPositionX(int legPositionX){
        fighterRect[Fighter.LEG].x = legPositionX;
    }
    public void setLegPositionY(int legPositionY){
        fighterRect[Fighter.LEG].y = legPositionY;
    }
    public void setHairPositionX(int hairPositionX){
        fighterRect[Fighter.HAIR].x = hairPositionX;
    }
    public void setHairPositionY(int hairPositionY){
        fighterRect[Fighter.HAIR].y = hairPositionY;
    }

    // Some change methods
    public void changeHeadPositionX(int deltaHeadPositionX){
        fighterRect[Fighter.HEAD].x += deltaHeadPositionX;
    }
    public void changeHeadPositionY(int deltaHeadPositionY){
        fighterRect[Fighter.HEAD].y += deltaHeadPositionY;
    }
    public void changeBodyPositionX(int deltaBodyPositionX){
        fighterRect[Fighter.BODY].x += deltaBodyPositionX;
    }
    public void changeBodyPositionY(int deltaBodyPositionY){
        fighterRect[Fighter.BODY].y += deltaBodyPositionY;
    }
    public void changeFistPositionX(int deltaFistPositionX){
        fighterRect[Fighter.FIST_L].x += deltaFistPositionX;
    }
    public void changeFistPositionY(int deltaFistPositionY){
        fighterRect[Fighter.FIST_L].y += deltaFistPositionY;
    }
    public void changeLegPositionX(int deltaLegPositionX){
        fighterRect[Fighter.LEG].x += deltaLegPositionX;
    }
    public void changeLegPositionY(int deltaLegPositionY){
        fighterRect[Fighter.LEG].y += deltaLegPositionY;
    }
    public void changeHairPositionX(int deltaHairPositionX){
        fighterRect[Fighter.HAIR].x += deltaHairPositionX;
    }
    public void changeHairPositionY(int deltaHairPositionY){
        fighterRect[Fighter.HAIR].y += deltaHairPositionY;
    }  
}