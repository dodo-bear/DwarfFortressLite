import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
/*ViewPriority Layers:
 * 0. UI elements
 * 1. Dwarves
 * 2. Animal subtypes
 * 3. Large objects
 * 4. Items
 * 5. Structures
 * 6. Terrain
 * 7. Background
*/
public class GameManager {
    static long last_time = System.nanoTime();
    static long elapsedTime;
    static boolean isRunning = true;
    static MyFrame window;
    static boolean paused = false;
    static ControlHandler controls;
    public static WorldGrid world;
    public static void main(String[] args) {
        try{
        world = new WorldGrid("world");
        }catch (Exception e){
            System.out.println("World not created: " + e.getMessage());
        }
        System.out.println("World Created");
        try{
        window = new MyFrame("DwarfFortressLite", world);
        window.setVisible(true);
        controls = window.getControls();
        }catch(Exception e){
            System.out.println("Window not created: " + e.getMessage());
        }
        System.out.println("Window Open");
        run();
    }
    private static Point checkCoords(int x, int y){
        int xCoord = 0;
        int yCoord = 0;
        if(x > 224){xCoord = 224;}else if(x < 0){xCoord = 0;}else{xCoord = x;}
        if(y > 224){yCoord = 224;}else if(y < 0){yCoord = 0;}else{yCoord = y;}
        return new Point(xCoord, yCoord);
    }

    public static void run() {
		long lastime = System.nanoTime();
		double AmountOfTicks = 5;
		double ns = 1000000000 / AmountOfTicks;
		double delta = 0;
		int frames = 0;
        int ticks = 0;
		double time = System.currentTimeMillis();
		double x = 0;
        double y = 0;
		while(isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastime) / ns;
			lastime = now;
            frames++;
            x += controls.getYMove() * delta;
            y += controls.getXMove() * delta;
            window.refreshGraphics(checkCoords((int)x, (int)y).x, checkCoords((int)x, (int)y).y);
			if(delta >= 1) {
                if(paused == false){
                    Update();
                    
                }
				ticks++;
				delta--;
				if(System.currentTimeMillis() - time >= 1000) {
					System.out.println("fps: " + frames + " tps: " + ticks);
					time += 1000;
					ticks = 0;
				}
            frames = 0;
			}
		}
	}
    public static void Update() {
        ArrayList<Animal> animals = new ArrayList<Animal>();
        for (GridSquare gridSquare : world.getAllGridSquares()) {
            for (Animal animal : Arrays.copyOf(gridSquare.findObjectsWithType("Animal"), gridSquare.findObjectsWithType("Animal").length, Animal[].class)) {
                if(!animals.contains(animal)){
                    animal.Move();
                    animals.add(animal);
                }
            }
        }
        //System.out.println("Animals: " + animals.size() + " Average Age: " + averageAge/animals.size());
        ArrayList<FruitPlant> fruitTrees = new ArrayList<FruitPlant>();
        for (GridSquare gridSquare : world.getAllGridSquares()) {
            for (FruitPlant fruitPlant : Arrays.copyOf(gridSquare.findObjectsWithType("Plant"), gridSquare.findObjectsWithType("Plant").length, FruitPlant[].class)) {
                if(!fruitTrees.contains(fruitPlant)){
                fruitPlant.grow();
                fruitTrees.add(fruitPlant);
                }
            }
        }
    }
}