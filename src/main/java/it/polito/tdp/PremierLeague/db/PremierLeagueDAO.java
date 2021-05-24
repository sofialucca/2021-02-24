package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Avversari;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID "
				+ "ORDER BY m.MatchID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setPlayerMatch(Map<Integer,Player> map,Match m){
		String sql = "SELECT p.PlayerID, p.Name "
				+ "FROM players AS p, actions AS a "
				+ "WHERE p.PlayerID = a.PlayerID AND a.MatchID = ?";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				if(!map.containsKey(player.getPlayerID()))
					map.put(player.getPlayerID(),player);
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Avversari> getListaAvversari(Map<Integer,Player> map, Match m){
		String sql = "SELECT a1.PlayerID, a2.playerID, ((a1.TotalSuccessfulPassesAll+ a1.assists)/a1.timePlayed) AS eff1, ((a2.TotalSuccessfulPassesAll+ a2.assists)/a2.timePlayed) AS eff2 "
				+ "FROM actions AS a1, actions AS a2 "
				+ "WHERE a1.TeamID <> a2.TeamID AND a1.MatchID = ? AND a2.MatchID = a1.MatchID "
				+ "HAVING eff1>eff2 OR (eff1=eff2 and a1.playerID > a2.playerID)";
		List<Avversari> result = new LinkedList<Avversari>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player1 = map.get(res.getInt("a1.PlayerId"));
				Player player2 = map.get(res.getInt("a2.PlayerId"));
				if(player1 != null && player2 != null) {
					result.add(new Avversari(player1,player2, (res.getDouble("eff1")-res.getDouble("eff2"))));
				}

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Team getTeamPlayer(Player p) {
		String sql = "SELECT DISTINCT t.TeamID, t.Name "
				+ "FROM actions AS a, teams AS t "
				+ "WHERE t.TeamID = a.TeamID and a.PlayerID = ?";
		
		Team team = null;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, p.getPlayerID());
			ResultSet res = st.executeQuery();
			if(res.next()) {
				team = new Team(res.getInt("TeamID"), res.getString("Name"));
			}
			conn.close();
			return team;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
