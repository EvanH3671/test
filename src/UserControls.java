import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/* Believe it or not, but this class is for the user controls
 */
public class UserControls implements KeyListener {

    Stats playerStats;
 
    UserControls(Stats playerStats){
        this.playerStats = playerStats;
    }
    boolean leftPressed,rightPressed, jump, fall;

    public void keyPressed(KeyEvent e){

        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_UP:
                if (playerStats.isTouchGrass()) {
                    jump = true;
                    playerStats.setJump(true);
                } else {
                  jump = false;
                    playerStats.setJump(false);
                }
                break;

          case KeyEvent.VK_DOWN:
              
        }

    }

    public void keyTyped(KeyEvent e){

    }

    public void keyReleased(KeyEvent e){

        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_UP:
                jump = false;
                break;
        }
    }
}

// JLabel player;
//   Action LeftAction;
//   Action RightAction;

//    // Constructor
//   public UserControls(){
//     JLabel player = new JLabel();
//     player.setBackground(Color.black);
//     player.setBounds(100,100,100,100);
//     player.setOpaque(true);

//     //window.add(player);
//   }

//   public JLabel getPlayer(){
//     return player;
//   }

//   public class LeftAction extends AbstractAction {
//     public void actionPerformed(ActionEvent e) {
//       player.setLocation(player.getX()-10, player.getY());
//     }
//   }

//   public class RightAction extends AbstractAction{
//     public void actionPerformed(ActionEvent e) {
//       player.setLocation(player.getX()+10, player.getY());
//     }
//   }