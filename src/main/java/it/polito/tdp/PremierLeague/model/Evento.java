package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{

	enum EventType{
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	};
	
	private int azione;
	private Team squadra;
	private EventType tipo;
	
	public Evento(int azione, EventType tipo) {
		super();
		this.azione = azione;
		//this.squadra = squadra;
		this.tipo = tipo;
	}

	public int getAzione() {
		return azione;
	}

	public void setAzione(int azione) {
		this.azione = azione;
	}

	public Team getSquadra() {
		return squadra;
	}

	public void setSquadra(Team squadra) {
		this.squadra = squadra;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.azione-o.azione;
	}
	
	
}
