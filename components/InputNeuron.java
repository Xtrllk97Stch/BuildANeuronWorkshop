package components;

public class InputNeuron {
	private double featureVal;
	private double weight;

	public void collect_feature_value( double newVal ) { featureVal = newVal; }

	public double get_feature_val(  ) { return featureVal; }

	public void set_weight( double newVal ) { weight = newVal; }
	
	public void update_weight( double additive ) { weight += additive; }
	
	public double get_weight(  ) { return weight; }

}
