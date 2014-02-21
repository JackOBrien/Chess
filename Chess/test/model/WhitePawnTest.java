package model;

public class WhitePawnTest extends PawnTest {

	public WhitePawnTest() {
		super(Player.WHITE);
	}
	
	protected final int direction() {
		return -1;
	}

}
