package components;

public class Datapoint {
	private double[  ] featureVals;
	private double trgtVal;
	private String label;

	public Datapoint( double[  ] featureVals, double trgtVal, String label ) {
		this.featureVals = featureVals;
		this.trgtVal = trgtVal;
		this.label = label;
	}

	public int get_num_features(  ) { return featureVals.length; }

	public double get_feature_val( int i ) { return featureVals[ i ]; }
	
	public double get_target_val(  ) { return trgtVal; }
	
	public String get_label(  ) { return label; }

	public void print_info( boolean print_target_val, boolean print_label ) {
		System.out.print( "For this datapoint, the feature values are" );
		for ( int i = 0; i < featureVals.length; i++ ) {
			if ( i > 0 ) { System.out.print( "," ); }
			System.out.print( " F" + ( i + 1 ) + " = " + featureVals[ i ] );
		}

		if ( print_target_val ) {
			System.out.print( ", the target value is " + trgtVal );
		}
		
		if ( print_target_val ) {
			System.out.print( ", and the label is \"" + label + "\"" );
		}

		System.out.println( "." );
	}	
}
