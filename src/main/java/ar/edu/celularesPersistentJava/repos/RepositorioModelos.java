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
	}
	
	public ModeloCelular createIfNotExists(ModeloCelular modeloCelular) {
		if(this.find(modeloCelular.getDescripcion()).isEmpty()){
			this.create(modeloCelular);
		}
		return modeloCelular;
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
