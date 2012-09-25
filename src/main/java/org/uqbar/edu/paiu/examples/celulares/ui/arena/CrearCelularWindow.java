package org.uqbar.edu.paiu.examples.celulares.ui.arena;

import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.edu.paiu.examples.celulares.dao.RepositorioCelulares;
import org.uqbar.edu.paiu.examples.celulares.domain.Celular;

public class CrearCelularWindow extends EditarCelularWindow {

	public CrearCelularWindow(WindowOwner owner) {
		super(owner, new Celular());
	}

	@Override
	protected void executeTask() {
		RepositorioCelulares.getInstance().create(this.getModelObject());
		super.executeTask();
	}
}
