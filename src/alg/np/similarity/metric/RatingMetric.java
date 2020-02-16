/**
 * Compute the similarity between two items based on the Cosine between item ratings
 */

package alg.np.similarity.metric;

import java.util.List;
import java.util.stream.Collectors;

import util.reader.DatasetReader;

public class RatingMetric implements SimilarityMetric {
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * 
	 * @param reader - dataset reader
	 */
	public RatingMetric(final DatasetReader reader) {
		this.reader = reader;
	}

	/**
	 * computes the similarity between items
	 * 
	 * @param X - the id of the first item
	 * @param Y - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y) {
		List<Pair> ratingsStream = reader.getUserProfiles().entrySet().stream().map(entry -> {
			Double r1 = entry.getValue().contains(X) ? entry.getValue().getValue(X) : 0.0;
			Double r2 = entry.getValue().contains(Y) ? entry.getValue().getValue(Y) : 0.0;
			return new Pair(r1, r2);
		}).collect(Collectors.toList());

		Double dotProduct = ratingsStream.stream().mapToDouble(p -> p.first * p.second).sum();
		Double lV1 = Math.sqrt(ratingsStream.stream().mapToDouble(p -> Math.pow(p.first, 2.0)).sum());
		Double lV2 = Math.sqrt(ratingsStream.stream().mapToDouble(p -> Math.pow(p.second, 2.0)).sum());

		return dotProduct / (lV1 * lV2);

	}

}
