package _main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import components.Datapoint;
import components.InputNeuron;
import components.OutputNeuron;
import components.SyntheticData;

public class BuildANeuronWorkshop {

	public static void main( String[  ] args ) throws FileNotFoundException {
		/*  "Neuron, Alice the Neuron!
		 *   Lookin' for smarts,
		 *   Chasin' some data points!
		 *
		 *   Neuron, I know she's out there!
		 *   Bumbly-tumbly
		 *   Climbin' a knowledge tree!
		 *
		 *   Learning never ends for us, we're so curious!
		 *   'Least every now and then
		 *   But when you're alone, and no one's in school,
		 *   It's nice to be able
		 *   To count on a friend like

		 *   Neuron, Alice the Neuron!
		 *   Whatever you learn, oh won't you teach me, please!"
		 */


		/* introducing the characters */
		OutputNeuron alice = new OutputNeuron( 0.01, 0.0 );
		InputNeuron bob = new InputNeuron(  );
		InputNeuron carol = new InputNeuron(  );

		ArrayList< InputNeuron > friends = new ArrayList< InputNeuron >(  );
		friends.add( bob );
		friends.add( carol );

		System.out.println( "Learning the Hatfields vs. McCoys Problem" );
		System.out.println(  );
		SyntheticData hvm = new SyntheticData(  );
		hvm.collect_data( "train", "data_files/train_hatfields_vs_mccoys.txt" );
		hvm.collect_data( "test", "data_files/test_hatfields_vs_mccoys.txt" );

		bob.set_weight( -0.5 );
		carol.set_weight( 0.5 );
		alice.set_bias( 0.0 );

		/* moved the learning process into the 'do_homework(  )' function */
		do_homework( alice, friends, hvm.get_training_set(  ), "Hatfield", "McCoy", true );

		/* moved the testing steps into the 'take_exam(  )' function */
		take_exam( alice, friends, hvm.get_test_set(  ), "Hatfield", "McCoy" );


		System.out.println(  );
		System.out.println( "Learning to Recognize Faces" );
		System.out.println(  );
		SyntheticData faceData = new SyntheticData(  );
		faceData.collect_data( "train", "data_files/train_face_recognition.txt" );
		faceData.collect_data( "test", "data_files/test_face_recognition.txt" );

		/* create Alice's other friends */
		InputNeuron deke = new InputNeuron(  );
		InputNeuron echo = new InputNeuron(  );
		InputNeuron foxtrot = new InputNeuron(  );
		InputNeuron gamma = new InputNeuron(  );
		InputNeuron hank = new InputNeuron(  );
		InputNeuron igloo = new InputNeuron(  );
		InputNeuron jacob = new InputNeuron(  );
		InputNeuron katie = new InputNeuron(  );
		InputNeuron lima = new InputNeuron(  );
		InputNeuron mack = new InputNeuron(  );
		InputNeuron nancy = new InputNeuron(  );
		InputNeuron opie = new InputNeuron(  );
		InputNeuron pedro = new InputNeuron(  );
		InputNeuron quirky = new InputNeuron(  );
		InputNeuron robin = new InputNeuron(  );
		InputNeuron silly = new InputNeuron(  );
		InputNeuron toby = new InputNeuron(  );
		InputNeuron unique = new InputNeuron(  );
		InputNeuron violet = new InputNeuron(  );
		InputNeuron wilco = new InputNeuron(  );
		InputNeuron xray = new InputNeuron(  );
		InputNeuron yankee = new InputNeuron(  );
		InputNeuron zulu = new InputNeuron(  );
		
		friends.addAll( Arrays.asList( deke, echo, foxtrot, gamma, hank, igloo, jacob, katie, lima, mack, nancy, opie,
				 					   pedro, quirky, robin, silly, toby, unique, violet, wilco, xray, yankee, zulu ) );
		
		/* initialize weights to random values*/
		for ( int i = 0; i < friends.size(  ); i++ ) {
		    friends.get( i ).set_weight( Math.random(  ) );
		}
		alice.set_bias( Math.random(  ) );

		do_homework( alice, friends, faceData.get_training_set(  ), "friend", "fry", true );
		take_exam( alice, friends, faceData.get_test_set(  ), "friend", "fry" );
	}


	public static void do_homework( OutputNeuron oNeuron,
			                           ArrayList< InputNeuron > inputNeurons,
			                           ArrayList< Datapoint > homework,
			                           String lbl1,
			                           String lbl2,
			                   		   boolean verbose_mode ) {
	    int maxIterations = 100;
	    int iteration = 1;
	    double totalError;
		   
	    System.out.println( "Doing homework ... ." );

		 do {
			 totalError = 0;
			 for( int i = 0; i < homework.size(  ); i++ ) {
			 	 Datapoint dp = homework.get( i );

				 for( int j = 0; j < dp.get_num_features(  ); j++ ) {
					 inputNeurons.get( j ).collect_feature_value( dp.get_feature_val( j ) );
				 }
				 oNeuron.collect_label( dp.get_label(  ) );
				 oNeuron.collect_target_value( dp.get_target_val(  ) );

				 if ( verbose_mode ) {
					 System.out.print( "The current weights are " );
					 for ( int k = 0; k < inputNeurons.size(  ); k++ ) {
						 System.out.print( String.format( "%.3f", inputNeurons.get( k ).get_weight(  ) ) + ", " );
					 }
					 System.out.println( "and the bias is " + String.format( "%.3f", oNeuron.get_bias(  ) ) + "." );
				 }

				 /* feedforward phase */
				 oNeuron.calc_predicted_target_val( inputNeurons );
				 oNeuron.calc_masked_target_val(  );
				 oNeuron.assign_predicted_label( lbl1, lbl2 );

				 if ( verbose_mode ) {
					 System.out.println( "The predicted target value is " + String.format( "%.3f", oNeuron.get_predicted_target_val(  ) ) +
										 ", the masked target value is " + oNeuron.get_masked_target_val(  ) +
										 ", and the predicted label is \"" + oNeuron.get_predicted_label(  ) +"\"." ); 
 				 }
				
				 /* backpropagate phase */
				 oNeuron.calc_error(  );
				 oNeuron.update_weights( inputNeurons );

				 totalError += Math.abs( oNeuron.get_error(  ) );
				 if ( verbose_mode ) {
					 System.out.println( "The real target value is " + oNeuron.get_real_target_val(  ) +
					 				     ", and the real label is \"" + oNeuron.get_real_label(  ) +
									     "\", so the error is " + String.format( "%.1f", oNeuron.get_error(  ) ) + "." );
					 System.out.println(  );
				 }
			}

			System.out.println( "After iteration " + iteration + ", the total error is " + String.format( "%.4f", totalError ) );
			System.out.println(  );
			iteration++;
		} while ( totalError > 0 && iteration <= maxIterations );
	}


	public static void take_exam( OutputNeuron oNeuron,
			                        ArrayList< InputNeuron > inputNeurons,
			                        ArrayList< Datapoint > exam,
			                        String lbl1,
			                        String lbl2 ) {
		System.out.println( "Taking the exam ...." );
		
		int numCorrect = 0;
		for( int i = 0; i < exam.size(  ); i++ ) {
			Datapoint dp = exam.get( i );
			dp.print_info( false, false );

			for( int j = 0; j < dp.get_num_features(  ); j++ ) {
				inputNeurons.get( j ).collect_feature_value( dp.get_feature_val( j ) );
			}

			/* feedforward phase ONLY */
			oNeuron.calc_predicted_target_val( inputNeurons );
			oNeuron.calc_masked_target_val(  );
			oNeuron.assign_predicted_label( lbl1, lbl2 );

			System.out.println( "The target value is predicted to be " + String.format( "%.3f", oNeuron.get_predicted_target_val(  ) ) +
		            			", so the masked value would be " + oNeuron.get_masked_target_val(  ) +
					            ", and the label should be \"" + oNeuron.get_predicted_label(  ) + "\"." );
			System.out.println( "The real target value is " + dp.get_target_val(  ) + ", and the real label is \"" + dp.get_label(  ) + "\"." );
			String correct = ( oNeuron.get_masked_target_val(  ) == dp.get_target_val(  ) ) ? "correct" : "wrong";
			System.out.println( "That is " + correct + "!" );
			System.out.println(  );
			numCorrect += ( oNeuron.get_masked_target_val(  ) == dp.get_target_val(  ) ) ? 1 : 0;
		}

		System.out.println( numCorrect + " out of " + exam.size(  ) + " test points correctly labeled!" );
		System.out.println(  );
	}

}
