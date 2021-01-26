package components;

import java.util.ArrayList;

public class OutputNeuron {
	private double learningRate;
	private double trgtVal;
	private String label;
	private double bias;
	private double prdctdTrgtVal;
    private int maskedTrgtVal;
	private String prdctdLabel;
	private double error;


	public OutputNeuron( double learningRate, double threshold ) {
		this.learningRate = learningRate;
	}
	
	public void collect_target_value( double newVal ) { trgtVal = newVal; }

	public double get_real_target_val(  ) { return trgtVal; }
	
	public void collect_label( String newVal ) { label = newVal; }
	
	public String get_real_label(  ) { return label; }

	public void calc_predicted_target_val( ArrayList< InputNeuron > inputNeurons ) {
		prdctdTrgtVal = inputNeurons.stream(  ).map( n-> n.get_feature_val(  ) * n.get_weight(  ) ).reduce( 0.0, ( a, b ) -> a + b ) + bias;
	}

	public double get_predicted_target_val(  ) { return prdctdTrgtVal; }

    public void	calc_masked_target_val(  ) {
        if ( prdctdTrgtVal > 0 )
            maskedTrgtVal = 1;
        else
            maskedTrgtVal = -1;
    }

    public int get_masked_target_val(  ) { return maskedTrgtVal; }
    
	public void assign_predicted_label( String lbl1, String lbl2 ) {
	    if ( maskedTrgtVal == 1 )
	        prdctdLabel = lbl1;
	    else
	        prdctdLabel = lbl2;
    }

	public String get_predicted_label(  ) { return prdctdLabel; }

	public void calc_error(  ) { error = trgtVal - maskedTrgtVal; }

	public double get_error(  ) { return error; }

	public void update_weights( ArrayList< InputNeuron > inputNeurons ) {
		bias += learningRate * error;
		inputNeurons.forEach( n -> n.update_weight( learningRate * error * n.get_feature_val(  ) ) );
	}

	public void set_bias( double newVal ) { bias = newVal; }

	public double get_bias(  ) { return bias; }

	public void print_trained_model( ArrayList< InputNeuron > inputNeurons ) {
		System.out.print( "Our latest trained model is:  " );
		for ( int i = 0; i < inputNeurons.size(  ); i++ ) {
			System.out.print( String.format( "%.3f", inputNeurons.get( i ).get_weight(  ) ) + " * f" + ( i + 1 ) + " + " );
		}
		System.out.println( String.format( "%.3f", bias ) );
		System.out.println(  );
	}
}
