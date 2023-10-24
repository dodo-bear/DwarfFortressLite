import java.awt.*;
public class Edible extends GridObject {
    int foodValue;
    public Edible(String name, int count, String description, int viewPriority, char displayChar, Color displayColor, GridSquare square, int foodValue) {
        super(name, count, description, "Food", viewPriority, displayChar, displayColor, square);
        this.foodValue = foodValue;
    }
    public int getFoodValue() {
        return foodValue;
    }
}
