package org.uqbar.edu.paiu.examples.celulares.dao;

import java.io.Serializable;
import java.util.List;

import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;
import org.uqbar.edu.paiu.examples.celulares.domain.ModeloCelular;

import uqbar.arena.persistence.PersistentHome;

@Observable
public class RepositorioModelos extends PersistentHome<ModeloCelular> implements Serializable {
	private static final long serialVersionUID = 4452732090618899782L;
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
		if(this.find(modeloCelular.getDescripcion()).size()==0){
			this.create(modeloCelular);
		}
	}

	public List<ModeloCelular> find(String descripcion) {
		ModeloCelular example = new ModeloCelular();
		example.setDescripcion(descripcion);
		return this.searchByExample(example);		
	}
	
	public ModeloCelular get(String descripcion) {
		List<ModeloCelular> list = this.find(descripcion);
		if(list.size() == 0)
			throw new UserException("No se encontró el modelo de celular: " + descripcion);
		
		return list.get(0);
	}

	public List<ModeloCelular> getModelos() {
		return this.allInstances();
	}
}
