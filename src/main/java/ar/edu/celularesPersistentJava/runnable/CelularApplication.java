package ar.edu.celularesPersistentJava.runnable;

import org.uqbar.arena.Application;
import org.uqbar.arena.windows.Window;

import uqbar.arena.persistence.Configuration;
import ar.edu.celularesPersistentJava.ui.BuscarCelularesWindow;

public class CelularApplication extends Application {

	public CelularApplication(CelularesBootstrap celularesBootstrap) {
		super(celularesBootstrap);
	}

	public static void main(String[] args) {
		Configuration.configure();
		new CelularApplication(new CelularesBootstrap()).start();
	}

	@Override
	protected Window<?> createMainWindow() {
		return new BuscarCelularesWindow(this);
	}
	
}
