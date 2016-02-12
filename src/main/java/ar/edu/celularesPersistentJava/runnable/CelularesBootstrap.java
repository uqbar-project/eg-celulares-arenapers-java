package ar.edu.celularesPersistentJava.runnable;

import org.uqbar.arena.bootstrap.Bootstrap;

import ar.edu.celularesPersistentJava.domain.ModeloCelular;
import ar.edu.celularesPersistentJava.repos.RepositorioCelulares;
import ar.edu.celularesPersistentJava.repos.RepositorioModelos;

public class CelularesBootstrap implements Bootstrap {

	@Override
	public void run() {
		RepositorioModelos repoModelos = RepositorioModelos.getInstance();
		ModeloCelular nokia1100 = repoModelos.createIfNotExists(new ModeloCelular("NOKIA 1100", 150, true));
		repoModelos.createIfNotExists(new ModeloCelular("Motorola M90", 350, true));
		repoModelos.createIfNotExists(new ModeloCelular("Samsung Galaxy SII", 440));

		RepositorioCelulares repoCelulares = RepositorioCelulares.getInstance();
		repoCelulares.createIfNotExists("Laura Iturbe", 21212524, nokia1100, true);
	}

	@Override
	public boolean isPending() {
		// por ahora no funciona pero esta es la idea
		return RepositorioCelulares.getInstance().allInstances().isEmpty();
	}

}
