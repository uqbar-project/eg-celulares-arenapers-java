package ar.edu.celularesPersistentJava.runnable;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import ar.edu.celularesPersistentJava.ui.BuscarCelularesWindow;
import uqbar.arena.persistence.Configuration;

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
