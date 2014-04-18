package view.sound;

import java.applet.Applet;
import java.applet.AudioClip;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Holds the game's sound effects.
 *
 * @author John O'Brien
 * @version Apr 1, 2014
 *******************************************************************/
public class SoundEffect {

	/** Sound for when white pieces are moved */
	public static final SoundEffect W_MOVE = new SoundEffect("whiteMoves.wav");
	
	/** Sound for when black pieces are moved */
	public static final SoundEffect B_MOVE = new SoundEffect("blackMoves.wav");
	
	/** Sound for when the game is in check */
	public static final SoundEffect CHECK = new SoundEffect("check.wav");
	
	/** Sound for when the game is over */
	public static final SoundEffect GAME_OVER = new SoundEffect("gameOver.wav");
	
	/** Sound for when a piece is captured */
	public static final SoundEffect CAPTURE = new SoundEffect("capture.wav");
	
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
	public final void play(){
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
