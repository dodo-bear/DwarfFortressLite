import java.awt.*;
public class Animal extends GridObject {
    private boolean hasMovedThisTick = false;
    private int roamDistance;
    private boolean hasPicked = false;
    private WorldGrid grid;
    private int targetx = this.getSquare().getX();
    private int targety = this.getSquare().getY();
    private GridSquare oldGridSquare;
    private int maxSatiety;
    private int currentSatiety;
    private String purpose;
    private int maxHealth = 100;
    private int currentHealth;
    private int maxAge;
    private int currentAge;
    private int turnsAlive = 0;
    
    public Animal(WorldGrid grid, String name, int count, String description, String type, int viewPriority, char displayChar, Color displayColor, GridSquare square, int roamDistance, int maxHealth, int maxAge, int maxSatiety) {
        super(name, count, description, type, viewPriority, displayChar, displayColor, square);
        this.roamDistance = roamDistance;
        this.grid = grid;
        this.maxSatiety = (int)(Math.round((maxSatiety * 0.8)) + (int)(Math.random() * ((maxSatiety * 1.2 - maxSatiety * 0.8) + 1)));
        this.maxAge = maxAge * (int)((Math.random() * 4) + 8);
        this.maxHealth = maxHealth * (int)((Math.random() * 4) + 8);
        this.currentHealth = maxHealth;
        this.currentAge = 0;
        oldGridSquare = this.getSquare();
        currentSatiety = maxSatiety;
    }
    private int checkCoords(int num){
        if(num < 0){
            return 0;
        }
        else if(num > 255){
            return 255;
        }
        else return num;
    }
    private GridSquare searchForFood(){
        GridSquare closestFood = null;
        GridSquare[][] searchGrid = new GridSquare[roamDistance * 2][roamDistance * 2];
        int x = 0;
        int y = 0;
        for (GridSquare[] gridSquares : searchGrid) {
            for(GridSquare  gridSquare : gridSquares){
                gridSquare = grid.getGrid()[checkCoords(x + this.getSquare().getX() - roamDistance)][checkCoords(y + this.getSquare().getY() - roamDistance)];
                if (gridSquare != null && gridSquare.findObjectsWithType("Food").length != 0) {
                    closestFood = gridSquare;
                }
                y++;
            }
            x++;
            y = 0;
        }
        return closestFood;
    }
    public GridSquare Move(){
        currentSatiety--;
        turnsAlive ++;
        if(turnsAlive >= 100){currentAge++;turnsAlive = 0;}
        if(currentSatiety <= 0 || currentHealth <= 0 || currentAge > maxAge){
            //Die
            die();
            return this.getSquare();
        }
        if(this.getSquare().getX() == checkCoords(oldGridSquare.getX() + targetx) && this.getSquare().getY() == checkCoords(oldGridSquare.getY() + targety)){
            oldGridSquare = this.getSquare();
            purpose = decidePurpose();
            this.Act(purpose);
        }
        if(Math.random() < 0.5 && targetx + oldGridSquare.getX() != this.getSquare().getX()){
            this.getSquare().removeObject(this);
            if(targetx + oldGridSquare.getX() > this.getSquare().getX()){
                if(this.getSquare().neighbors[1] != null){
                this.setSquare(this.getSquare().neighbors[1]);
                }
            } else {
                if(this.getSquare().neighbors[0] != null){
                this.setSquare(this.getSquare().neighbors[0]);
                }
            }
                this.getSquare().addObject(this);
        } else if(targety + oldGridSquare.getY() != this.getSquare().getY()){
            this.getSquare().removeObject(this);
            if(targety + oldGridSquare.getY() > this.getSquare().getY()){
                if(this.getSquare().neighbors[3] != null){
                this.setSquare(this.getSquare().neighbors[3]);
                }
            } else {
                if(this.getSquare().neighbors[2] != null){
                this.setSquare(this.getSquare().neighbors[2]);
                }
            }
                this.getSquare().addObject(this);
        } else if( targetx + oldGridSquare.getX() != this.getSquare().getX()) {
            this.getSquare().removeObject(this);
            if(targetx + oldGridSquare.getX() > this.getSquare().getX()){
                if(this.getSquare().neighbors[1] != null){
                this.setSquare(this.getSquare().neighbors[1]);
                }
            } else {
                if(this.getSquare().neighbors[0]!= null){
                this.setSquare(this.getSquare().neighbors[0]);
                }
            }
                this.getSquare().addObject(this);
        }
        return this.getSquare();
    }
    public boolean haveIMoved(){
        return hasMovedThisTick;
    }
    public void setMoved(boolean moved){
        hasMovedThisTick = moved;
    }
    public boolean haveIPicked(){
        return hasPicked;
    }
    public void setTarget(GridSquare target){
        targetx = target.getX() - this.getSquare().getX();
        targety = target.getY() - this.getSquare().getY();
    }
    public int getAge(){
        return currentAge;
    }
    private void Act(String purpose){
        switch(purpose){
            case "wander":
            setTarget(grid.getGrid()[checkCoords(this.getSquare().getX() + (int)(Math.random() * roamDistance * 2) - roamDistance)][checkCoords(this.getSquare().getY() + (int)(Math.random() * roamDistance * 2) - roamDistance)]);
            break;
            case "eat":
            if(this.getSquare().findObjectsWithType("Food")[0] instanceof Edible){
                if(currentSatiety + ((Edible)this.getSquare().findObjectsWithType("Food")[0]).getFoodValue() > maxSatiety){
                    currentSatiety = maxSatiety;
                } else {
                    currentSatiety += ((Edible)this.getSquare().findObjectsWithType("Food")[0]).getFoodValue();
                }
                this.getSquare().findObjectsWithType("Food")[0].setCount(this.getSquare().findObjectsWithType("Food")[0].getCount() - 1);
            }
            break;
            case "findfood":
            
            if(this.searchForFood() != null){
                setTarget(this.searchForFood());
            }
            else{
                setTarget(grid.getGrid()[checkCoords(this.getSquare().getX() + (int)(Math.random() * roamDistance * 2) - roamDistance)][checkCoords(this.getSquare().getY() + (int)(Math.random() * roamDistance * 2) - roamDistance)]);
            }
            break;
        }
    }
    private String decidePurpose(){
        if(currentSatiety < maxSatiety/2 && this.getSquare().findObjectsWithType("Food").length != 0){
            return "eat";
        } else if (currentSatiety < maxSatiety/2){
            return "findfood";
        } else {
            return "wander";
        }
        
    }
    private void die(){
        this.getSquare().removeObject(this);
        this.getSquare().addObject(new Edible("Corpse", 1, "The corpse of an animal.", 2, this.getDisplayChar(), Color.GRAY, this.getSquare(), this.currentSatiety * maxHealth/100));
    }
}