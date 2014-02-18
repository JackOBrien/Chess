package model;

import model.Player;

public class WhitePawnTest extends PawnTest {

	public WhitePawnTest() {
		super(Player.WHITE);
	}
	
	protected int direction() {
		return -1;
	}

}
