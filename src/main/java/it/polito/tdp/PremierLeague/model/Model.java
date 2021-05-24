package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Player, DefaultWeightedEdge> grafo;
	Map< Integer,Player> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(Match m) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		idMap = new HashMap<>();
		dao.setPlayerMatch(idMap, m);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//archi
		for(Avversari a: dao.getListaAvversari(idMap, m)) {
			Player p1 = a.getPlayer1();
			Player p2 = a.getPlayer2();
			double eff = a.getDelta();
			Graphs.addEdge(this.grafo, p1, p2, eff);
			p1.setDeltaEff(p1.getDeltaEff()+eff);
			p2.setDeltaEff(p2.getDeltaEff()-eff);
		}
		
	}
	
	public Set<Player> getVertexSet(){
		if(grafo !=  null)
			return this.grafo.vertexSet();
		else
			return null;
	}
	
	public Set<DefaultWeightedEdge> getEdgeSet(){
		if(grafo !=  null)
			return this.grafo.edgeSet();
		else
			return null;
	}
	
	public Player getBestPlayer() {
		double effMigliore = 0;
		Player bestPlayer = null;
		for(Player p: grafo.vertexSet()) {
			if(p.getDeltaEff() > effMigliore) {
				bestPlayer = p;
				effMigliore = p.getDeltaEff();
			}
		}
		
		return bestPlayer;
	}
	
	public List<Match> listMatch(){
		return dao.listAllMatches();
	}
}
