/**
 * Compute the similarity between two items based on the Cosine between item ratings
 */ 

package alg.np.similarity.metric;

import util.reader.DatasetReader;

public class RatingMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * @param reader - dataset reader
	 */
	public RatingMetric(final DatasetReader reader)
	{
		this.reader = reader;
	}

	/**
	 * computes the similarity between items
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y)
	{
		// calculate similarity using Cosine
		return 0;
	}
}
