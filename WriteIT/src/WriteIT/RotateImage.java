package WriteIT;

//Import the basic graphics classes.
import java.awt.*;
import javax.swing.*;
/**
* Simple program that loads, rotates and displays an image.
* Uses the file Duke_Blocks.gif, which should be in
* the same directory.
* 
* @author MAG
* @version 20Feb2009
*/

public class RotateImage extends JPanel{

	 // Declare an Image object for us to use.
	 Image image;
	 
	 // Create a constructor method
	 public RotateImage(){
	    super();
	    // Load an image to play with.
	    image = Toolkit.getDefaultToolkit().getImage("//Users//nathalievonhuth//Desktop//BT3.jpg");
	 }

	 public void paintComponent(Graphics g){
	      Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
	      g2d.translate(170, 0); // Translate the center of our coordinates.
	      g2d.rotate(1);  // Rotate the image by 1 radian.
	      g2d.drawImage(image, 0, 0, 200, 200, this);
	 }

	 public static void main(String arg[]){
	    JFrame frame = new JFrame("RotateImage");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(600,400);
	
	    RotateImage panel = new RotateImage();
	    frame.setContentPane(panel);  
	    frame.setVisible(true);  
	 }
}
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//public class RotateImage {
//	
//	public static void rotate(Graphics g, BufferedImage in, double degrees, int x, int y) throws IOException{
//		Graphics2D g2d = (Graphics2D) g;
//		AffineTransform tx = AffineTransform.getRotateInstance(degrees);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//		g2d.drawImage(op.filter(in, null), x, y, null);
//		
//		ImageIO.write(in, "jpg", new File("//Users//nathalievonhuth//Desktop//rotate1.jpg"));
//	}
//	
//	public static void main(String[] args) throws IOException {
//		File img = new File("//Users//nathalievonhuth//Desktop//BT1.jpg");
//		BufferedImage in = ImageIO.read(img);
//		rotate(null, in, 45, 0, 0);
//	}
//}
