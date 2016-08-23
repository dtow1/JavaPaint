/**
 *@author Jason Boyer
 *Date  :  12/16/15
 *Assignment: Homework 5
 *Program Function: To create a drawing board program that allows the user to make their own custom drawings.
 */


import java.awt.*;
import java.io.Serializable;


public class DrawingPoint implements Serializable
{
    private Point dataPoint;
    private String brush;
    private Color color;
    
    DrawingPoint()
    {
        color = Color.BLACK;
        brush = "*";
        setDataPoint(new Point(0,0));
    }

    DrawingPoint(Color newColor, String newBrush, Point newPoint)
    {
        color = newColor;
        brush = newBrush;
        dataPoint = newPoint;
    }
    
    public Point getDataPoint()
    {
        return dataPoint;
    }
    
    public int getX()
    {
        return dataPoint.x;
    }
    
    public int getY()
    {
        return dataPoint.y;
    }
    
    public Point getPoint()
    {
        return dataPoint;
    }

    public void setDataPoint(Point dataPoint)
    {
        this.dataPoint = dataPoint;
    }
    
    public String getBrush()
    {
        return brush;
    }

    public void setBrush(String brush)
    {
        this.brush = brush;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

}
