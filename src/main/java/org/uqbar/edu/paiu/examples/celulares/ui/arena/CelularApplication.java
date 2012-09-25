package org.uqbar.edu.paiu.examples.celulares.ui.arena;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import uqbar.arena.persistence.Configuration;

/**
 * Correr esta clase con el siguiente argument
 * 
 * -Djava.system.class.loader=org.uqbar.arena.aop.ArenaClassLoader
 */
public class CelularApplication extends Application {

	public static void main(String[] args) {
		Configuration.configure();
		new CelularApplication().start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new BuscarCelularesWindow(this);
	}
	
}
