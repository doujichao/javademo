package thread.swing.事件;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class MouseComponent extends JComponent {

    private static final int DEFAULT_WIDTH=300;
    private static  final int DEFAULT_HEIGHT=200;

    private static final int SIDELENGTH=10;
    private ArrayList<Rectangle2D> squares;
    private Rectangle2D current;
    public MouseComponent(){
        squares=new ArrayList<>();
        current=null;

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());

    }

    public Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    private class MouseHandler extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            current=find(e.getPoint());
            if (current==null) add(e.getPoint());
        }

        public void mouseClicked(MouseEvent event){
            current=find(event.getPoint());
            if (current!=null&&event.getClickCount() >2)remove(current);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D)g;
        for (Rectangle2D r:squares){
            g2.draw(r);
        }
    }

    /**
     * Find the first square containing a point
     * @param p a point
     * @return the first square that contains p
     */
    public Rectangle2D find(Point2D p){
        for (Rectangle2D r:squares){
            if (r.contains(p))return r;
        }
        return null;
    }

    /**
     * Adds a square to collection
     */
    public void add(Point2D p){
        double x=p.getX();
        double y = p.getY();

        current=new Rectangle2D.Double(x-SIDELENGTH/2,
                y-SIDELENGTH/2,SIDELENGTH,SIDELENGTH);
        squares.add(current);
        repaint();
    }

    /**
     * Removes a square from the collection
     * @param p the square to move
     */
    public void remove(Rectangle2D p){
        if (p==null)return;
        if (p == current)current=null;
        squares.remove(p);
        repaint();
    }

    private class MouseMotionHandler implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            if (current!=null){
                int x = e.getX();
                int y = e.getY();
                current.setFrame(x-SIDELENGTH/2,y-SIDELENGTH/2,SIDELENGTH,SIDELENGTH);
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (find(e.getPoint())==null) setCursor(Cursor.getDefaultCursor());
            else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }
}
