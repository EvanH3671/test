// Last updated: 05/06/23

/* This class is used for creating hitboxes of objects and characters
 */
public class Hitbox {

    // Setting length and height of fist, body, head, and leg.
    public int fistLength, fistHeight, bodyHeight, bodyLength, headHeight, headLength, legHeight, legLength, 
               fistPositionX, fistPositionY, bodyPositionX, bodyPositionY, headPositionX, headPositionY, legPositionX, legPositionY;
    Hitbox (Fighter fighter, Direction fighterDirection) {

        fistLength = 16;
        fistHeight = 16;
        bodyHeight = 24;
        bodyLength = 48;
        headHeight = 24;
        headLength = 24;
        legHeight = 24;
        legLength = 48;

        // Finding coordinates
        fistPositionX = fighterDirection.isDirectionLeft() ? fighter.getFistPositionX() : fighter.getFistPositionX() + 58;
        fistPositionY = fighter.getFistPositionY();
        bodyPositionX = fighter.getBodyPositionX();
        bodyPositionY = fighter.getBodyPositionY();
        headPositionX = fighter.getHeadPositionX();
        headPositionY = fighter.getHeadPositionY();
        legPositionX = fighter.getLegPositionX();
        legPositionY = fighter.getLegPositionY();

        // Resizing length & height
        fistLength += fistPositionX;
        fistHeight += fistPositionY;
        bodyLength += bodyPositionX;
        bodyHeight += bodyPositionY;
        headLength += headPositionX;
        headHeight += headPositionY;
        legLength += legPositionX;
        legHeight += legPositionY;
    }
}