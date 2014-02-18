package model;

import model.Player;

public class BlackPawnTest extends PawnTest {

	public BlackPawnTest(){
		super(Player.BLACK);
	}
	
	protected int direction() {
		return 1;
	}

	

}
