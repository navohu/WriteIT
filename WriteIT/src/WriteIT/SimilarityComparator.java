import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SimilarityComparator {
	int THRES = 300;
	String bestSampleName = "";
	
	public boolean getBestSample() throws IOException {
		// this populates rotatedScans
		File image = new File("//home//marko//workspace//HackLondon//src//scanned.bmp");
		BufferedImage in = ImageIO.read(image);
		BWConverter.writeBWImages(in);
				
		
		File dir = new File("//home//marko//workspace//HackLondon//src//database");
		File[] samples = dir.listFiles(new JPEGImageFileFilter());
		
		for (File sample : samples) {
			NaiveSimilarityFinder nsf = new NaiveSimilarityFinder(sample);
			if (nsf.closestDistance < THRES) {
				this.bestSampleName = sample.getName();
				System.out.println(nsf.closestDistance);
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
				
		// this finds the best fit
		SimilarityComparator sc = new SimilarityComparator();
		if (sc.getBestSample()) {
			System.out.println("SAMPLE FOUND: " + sc.bestSampleName);
		}
	}
}
