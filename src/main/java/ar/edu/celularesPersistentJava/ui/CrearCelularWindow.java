package ar.edu.celularesPersistentJava.ui;

import org.uqbar.arena.windows.WindowOwner;

import ar.edu.celularesPersistentJava.domain.Celular;

@SuppressWarnings("serial")
public class CrearCelularWindow extends EditarCelularWindow {

	public CrearCelularWindow(WindowOwner owner) {
		super(owner, new Celular());
	}

}
