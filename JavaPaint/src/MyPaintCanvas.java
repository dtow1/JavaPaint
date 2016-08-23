/**
 *@author Jason Boyer
 *Date  :  12/16/15
 *Assignment: Homework 5
 *Program Function: To create a drawing board program that allows the user to make their own custom drawings.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.SwingUtilities;




public class MyPaintCanvas extends Canvas implements MouseListener, 
MouseMotionListener, Serializable
{

    
    private boolean clearCanvas = false, drawSquare=false, 
            squareDraw=false, drawCircle = false, circleDraw=false, 
            fanDraw=false, dragging = false, drawLine = false,
            erase=false,fill=false, loadPoints=false;
    private Point firstPoint=null, secondPoint=null;
    private Point drag1 = null, drag2 = null,last = null;
    private String brush = "*";
    private ArrayList<Point> pointList = new ArrayList<Point>();
    private static final int X_SIZE = 600, Y_SIZE=500;
    private ArrayList<DrawingPoint> savePoints = new ArrayList<>();
    
    private static final long serialVersionUID = 1L;
    private Color currentColor;
    
    MyPaintCanvas()
    {
        currentColor = new Color(0,0,0);
        addMouseListener(this);
        addMouseMotionListener(this);
        setSize(X_SIZE,Y_SIZE);
    }
    public void paint(Graphics g) 
    {
        super.paint(g);
        update(g);       
    }
    
    public void loadList(ArrayList<DrawingPoint> points)
    {
        savePoints=points;
        loadPoints=true;
        firstPoint = null;
        secondPoint = null;
        repaint();

    }

    public ArrayList<DrawingPoint> getData() 
    {
        return savePoints;
    }
    
    public int getWidth(int firstx, int secondx) 
    {
        int width=firstx-secondx;
        return width < 0 ? -1*width: width;
    }
    
    public int getHeight(int firsty, int secondy)
    {
        int height = firsty-secondy;
        return height < 0 ? -1*height: height;
    }
    public int getx1(int firstx, int secondx)
    {
        return firstx<secondx ? firstx : secondx;
    }
    
    public int gety1(int firsty, int secondy)
    {
        return firsty<secondy ? firsty : secondy;
    }

    
    @Override
    public void update(Graphics g) 
    {   
        g.setColor(currentColor);
        if(loadPoints)
        {
            DrawingPoint tempPoint;
            boolean first = true;
            Graphics2D g2 = (Graphics2D)g;
            for(int i = 0; i<savePoints.size(); i++)
            {   
                tempPoint=savePoints.get(i);
                if(tempPoint!=null)
                {
                    g.setColor(tempPoint.getColor());
                    switch(tempPoint.getBrush())
                    {
                        case "Thin Line":
                            if(first)
                            {   if(!(tempPoint.getPoint()==null))
                                {
                                    drag1=tempPoint.getPoint();
                                    i++;
                                    drag2=savePoints.get(i).getPoint();
                                    first=false;
                                }
                            }
                            else
                            {
                                drag1=drag2;
                                drag2=tempPoint.getPoint();
                            }
                            g2.setStroke(new BasicStroke(2));
                            g2.drawLine(drag1.x,drag1.y,
                                drag2.x,
                                drag2.y);
                            break;
                        case "Medium Line":
                            if(first)
                            {
                                drag1=tempPoint.getPoint();
                                i++;
                                drag2=savePoints.get(i).getPoint();
                                first=false;
                            }
                            else
                            {
                                drag1=drag2;
                                drag2=tempPoint.getPoint();
                            }
                            g2.setStroke(new BasicStroke(10));
                            g2.drawLine(drag1.x,drag1.y,
                                drag2.x,
                                drag2.y);
                            break;
                        case "Thick Line":
                            if(first)
                            {
                                drag1=tempPoint.getPoint();
                                i++;
                                drag2=savePoints.get(i).getPoint();
                                first=false;
                            }
                            else
                            {
                                drag1=drag2;
                                drag2=tempPoint.getPoint();
                            }
                            g2.setStroke(new BasicStroke(20));
                            g2.drawLine(drag1.x,drag1.y,
                                drag2.x,
                                drag2.y);
                            break;
                        case "Thin Fan":
                            if(first)
                            {
                                drag1=tempPoint.getPoint();
                                first=false;
                            }else
                            {
                                drag2=tempPoint.getPoint();
                                g2.setStroke(new BasicStroke(2));
                                g2.drawLine(drag1.x,drag1.y,
                                        drag2.x,
                                        drag2.y);
                            }
                            break;
                        case "Medium Fan":
                            if(first)
                            {
                                drag1=tempPoint.getPoint();
                                first=false;
                            }else
                            {
                                drag2=tempPoint.getPoint();
                                g2.setStroke(new BasicStroke(10));
                                g2.drawLine(drag1.x,drag1.y,
                                        drag2.x,
                                        drag2.y);
                            }
                            break;
                        case "Thick Fan":
                            if(first)
                            {
                                drag1=tempPoint.getPoint();
                                first=false;
                            }else
                            {
                                drag2=tempPoint.getPoint();
                                g2.setStroke(new BasicStroke(20));
                                g2.drawLine(drag1.x,drag1.y,
                                        drag2.x,
                                        drag2.y);
                            }
                            break;
                        case "Solid Square":
                            if(first)
                            {
                                firstPoint=tempPoint.getPoint();
                                first=false;
                            }
                            else
                            {
                                secondPoint=tempPoint.getPoint();
                                int x1 = firstPoint.x, x2 = secondPoint.x;
                                int y1 = firstPoint.y, y2 = secondPoint.y;
                                
                                g.fillRect(getx1(x1,x2), gety1(y1,y2), 
                                        getWidth(x1,x2), getHeight(y1,y2));

                                firstPoint=null;
                                secondPoint=null;
                            }
                            
                            break;
                        case "Square":
                            if(first)
                            {
                                firstPoint=tempPoint.getPoint();
                                first=false;
                            }
                            else
                            {
                                secondPoint=tempPoint.getPoint();
                                int x1 =(int)firstPoint.getX(), 
                                        x2 = (int)secondPoint.getX();
                                int y1 =(int)firstPoint.getY(), 
                                        y2 = (int)secondPoint.getY();
                                
                                g.drawRect(getx1(x1,x2), gety1(y1,y2), 
                                        getWidth(x1,x2), getHeight(y1,y2));
                                firstPoint=null;
                                secondPoint=null;
                            }
                            break;
                        case "Solid Circle":
                            if(first)
                            {
                                firstPoint=tempPoint.getPoint();
                                first=false;
                            }
                            else
                            {
                                secondPoint=tempPoint.getPoint();
                                int x1 =(int)firstPoint.getX(), 
                                        x2 = (int)secondPoint.getX();
                                int y1 =(int)firstPoint.getY(), 
                                        y2 = (int)secondPoint.getY();
                                
                                g.fillOval(getx1(x1,x2), gety1(y1,y2), 
                                        getWidth(x1,x2), getHeight(y1,y2));
                                firstPoint=null;
                                secondPoint=null;
                            }
                            break;
                        case "Circle":
                            if(first)
                            {
                                firstPoint=tempPoint.getPoint();
                                first=false;
                            }
                            else
                            {
                                secondPoint=tempPoint.getPoint();
                                int x1 =(int)firstPoint.getX(), 
                                        x2 = (int)secondPoint.getX();
                                int y1 =(int)firstPoint.getY(), 
                                        y2 = (int)secondPoint.getY();
                                
                                g.drawOval(getx1(x1,x2), gety1(y1,y2), 
                                        getWidth(x1,x2), getHeight(y1,y2));
                                firstPoint=null;
                                secondPoint=null;
                            }
                            break;
                        case "Erase":
                            g.clearRect(tempPoint.getPoint().x, 
                                    tempPoint.getPoint().y, 10, 10);
                            break;
                        default:
                            g.setColor(tempPoint.getColor());
                            g.drawString(tempPoint.getBrush(), 
                                    tempPoint.getX(), tempPoint.getY());
                            break;
                    }
                }
                else if(tempPoint==null)
                {
                    first=true;
                    drag1=null;
                    drag2=null;
                }
            }
            loadPoints=false;
        }else
        if(clearCanvas)
        {
            System.out.println(this.getSize());
            g.clearRect(0,0,this.getSize().width,this.getSize().height);
            //g.clearRect(0,0,X_SIZE,Y_SIZE);
            clearCanvas=false;
        }else if(erase)
        {
            g.clearRect(drag2.x, drag2.y, 10, 10);
        }else if(drawSquare||drawCircle)
        {
            int width = firstPoint.x-secondPoint.x;
            int height = firstPoint.y-secondPoint.y;
            int x1 = firstPoint.x<secondPoint.x ? firstPoint.x : 
                secondPoint.x;
            int y1 = firstPoint.y<secondPoint.y ? firstPoint.y : 
                secondPoint.y;

            height = height < 0 ? -1*height : height;
            width = width < 0 ? -1*width : width;   


            if(drawSquare)
            {
                if(!fill)
                {
                    g.drawRect(x1, y1, width, height);
                }
                else
                {
                    g.fillRect(x1, y1, width, height);
                }
                drawSquare=false;
            }
            else if(drawCircle)
            {
                if(!fill)
                {
                    g.drawOval(x1, y1, width, height);
                }
                else
                {
                    g.fillOval(x1,y1,width,height);
                }
                drawCircle=false;

            }

            clearPoints();
        }
        else if(dragging&&drawLine)
        {
            Graphics2D g2 = (Graphics2D)g;
            switch(brush)
            {

            case "Thin Line":   
                                if(!fanDraw)
                                {
                                    while(pointList.size()>0)
                                    {
                                        //first time
                                        if(last==null)
                                          last=pointList.remove(0);
                                        if(pointList.size()>0)
                                        {
                                            g2.setStroke(new BasicStroke(2));
                                            g2.drawLine(last.x,last.y,
                                                pointList.get(0).x,
                                                pointList.get(0).y);
                                            last=pointList.remove(0);
                                        }
                                    }
                                } 
                                
                                else
                                {
                                    g2.setStroke(new BasicStroke(2));
                                    g2.drawLine(drag1.x,drag1.y,
                                            drag2.x,drag2.y);
                                }
                                break;
            case "Thick Line":  
                                if(!fanDraw)
                                {
                                    while(pointList.size()>0)
                                    {
                                        if(last==null)
                                            last=pointList.remove(0);
                                        if(pointList.size()>0)
                                        {
                                            g2.setStroke(new BasicStroke(20));
                                            g2.drawLine(last.x,last.y,
                                                    pointList.get(0).x,
                                                    pointList.get(0).y);
                                            last=pointList.remove(0);
                                        }
                                    }
                                }
                                else
                                {
                                    g2.setStroke(new BasicStroke(20));
                                    g2.drawLine(drag1.x,drag1.y,
                                            drag2.x,drag2.y);
                                }
                                break;
            case "Medium Line":                                 
                                if(!fanDraw)
                                {
                                    while(pointList.size()>0)
                                    {
                                        if(last==null)
                                            last=pointList.remove(0);
                                        if(pointList.size()>0)
                                        {    
                                            g2.setStroke(new BasicStroke(10));
                                            g2.drawLine(last.x,last.y,
                                                    pointList.get(0).x,
                                                    pointList.get(0).y);
                                            last=pointList.remove(0);
                                        }
                                    }
                                }
                                else
                                {
                                    g2.setStroke(new BasicStroke(10));
                                    g2.drawLine(drag1.x,drag1.y,
                                            drag2.x,drag2.y);
                                }
                                break;
            default:
                    g.drawString(brush, drag1.x, drag1.y);
            }
        }

    }
    public void load() 
    {
        repaint();
    }
    
    public void setBrush(String newBrush)
    {
        brush = newBrush;
    }
    
    public void clearPoints()
    {
        firstPoint=null;
        secondPoint=null;
        drag1=null;
        drag2=null;
    }
    public void setColor(Color newColor) 
    {
        currentColor = newColor;
        repaint();
    }
    
    public void testString(Graphics g) 
    {
        g.drawString("TESTING", 50, 50);
    }
    
    public void setSquare()
    {
        squareDraw = squareDraw ? false : true;
        circleDraw=false;
        fanDraw=false;
        brush = "Thin Line";
    }
    
    public void setCircle()
    {
        circleDraw = circleDraw ? false : true;
        squareDraw=false;
        fanDraw=false;
        brush = "Thin Line";
    }
    
    public void setFan()
    {
        fanDraw = fanDraw ? false : true;
        squareDraw=false;
        circleDraw=false;
        brush = "Thin Line";
    }
    
    public void draw(Point start, Point end)
    {
        firstPoint = start;
        secondPoint = end;
        repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0)
    {

        if(SwingUtilities.isRightMouseButton(arg0))
        {
            fill=true;
        }
        else
        {
            fill = false;
        }
        if(squareDraw||circleDraw)
        {
            
            if(firstPoint==null)
            {
                firstPoint=arg0.getPoint();
            }else if(firstPoint!=null)
            {
                secondPoint=arg0.getPoint();
                if(squareDraw)
                {
                    drawSquare=true;
                    draw(firstPoint, secondPoint);
                    if(fill==true)
                    {
                        savePoints.add(new DrawingPoint(currentColor,
                                "Solid Square",firstPoint));
                        savePoints.add(new DrawingPoint(currentColor,
                                "Solid Square",secondPoint));
                        savePoints.add(null);
                    }
                    else
                    {
                        savePoints.add(new DrawingPoint(currentColor,
                                "Square",firstPoint));
                        savePoints.add(new DrawingPoint(currentColor,
                                "Square",secondPoint));
                        savePoints.add(null);
                    }

                }else if(circleDraw)
                {
                    drawCircle=true;
                    draw(firstPoint, secondPoint);
                    if(fill==true)
                    {
                        savePoints.add(new DrawingPoint(currentColor,
                                "Solid Circle",firstPoint));
                        savePoints.add(new DrawingPoint(currentColor,
                                "Solid Circle",secondPoint));
                        savePoints.add(null);
                    }
                    else
                    {
                        savePoints.add(new DrawingPoint(currentColor,
                                "Circle",firstPoint));
                        savePoints.add(new DrawingPoint(currentColor,
                                "Circle",secondPoint));
                        savePoints.add(null);

                    }
                }

            }
            
        }

        
    }
    
    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        dragging=true;
        drawLine=true;
        if(pointList.size()>0)
        {
            pointList.clear();
        }
  
        drag1= arg0.getPoint();
        
    }
    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        if(dragging=true)
        {
            savePoints.add(null);
        }
        dragging=false;
        erase=false;
        last=null;
        drawLine=true;

        

        
    }
    @Override
    public void mouseDragged(MouseEvent arg0)
    {
        drag2=arg0.getPoint();
        if(SwingUtilities.isLeftMouseButton(arg0))
        {
            drawLine=true;
            if(!fanDraw)
            {
                if(brush.contains("Line"))
                {
                    pointList.add(drag2);
                }
                else
                {
                    drag1=drag2;
                }
                savePoints.add(
                        new DrawingPoint(currentColor,brush,drag2));
            }
            else if(fanDraw)
            {
                if(!brush.contains("Line"))
                {
                    setFan();
                    savePoints.add(
                            new DrawingPoint(currentColor,brush,drag2));
                }
                else
                {
                    if(brush.equals("Thin Line"))
                    {
                        savePoints.add(
                                new DrawingPoint(currentColor,
                                        "Thin Fan",drag2));
                    }
                    else if(brush.equals("Medium Line"))
                    {
                        savePoints.add(
                                new DrawingPoint(currentColor,
                                        "Medium Fan",drag2));
                    }
                    else if(brush.equals("Thick Line"))
                    {
                        savePoints.add(
                                new DrawingPoint(currentColor,
                                        "Thick Fan",drag2));
                    }
                }
                
            }

        }
        
        if(SwingUtilities.isRightMouseButton(arg0))
        {
            drawLine=false;
            erase=true;
            savePoints.add(new DrawingPoint(currentColor,"Erase",drag2));
        }
        
        repaint();
        
    }
    
    public void clear()
    {
        clearCanvas=true;
        repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }
    

}
