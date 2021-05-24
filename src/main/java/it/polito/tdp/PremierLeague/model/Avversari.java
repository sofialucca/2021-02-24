package it.polito.tdp.PremierLeague.model;

public class Avversari {

	private Player player1;
	private Player player2;
	private double delta;
	
	public Avversari(Player player1, Player player2, double delta) {
		this.player1 = player1;
		this.player2 = player2;
		this.delta = delta;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}
	
}
