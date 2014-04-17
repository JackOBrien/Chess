package view;

/********************************************************************
 * CIS 350 - 01
 * Chess
 *
 * Handles time format for the game timers.
 *
 * @author John O'Brien
 * @version Apr 16, 2014
 *******************************************************************/
public class Clock {
	
	/** Minute value. */
	 private int minutes;
	 
	 /** Second Value. */
	 private int seconds;
	 
	 /** 1/10 second Value. */
	 private int tenth;
	 
	/***************************************************************
	 * Clock to that counts up by 1/10 of a second.
	 **************************************************************/
	public Clock() {
		minutes = 0;
		seconds = 0;
		tenth = 0;
	}
	
	/****************************************************************
	 * Returns a string representing the time in the following format.
	 * "minutes:seconds.tenth"
	 * Adds leading 0's to minutes and seconds "0:00.0"
	 * 
	 * @return  String representing the time
	 ****************************************************************/
	public final String toString() {
		String out = "";
		
		out += minutes + ":";
		
		final int ten = 10;
		
		// Adds leading 0 to minutes if needed
		if (seconds < ten) {
			out += "0";
		}

		return out + seconds + "." + tenth;
	}
	
	/****************************************************************
	 * Adds one second to the clock.
	 ***************************************************************/
	public final void count() {
		add(1);
	}
	
	/****************************************************************
	 * Adds amount of time in 1/10 seconds.
	 * 
	 * @param pT time value to be added in 1/10 seconds.
	 ****************************************************************/
	public final void add(final int pT) {

		int t = pT;
		
		final int nine = 9;
		final int fiftyNine = 59;
		
		while (t > 0) {	

			//Add to tenth value if in range
			while (tenth < nine && t > 0) {
				tenth++;
				t--;
			}

			// Adds to seconds value if there are 10 tenth
			// rolls time over from tenth
			if (seconds < fiftyNine && t > 0) {
				seconds++;
				tenth -= nine;
				t--;
			}
			// Adds to minutes value if there are 60 seconds
			// rolls over to seconds and tenth
			if (t > 0 && seconds ==  fiftyNine) {
				minutes++;
				seconds -= fiftyNine;
				tenth -= nine;
				t--;
			}
		}
	}
}
