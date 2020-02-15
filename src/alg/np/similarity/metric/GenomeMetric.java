/**
 * Compute the similarity between two items based on the Cosine between item genome scores
 */ 

package alg.np.similarity.metric;

import java.util.Set;
import java.util.stream.Collectors;



import profile.Profile;
import util.Item;
import util.reader.DatasetReader;

public class GenomeMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new GenomeMetric object
	 * @param reader - dataset reader
	 */
	public GenomeMetric(final DatasetReader reader)
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
		class Pair {
			protected Pair(Double first, Double second) {
				this.first = first;
				this.second = second;
			}
			protected Double first;
			protected Double second;
		}
		// calculate similarity using weighted Jaccard
		Profile p1 = reader.getItem(X).getGenomeScores(); 
		Profile p2 = reader.getItem(Y).getGenomeScores();		
		Pair sums = p1.getCommonIds(p2)
				.stream().map(id -> {
					return new Pair(p1.getValue(id),p2.getValue(id));
				})
				.reduce(new Pair(0.0,0.0) , (sum, newElement) -> {
					sum.first+=Math.min(newElement.first, newElement.second);
					sum.second+=Math.max(newElement.first, newElement.second);
					return sum;
		});		
		return (sums.first/sums.second);
	}
}
