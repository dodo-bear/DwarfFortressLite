import java.awt.*;
public class Structure extends GridObject {
    boolean walkable;
    public Structure(String name, String description, int viewPriority, char displayChar, Color displayColor, GridSquare square, boolean walkable) {
        super(name, 1, description, "Structure", viewPriority, displayChar, displayColor, square);
    }
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
    public boolean isWalkable() {
        return walkable;
    }
}