// Last updated: 05/10/2023

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.nio.file.NoSuchFileException;

import javax.swing.Timer;

/*
 * The GamePanel creates the JPanel that the game is displayed upon
 * It also initiates the game loop
 */
public class GamePanel extends JPanel implements Runnable {
    // Screen Settings
    //? Are these variables gonna get used anywhere else?
    final int ORIGINAL_TILE_SIZE = 16; //16X16 tiles
    final int SCALE = 3;

    final int TILE_SIZE = ORIGINAL_TILE_SIZE*SCALE;
    // Now the pixels are 48x48

    final int MAX_SCREEN_COLUMN = 16;
    final int MAX_SCREEN_ROW = 12;
    // Ultimately, a 4:3 screen ratio is created

    final int SCREEN_WIDTH = TILE_SIZE*MAX_SCREEN_COLUMN; // 768 pixels
    final int SCREEN_HEIGHT = TILE_SIZE*MAX_SCREEN_ROW; // 576 pixels

    final int FPS = 60; // Set FPS to 60
    final int DELAY = 100; // milliseconds

    // Instantiating objects (should be done in the constructor)
    Thread gameThread; // Gives a sense of time to the game
    public Fighter player = new Fighter(); // Sets up the player's fighter
    Fighter opponent = new Fighter(); // Sets up the opponent's fighter
    PlayerStats playerSStats;
    Appearance playerAppearance;
    Appearance opponentAppearance;
//    Action playerAction = new Action();
//    Action opponentAction = new Action();
    DeltaPosition playerDeltaPosition = new DeltaPosition();
    DeltaPosition opponentDeltaPosition = new DeltaPosition();
    Stats playerStats = new Stats();
    Stats opponentStats = new Stats();
    UserControls userControls = new UserControls(playerStats);
    CollisionDetector collisionDetector = new CollisionDetector(player,opponent,playerStats,opponentStats);
    Direction playerDirection = new Direction();
    Direction opponentDirection = new Direction();

    // Constructor
    public GamePanel(int saveIndex){
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Making the panel size
        setBackground(Color.blue); // Making the background colour
        setDoubleBuffered(true); // Making the game easier/faster to render
        addKeyListener(userControls);
        setFocusable(true);  // Allowing the panel to listen to the keystrokes
    
        opponentAppearance = new Appearance();
    
        try {
            playerSStats = SaveManager.getFileData(saveIndex);
        } catch (NoSuchFileException nsfe) {
            nsfe.printStackTrace();
        }
    
        playerAppearance = playerSStats.appearance();
    }

    // Starts the sense of time
    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Where all the logic of the game will be run
    public void run(){

        // setting the opponent to actually land on the platform
        opponent.changeFighterLocation(150, 0);
        // opponent.changeHairPositionX(150);
        // opponent.changeFistPositionX(150);
        // opponent.changeHeadPositionX(150);
        // opponent.changeLegPositionX(150);
        // opponent.changeBodyPositionX(150);

        // For the FPS limiter
        double drawInterval = (1000000000) /FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;

        double timer = 0;
        int drawCount = 0;


        while(gameThread != null){

            // Basically makes sure that there is some lag between update and repaint
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta>=1){
                // 1. Update character position
                update();

                // 2. Draw: i.e. redraw the screen with updated information
                repaint();

                // 60 FPS baby
                delta--;
                drawCount++;
            }

            // Display FPS
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                timer = 0;
                drawCount = 0;
            }
        }
    }

    // Update game data
    public void update(){
        updatePlayer();

        // Refreshes the hitboxes for collision detection
        Hitbox playerHitbox = new Hitbox(player, playerDirection);
        Hitbox opponentHitbox = new Hitbox(opponent, opponentDirection);

        collisionDetector.checkWallCollision();
        collisionDetector.checkPlatformCollision(playerHitbox, opponentHitbox);
        collisionDetector.checkBorderCollision();

        collisionDetector.checkOpponentCollision(playerHitbox, opponentHitbox);
        collisionDetector.checkCollision(); // Make sure the opponent collision is working

    }

    // Updates player position and moves
    public void updatePlayer() {
        DeltaPosition playerDeltaPosition = new DeltaPosition(); // Reset deltas to 0 each time
        int speed = player.getSpeed() / 10;

        if (userControls.leftPressed) {
            player.changeFighterLocation(-speed, 0);
            playerDirection.setDirectionLeft(true);

        } else if (userControls.rightPressed) {
            playerDirection.setDirectionLeft(false);
            player.changeFighterLocation(speed, 0);
        }

        if (playerStats.isJump()){
            Timer timer = new Timer(10, collisionDetector.actionListener);
            timer.start();

            System.out.println(playerAppearance.getFirstName() + " " + playerAppearance.getLastName() + " is jumping");
            playerStats.setJump(false);
            playerStats.setTouchGrass(false);
        }
    //   if(playerStats.isPunch()){
        
        
    //     System.out.println(playerAppearance.getFirstName() + " " + playerAppearance.getLastName() + " is punching");
    //     playerStats.setPunch(false);
    //   }

        player.changeHeadPositionY(playerDeltaPosition.getDeltaHeadPositionY());
        player.changeBodyPositionY(playerDeltaPosition.getDeltaBodyPositionY());
        player.changeFistPositionY(playerDeltaPosition.getDeltaFistPositionY());
        player.changeLegPositionY(playerDeltaPosition.getDeltaLegPositionY());
        player.changeHairPositionY(playerDeltaPosition.getDeltaHairPositionY());
    }

    // Redraws screen with updated info
    public void  paintComponent(Graphics g){
        super.paintComponent(g); // Calls the superclass (JPanel) to start allowing to paint

        Graphics2D g2 = (Graphics2D) g; // Makes graphics into 2D for extra added features

        //playerAppearance.setOutfit(playerAppearance.getAppearance()); // Making the outfit for drawing
        //opponentAppearance.setOutfit(Appearance.getRandomizedAppearance());

        // Creates platform
        g2.setColor(new Color(255, 212,23));
        g2.fillRect(100, 300+48, 768-2*(100), TILE_SIZE);

        // Creates player character
        final int rightGlovePosition = 52;
        final int uselessGloveRightPosition = 52-20;

        g2.setColor(playerAppearance.getSkinColor());
        g2.fill(player.getPartRectangle(Fighter.HEAD));

        g2.setColor(playerAppearance.getSkinColor()); // Body
        g2.fill(player.getPartRectangle(Fighter.BODY));

        g2.setColor(playerAppearance.getShortsColor()); // Legs
        g2.fill(player.getPartRectangle(Fighter.LEG));

        if(playerDirection.isDirectionLeft()){
            g2.setColor(playerAppearance.getGlovesColor()); // Gloves
            g2.fillRect(player.getFistPositionX(),player.getFistPositionY(),player.getFistWidth(),player.getFistHeight());

            g2.setColor(playerAppearance.getGlovesColor());
            g2.fillRect(player.getFistPositionX()+20,player.getFistPositionY()+15,player.getFistWidth(),player.getFistHeight());
        }
        else{
            g2.setColor(playerAppearance.getGlovesColor()); // Gloves
            g2.fillRect(player.getFistPositionX()+rightGlovePosition,player.getFistPositionY(),player.getFistWidth(),player.getFistHeight());

            g2.setColor(playerAppearance.getGlovesColor());
            g2.fillRect(player.getFistPositionX()+uselessGloveRightPosition,player.getFistPositionY()+15,player.getFistWidth(),player.getFistHeight());
        }
        g2.setColor(playerAppearance.getHairColor()); // Hair
        g2.fillRect(player.getHairPositionX(),player.getHairPositionY(),player.getHairWidth(),player.getHairHeight());

        // Creates opponent character
        g2.setColor(opponentAppearance.getSkinColor()); // Body
        g2.fillRect(opponent.getBodyPositionX(),opponent.getBodyPositionY(),opponent.getBodyWidth(),opponent.getBodyHeight());

        g2.setColor(opponentAppearance.getSkinColor()); // Head
        g2.fillRect(opponent.getHeadPositionX(),opponent.getHeadPositionY(),opponent.getHeadWidth(),opponent.getHeadHeight());

        g2.setColor(opponentAppearance.getShortsColor()); // Legs
        g2.fillRect(opponent.getLegPositionX(),opponent.getLegPositionY(),opponent.getLegWidth(),opponent.getLegHeight());

        if(opponentDirection.isDirectionLeft()){
            g2.setColor(opponentAppearance.getGlovesColor()); // Gloves
            g2.fillRect(opponent.getFistPositionX(),opponent.getFistPositionY(),opponent.getFistWidth(),opponent.getFistHeight());

            g2.setColor(opponentAppearance.getGlovesColor());
            g2.fillRect(opponent.getFistPositionX()+20,opponent.getFistPositionY()+15,opponent.getFistWidth(),opponent.getFistHeight());
        }
        else{
            g2.setColor(opponentAppearance.getGlovesColor()); // Gloves
            g2.fillRect(opponent.getFistPositionX()+rightGlovePosition,opponent.getFistPositionY(),opponent.getFistWidth(),opponent.getFistHeight());

            g2.setColor(opponentAppearance.getGlovesColor());
            g2.fillRect(opponent.getFistPositionX()+uselessGloveRightPosition,opponent.getFistPositionY()+15,opponent.getFistWidth(),opponent.getFistHeight());
        }
        g2.setColor(opponentAppearance.getHairColor()); // Hair
        g2.fillRect(opponent.getHairPositionX(),opponent.getHairPositionY(),opponent.getHairWidth(),opponent.getHairHeight());
        g2.dispose(); // kinda like closing a scanner
    }
}
