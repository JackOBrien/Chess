package view.sound;

import java.applet.Applet;
import java.applet.AudioClip;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * 
 *
 * @author John O'Brien
 * @version Apr 1, 2014
 *******************************************************************/
public class SoundEffect {

	/** Sound for when white pieces are moved */
	public static final SoundEffect W_MOVE = new SoundEffect("whiteMoves.mp3");
	
	/** Sound for when black pieces are moved */
	public static final SoundEffect B_MOVE = new SoundEffect("blackMoves.mp3");
	
	/** Sound for when the game is in check */
	public static final SoundEffect CHECK = new SoundEffect("check.mp3");
	
	/** Sound for when a piece is captured */
	public static final SoundEffect CAPTURE = new SoundEffect("capture.mp3");
	
	/** AudioClip of the selected sound */
	private AudioClip clip;
	
	/********************************************************
	 * Selects the requested sound file and assigns it to
	 * the AudioClip.
	 * 
	 * @param filename name of the requested file.
	 *******************************************************/
	public SoundEffect(final String filename) {
		try {
			clip = Applet.newAudioClip(
					SoundEffect.class.getResource(filename));
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	/********************************************************
	 * Plays the selected audio.
	 *******************************************************/
	public void play(){
		try {
			
			Thread th = new Thread() {
				public void run() {
					clip.play();
				}
			};
			
			th.start();
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
}
