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
		// Get all ratings for X
		List<Double> ratingsX = ratingsXY.stream().filter(p -> p.first != null).map(p -> p.first)
				.collect(Collectors.toList());
		// Remove all ratings which do not contain both ratings
		ratingsXY = ratingsXY.stream().filter(p -> p.first != null && p.second != null).collect(Collectors.toList());
		float suppX, suppXY, confXY, negSuppX, negSuppXsuppY, confNegXconfY;
		// Store number of all x ratings and number of all x and y ratings
		int totalRatingsX = ratingsX.size(), totalRatingsXY = ratingsXY.size();
		// Calculate support for X
		suppX = totalRatingsX != 0
				? (float) ratingsX.stream().filter(rating -> rating >= RATING_THRESHOLD).count() / totalRatingsX
				: 0f;
		// Calculate support for X and Y
		suppXY = totalRatingsXY != 0
				? (float) ratingsXY.stream().filter(p -> p.first >= RATING_THRESHOLD && p.second >= RATING_THRESHOLD).count() / totalRatingsXY
				: 0f;
		// Calculate confidence x => y
		confXY = suppX != 0 ? suppXY / suppX : 0f;
		// Calculate negative support for X
		negSuppX = totalRatingsX != 0
				? (float) ratingsX.stream().filter(rating -> rating < RATING_THRESHOLD).count() / totalRatingsX
				: 0f;
		// Calculate support for negative X and positive Y
		negSuppXsuppY = totalRatingsXY != 0
				? (float) ratingsXY.stream().filter(p -> p.first < RATING_THRESHOLD && p.second >= RATING_THRESHOLD).count() / totalRatingsXY
				: 0f;
		// Calculate confidence in negative X and positive Y
		confNegXconfY = negSuppX != 0 ? negSuppXsuppY / negSuppX : 0f;
		return confNegXconfY != 0 ? confXY / confNegXconfY : 0f;
	}

}
