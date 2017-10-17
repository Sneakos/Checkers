import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Picture extends JPanel
{
    static int x, r, c;
    static boolean clicked, update;
    ArrayList<Integer> rows, cols, rows2, cols2;

    public Picture()
    {
        x = r = c = 0;
        rows = new ArrayList<Integer>();
        cols = new ArrayList<Integer>();
        rows2 = new ArrayList<Integer>();
        cols2 = new ArrayList<Integer>();

        MouseAdapter mouseHandler;
        mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                x = e.getX();
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e){
                if(Four.turn == 1)
                {   clicked = true;
                    repaint();
                }
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Image board = null;
        Image black = null;
        Image red = null;
        Image red1 = null;
        Image black1 = null;
        int loc[] = new int[7];
        for(int i = 0 ; i < loc.length; i++)
            loc[i] = (10-i) + Math.round(getWidth() / 7) * i;
        int locs[] = new int[6];
        for(int i = 0; i < locs.length; i++)
            locs[i] = 75 + (5+i) + Math.round((getHeight()-75) / 6) * i;

        int i = getIndex(loc);

        try {
            board = ImageIO.read(new File("ConnectFour.png"));
            black = ImageIO.read(new File("Black.png"));
            red = ImageIO.read(new File("Red.png"));
            black1 = ImageIO.read(new File("Black2.png"));
            red1 = ImageIO.read(new File("Red2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        if(i < loc.length)
            g2.drawImage(red, loc[i], 2, 70, 70, null);
        g2.drawImage(board, 0, 75, getWidth(), getHeight() - 75, null);
        for(int o = 0; o < rows.size(); o++)
            g2.drawImage(red1, loc[rows.get(o)], locs[cols.get(o)], 70, 70, null);

        for(int o = 0; o < rows2.size(); o++)
            g2.drawImage(black1, loc[rows2.get(o)], locs[cols2.get(o)], 70, 70, null);
        if(i < loc.length && clicked && Four.turn == 1)
        {
            int j = placeChecker(i);
            g2.drawImage(red1, loc[i], locs[j], 70, 70, null);
            rows.add(i);
            cols.add(j);
            Four.board[j][i] = 1;
            Four.turn = 2;
            repaint();
            Four.turn();
            clicked = false;
        }
        if(update)
        {
            g2.drawImage(black1, loc[c], locs[r], 70, 70, null);
            rows2.add(c);
            cols2.add(r);
            update = false;
        }
    }

    public int getIndex(int loc[])
    {
        int i = 0;

        while(i < loc.length && x > loc[i])
            i++;
        if(i > 0) i--;
        return i;
    }

    public int placeChecker(int col)
    {
        int row = Four.getDepth(col, Four.board);

        return row;
    }
}