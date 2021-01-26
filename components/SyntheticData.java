package components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SyntheticData {
	private ArrayList< Datapoint > trainingSet;
	private ArrayList< Datapoint > testSet;

	public SyntheticData(  ) {
		trainingSet = new ArrayList< Datapoint >(  );
		testSet = new ArrayList< Datapoint >(  );		
	}

	public int get_num_training_datapoints(  ) { return trainingSet.size(  ); }

	public int get_num_test_datapoints(  ) { return testSet.size(  ); }
	
	public Datapoint get_training_data( int i ) { return trainingSet.get( i ); }
	
	public Datapoint get_test_data( int i ) { return testSet.get( i ); }

	public ArrayList< Datapoint > get_training_set(  ) { return trainingSet; }

	public ArrayList< Datapoint > get_test_set(  ) { return testSet; }
	
	public void collect_data( String datasetType, String fileLoc ) throws FileNotFoundException {
		Scanner scnrPage = new Scanner( new File( fileLoc ) );
		while ( scnrPage.hasNextLine(  ) )
		{
			Scanner scnrLine = new Scanner( scnrPage.nextLine(  ) );
			scnrLine.useDelimiter( "[\t\r\n]" );

			ArrayList< String > inputLine = new ArrayList< String >(  );
			while ( scnrLine.hasNext(  ) )
				{ inputLine.add( scnrLine.next(  ) ); }
			scnrLine.close(  );

			double[  ] featureVals = new double[ inputLine.size(  ) - 2 ];
			for ( int i = 0; i < inputLine.size(  ) - 2; i++ ) {
				featureVals[ i ] = Double.parseDouble( inputLine.get( i ) );
			}
			double trgtVal = Double.parseDouble( inputLine.get( inputLine.size(  ) - 2 ) );
			String label = inputLine.get( inputLine.size(  ) - 1 );

			if ( datasetType == "train" ) { trainingSet.add( new Datapoint( featureVals, trgtVal, label ) ); }
			else if ( datasetType == "test" ) { testSet.add( new Datapoint( featureVals, trgtVal, label ) ); }
		}
		scnrPage.close(  );
	}

	/**
	 * @param datasetType:  "train" or "test"
	 * @param numDatapoints:  how many datapoints to generate
	 * @param features:  an array in which each row contains exactly two ints, min / max values for each feature
	 * @param minTrgtVal:  minimum the target value should be
	 * @param maxTrgtVal:  maximum the target value should be
	 * @param label
	 * 		target value:  randomly generated value between the minTrgtVal and the maxTrgtVal
	 * 		feature values:  each value randomly generated between the min / max values specified in features[  ] for that particular feature
	 */
	public void generate_data( String datasetType, int numDatapoints, double[  ][  ] features, double minTrgtVal, double maxTrgtVal, String label ) {
		ArrayList< Datapoint > dataset = new ArrayList< Datapoint >(  );

		for ( int i = 0; i < numDatapoints; i++ ) {
			double[  ] featureVals = new double[ features.length ];
			for ( int j = 0; j < features.length; j++ ) {
				featureVals[ j ] = ( int ) Math.round( features[ j ][ 0 ] + Math.random(  ) * ( features[ j ][ 1 ] - features[ j ][ 0 ] ) );
			}
			double trgtVal = minTrgtVal + Math.random(  ) * ( maxTrgtVal - minTrgtVal );
			dataset.add( new Datapoint( featureVals, trgtVal, label ) );
		}

		if ( datasetType == "train" ) { trainingSet = dataset; }
		else if ( datasetType == "test" ) { testSet = dataset; }
	}

}
