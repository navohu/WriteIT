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
		
		double k = 0.60; // correction constant
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color c = new Color(in.getRGB(i, j));
				
				if (greyValue(c) < k * averageGrey) {
					bw.setRGB(i,j,0);
				}
				else {
					bw.setRGB(i, j, -1);
				}
			}
		}

	    ImageIO.write(bw, "jpg", new File("//home//marko//workspace//HackLondon//src//bw2.jpg"));
	}
	
	public static void main(String[] args) throws IOException {
		File img = new File("//home//marko//workspace//HackLondon//src//1.jpg");
		BufferedImage in = ImageIO.read(img);
		writeBWImage(in);
	}
}
