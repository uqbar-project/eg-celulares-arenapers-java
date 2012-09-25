package org.uqbar.edu.paiu.examples.celulares.ui.arena;

import org.uqbar.edu.paiu.examples.celulares.domain.Celular;

import com.uqbar.commons.collections.Transformer;

/**
 * Transforma un booleano a un String, devolviendo "SÍ" en caso de ser verdadero y "NO" en caso de ser falso.
 * 
 * @author npasserini
 */
public final class BooleanToSiNoTransformer implements Transformer<Celular, String> {
	@Override
	public String transform(Celular celular) {
		if(celular.getRecibeResumenCuenta() == null)
			return "NO";
		return celular.getRecibeResumenCuenta() ? "SÍ" : "NO";
	}
}