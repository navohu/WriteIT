import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BWConverter {

	public static int greyValue(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		return (int) (0.24 * r + 0.72 * g + 0.07 * b);
	}
	
	public static void writeBWImage(BufferedImage in) throws IOException {
		BufferedImage bw = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

		int w = in.getWidth();
		int h = in.getHeight();
		
		int averageGrey = 0;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++){
				averageGrey += greyValue(new Color(in.getRGB(i, j)));
			}
		}
		averageGrey /= w*h;
		
		double k = 0.58; // correction constant    
	    
	    int[][] neighs = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color c = new Color(in.getRGB(i, j));
				
				if (greyValue(c) < k * averageGrey) {
					bw.setRGB(i,j,0);

			          for (int ii = Math.max(0, i - 2); ii < Math.min(w - 1, i + 2); ii++) {
			        	  for (int jj = Math.max(0, j - 2); jj < Math.min(h - 1, j + 2); jj++) {
			        		  neighs[ii][jj]++;
			        	  }
			          }
				}
				else {
					bw.setRGB(i, j, -1);
				}
			}
		}

		int left = bw.getWidth() - 1;
	    int right = 0;
	    int up = bw.getHeight() - 1;
	    int down = 0;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (neighs[i][j] > 10) {
					  if (i > right) {
			            right = i;
			          }
			          if (i < left){
			            left = i;
			          }
			          if (j < up) {
			            up = j;
			          }
			          if (j > down) {
			            down = j;
			          }
				}
			}
		}
		
		
		// crop the image
	    BufferedImage cropped = new BufferedImage(right - left + 1, down - up + 1, BufferedImage.TYPE_BYTE_BINARY);
	    for (int i = 0; i <= right - left; i++) {
	    	for (int j = 0; j <= down - up; j++) {
	    		if (neighs[left + i][up + j] > 5) cropped.setRGB(i, j, 0);
	    		else cropped.setRGB(i, j, -1);
	    	}
	    }
	   //  ImageIO.write(cropped, "jpg", new File("//home//marko//workspace//HackLondon//src//bww1.jpg"));
	    
	    AffineTransform tx = new AffineTransform();
	    tx.rotate(0.5, cropped.getWidth(), cropped.getHeight());

	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    cropped = op.filter(cropped, null);

	    BufferedImage stupid = new BufferedImage(cropped.getWidth(), cropped.getHeight(),  BufferedImage.TYPE_BYTE_BINARY);
	    for (int i = 0; i < cropped.getWidth(); i++) {
	    	for (int j = 0; j < cropped.getHeight(); j++) {
	    		if (cropped.getRGB(i, j) > -1) stupid.setRGB(i, j, -1);
	    		else {
	    			stupid.setRGB(i, j, cropped.getRGB(i, j));
	    		}
	    	}
	    }
	    
	    ImageIO.write(stupid, "jpg", new File("//home//marko//workspace//HackLondon//src//bww1.jpg"));
	}
	
	public static void main(String[] args) throws IOException {
		File img = new File("//home//marko//workspace//HackLondon//src//w1.jpg");
		BufferedImage in = ImageIO.read(img);
		writeBWImage(in);
	}
}
