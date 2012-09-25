package org.uqbar.edu.paiu.examples.celulares.ui.arena;

import java.awt.Color;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.bindings.NotNullObservable;
import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.edu.paiu.examples.celulares.domain.Celular;

/**
 * Ventana de búsqueda de celulares.
 * 
 * @see BuscadorCelular el modelo subyacente.
 * 
 * @author ?
 */
public class BuscarCelularesWindow extends SimpleWindow<BuscadorCelular> {

	public BuscarCelularesWindow(WindowOwner parent) {
		super(parent, new BuscadorCelular());
		this.getModelObject().search();
	}

	/**
	 * El default de la vista es un formulario que permite disparar la búsqueda (invocando con super) Además
	 * le agregamos una grilla con los resultados de esa búsqueda y acciones que pueden hacerse con elementos
	 * de esa búsqueda
	 */
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		this.setTitle("Buscador de Celulares");
		this.setTaskDescription("Ingrese los parámetros de búsqueda");

		super.createMainTemplate(mainPanel);

		this.createResultsGrid(mainPanel);
		this.createGridActions(mainPanel);

	}

// *************************************************************************
// * FORMULARIO DE BUSQUEDA 
// *************************************************************************
	
	/**
	 * El panel principal de búsuqeda permite filtrar por número o nombre
	 */
	@Override
	protected void createFormPanel(Panel mainPanel) {
		Panel searchFormPanel = new Panel(mainPanel);
		searchFormPanel.setLayout(new ColumnLayout(2));

		new Label(searchFormPanel).setText("Número").setForeground(Color.BLUE);
		new TextBox(searchFormPanel).bindValueToProperty("numero");

		new Label(searchFormPanel).setText("Nombre del cliente").setForeground(Color.BLUE);
		new TextBox(searchFormPanel).bindValueToProperty("nombre");
	}

	/**
	 * Acciones asociadas de la pantalla principal. Interesante para ver es cómo funciona el binding que mapea
	 * la acción que se dispara cuando el usuario presiona click Para que el binding sea flexible necesito
	 * decirle objeto al que disparo la acción y el mensaje a enviarle Contra: estoy atado a tener métodos sin
	 * parámetros. Eso me impide poder pasarle parámetros como en el caso del alta/modificación.
	 * Buscar/Limpiar -> son acciones que resuelve el modelo (BuscadorCelular) Nuevo -> necesita disparar una
	 * pantalla de alta, entonces lo resuelve la vista (this)
	 * 
	 */
	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Buscar")
			.onClick(new MessageSend(this.getModelObject(), "search"))
			.setAsDefault()
			.disableOnError();

		new Button(actionsPanel) //
			.setCaption("Limpiar")
			.onClick(new MessageSend(this.getModelObject(), "clear"));

		new Button(actionsPanel)//
			.setCaption("Nuevo Celular")
			.onClick(new MessageSend(this, "crearCelular"));
	}

// *************************************************************************
// **  RESULTADOS DE LA BUSQUEDA 
// *************************************************************************
	
	/**
	 * Se crea la grilla en el panel de abajo El binding es: el contenido de la grilla en base a los
	 * resultados de la búsqueda Cuando el usuario presiona Buscar, se actualiza el model, y éste a su vez
	 * dispara la notificación a la grilla que funciona como Observer
	 */
	protected void createResultsGrid(Panel mainPanel) {
		Table<Celular> table = new Table<Celular>(mainPanel, Celular.class);
		table.setHeigth(200);
		table.setWidth(450);

		table.bindItemsToProperty("resultados");
		table.bindValueToProperty("celularSeleccionado");

		this.describeResultsGrid(table);
	}

	/**
	 * Define las columnas de la grilla Cada columna se puede bindear 1) contra una propiedad del model, como
	 * en el caso del número o el nombre 2) contra un transformer que recibe el model y devuelve un tipo
	 * (generalmente String), como en el caso de Recibe Resumen de Cuenta
	 * 
	 * @param table
	 */
	protected void describeResultsGrid(Table<Celular> table) {
		new Column<Celular>(table) //
			.setTitle("Nombre")
			.setFixedSize(150)
			.bindContentsToProperty("nombre");

		Column<Celular> numeroColumn = new Column<Celular>(table);
		numeroColumn.setTitle("Número");
		numeroColumn.setFixedSize(100);
		numeroColumn.bindContentsToProperty("numero");

		Column<Celular> modeloColumn = new Column<Celular>(table);
		modeloColumn.setTitle("Modelo");
		modeloColumn.setFixedSize(150);
		modeloColumn.bindContentsToProperty("modeloCelular");

		Column<Celular> ingresoColumn = new Column<Celular>(table);
		ingresoColumn.setTitle("Recibe resumen de cuenta");
		ingresoColumn.setFixedSize(50);
		ingresoColumn.bindContentsToTransformer(new BooleanToSiNoTransformer());
	}

	protected void createGridActions(Panel mainPanel) {
		Panel actionsPanel = new Panel(mainPanel);
		actionsPanel.setLayout(new HorizontalLayout());

		Button edit = new Button(actionsPanel);
		edit.setCaption("Editar");
		edit.onClick(new MessageSend(this, "modificarCelular"));

		Button remove = new Button(actionsPanel);
		remove.setCaption("Borrar");
		remove.onClick(new MessageSend(this.getModelObject(), "eliminarCelularSeleccionado"));

		// Deshabilitar los botones si no hay ningún elemento seleccionado en la grilla.
		NotNullObservable elementSelected = new NotNullObservable("celularSeleccionado");
		remove.bindEnabled(elementSelected);
		edit.bindEnabled(elementSelected);
	}

	// ********************************************************
	// ** Acciones
	// ********************************************************

	public void crearCelular() {
		this.openDialog(new CrearCelularWindow(this));
	}

	public void modificarCelular() {
		this.openDialog(
			new EditarCelularWindow(
				this, this.getModelObject().getCelularSeleccionado()));
	}

	protected void openDialog(Dialog<?> dialog) {
		dialog.onAccept(new MessageSend(this.getModelObject(), "search"));
		dialog.open();
	}

}
