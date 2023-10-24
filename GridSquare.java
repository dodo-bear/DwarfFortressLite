import java.awt.*;
import java.util.*;

public class GridSquare {

    int xCoord;
    int yCoord;
    GraphicsEntity graphicsEntity = new GraphicsEntity();
    GridSquare upSquare = null;
    GridSquare downSquare = null;
    GridSquare leftSquare = null;
    GridSquare rightSquare = null;
    GridSquare[] neighbors = new GridSquare[4];
    ArrayList<GridObject> objects = new ArrayList<GridObject>();
    WorldGrid world;
    char displayChar = '\u2588';
    Color displayColor = Color.MAGENTA;

    public GridSquare(int x, int y, WorldGrid world) {
        xCoord = x;
        yCoord = y;
        this.world = world;
        refreshDisplayInfo();
    }
    private GridObject[] objectArrayConvert(Object[] startingObjects){
        GridObject[] gridObjects = Arrays.copyOf(startingObjects, startingObjects.length, GridObject[].class);
        return gridObjects;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public GridObject[] getObjects() {

        return objectArrayConvert(objects.toArray());
    }

    public GridObject[] findObjectsWithType(String type) {
        ArrayList<GridObject> objs = new ArrayList<GridObject>();
        for (GridObject obj : objects) {
            if (obj.getType().equals(type)) {
                objs.add(obj);
            }
        }
        return Arrays.copyOf(objs.toArray(), objs.toArray().length, GridObject[].class);
    }

    public void findAdjacencies(GridSquare[][] grid) {
        try {
            upSquare = grid[this.xCoord -1][this.yCoord];
        } catch (Exception e) {
            upSquare = null;
        }
        try {
            downSquare = grid[this.xCoord +1][this.yCoord];
        } catch (Exception e) {
            downSquare = null;
        }
        try {
            rightSquare = grid[this.xCoord][this.yCoord +1];
        } catch (Exception e) {
            rightSquare = null;
        }
        try {
            leftSquare = grid[this.xCoord][this.yCoord-1];
        } catch (Exception e) {
            leftSquare = null;
        }
        neighbors = new GridSquare[] { upSquare, downSquare, leftSquare, rightSquare };
    }

    public void refreshDisplayInfo() {
        ArrayList<Integer> priorities = new ArrayList<Integer>();
        if(this.getObjects().length < 1){
            addObject(new GridObject("Background", 1, "A default object. You should not be seeing this.", "Background", 7, '\u2588',Color.GREEN, this));
        }
        for (GridObject obj : this.getObjects()) {
            priorities.add(obj.getViewPriority());
        }
        displayChar = getObjects()[priorities.indexOf(Collections.min(Arrays.asList(objectToIntArray(priorities.toArray()))))].getDisplayChar();
        displayColor = getObjects()[priorities.indexOf(Collections.min(Arrays.asList(objectToIntArray( priorities.toArray()))))].getDisplayColor();

    }
    private Integer[] objectToIntArray( Object[] objectArray){
        Integer[] integers = Arrays.copyOf(objectArray, objectArray.length, Integer[].class);
        return integers;
    }

    public void addObject(GridObject obj) {
        if(this.findObjectsWithType("Structure").length == 0){
            objects.add(obj);
        }
    }

    public void removeObject(GridObject obj) {
        objects.remove(obj);
    }

    public void clearObjects() {
        objects.clear();
    }

    public GridSquare[] getNeighbors() {
        return neighbors;
    }

    public GraphicsEntity getGraphics() {
        graphicsEntity = new GraphicsEntity(displayChar, displayColor);
        return graphicsEntity;
    }
}