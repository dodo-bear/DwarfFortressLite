import java.awt.*;
public class GridObject {
    String name;
    int count;
    String description;
    String type;
    int viewPriority;
    char displayChar;
    Color displayColor;
    GridSquare square;
    public GridObject(String name, int count, String description, String type, int viewPriority, char displayChar, Color displayColor, GridSquare square) {
        this.name = name;
        this.count = count;
        this.description = description;
        this.type = type;
        this.viewPriority = viewPriority;
        this.displayChar = displayChar;
        this.displayColor = displayColor;
        this.square = square;
    }
    public GridObject(){
        name = "DefaultObj";
        count = 1;
        description = "Default Description";
        type = "DefaultType";
        viewPriority = 0;
        displayChar = '\u2588';
    }
    public GridSquare getSquare() {
        return square;
    }
    public void setSquare(GridSquare square) {
        this.square = square;
    }
    public String getName() {
        return name;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
        if(count < 1){
            this.getSquare().removeObject(this);
        }
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public int getViewPriority() {
        return viewPriority;
    }
    public char getDisplayChar() {
        return displayChar;
    }
    public Color getDisplayColor() {
        return displayColor;
    }
}