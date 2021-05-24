package it.polito.tdp.PremierLeague.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		PremierLeagueDAO dao = new PremierLeagueDAO();
//		System.out.println("Players:");
//		System.out.println(dao.listAllPlayers());
//		System.out.println("Teams:");
//		System.out.println(dao.listAllTeams());
//		System.out.println("Actions:");
//		System.out.println(dao.listAllActions());
//		System.out.println("Matches:");
//		System.out.println(dao.listAllMatches());
		Map<Integer,Player> map = new HashMap<>();
		dao.setPlayerMatch(map, dao.listAllMatches().get(0));
		System.out.println(map);
		System.out.println(dao.getListaAvversari(map, dao.listAllMatches().get(0)));
	}

}
