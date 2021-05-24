package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {

	private PriorityQueue<Evento> queue;
	
	//parametri simulazione
	private int N;
	private Match partita;
	private Team tHome;
	private Team tAway;
	private Team tbestGiocatore;
	
	//valori in output
	private int GOLALTRI;
	private int GOLBESTPLAYER;
	private int ESPULSIBESTPLAYER;
	private int ESPULSIALTRI;
	
	public void init(Match m, int N, Team t) {
		this.N = N;
		partita = m;
		tHome = new Team(m.teamHomeID, m.teamHomeNAME);
		tAway = new Team(m.teamAwayID, m.teamAwayNAME);
		tbestGiocatore = t;
		
		GOLBESTPLAYER = 0;
		GOLALTRI = 0;
		ESPULSIBESTPLAYER = 0;
		ESPULSIALTRI = 0;
		
		queue = new PriorityQueue<>();
		
		generaEventi(queue, N);
	
	}
	
	public void generaEventi(PriorityQueue<Evento> queue, int numAzioni) {
		for(int i = 0; i<=numAzioni; i++) {
			double prob = Math.random();
			if(prob<=0.5) {
				queue.add(new Evento(i,EventType.GOAL));
			}else if(prob<=0.8) {
				queue.add(new Evento(i,EventType.ESPULSIONE));
			}else {
				queue.add(new Evento(i,EventType.INFORTUNIO));
			}
		}
	}
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			Evento e = queue.poll();
			EventType tipo = e.getTipo();
			
			switch(tipo) {
				case GOAL:
					if(this.ESPULSIALTRI>this.ESPULSIBESTPLAYER) {
						this.GOLBESTPLAYER ++;
					}else if(this.ESPULSIALTRI<this.ESPULSIBESTPLAYER) {
						this.GOLALTRI ++;
					}else {
						this.GOLBESTPLAYER ++;
					}
					break;
				case ESPULSIONE:
					double probEsp = Math.random();
					if(probEsp<=0.6) {
						this.ESPULSIBESTPLAYER ++;
					}else {
						this.ESPULSIALTRI ++;
					}
					break;
				case INFORTUNIO:
					double probInf = Math.random();
					if(probInf <= 0.5) {
						generaEventi(queue,2);
					}else {
						generaEventi(queue,3);
					}
					break;
			}
						
		}
	}
	
	public int getGolHome() {
		if(tbestGiocatore.equals(tHome))
			return this.GOLBESTPLAYER;
		else
			return this.GOLALTRI;
	}
	
	public int getGolAway() {
		if(tbestGiocatore.equals(tAway))
			return this.GOLBESTPLAYER;
		else
			return this.GOLALTRI;
	}
	
	public int getEspulsiHome() {
		if(tbestGiocatore.equals(tHome))
			return this.ESPULSIBESTPLAYER;
		else
			return this.ESPULSIALTRI;
	}
	
	public int getEspulsiAway() {
		if(tbestGiocatore.equals(tAway))
			return this.ESPULSIBESTPLAYER;
		else
			return this.ESPULSIALTRI;
	}
	

}
