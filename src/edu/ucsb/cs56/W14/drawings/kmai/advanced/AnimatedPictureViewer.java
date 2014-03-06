package edu.ucsb.cs56.w14.drawings.kmai.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedPictureViewer{

    private DrawPanel panel = new DrawPanel();
    private StarOfDavid star = new StarOfDavid(0,0,100);
    
    Thread anim; 
    
    private int x = 100;
    private int y = 100;

    private int x2 = 300;
    private int y2 = 300;
    
    private int dx = 5;
    private int dy = 5;

    public static void main (String[] args) {
      new AnimatedPictureViewer().go();
    }

    public void go() {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(panel);
      frame.setSize(640,480);
      frame.setVisible(true);
      
      frame.getContentPane().addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e){
          anim = new Animation();
          anim.start();
        }

        public void mouseExited(MouseEvent e){        
          anim.interrupt();
          while (anim.isAlive()){}
          anim = null;         
          panel.repaint();        
        }
      });
      
    } // go()
    class DrawPanel extends JPanel {
	public void paintComponent(Graphics g) {
	    Stroke thick = new BasicStroke (4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL); 
	    Graphics2D g2 = (Graphics2D) g;
	    
	    // Clear the panel first
	    g2.setColor(Color.lightGray);
	    g2.setStroke(thick);
	    g2.fillRect(0,0,this.getWidth(), this.getHeight());
	  
          // Draw the StarOfDavid
	  
          g2.setColor(Color.YELLOW);
          StarOfDavid test = new StarOfDavid(x, y, 100);
          g2.draw(test);

	  Graphics2D g22 = (Graphics2D) g;
          g22.setColor(Color.RED);
          StarOfDavid test2 = new StarOfDavid(x2, y2, 100);
	  g22.draw(test2);

       }
    }
    
    class Animation extends Thread {
      public void run() {
        try {
          while (true) {
            // Bounce off the walls
	      double random = Math.random();
	      int base = 50;
	      if (x >= 550) { dx = -5; }
	      if (x <= 0) { dx = 5; }
	      if (y >= 350) { dy = -5; }
	      if (y <= 0) { dy = 5; }
	      
	      if(x>0 && x<150) { base = 20; }
	      if(x>300 && x<450) { base = 20; }
	      x += dx;               
	      y += dy;
	      panel.repaint();
	      random = random * base;
	      Thread.sleep((int)random);
          }
        } catch(Exception ex) {
          if (ex instanceof InterruptedException) {
            // Do nothing - expected on mouseExited
          } else {
            ex.printStackTrace();
            System.exit(1);
          }
        }
      }
    }
    
}
