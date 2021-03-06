/**
 * @author Feildon Hampton
 */
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class LifeParallel
{
    private static int MAX_THREADS = 4;
    
    public static int mRow = 30;
    public static int mCol = 30;
    //find middle of row array
    public int mid = mRow/MAX_THREADS;
    //find the middle of the first half of the row array
    public int aMid = mid/2;
    //find the middle of the second half of the row array
    public int bMid = aMid + mid;
    public static int[][] firstArray;
    public static int[][] newArray;
    
    
    public LifeParallel()
    {
    }
 
    //Create a random starting matrix for the game
    public void createFirstMatrix()
    {
        Random random = new Random();
        firstArray = new int[mRow][mCol];
        newArray = new int[mRow][mCol];
        for (int i = 0; i < mRow; i++)
        {
            for (int j = 0; j < mCol; j++)
            {
                int randomInt = random.nextInt(20);
                if (randomInt % 10 == 0)
                {
                    firstArray[i][j] = 1;
                } else {
                    firstArray[i][j] = 0;
                }
                
            }
        }
    }
    
    //overriding the run command for threads
    public void run()
    {
        Thread t1 = new Thread(new computeMatrix(0, aMid));
        Thread t2 = new Thread(new computeMatrix(aMid+1, mid));
        Thread t3 = new Thread(new computeMatrix(mid+1, bMid));
        Thread t4 = new Thread(new computeMatrix(bMid+1, mRow));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
    //print ou the matrix in console for debugging if required
    public void printMatrix(int[][] matrix)
    {
    
        for (int j = 0; j < mRow; j++)
        {
            System.out.print("|");
            for (int k = 0; k < mCol; k++)
            {
                if (matrix[j][k] == 0)
                {
                    System.out.print("-");
                } else {
                    System.out.print("X");
                }
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println();
    }
}


//class to compute the rules of the game
class computeMatrix implements Runnable
{
    private int j;
    private int endRow;
    public computeMatrix(int j, int endRow)
    {
        this.j = j;
        this.endRow = endRow;
    }
    
     public void run()
    {
        int deadOrAlive;
        for (j = 0; j < endRow; j++)
        {
            for (int k = 0; k < LifeParallel.mCol; k++)
            {
                deadOrAlive = checkPosition(j, k, LifeParallel.firstArray);
                if (LifeParallel.firstArray[j][k] == 1 && deadOrAlive == 2)
                {
                    LifeParallel.newArray[j][k] = 1;

                } else if (deadOrAlive == 3)
                {

                    LifeParallel.newArray[j][k] = 1;
                } else
                {

                    LifeParallel.newArray[j][k] = 0;
                }
            }
        }
    }
     
    //Check whether the position in question is on the left/right/top/bottom
    //side or if its one of the 4 corners or is a middle position to identify
    //which nearby positions to look at.
    public int checkPosition(int row, int col, int[][] oldArray)
    {
        int deadoralive = 0;
        if (row == 0 && col == 0)
        {
            deadoralive = upperLeft(row, col, oldArray);
        } else if (row == 0 && col != 0 && col != (LifeParallel.mCol-1))
        {
            deadoralive = topEdge(row, col, oldArray);
        } else if (row == 0 && col == (LifeParallel.mCol-1))
        {
            deadoralive = upperRight(row, col, oldArray);
        } else if (row != 0 && row != (LifeParallel.mRow-1) && col == 0)
        {
            deadoralive = leftEdge(row, col, oldArray);
        } else if (row != 0 && row != (LifeParallel.mRow-1) && col == (LifeParallel.mCol-1))
        {
            deadoralive = rightEdge(row, col, oldArray);
        } else if (row == (LifeParallel.mRow-1) && col == 0)
        {
            deadoralive = bottomLeft(row, col, oldArray);
        } else if (row == (LifeParallel.mRow-1) && col != 0 && col != (LifeParallel.mCol-1))
        {
            deadoralive = bottomEdge(row, col, oldArray);
        } else if (row == (LifeParallel.mRow-1) && col == (LifeParallel.mCol-1))
        {
            deadoralive = bottomRight(row, col, oldArray);
        } else if (row > 0 && row < (LifeParallel.mRow-1) && col > 0 && col < (LifeParallel.mCol-1))
        {
            deadoralive = insideEdges(row, col, oldArray);
        }
        return deadoralive;
    }
     
    public int upperLeft(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col+1] == 1)
        {
            count++;
        } 
        if (oldArray[row+1][col] == 1)
        {
            count++;
        } 
        if (oldArray[row][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        } 
        if (oldArray[row+1][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        } 
        if (oldArray[row+(LifeParallel.mRow-1)][col] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
            return count;
        } else {
            return 0;
        }
    }

    public int topEdge(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col-1] == 1)
        {
            count++;
        } 
        if (oldArray[row+1][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col+1] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
              return count;
        } else {
            return 0;
        }
    }

    public int upperRight(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col] == 1)
        {
            count++;
        }
        if (oldArray[row][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+(LifeParallel.mRow-1)][col] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
             return count;
        } else {
            return 0;
        }
    }

    public int leftEdge(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
             return count;
        } else {
            return 0;
        }
    }

    public int rightEdge(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col-1] == 1)
        {
            count++;
        }
       if (count == 2 || count == 3)
        {
              return count;
        } else {
            return 0;
        }
    }

    public int bottomLeft(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col+(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
              return count;
        } else {
            return 0;
        }
    }

    public int bottomEdge(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col-1] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
              return count;
        } else {
            return 0;
        }
    }

    public int bottomRight(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col] == 1)
        {
            count++;
        }
        if (oldArray[row-(LifeParallel.mRow-1)][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col-(LifeParallel.mCol-1)] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col-1] == 1)
        {
            count++;
        }
        if (count == 2 || count == 3)
        {
              return count;
        } else {
            return 0;
        }
    }

    public int insideEdges(int row, int col, int[][] oldArray) {
        int count = 0;
        if (oldArray[row][col-1] == 1)
        {
            count++;
        } 
        if (oldArray[row+1][col-1] == 1)
        {
            count++;
        }
        if (oldArray[row+1][col] == 1)
        {
            count++;
        } 
        if (oldArray[row+1][col+1] == 1)
        {
            count++;
        } 
        if (oldArray[row][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col+1] == 1)
        {
            count++;
        }
        if (oldArray[row-1][col] == 1)
        {
            count++;
        } 
        if (oldArray[row-1][col-1] == 1)
        {
            count++;
        }
       if (count == 2 || count == 3)
        {
              return count;
        } else {
            return 0;
        }
    }

}