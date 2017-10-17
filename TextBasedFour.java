
/**
 * Write a description of class Four here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class TextBasedFour
{
    static int rows = 6, cols = 7;
    static int board[][] = new int[rows][cols];
    static int turn; //user is turn=1, comp is turn=2
    static boolean win;
    static TextBasedAI comp;

    public static void main(String[] args)
    {
        //riggedBoard();
        printBoard();
        turn = 1;
        win = false;
        while(!win)
            turn();
        System.out.println("\nWinner!");
    }

    public static void turn()
    {
        Scanner scan = new Scanner(System.in);
        int col = -1; int row = -1;
        if(turn == 1)
        {
            /*
            comp = new AI(1);
            comp.minmax(board, 0, 2);
            col = comp.choice;
            row = getDepth(col,board);
            */
            while(row == -1)
            {
                System.out.print("\nEnter column (-1 to quit): "); //no error checking
                try{
                    col = scan.nextInt();
                } catch (InputMismatchException e)
                {
                    return;
                }
                if(col == -1)
                {
                    win = true;
                    return;
                }
                row = getDepth(col, board);
            }
            
        } else {
            
            comp = new TextBasedAI(2);
            comp.minmax(board, 1, 1);
            col = comp.choice;
            row = getDepth(col,board);
            /*
            row = -1;
            while(row == -1)
            {
                System.out.print("\nEnter column (-1 to quit): ");
                try{
                    col = scan.nextInt();
                } catch (InputMismatchException e)
                {
                    return;
                }
                if(col == -1)
                {
                    win = true;
                    return;
                }
                row = getDepth(col, board);
            }
            */
        }
        board[row][col] = turn;
        check(row, col, board, turn);
        if(turn == 1)
            turn = 2;
        else
            turn = 1;
        System.out.println("\f");
        printBoard();
        if(win)
            return;
    }

    public static int getDepth(int col, int[][] board)
    {
        if(col < 0 || col >= cols) 
            return -1;
        int row = rows -1;
        while(board[row][col] > 0)
        {
            row--;
            if(row < 0)
                return -1;
        }

        return row;
    }

    public static int check(int row, int col, int board[][], int turn)
    {
        if(row < 0 || col < 0)
            return 0;
        int iMod, jMod, x;
        iMod = jMod = -1;
        win = false;
        for(int i = row - 1; i <= row + 1; i++)
        {
            for(int j = col - 1; j <= col + 1; j++)
            {
                if(i >= 0 && i < rows && j >= 0 && j < cols) //simple bounds check
                {
                    if(i != row || j != col)
                    {
                        if(board[i][j] == turn)
                        {
                            chain(row, col, iMod, jMod, 0, turn, board);
                            if(win)
                                return turn;
                        }
                    }
                }
                jMod++;
            }
            iMod++;
            jMod = -1;
        }
        return 0;
    }

    public static int chain(int row, int col, int iMod, int jMod, int count, int turn, int[][] board)
    {
        row += iMod;
        col += jMod;
        if(row >= 0 && row < rows && col >= 0 && col < cols) //simple bounds check
        {
            if(board[row][col] == turn)
                count = chain(row, col, iMod, jMod, count, turn, board);
        }
        if(count == 2)
        {
            iMod *= -1; //reverse signs
            jMod *= -1; //reverse signs
            row += iMod; row += iMod;
            col += jMod; col += jMod;
            if(row >= 0 && row < rows && col >= 0 && col < cols) //simple bounds check
            {
                if(board[row][col] == turn)
                    count++;
            }
        }
        if(count == 3)
        {
            win = true;
            return 3;
        }
        return count + 1;
    }

    public static void printBoard()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
            {
                if(board[i][j] == 0)
                    System.out.print("[   ]");
                else if(board[i][j] == 1)
                    System.out.print("[ X ]");
                else
                    System.out.print("[ O ]");
            }
            System.out.println();
        }
        for(int i = 0; i < board[0].length; i++)
            System.out.print("  " + i + "  ");
    }

    public static void riggedBoard()
    {
        int game[][] = { {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1},
                {0, 0, 2, 0, 0, 0, 0},
                {2, 0, 2, 1, 0, 0, 0},
                {1, 2, 2, 2, 1, 2, 1},
                {1, 1, 1, 2, 2, 1, 1} };
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
                board[i][j] = game[i][j];
        }
    }
}