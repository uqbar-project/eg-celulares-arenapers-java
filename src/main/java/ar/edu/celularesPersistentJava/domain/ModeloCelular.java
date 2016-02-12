package ar.edu.celularesPersistentJava.domain;
import java.math.BigDecimal;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.TransactionalAndObservable;

import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;


@SuppressWarnings("serial")
@TransactionalAndObservable
@PersistentClass
public class ModeloCelular extends Entity {
	public static final String DESCRIPCION = "descripcion";
	
	@PersistentField private String descripcion;
	@PersistentField private BigDecimal costo;
	@PersistentField private Boolean requiereResumenCuenta;  // FED: boolean tiene problemas
	
	public ModeloCelular(){
	}
	
	public ModeloCelular(String descripcion, double costo) {
		this(descripcion, costo, false);
	}

	public ModeloCelular(String descripcion, double costo, boolean requiereResumenCuenta) {
		this.descripcion = descripcion;
		this.costo = new BigDecimal(costo);
		this.requiereResumenCuenta = requiereResumenCuenta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
	
	public String getDescripcionEntera() {
		return this.getDescripcion() + " ($ " + this.getCosto() + ")";
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public BigDecimal getCosto() {
		return this.costo;
	}
	
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	
	public Boolean getRequiereResumenCuenta() {
		return requiereResumenCuenta;
	}

	public void setRequiereResumenCuenta(Boolean requiereResumenCuenta) {
		this.requiereResumenCuenta = requiereResumenCuenta;
	}

	@Override
	public String toString() {
		return this.getDescripcionEntera();
	}

}
