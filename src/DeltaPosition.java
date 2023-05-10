// Last updated: 04/23/23

/* The delta position class denotes the change in position of the fighter object
 */
public class DeltaPosition{
    private int deltaBodyPositionX,deltaBodyPositionY,deltaHeadPositionX,deltaHeadPositionY,deltaLegPositionX,deltaLegPositionY,deltaFistPositionX,deltaFistPositionY, deltaHairPositionX, deltaHairPositionY;

    /*
     * Constructor
     */
    public DeltaPosition(){
        deltaBodyPositionX=deltaBodyPositionY=deltaHeadPositionX=deltaHeadPositionY=deltaLegPositionX=deltaLegPositionY=deltaFistPositionX=deltaFistPositionY=0; // Sets all default deltas to 0

    }

    /*
     * get functions
     */
    public int getDeltaHairPositionX(){
        return deltaHairPositionX;
    }
    public int getDeltaHairPositionY(){
        return deltaHairPositionY;
    }
    public int getDeltaBodyPositionX(){
        return deltaBodyPositionX;
    }

    public int getDeltaBodyPositionY(){
        return deltaBodyPositionY;
    }

    public int getDeltaHeadPositionY(){
        return deltaHeadPositionY;
    }

    public int getDeltaHeadPositionX(){
        return deltaHeadPositionX;
    }

    public int getDeltaLegPositionY(){
        return deltaLegPositionY;
    }

    public int getDeltaLegPositionX(){
        return deltaLegPositionX;
    }

    public int getDeltaFistPositionX(){
        return deltaFistPositionX;
    }
    public int getDeltaFistPositionY(){
        return deltaFistPositionY;
    }

    /*
     * Set functions
     */
    public void setDeltaBodyPositionX(int deltaBodyPositionX){
        this.deltaBodyPositionX = deltaBodyPositionX;
    }

    public void setDeltaBodyPositionY(int deltaBodyPositionY){
        this.deltaBodyPositionY = deltaBodyPositionY;
    }

    public void setDeltaHeadPositionX(int deltaHeadPositionX){
        this.deltaHeadPositionX = deltaHeadPositionX;
    }

    public void setDeltaHeadPositionY(int deltaHeadPositionY){
        this.deltaHeadPositionY = deltaHeadPositionY;
    }

    public void setDeltaLegPositionX(int deltaLegPositionX){
        this.deltaLegPositionX = deltaLegPositionX;
    }

    public void setDeltaLegPositionY(int deltaLegPositionY){
        this.deltaLegPositionY = deltaLegPositionY;
    }

    public void setDeltaFistPositionX(int deltaFistPositionX){
        this.deltaFistPositionX = deltaFistPositionX;
    }

    public void setDeltaFistPositionY(int deltaFistPositionY){
        this.deltaFistPositionY = deltaFistPositionY;
    }
    public void setDeltaHairPositionX(int deltaHairPositionX){
        this.deltaHairPositionX = deltaHairPositionX;
    }

    public void setDeltaHairPositionY(int deltaHairPositionY){
        this.deltaHairPositionY = deltaHairPositionY;
    }
}