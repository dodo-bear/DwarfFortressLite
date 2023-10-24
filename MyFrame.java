import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {

  GridSquare[][] displayGrid = new GridSquare[32][32];
  char[][] graphicsGrid = new char[32][32];
  WorldGrid world;
  ControlHandler controls;
  GridLayout layout;
  JPanel mainPanel;
  JPanel paintPanel;
  JPanel textPanel;
  JPanel[][] panelHolder;

  MyFrame(String title, WorldGrid world) {
    setTitle(title);
    this.world = world;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    setSize ((int)size.getWidth(), (int)size.getHeight());
    setLocationRelativeTo(null);

    layout = new GridLayout(32, 32);
    panelHolder = new JPanel[32][32];
    
    mainPanel = new JPanel();
    mainPanel.setBackground(Color.BLACK);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    paintPanel = new JPanel();
    textPanel = new JPanel();
    paintPanel.setPreferredSize(new Dimension(4, 600));
    paintPanel.setBackground(Color.BLACK);
    textPanel.setBackground(Color.BLACK);
    mainPanel.add(paintPanel);
    mainPanel.add(textPanel);
    for(int i = 0; i < 32; i ++){
      for(int j = 0; j < 32; j ++)  {
        panelHolder[i][j] = new JPanel();
        panelHolder[i][j].setBackground(Color.BLACK);
        panelHolder[i][j].setPreferredSize(new Dimension(10, 10));
        paintPanel.add(panelHolder[i][j]);
        panelHolder[i][j].add(new JLabel(""));
        
      }
    }
    paintPanel.setLayout(layout);
    mainPanel.setFocusable(true);
    mainPanel.requestFocus();
    controls = new ControlHandler();
    mainPanel.addKeyListener(controls);
    this.add(mainPanel);
    mainPanel.setVisible(true);
    paintPanel.setVisible(true);
  }
  public void refreshGraphics(int startX, int startY){
    refreshGraphicsLabel(startX, startY);
  }
  public ControlHandler getControls(){
    return controls;
  }
  public String refreshGraphicsLabel(int startX, int startY){
    GridSquare[][] displayGrid = new GridSquare[32][32];
    StringBuilder finalString = new StringBuilder();
    int currentSquare = 0;
    int curX = 0;
      for (GridSquare[] gridSquareArray : displayGrid) {
        int curY = 0;
        for (int i = 0; i < gridSquareArray.length; i++) {
          try{
          displayGrid[curX][curY] = world.getGrid()[curX + startX][curY + startY];
          displayGrid[curX][curY].refreshDisplayInfo();
          JLabel curlabel = ((JLabel)panelHolder[curX][curY].getComponents()[0]);
          curlabel.setText(displayGrid[curX][curY].getGraphics().getDisplayChar() + "");
          curlabel.setForeground(displayGrid[curX][curY].getGraphics().getDisplayColor());
          currentSquare++;
          curY++;
          }catch(Exception e){
            System.out.println("Error in refreshGraphicsLabel at square " + currentSquare + ": ");
            e.printStackTrace();
          }
        }
        finalString.append("\n");
        curX++;
      }
      return(finalString.toString());
  }
}