package ar.edu.celularesPersistentJava.repos;

import java.io.Serializable;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import uqbar.arena.persistence.PersistentRepo;
import ar.edu.celularesPersistentJava.domain.ModeloCelular;

@SuppressWarnings("serial")
@Observable
public class RepositorioModelos extends PersistentRepo<ModeloCelular> implements Serializable {
	
	/**
	 * Definición del Singleton
	 */
	private static RepositorioModelos instance;

	public static RepositorioModelos getInstance() {
		if (instance == null) {
			instance = new RepositorioModelos();
		}
		return instance;
	}

	public ModeloCelular createExample(){
		return new ModeloCelular();
	}
	
	public Class<ModeloCelular> getEntityType(){
		return ModeloCelular.class;
	}
	
	private RepositorioModelos() {
		this.createIfNotExists(new ModeloCelular("NOKIA 1100", 150, true));
		this.createIfNotExists(new ModeloCelular("Motorola M90", 350, true));
		this.createIfNotExists(new ModeloCelular("Samsung Galaxy SII", 440));
	}
	
	private void createIfNotExists(ModeloCelular modeloCelular) {
		if(this.find(modeloCelular.getDescripcion()).isEmpty()){
			this.create(modeloCelular);
		}
	}

	public List<ModeloCelular> find(String descripcion) {
		ModeloCelular example = new ModeloCelular();
		example.setDescripcion(descripcion);
		return this.searchByExample(example);		
	}
	
	public ModeloCelular get(String descripcion) {
		List<ModeloCelular> modelos = this.find(descripcion);
		if(modelos.isEmpty())
			throw new UserException("No se encontró el modelo de celular: " + descripcion);
		
		return modelos.get(0);
	}

	public List<ModeloCelular> getModelos() {
		return this.allInstances();
	}
}
