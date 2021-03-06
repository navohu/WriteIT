import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
	
	public static void rotateAndWrite(BufferedImage img, double angle, int count, String filename) throws IOException {
		AffineTransform tx = new AffineTransform();
	    tx.rotate(angle, img.getWidth() / 2, img.getHeight() / 2);

	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    img = op.filter(img, null);

	    BufferedImage stupid = new BufferedImage(img.getWidth(), img.getHeight(),  BufferedImage.TYPE_BYTE_BINARY);
	    for (int i = 0; i < img.getWidth(); i++) {
	    	for (int j = 0; j < img.getHeight(); j++) {
	    		if (img.getRGB(i, j) > -1) stupid.setRGB(i, j, -1);
	    		else {
	    			stupid.setRGB(i, j, img.getRGB(i, j));
	    		}
	    	}
	    }
	    
	    boolean[][] passer = new boolean[img.getWidth()][img.getHeight()];
	    for (int i = 0; i < img.getWidth(); i++) {
	    	for (int j = 0; j < img.getHeight(); j++) {
	    		passer[i][j] = stupid.getRGB(i, j) != -1;
	    	}
	    }

	    cropAndWrite(passer, img.getWidth(), img.getHeight(), count, filename);
	}
	
	public static void cropAndWrite(boolean[][] passer, int width, int height, int count, String filename) throws IOException {
		int left = width - 1;
	    int right = 0;
	    int up = height - 1;
	    int down = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (passer[i][j]) {
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

		BufferedImage cropped = new BufferedImage(right - left + 1, down - up + 1, BufferedImage.TYPE_BYTE_BINARY);
	    for (int i = 0; i <= right - left; i++) {
	    	for (int j = 0; j <= down - up; j++) {
	    		cropped.setRGB(i, j, passer[left + i][up + j] ? 0 : -1);
	    	}
	    }
	    
	    ImageIO.write(cropped, "jpg", new File("C://wamp//www//WriteIT//database//" + filename + ".jpg"));
	}
	
	public static void makeRotations(BufferedImage img, int n, String filename) throws IOException {
		for (int c = 0; c < n; c++) {
			rotateAndWrite(img, 2 * c * Math.PI / n, c + 1, filename);
		}
	}
	
	public static void writeBWImages(BufferedImage in, String filename) throws IOException {
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
	   
	   // cropped is now the sample pic
	   // now center the cropped part in a large picture, so we won't get out of the bounds when rotating
	    int croppedWidth = cropped.getWidth();
	    int croppedHeight = cropped.getHeight();
	    int size = (int) Math.ceil(Math.sqrt(croppedWidth * croppedWidth + croppedHeight * croppedHeight)) + 1;
	    BufferedImage centered = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_BINARY);
	    
	    // set centered to white
	    for (int i = 0; i < size; i++) {
	    	for (int j = 0; j < size; j++) {
	    		centered.setRGB(i, j, -1);
	    	}
	    }
	    
	    for (int i = 0; i < croppedWidth; i++) {
	    	for (int j = 0; j < croppedHeight; j++) {
	    		centered.setRGB((size - croppedWidth) / 2 + i, (size - croppedHeight) / 2 + j, cropped.getRGB(i, j));
	    	}
	    }
	    
	    // how many rotations we want
	    makeRotations(centered, 1, filename);
	}
	
	public static void main(String[] args) throws IOException {
		/*
		File img = new File("//home//marko//workspace//HackLondon//src//1.bmp");
		BufferedImage in = ImageIO.read(img);
		writeBWImages(in);
		*/
	}
}
