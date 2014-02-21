package model;

public class BlackPawnTest extends PawnTest {

	public BlackPawnTest() {
		super(Player.BLACK);
	}
	
	protected final int direction() {
		return 1;
	}

	

}
