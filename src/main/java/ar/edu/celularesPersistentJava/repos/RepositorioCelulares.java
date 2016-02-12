package ar.edu.celularesPersistentJava.repos;

import java.io.Serializable;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import uqbar.arena.persistence.PersistentRepo;
import ar.edu.celularesPersistentJava.domain.Celular;

/**
 * 
 * @author npasserini
 */
@SuppressWarnings("serial")
@Observable
public class RepositorioCelulares extends PersistentRepo<Celular> implements Serializable {
	private static RepositorioCelulares instance;

	/**
	 * Definición del Singleton
	 */
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
	 * Para que funcione correctamente el search by example hay que tener cuidado 
	 * ya que se incluyen en la búsqueda cualquiera de los valores de un objeto example que no sean nulos, esto implica
	 * 1) ojo con los tipos primitivos boolean, int, float, etc.
	 * 2) pero además ojo con los valores inicializados por default, tanto en el constructor como en la definición de la clase
	 * ej: Boolean recibeResumenCuenta = false implica que siempre va a buscar a los clientes que no reciban resumen de cuenta
	 *
	 * Para soportar búsquedas por substring hay que descomentar todo el código de abajo, el problema es que trae 
	 * a memoria todo el grafo de celulares (con una cantidad enorme de celulares puede traer problemas de performance)
	 * En ese caso el celular (12345, "Juan Gonzalez") será contemplado por la búsqueda (23, "Gonza")
	 * 
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
