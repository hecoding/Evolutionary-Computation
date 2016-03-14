package model.geneticAlgorithm;

import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.Percentage;

public class TransferGeneticAlgorithm {
	private String funcion;
	private Double precision;
	private int poblacion;
	private int generaciones;
	private double porcCruces;
	private double porcMutacion;
	private boolean semillaPersonalizada;
	private int semilla;
	private Check elitismo;
	private Percentage porcElite;
	
	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public int getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}

	public int getGeneraciones() {
		return generaciones;
	}

	public void setGeneraciones(int generaciones) {
		this.generaciones = generaciones;
	}

	public double getPorcCruces() {
		return porcCruces;
	}

	public void setPorcCruces(double porcCruces) {
		this.porcCruces = porcCruces;
	}

	public double getPorcMutacion() {
		return porcMutacion;
	}

	public void setPorcMutacion(double porcMutacion) {
		this.porcMutacion = porcMutacion;
	}

	public boolean getSemillaPersonalizada() {
		return semillaPersonalizada;
	}

	public void setSemillaPersonalizada(boolean semillaPersonalizada) {
		this.semillaPersonalizada = semillaPersonalizada;
	}

	public int getSemilla() {
		return semilla;
	}

	public void setSemilla(int semilla) {
		this.semilla = semilla;
	}

	public Check getElitismo() {
		return elitismo;
	}

	public void setElitismo(Check elitismo) {
		this.elitismo = elitismo;
	}

	public Percentage getPorcElite() {
		return porcElite;
	}

	public void setPorcElite(Percentage porcElite) {
		this.porcElite = porcElite;
	}

	public String toString() {
		return "función: " + this.funcion + System.lineSeparator() +
				"precisión: " + this.precision + System.lineSeparator() +
				"población: " + this.poblacion + System.lineSeparator() +
				"generaciones: " + this.generaciones + System.lineSeparator() +
				"% cruces: " + this.porcCruces + System.lineSeparator() +
				"% mutación: " + this.porcMutacion + System.lineSeparator() +
				"semilla personalizada: " + this.semillaPersonalizada + System.lineSeparator() +
				"semilla: " + this.semilla + System.lineSeparator() +
				"elitismo: " + this.elitismo + System.lineSeparator() +
				"% elitismo: " + this.porcElite.getPerc();
	}
}
