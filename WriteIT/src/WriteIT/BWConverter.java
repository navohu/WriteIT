import java.awt.Color;
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
		
		double k = 0.50; // correction constant
		int left = bw.getWidth() - 1;
	    int right = 0;
	    int up = bw.getHeight() - 1;
	    int down = 0;
	    
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color c = new Color(in.getRGB(i, j));
				
				if (greyValue(c) < k * averageGrey) {
					bw.setRGB(i,j,0);
					
					// set to black
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
				else {
					bw.setRGB(i, j, -1);
				}
			}
		}

		
		// crop the image
		// System.out.println(left + " " + right + " " + up + " " + down);
	    BufferedImage cropped = new BufferedImage(right - left + 1, down - up + 1, BufferedImage.TYPE_BYTE_BINARY);
	    for (int i = 0; i <= right - left; i++) {
	    	for (int j = 0; j <= down - up; j++) {
	    		cropped.setRGB(i, j, bw.getRGB(left + i, up + j));
	    	}
	    }
	    ImageIO.write(cropped, "jpg", new File("//home//marko//workspace//HackLondon//src//bw10.jpg"));
	}
	
	public static void main(String[] args) throws IOException {
		File img = new File("//home//marko//workspace//HackLondon//src//10.jpg");
		BufferedImage in = ImageIO.read(img);
		writeBWImage(in);
	}
}
