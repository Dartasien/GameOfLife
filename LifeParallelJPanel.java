/**
 * @author Feildon Hampton
 */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

public class LifeParallelJPanel extends JPanel{
    LifeParallel LS = new LifeParallel();
    public LifeParallelJPanel()
    {
        LS.createFirstMatrix();
        LS.mRow = 30;
        LS.mCol = 30;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        
        super.paintComponent(g);
         int width = 15;
        int height = 15;
        int y = 35;
        //printMatrix(firstArray);
        long startTime = System.nanoTime();
        LS.run();
        long endTime = System.nanoTime();
        for (int j = 0; j < LS.mRow; j++)
        {
            int x = 5;
            for (int k = 0; k < LS.mCol; k++)
            {
                if (LS.newArray[j][k] == 1)
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y, width, height);
                } else
                {
                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, width, height);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);
                }
                x+=15;
            }
             y+= 15;
        }
        g.setColor(Color.RED);
        g.drawString("Parallel took " + (endTime - startTime) + " nanoseconds", 50, 15);
        LS.firstArray = LS.newArray.clone();
        repaint();
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
