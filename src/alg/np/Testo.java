package alg.np;

import java.util.ArrayList;import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import alg.np.similarity.metric.Pair;

public class Testo {

	
	public static void main (String[] args) {
		List<Pair> pairs = new ArrayList<Pair>();
		pairs.add(new Pair(3.0,1.0));
		pairs.add(new Pair(2.0,0.0));
		pairs.add(new Pair(0.0,0.0));
		pairs.add(new Pair(5.0,0.0));
		pairs.add(new Pair(0.0,0.0));
		pairs.add(new Pair(0.0,0.0));
		pairs.add(new Pair(0.0,0.0));
		pairs.add(new Pair(2.0,1.0));
		pairs.add(new Pair(0.0,0.0));
		pairs.add(new Pair(0.0,2.0));
		System.out.println(pairs.size());
		System.out.println(pairs.stream().filter(p -> p.first > 0).collect(Collectors.toList()).size());
		System.out.println(pairs.size());

	}
	
	public static Double cosine(List<Pair> vectors) {
		
		Double dotProduct = vectors.stream()
				.mapToDouble(pair -> pair.first*pair.second).sum();
			Double lV1 = Math.sqrt(vectors.stream()
					.mapToDouble(val -> Math.pow(val.first,2.0)).sum());
			Double lV2 = Math.sqrt(vectors.stream()
					.mapToDouble(val -> Math.pow(val.second,2.0)).sum());
		return dotProduct/(lV1*lV2);
	}
}
