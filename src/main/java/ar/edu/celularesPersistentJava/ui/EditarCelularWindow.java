package ar.edu.celularesPersistentJava.ui;

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
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;

import ar.edu.celularesPersistentJava.domain.Celular;
import ar.edu.celularesPersistentJava.domain.ModeloCelular;
import ar.edu.celularesPersistentJava.repos.RepositorioCelulares;
import ar.edu.celularesPersistentJava.repos.RepositorioModelos;

@SuppressWarnings("serial")
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

		Binding<ModeloCelular, Selector<ModeloCelular>, ListBuilder<ModeloCelular>> itemsBinding = selector.bindItems( //
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
			.onClick(() -> {
				RepositorioCelulares repoCelulares = RepositorioCelulares.getInstance();
				Celular modelObject = this.getModelObject();
				if (modelObject.isNew()) {
					repoCelulares.create(modelObject);
				} else {
					repoCelulares.update(modelObject);
				}
				this.accept();
			})
			.setAsDefault()
			.disableOnError();

		new Button(actions) //
				.setCaption("Cancelar").onClick( () -> this.cancel() );
	}
}
