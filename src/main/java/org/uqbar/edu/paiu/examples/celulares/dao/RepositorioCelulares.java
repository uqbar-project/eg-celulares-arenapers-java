package org.uqbar.edu.paiu.examples.celulares.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;
import org.uqbar.edu.paiu.examples.celulares.domain.Celular;

import uqbar.arena.persistence.PersistentHome;

/**
 * 
 * @author npasserini
 */
@Observable
public class RepositorioCelulares extends PersistentHome<Celular> implements Serializable {
	private static RepositorioCelulares instance;
	private List<Celular> data = new ArrayList<Celular>();

	public static synchronized RepositorioCelulares getInstance() {
		if (instance == null) {
			instance = new RepositorioCelulares();
		}
		return instance;
	}

	private RepositorioCelulares() {
	}

	public void create(Celular celular) {
		this.validarClientesDuplicados(celular);
		celular.validar();
		super.create(celular);
	}

	protected void validarClientesDuplicados(Celular celular) {
		if (!this.search(celular.getNumero()).isEmpty()) {
			throw new UserException("Ya existe un celular con el número: " + celular.getNumero());
		}
	}

	// ********************************************************
	// ** Búsquedas
	// ********************************************************

	public List<Celular> search(Integer numero) {
		return this.search(numero, null);
	}

	/**
	 * Busca los celulares que coincidan con los datos recibidos. Tanto número como nombre pueden ser nulos,
	 * en ese caso no se filtra por ese atributo.
	 * 
	 * Soporta búsquedas por substring, por ejemplo el celular (12345, "Juan Gonzalez") será contemplado por
	 * la búsqueda (23, "Gonza")
	 */
	public List<Celular> search(Integer numero, String nombre) {
		Celular example = new Celular(nombre, numero);
		return this.searchByExample(example);
	}

	@Override
	public Class<Celular> getEntityType() {
		return Celular.class;
	}

	@Override
	public Celular createExample() {
		return new Celular();
	}
}
