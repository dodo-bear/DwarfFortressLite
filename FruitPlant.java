import java.awt.*;
public class FruitPlant extends GridObject{
    int fruitCount;
    GridObject fruit;
    int maxFruit = (int)(Math.random() * 8) + 2;
    public FruitPlant(String name, int count, String description, char displayChar, Color displayColor, GridSquare square, GridObject fruit, int fruitCount){
        super(name, count, description, "Plant", 5, displayChar, displayColor, square);
        this.fruit = fruit;
        this.fruitCount = fruitCount;
        for(int i = 0; i < fruitCount; i++){
            this.getSquare().addObject(fruit);
        }
    }
    public void grow(){
        fruitCount = this.getSquare().findObjectsWithType("Food").length;
        if(Math.random() < 0.05 && fruitCount < maxFruit){
            this.getSquare().addObject(fruit);
        }
    }
}
