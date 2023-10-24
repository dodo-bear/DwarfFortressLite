import java.awt.Color;

public class GraphicsEntity{
    private char displayChar = '\u2588';
    private Color displayColor = Color.MAGENTA;
    public GraphicsEntity(char displayChar, Color displayColor){
        this.displayChar = displayChar;
        this.displayColor = displayColor;
    }
    public GraphicsEntity(char displayChar){
        this.displayChar = displayChar;
        displayColor = Color.WHITE;
    }
    public GraphicsEntity(){
        displayChar = '\u2588';
        displayColor = Color.MAGENTA;
    }
    public char getDisplayChar(){
        return displayChar;
    }
    public Color getDisplayColor(){
        return displayColor;
    }
}
