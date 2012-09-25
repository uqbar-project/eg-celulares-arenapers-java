package org.uqbar.edu.paiu.examples.celulares.ui.arena;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.aop.windows.TransactionalDialog;
import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.CheckBox;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.edu.paiu.examples.celulares.dao.RepositorioCelulares;
import org.uqbar.edu.paiu.examples.celulares.dao.RepositorioModelos;
import org.uqbar.edu.paiu.examples.celulares.domain.Celular;
import org.uqbar.edu.paiu.examples.celulares.domain.ModeloCelular;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

public class EditarCelularWindow extends TransactionalDialog<Celular> {

	public EditarCelularWindow(WindowOwner owner, Celular model) {
		super(owner, model);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel form = new Panel(mainPanel);
		form.setLayout(new ColumnLayout(2));

		new Label(form).setText("Número");
		new TextBox(form).bindValueToProperty("numero");

		new Label(form).setText("Nombre del cliente");
		new TextBox(form).bindValueToProperty("nombre");

		new Label(form).setText("Modelo del aparato");
		Selector<ModeloCelular> selector = new Selector<ModeloCelular>(form) //
			.allowNull(false);
		selector.bindValueToProperty("modeloCelular");

		Binding<ListBuilder<ModeloCelular>> itemsBinding = selector.bindItems( //
			new ObservableProperty(RepositorioModelos.getInstance(), "modelos"));
		itemsBinding.setAdapter( //
			new PropertyAdapter(ModeloCelular.class, "descripcionEntera"));

		new Label(form).setText("Recibe resumen cuenta en domicilio");
		CheckBox chkRecibeResumenCuenta = new CheckBox(form);
		chkRecibeResumenCuenta.bindValueToProperty("recibeResumenCuenta");
		chkRecibeResumenCuenta.bindEnabledToProperty("habilitaResumenCuenta");
	}

	@Override
	protected void addActions(Panel actions) {
		new Button(actions)
			.setCaption("Aceptar")
			.onClick(new MessageSend(this, "accept"))
			.setAsDefault()
			.disableOnError();

		new Button(actions) //
			.setCaption("Cancelar")
			.onClick(new MessageSend(this, "cancel"));
		this.onAccept(new Action() {
			@Override
			public <T> void execute(T... objects) {
				
			}
			
			@Override
			public void execute() {
				RepositorioCelulares.getInstance().update(EditarCelularWindow.this.getModelObject());
			}
		});
	}
}
