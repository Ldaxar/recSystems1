/**
 * Compute the similarity between two items based on increase in confidence
 */

package alg.np.similarity.metric;

import java.util.List;
import java.util.stream.Collectors;

import util.reader.DatasetReader;

public class IncConfidenceMetric implements SimilarityMetric {
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new IncConfidenceMetric object
	 * 
	 * @param reader - dataset reader
	 */
	public IncConfidenceMetric(final DatasetReader reader) {
		this.reader = reader;
	}

	/**
	 * computes the similarity between items
	 * 
	 * @param X - the id of the first item
	 * @param Y - the id of the second item
	 */	
	public double getItemSimilarity(final Integer X, final Integer Y) {
		List<Pair> ratingsXY = reader.getUserProfiles().entrySet().stream().map(entry -> {
			return new Pair(entry.getValue().getValue(X), entry.getValue().getValue(Y));
		}).collect(Collectors.toList());
		//Get all ratings for X
		List<Double> ratingsX = ratingsXY.stream().filter(p -> p.first != null).map(p -> p.first)
				.collect(Collectors.toList());
		//Remove all ratings which do not contain both ratings
		ratingsXY = ratingsXY.stream().filter(p -> p.first != null && p.second != null)
				.collect(Collectors.toList());
		//Store number of all x ratings and number of all x and y ratings
		int totalRatingsX = ratingsX.size(), totalRatingsXY = ratingsXY.size();
		if (totalRatingsX == 0 || totalRatingsXY==0)
			return 0;
		//Calculate support for X
		double suppX = (double)ratingsX.stream().filter(rating -> rating >=RATING_THRESHOLD).count() / totalRatingsX;
		//Calculate support for X and Y
		double suppXY = (double) ratingsXY.stream().filter(p -> p.first >=RATING_THRESHOLD && p.second >= RATING_THRESHOLD).count() 
				/ totalRatingsXY;
		if (suppX==0) return 0;
		//Calculate confidence x => y
		double confXY = suppXY / suppX;
		
		//Calculate negative support for X
		double negSuppX = (double) ratingsX.stream().filter(rating -> rating <RATING_THRESHOLD).count() / totalRatingsX;
		if (negSuppX==0)
			return 0;
		//Calculate support for negative X and positive Y
		double negSuppXsuppY = (double) ratingsXY.stream().filter(p -> p.first <RATING_THRESHOLD && p.second >= RATING_THRESHOLD).count() 
				/ totalRatingsXY;
		//Calculate confidence in negative X and positive Y
		double confNegXconfY = negSuppXsuppY/negSuppX;
		return confNegXconfY!=0 ? confXY / confNegXconfY : 0.0;
	}
	
}
