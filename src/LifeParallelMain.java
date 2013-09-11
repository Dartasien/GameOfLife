
/**
 * @author Feildon Hampton
 */
import java.awt.*;
import javax.swing.*;
public class LifeParallelMain{
    
    public int finish = 10;

       
    
    public static void main(String[] args)
    {       
        JFrame frame = new JFrame();
        LifeParallelJPanel lpjp = new LifeParallelJPanel();
        frame.getContentPane().add(lpjp);
        frame.setSize(lpjp.LS.mRow * 27, lpjp.LS.mCol * 28);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
