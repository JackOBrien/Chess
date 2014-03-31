package view;

public class Clock {
	
	/** Minute value */
	 private int minutes;
	 
	 /** Second Value */
	 private int seconds;
	 
	 /** 1/10 second Value */
	 private int tenth;
	 
	/***************************************************************
	 * Clock to that counts up by 1/10 of a second
	 **************************************************************/
	public Clock() {
		minutes = 0;
		seconds = 0;
		tenth = 0;
	}
	
	/****************************************************************
	 * Returns a string representing the time in the following format
	 * "minutes:seconds.tenth"
	 * Adds leading 0's to minutes and seconds "0:00.0"
	 * 
	 * @return  String representing the time
	 ****************************************************************/
	public String toString() {
		String out = "";
		
		out += minutes + ":";
		
		// Adds leading 0 to minutes if needed
		if (seconds < 10){
			out += "0";
		}

		return out + seconds + "." + tenth;
	}
	
	public void count(){
		add(1);
	}
	
	/****************************************************************
	 * Adds amount of time in 1/10 seconds.
	 * 
	 * @param t time value to be added in 1/10 seconds.
	 ****************************************************************/
	public void add(int t) {
		if (t < 0){
			throw new IllegalArgumentException("Can't add a negitive");
		}	
		
		while (t > 0){	

			//Add to tenth value if in range
			while (tenth < 9 && t > 0){
				tenth ++;
				t --;
			}

			// Adds to seconds value if there are 10 tenth
			// rolls time over from tenth
			if (seconds < 59 && t > 0) {
				seconds ++;
				tenth -= 9;
				t --;
			}
			// Adds to minutes value if there are 60 seconds
			// rolls over to seconds and tenth
			if (t > 0 && seconds ==  59){
				minutes ++;
				seconds -= 59;
				tenth -= 9;
				t --;
			}
		}
	}
}
