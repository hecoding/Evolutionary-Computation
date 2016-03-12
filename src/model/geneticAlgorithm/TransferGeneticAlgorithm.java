package model.geneticAlgorithm;

import view.gui.swing.SettingsPanel.Check;
import view.gui.swing.SettingsPanel.No;

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
	
	public void setDefault() {
		this.funcion = new String("función 1");
		this.precision = 0.001;
		this.poblacion = 100;
		this.generaciones = 100;
		this.porcCruces = 0.6;
		this.porcMutacion = 0.05;
		this.semillaPersonalizada = false;
		this.semilla = 0;
		this.setElitismo(new No());
	}
	
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

	public String toString() {
		return "función: " + this.funcion + System.lineSeparator() +
				"precisión: " + this.precision + System.lineSeparator() +
				"población: " + this.poblacion + System.lineSeparator() +
				"generaciones: " + this.generaciones + System.lineSeparator() +
				"% cruces: " + this.porcCruces + System.lineSeparator() +
				"% mutación: " + this.porcMutacion + System.lineSeparator() +
				"semilla personalizada: " + this.semillaPersonalizada + System.lineSeparator() +
				"semilla: " + this.semilla + System.lineSeparator() +
				"elitismo: " + this.elitismo;
	}
}
