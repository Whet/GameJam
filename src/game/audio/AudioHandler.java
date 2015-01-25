package game.audio;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.SamplePlayer;

public class AudioHandler {

	private AudioContext ac;
	
	public AudioHandler() {
		ac = new AudioContext();
	}
	
	//move later
	public void playAudio(String audio) {

		try {
		ac.out.clearInputConnections();
		//Stream ting
		//SampleManager.setBufferingRegime(Sample.Regime.newStreamingRegime(1000));
		SamplePlayer player = new SamplePlayer(ac, SampleManager.sample(audio));
		Gain g = new Gain(ac, 2, 0.2f);
		g.addInput(player);
		ac.out.addInput(g);
		System.out.println("playing audio");
		ac.start();
		}
		catch(NullPointerException e) {
			
		}
	}
	
	public void playNothing()
	{
		ac.out.clearInputConnections();
	}
}
