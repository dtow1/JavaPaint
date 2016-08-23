/**
 *@author Jason Boyer
 *Date  :  12/16/15
 *Assignment: Homework 5
 *Program Function: To create a drawing board program that allows the user to make their own custom drawings.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;


public class DrawingBoard
{
    static MyPaintCanvas paintCanvas = new MyPaintCanvas();
    static boolean drawSquare,drawCircle,firstPoint;
    static Point point1,point2;
    
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(()-> new DrawingBoard());    
    }
    
    DrawingBoard()
    {
        JFrame board=new JFrame("Drawing Board");
        board.setSize(600,600);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setLocationRelativeTo(null);
        board.setResizable(false);
        board.setLayout(new BorderLayout());
        
        
        /*setup*/
        paintCanvas = new MyPaintCanvas();
        drawSquare = false;
        drawCircle = false;
        firstPoint = false;
        
        /*
         * Button panel for the top of the frame
         */
        JPanel topButtons = new JPanel();


        JButton circle = new JButton("Circle");
        JButton square = new JButton("Square");
        JButton fan = new JButton("Fan");
        
        topButtons.add(square);
        topButtons.add(circle);
        topButtons.add(fan);
        
        /*
         * Button Panel for the Bottom of the frame
         */
        
        
        JPanel bottomButtons = new JPanel();



        JButton exit = new JButton("Exit");
        JButton color = new JButton("Color");
        JButton save = new JButton("Save"); 
        JButton load = new JButton("Load");
        JButton clear = new JButton("Clear");
        JButton brush = new JButton("Brush");
        

        
        bottomButtons.add(save);
        bottomButtons.add(load);
        bottomButtons.add(color);
        bottomButtons.add(exit);
        bottomButtons.add(brush);
        bottomButtons.add(clear);
        
        
        /*add Tooltips
         * 
         */
        exit.setToolTipText("<html><h4>Exit</h4>Close the program.</html>");
        color.setToolTipText("<html><h4>Color</h4>Select the color to use in your current brush or shape.</html>");
        save.setToolTipText("<html><h4>Save</h4>Save your drawing to a file.</html>");
        load.setToolTipText("<html><h4>Load</h4>Load a previous drawing into the editor.</html>");
        clear.setToolTipText("<html><h4>Clear</h4>Reset your drawing to a black canvas.</html>");
        brush.setToolTipText("<html><h4>Brush</h4>Select the brush to paint with. Default is *. Hold down the right mouse button to erase the area near the mouse cursor</html>");
        circle.setToolTipText("<html><h4>Circle</h4>To draw a circle or an oval, press the mouse in two different locations to set the size of the circle. Left clicks for an empty circle, right clicks for a filled circle.</html>");
        square.setToolTipText("<html><h4>Square</h4>To draw a square or an rectangle, press the mouse in two different locations to set the size of the square. Left clicks for an empty square, right clicks for a filled square.</html>");
        fan.setToolTipText("<html><h4>Fan</h4>The fan button allows you to draw shapes that are anchored at one end. Click and hold the mouse button and you will draw lines that fan out about the point of the inital mouse press. This only works with the thick, thin, and medium brushes.</html>");
      
        /*
         * ActionListener for the buttons
         */
        
        ActionListener buttonListener = new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                // TODO Auto-generated method stub
                JButton temp = (JButton)arg0.getSource();
                
                if(temp.equals(color)) 
                {
                    paintCanvas.setColor(JColorChooser.showDialog(null, "Choose a Color",Color.BLACK));
                }
                else if(temp.equals(save))
                {
                    JFileChooser saveFile = new JFileChooser();
                    if(saveFile.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
                    {
                        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile.getSelectedFile())))
                        {
                            oos.writeObject(paintCanvas.getData());
                        } 
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else if(temp.equals(load))
                {
                    JFileChooser openFile = new JFileChooser();
                    if(openFile.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
                    {
                        ArrayList<DrawingPoint> tempPoint;
                        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(openFile.getSelectedFile())))
                        {
                            tempPoint = (ArrayList<DrawingPoint>) ois.readObject();
                            paintCanvas.loadList(tempPoint);
                            ois.close();
                            
                        } catch (IOException | ClassNotFoundException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        

                    }
                }
                else if(temp.equals(clear))
                {
                    paintCanvas.clear();

                }
                else if(temp.equals(square))
                {
                    paintCanvas.clearPoints();
                    paintCanvas.setSquare();

                }
                
                else if(temp.equals(circle))
                {
                    paintCanvas.clearPoints();
                    paintCanvas.setCircle();

                }
                else if(temp.equals(exit))
                {
                    board.dispatchEvent(
                            new WindowEvent(board,WindowEvent.WINDOW_CLOSING));
                }
                else if(temp.equals(fan))
                {
                    paintCanvas.setFan();
                }

                else if(temp.equals(brush))
                {
                    Object [] defaultBrushes = {"*","-","+",
                            "x","Thick Line", "Thin Line",
                            "Medium Line", "Custom String"};

                    String s = (String)JOptionPane.showInputDialog(
                                        null,
                                        "Select your Brush",
                                        "Brushes",
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        defaultBrushes,
                                        "*");
                    
                    if( s.equals("Custom String"))
                    {
                       paintCanvas.setBrush(
                               JOptionPane.showInputDialog(null,
                                       "Enter your Brush"));
                    }
                    else
                    {
                        paintCanvas.setBrush(s);
                    }    
                   
                }
                    
                
                
                
                
            }
            
        };
        
        
        /*add actionListener to Buttons*/
        
        color.addActionListener(buttonListener);
        save.addActionListener(buttonListener);
        load.addActionListener(buttonListener);
        clear.addActionListener(buttonListener);
        square.addActionListener(buttonListener);
        circle.addActionListener(buttonListener);
        brush.addActionListener(buttonListener);
        fan.addActionListener(buttonListener);
        exit.addActionListener(buttonListener);
        
        
        /*
         * Add panels to the frame1
         * 
         */
        board.add(topButtons, BorderLayout.NORTH);
        board.add(paintCanvas, BorderLayout.CENTER);
        board.add(bottomButtons, BorderLayout.SOUTH);
        
        
        board.setVisible(true);
    }    
}
