import java.awt.Color;
import java.util.*;
public class WorldGrid {
    
    String name = "New World";
    long playTime = 0;
    GridSquare[][] grid = new GridSquare[256][256];
    static ArrayList<GridSquare> allGridSquares = new ArrayList<GridSquare>();
    public WorldGrid(String name) {
        this.name = name;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                try{
                grid[i][j] = new GridSquare(i,j, this);
                allGridSquares.add(grid[i][j]);
                if(Math.random() < 0.1){grid[i][j].addObject(new FruitPlant("Fruit Tree", 1, "A fruit tree. You don't know what it grows.", 'T', new Color(150, 75, 0), grid[i][j], new Edible("Berry",1,"A default object. You should not be seeing this.",4,'o',Color.RED, grid[i][j], 50), (int)(Math.random()*2)));}
                } catch(Exception e){
                    System.out.println("Failed to create GridSquare " + i + "," + j + ". " );
                }
            }

        }
        System.out.println("Finding Adjacencies");
        for (GridSquare gridSquare : allGridSquares) {
            try {
                gridSquare.findAdjacencies(this.getGrid());
            } catch (Exception e) {
                System.out.println("Adjacencies not found for square " + allGridSquares.indexOf(gridSquare) + ".");
            }
        }
        for(int i = 0; i < 1024; i++){
                grid[0][0].addObject(new Animal(this, "Default Animal", 1, "A default animal", "Animal", 2, '@', Color.WHITE, grid[0][0], 10, 10, 4, 100));
        }
        //grid[0][0].addObject(new Animal(this, "Default Animal", 1, "A default animal", "Animal", 0, 'X', Color.GRAY, grid[0][0], 10));
        
        //grid[1][3].addObject(new GridObject("Default Object 1",1,"A default object. You should not be seeing this.","DefaultType",0,'+',Color.MAGENTA, grid[1][3]));
    }
    public GridSquare[][] getGrid() {
        return grid;
    }
    public GridSquare[] getAllGridSquares(){
        GridSquare[] gridSquares = new GridSquare[allGridSquares.size()];
        return (GridSquare[])allGridSquares.toArray(gridSquares);
    }
    public String getName() {
        return name;
    }
}
