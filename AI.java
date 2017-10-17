
/**
 * Write a description of class AI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;

public class AI
{
    int choice;
    final int maxDepth = 7;
    int turn;

    public AI(int turn)
    {
        choice = 0;
        this.turn = turn;
    }

    public int minmax(int board[][], int depth, int player)
    {
        if(player == 1)
            player = 2;
        else
            player = 1;
        int x;
        int result = score(board,depth,player);
        if(full(board) || depth == maxDepth || result != 0)
            return result;
            
        ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<Integer> moves = new ArrayList<Integer>();
        ArrayList<Integer> spaces = getMoves(board);
        
        for(int i = 0; i < spaces.size(); i++)
        {
            int state[][] = getBoard(spaces.get(i), player, board);
            scores.add(minmax(state, depth+1, player));
            moves.add(spaces.get(i));
        }
        
        if(player == turn) //computer
        {
            if(depth == 0)
                x = 0;
            int max = max(scores);
            choice = moves.get(max);
            return scores.get(max);
        } else //player
        {
            if(depth == 1)
                x = 0;
            int min = min(scores);
            return scores.get(min);
        }
    }

    public int[][] getBoard(int col, int player, int[][] board)
    {
        int game[][] = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
            {
                game[i][j] = board[i][j];
            }
        }
        int row = Four.getDepth(col, board);
        game[row][col] = player;

        return game;
    }

    public int max(ArrayList<Integer> scores)
    {
        int max, maxIndex;
        ArrayList<Integer> moves = new ArrayList<Integer>();
        maxIndex = 0;
        max = -10;
        for(int i = 0; i < scores.size(); i++)
        {
            if(scores.get(i) == max)
                moves.add(i);
            if(scores.get(i) > max)
            {
                max = scores.get(i);
                maxIndex = i;
                moves.clear();
                moves.add(i);
            }
        }
        int random = (int)Math.floor(Math.random()*moves.size());
        return moves.get(random);
    }

    public int min(ArrayList<Integer> scores)
    {
        int min, minIndex;
        minIndex = 0;
        min = 10;
        for(int i = 0; i < scores.size(); i++)
        {
            if(scores.get(i) < min)
            {
                min = scores.get(i);
                minIndex = i;
            }
        }  
        return minIndex;
    }

    public int score(int[][] board, int depth, int player)
    {
        int win = 0;
        int row;
        for(int i = 0; i < board[0].length && win == 0; i++)
        {
            row = Four.getDepth(i, board) + 1;
            if(row <= 5)
                win = Four.check(row, i, board, board[row][i]);
        }
        if(win == turn) //player
            return 10-depth;
        else if(win == 0) //computer
            return 0;
        else
            return depth-10;
    }

    public boolean full(int[][] board)
    {
        for(int i = 0; i < board[0].length; i++)
        {
            if(board[0][i] == 0)
                return false;
        }
        return true;
    }

    public ArrayList<Integer> getMoves(int board[][])
    {
        ArrayList<Integer> moves = new ArrayList<Integer>();
        for(int i = 0; i < board[0].length; i++)
        {
            int row = board.length - 1;
            while(row >= 0 && board[row][i] > 0)
                row--;
            if(row >= 0)
                moves.add(i);
        }
        return moves;
    }
}
