package it.polito.tdp.PremierLeague.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model();
		m.creaGrafo(new Match(32,0,0,0,0,0, null, null, null));
		System.out.println(m.getBestPlayer());
		m.setSim(new Match(32,0,0,0,0,0, null, null, null), 7);
		System.out.println(m.getEspulsioniHome());
		System.out.println(m.getEspulsioniAway());
		System.out.println(m.getGolHome());
		System.out.println(m.getGolAway());
	}

}
