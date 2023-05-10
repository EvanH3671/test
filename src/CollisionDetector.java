// Last updated: 05/10/23

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

/* The collision detector is used to detect if two hitboxes come in contact
 */
public class CollisionDetector {

    private final Fighter player, opponent;

    private final Stats playerDeltaStats, opponentDeltaStats;
    private boolean playerFistCollideOpponentFist, playerFistCollideOpponentHead, playerFistCollideOpponentBody, playerFistCollideOpponentLeg, playerHeadCollideOpponentFist, playerHeadCollideOpponentHead, playerHeadCollideOpponentBody, playerHeadCollideOpponentLeg, playerBodyCollideOpponentFist, playerBodyCollideOpponentHead, playerBodyCollideOpponentBody, playerBodyCollideOpponentLeg, playerLegCollideOpponentFist, playerLegCollideOpponentHead, playerLegCollideOpponentBody, playerLegCollideOpponentLeg, playerLegCollidePlatform, opponentLegCollidePlatform, playerLegCollideTopBorder, playerLegCollideBottomBorder, opponentLegCollideTopBorder, opponentLegCollideBottomBorder;

    private int platformHeight, platformWidth, platformPositionX, platformPositionY;

    private Random rand = new Random();
    /*
     * Constructor
     */
    public CollisionDetector(Fighter player, Fighter opponent, Stats playerDeltaStats, Stats opponentDeltaStats){
        this.player = player;
        this.opponent = opponent;
        this.playerDeltaStats = playerDeltaStats;
        this.opponentDeltaStats = opponentDeltaStats;

        platformHeight = 48;
        platformWidth = 768-2*(100);
        platformPositionX = 100;
        platformPositionY = 300+24+24;

        platformHeight += platformPositionY;
        platformWidth += platformPositionX;
    }

    /*
     * if player hit wall player reset to wall position
     */
    public void checkWallCollision() {
        int playerBodyPositionX = player.getPartLocation(Fighter.BODY).x;

        int leftBorder = 0;
        int rightBorder = 768 - 48; // Right border - body size

        if(playerBodyPositionX < leftBorder) {
            player.setFighterLocation(leftBorder, null);
            // player.setBodyPositionX(leftBorder);
            // player.setHeadPositionX(leftBorder+12);
            // player.setFistPositionX(leftBorder-10);
            // player.setLegPositionX(leftBorder);
            // player.setHairPositionX(leftBorder+12);
        }
        else if (playerBodyPositionX > rightBorder) {
            player.setFighterLocation(rightBorder, null);
            // player.setBodyPositionX(rightBorder);
            // player.setHeadPositionX(rightBorder+12);
            // player.setFistPositionX(rightBorder-10);
            // player.setLegPositionX(rightBorder);
            // player.setHairPositionX(rightBorder+12);
        }
    }

    /*
     * This checks if the player or opponent fall under/above the top and bottom borders
     */
    public void checkBorderCollision(){
        int topBorder = 0;
        int bottomBorder = 576;

        playerLegCollideTopBorder = player.getLegPositionY() < topBorder;
        playerLegCollideBottomBorder = player.getLegPositionY() > bottomBorder;

        opponentLegCollideTopBorder = opponent.getLegPositionY() < topBorder;
        opponentLegCollideBottomBorder = opponent.getLegPositionY() > bottomBorder;

    }

    /*
     * This checks if the player or opponent is actually on the platform
     */
    public void checkPlatformCollision(Hitbox playerHitbox, Hitbox opponentHitbox){

        playerLegCollidePlatform = (playerHitbox.legLength < playerHitbox.legPositionX || playerHitbox.legLength > platformPositionX) &&
                (playerHitbox.legHeight < playerHitbox.legPositionY || playerHitbox.legHeight > platformPositionY) &&
                (platformWidth < platformPositionX || platformWidth > playerHitbox.legPositionX) &&
                (platformHeight < platformPositionY || platformHeight > playerHitbox.legPositionY);
        opponentLegCollidePlatform = (opponentHitbox.legLength < opponentHitbox.legPositionX || opponentHitbox.legLength > platformPositionX) &&
                (opponentHitbox.legHeight < opponentHitbox.legPositionY || opponentHitbox.legHeight > platformPositionY) &&
                (platformWidth < platformPositionX || platformWidth > opponentHitbox.legPositionX) &&
                (platformHeight < platformPositionY || platformHeight > opponentHitbox.legPositionY);
    }

    public void enableGravity(Fighter fighter){
        fighter.changeFighterLocation(0, 2);
        // fighter.changeBodyPositionY(2);
        // fighter.changeFistPositionY(2);
        // fighter.changeHeadPositionY(2);
        // fighter.changeLegPositionY(2);
        // fighter.changeHairPositionY(2);
    }

    ActionListener actionListener = new ActionListener() {
        int elapsedTime = 0;
        final int TIMER_DELAY = 10;
        final int MAX = 100;
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime += TIMER_DELAY;
            if (elapsedTime <= MAX) {
                player.changeFighterLocation(0, -5);
            } else {
                Timer s = (Timer)(e.getSource());
                s.stop();
                elapsedTime = 0;
            }
        }
    };

    /*public void jump(Fighter fighter){
        fighter.changeHeadPositionY(-1);
        fighter.changeBodyPositionY(-1);
        fighter.changeFistPositionY(-1);
        fighter.changeLegPositionY(-1);
        fighter.changeHairPositionY(-1);
        Timer timer = new Timer(3000, actionListener);
        timer.start();
    }*/

    /*
     * This checks if the player hits the opponent
     */
    public void checkOpponentCollision(Hitbox playerHitbox, Hitbox opponentHitbox){
        opponent.setFighterLocation(rand.nextInt(768), rand.nextInt(576));

        /*System.out.println("Player fist length: " + playerHitbox.fistLength);
        System.out.println("Player fist position X: " + playerHitbox.fistPositionX);
        System.out.println("Opponent fist position X: " + opponentHitbox.fistPositionX);
        System.out.println("Opponent fist length: " + opponentHitbox.fistLength);*/

        // overflow || intersect
        
        // playerFistCollideOpponentFist = (playerHitbox.fistLength < playerHitbox.fistPositionX || playerHitbox.fistLength > opponentHitbox.fistPositionX) &&
        //         (playerHitbox.fistHeight < playerHitbox.fistPositionY || playerHitbox.fistHeight > opponentHitbox.fistPositionY) &&
        //         (opponentHitbox.fistLength < opponentHitbox.fistPositionX || opponentHitbox.fistLength > playerHitbox.fistPositionX) &&
        //         (opponentHitbox.fistHeight < opponentHitbox.fistPositionY || opponentHitbox.fistHeight > playerHitbox.fistPositionY);
        // playerFistCollideOpponentHead = (playerHitbox.fistLength < playerHitbox.fistPositionX || playerHitbox.fistLength > opponentHitbox.headPositionX) &&
        //         (playerHitbox.fistHeight < playerHitbox.fistPositionY || playerHitbox.fistHeight > opponentHitbox.headPositionY) &&
        //         (opponentHitbox.headLength < opponentHitbox.headPositionX || opponentHitbox.headLength > playerHitbox.fistPositionX) &&
        //         (opponentHitbox.headHeight < opponentHitbox.headPositionY || opponentHitbox.headHeight > playerHitbox.fistPositionY);
        // playerFistCollideOpponentBody = (playerHitbox.fistLength < playerHitbox.fistPositionX || playerHitbox.fistLength > opponentHitbox.bodyPositionX) &&
        //         (playerHitbox.fistHeight < playerHitbox.fistPositionY || playerHitbox.fistHeight > opponentHitbox.bodyPositionY) &&
        //         (opponentHitbox.bodyLength < opponentHitbox.bodyPositionX || opponentHitbox.bodyLength > playerHitbox.fistPositionX) &&
        //         (opponentHitbox.bodyHeight < opponentHitbox.bodyPositionY || opponentHitbox.bodyHeight > playerHitbox.fistPositionY);
        // playerFistCollideOpponentLeg = (playerHitbox.fistLength < playerHitbox.fistPositionX || playerHitbox.fistLength > opponentHitbox.legPositionX) &&
        //         (playerHitbox.fistHeight < playerHitbox.fistPositionY || playerHitbox.fistHeight > opponentHitbox.legPositionY) &&
        //         (opponentHitbox.legLength < opponentHitbox.legPositionX || opponentHitbox.legLength > playerHitbox.fistPositionX) &&
        //         (opponentHitbox.legHeight < opponentHitbox.legPositionY || opponentHitbox.legHeight > playerHitbox.fistPositionY);
        // playerHeadCollideOpponentFist = (playerHitbox.headLength < playerHitbox.headPositionX || playerHitbox.headLength > opponentHitbox.fistPositionX) &&
        //         (playerHitbox.headHeight < playerHitbox.headPositionY || playerHitbox.headHeight > opponentHitbox.fistPositionY) &&
        //         (opponentHitbox.fistLength < opponentHitbox.fistPositionX || opponentHitbox.fistLength > playerHitbox.headPositionX) &&
        //         (opponentHitbox.fistHeight < opponentHitbox.fistPositionY || opponentHitbox.fistHeight > playerHitbox.headPositionY);
        // playerHeadCollideOpponentLeg = (playerHitbox.headLength < playerHitbox.headPositionX || playerHitbox.headLength > opponentHitbox.legPositionX) &&
        //         (playerHitbox.headHeight < playerHitbox.headPositionY || playerHitbox.headHeight > opponentHitbox.legPositionY) &&
        //         (opponentHitbox.legLength < opponentHitbox.legPositionX || opponentHitbox.legLength > playerHitbox.headPositionX) &&
        //         (opponentHitbox.legHeight < opponentHitbox.legPositionY || opponentHitbox.legHeight > playerHitbox.headPositionY);
        // playerBodyCollideOpponentFist = (playerHitbox.bodyLength < playerHitbox.bodyPositionX || playerHitbox.bodyLength > opponentHitbox.fistPositionX) &&
        //         (playerHitbox.bodyHeight < playerHitbox.bodyPositionY || playerHitbox.bodyHeight > opponentHitbox.fistPositionY) &&
        //         (opponentHitbox.fistLength < opponentHitbox.fistPositionX || opponentHitbox.fistLength > playerHitbox.bodyPositionX) &&
        //         (opponentHitbox.fistHeight < opponentHitbox.fistPositionY || opponentHitbox.fistHeight > playerHitbox.bodyPositionY);
        // playerBodyCollideOpponentBody = (playerHitbox.bodyLength < playerHitbox.bodyPositionX || playerHitbox.bodyLength > opponentHitbox.bodyPositionX) &&
        //         (playerHitbox.bodyHeight < playerHitbox.bodyPositionY || playerHitbox.bodyHeight > opponentHitbox.bodyPositionY) &&
        //         (opponentHitbox.bodyLength < opponentHitbox.bodyPositionX || opponentHitbox.bodyLength > playerHitbox.bodyPositionX) &&
        //         (opponentHitbox.bodyHeight < opponentHitbox.bodyPositionY || opponentHitbox.bodyHeight > playerHitbox.bodyPositionY);
        // playerBodyCollideOpponentLeg = (playerHitbox.bodyLength < playerHitbox.bodyPositionX || playerHitbox.bodyLength > opponentHitbox.legPositionX) &&
        //         (playerHitbox.bodyHeight < playerHitbox.bodyPositionY || playerHitbox.bodyHeight > opponentHitbox.legPositionY) &&
        //         (opponentHitbox.legLength < opponentHitbox.legPositionX || opponentHitbox.legLength > playerHitbox.bodyPositionX) &&
        //         (opponentHitbox.legHeight < opponentHitbox.legPositionY || opponentHitbox.legHeight > playerHitbox.bodyPositionY);
        // playerLegCollideOpponentHead = (playerHitbox.legLength < playerHitbox.legPositionX || playerHitbox.legLength > opponentHitbox.fistPositionX) &&
        //         (playerHitbox.legHeight < playerHitbox.legPositionY || playerHitbox.legHeight > opponentHitbox.fistPositionY) &&
        //         (opponentHitbox.fistLength < opponentHitbox.fistPositionX || opponentHitbox.fistLength > playerHitbox.legPositionX) &&
        //         (opponentHitbox.fistHeight < opponentHitbox.fistPositionY || opponentHitbox.fistHeight > playerHitbox.legPositionY);
        // playerLegCollideOpponentHead = (playerHitbox.legLength < playerHitbox.legPositionX || playerHitbox.legLength > opponentHitbox.headPositionX) &&
        //         (playerHitbox.legHeight < playerHitbox.legPositionY || playerHitbox.legHeight > opponentHitbox.headPositionY) &&
        //         (opponentHitbox.headLength < opponentHitbox.headPositionX || opponentHitbox.headLength > playerHitbox.legPositionX) &&
        //         (opponentHitbox.headHeight < opponentHitbox.headPositionY || opponentHitbox.headHeight > playerHitbox.legPositionY);
        // playerLegCollideOpponentBody = (playerHitbox.legLength < playerHitbox.legPositionX || playerHitbox.legLength > opponentHitbox.bodyPositionX) &&
        //         (playerHitbox.legHeight < playerHitbox.legPositionY || playerHitbox.legHeight > opponentHitbox.bodyPositionY) &&
        //         (opponentHitbox.bodyLength < opponentHitbox.bodyPositionX || opponentHitbox.bodyLength > playerHitbox.legPositionX) &&
        //         (opponentHitbox.bodyHeight < opponentHitbox.bodyPositionY || opponentHitbox.bodyHeight > playerHitbox.legPositionY);
        // playerLegCollideOpponentLeg = (playerHitbox.legLength < playerHitbox.legPositionX || playerHitbox.legLength > opponentHitbox.legPositionX) &&
        //         (playerHitbox.legHeight < playerHitbox.legPositionY || playerHitbox.legHeight > opponentHitbox.legPositionY) &&
        //         (opponentHitbox.legLength < opponentHitbox.legPositionX || opponentHitbox.legLength > playerHitbox.legPositionX) &&
        //         (opponentHitbox.legHeight < opponentHitbox.legPositionY || opponentHitbox.legHeight > playerHitbox.legPositionY);
        
        // not working perfectly

        playerFistCollideOpponentFist = checkCollision(player, Fighter.FIST_L, opponent, Fighter.FIST_L);
        playerFistCollideOpponentHead = checkCollision(player, Fighter.FIST_L, opponent, Fighter.HEAD);
        playerFistCollideOpponentBody = checkCollision(player, Fighter.FIST_L, opponent, Fighter.BODY);
        playerFistCollideOpponentLeg = checkCollision(player, Fighter.FIST_L, opponent, Fighter.LEG);
        playerHeadCollideOpponentFist = checkCollision(player, Fighter.HEAD, opponent, Fighter.FIST_L);
        playerHeadCollideOpponentLeg = checkCollision(player, Fighter.HEAD, opponent, Fighter.LEG);
        playerBodyCollideOpponentFist = checkCollision(player, Fighter.BODY, opponent, Fighter.FIST_L);
        playerBodyCollideOpponentBody = checkCollision(player, Fighter.BODY, opponent, Fighter.BODY);
        playerBodyCollideOpponentLeg = checkCollision(player, Fighter.BODY, opponent, Fighter.LEG);
        playerLegCollideOpponentHead = checkCollision(player, Fighter.LEG, opponent, Fighter.HEAD);
        playerLegCollideOpponentBody = checkCollision(player, Fighter.LEG, opponent, Fighter.BODY);
        playerLegCollideOpponentLeg = checkCollision(player, Fighter.LEG, opponent, Fighter.LEG);
    }

    public boolean checkCollision(Fighter player1, int part1, Fighter player2, int part2) {
        return player1.getPartRectangle(part1).intersects(player2.getPartRectangle(part2));
    }

    // Make sure the collision detector is working
    public void checkCollision(){

        // Fighter collision
        if(playerFistCollideOpponentFist){
            System.out.println("playerFistCollideOpponentFist");
        }
        else if(playerFistCollideOpponentHead){
            System.out.println("playerFistCollideOpponentHead");
        }
        else if(playerFistCollideOpponentBody){
            System.out.println("playerFistCollideOpponentBody");
        }
        else if(playerFistCollideOpponentLeg){
            System.out.println("playerFistCollideOpponentLeg");
        }
        else if(playerHeadCollideOpponentFist){
            System.out.println("playerHeadCollideOpponentFist");
        }
        else if(playerHeadCollideOpponentHead){
            System.out.println("playerHeadCollideOpponentHead");
        }
        else if(playerHeadCollideOpponentBody){
            System.out.println("playerHeadCollideOpponentBody");
        }
        else if(playerHeadCollideOpponentLeg){
            System.out.println("playerHeadCollideOpponentLeg");
        }
        else if(playerBodyCollideOpponentFist){
            System.out.println("playerBodyCollideOpponentFist");
        }
        else if(playerBodyCollideOpponentHead){
            System.out.println("playerBodyCollideOpponentHead");
        }
        else if(playerBodyCollideOpponentBody){
            System.out.println("playerBodyCollideOpponentBody");
        }
        else if(playerBodyCollideOpponentLeg){
            System.out.println("playerBodyCollideOpponentLeg");
        }
        else if(playerLegCollideOpponentFist){
            System.out.println("playerLegCollideOpponentFist");
        }
        else if (playerLegCollideOpponentHead){
            System.out.println("playerLegCollideOpponentHead");
        }
        else if(playerLegCollideOpponentBody){
            System.out.println("playerLegCollideOpponentBody");
        }
        else if(playerLegCollideOpponentLeg){
            System.out.println("playerLegCollideOpponentLeg");
        }

        // platform collision
        if (playerDeltaStats.isJump()) {
            playerDeltaStats.setTouchGrass(false);
        } else if (playerLegCollidePlatform) {
            player.setFighterLocation(null, 301);
            playerDeltaStats.setTouchGrass(true);
        } else {
            enableGravity(player);
        }

        if (opponentDeltaStats.isJump()){
            opponentDeltaStats.setTouchGrass(false);
        } else if (opponentLegCollidePlatform) {
            // opponent.setFighterLocation(null, 301);
            opponentDeltaStats.setTouchGrass(true);
        } else {
            enableGravity(opponent);
        }

        // For the top and bottom borders
        if (playerLegCollideTopBorder) {
            player.setFighterLocation(null, 0);

            System.out.println("Game Over");
        } else if (playerLegCollideBottomBorder) {
            player.setFighterLocation(null, 0); // 0 is temp

            System.out.println("Game Over");
        }

        if(opponentLegCollideTopBorder){
            opponent.setFighterLocation(null, 0);

            System.out.println("Game Over");
        }
        else if(opponentLegCollideBottomBorder){
            opponent.setFighterLocation(null, 0); // 0 is temp

            System.out.println("Game Over");
        }
    }
}