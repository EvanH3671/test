// Last updated: 05/09/23

/* This class stores all the Stats
 */
 public class Stats{

    private boolean punching, blocking, kneeing, uppercutting, jump, touchGrass;
  
    /*
     * Constructor
     */
    Stats(){
        punching = blocking = kneeing = uppercutting = jump = touchGrass = false; // States are false by default
    }
  
    /*
     * He must touch grass before jumping again
     * 
     */
    public void setTouchGrass(boolean touchGrass){
        this.touchGrass = touchGrass;
    }
    public boolean isTouchGrass(){
        return touchGrass;
    }
  
    public void setJump(boolean jump){
        this.jump = jump;
    }
  
    public boolean isJump(){
        return jump;
    }
   
  }