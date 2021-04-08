package controller;

import model.Model;
import view.GUI;

/**
 * 
 * Class to update model->view according to MVC pattern. Actually, it only adds
 * listeners to gui components (buttons, inputs) and observer to observable to
 * update view automatically once model is changed.
 *
 */
public class Controller {

	/**
	 * {@link Model}
	 */
	private Model model;

	/**
	 * {@link GUI}
	 */
	private GUI view;

	/**
	 * Constructor to initialize Controller with model and view.
	 * 
	 * @param model {@link Model}
	 * @param view  {@link GUI}
	 */
	public Controller(Model model, GUI view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Initializes the controller by adding observers and listeners. Listeners are
	 * responsible for the user interaction (click button, change value etc.).
	 * Observers are needed to update state of observable classes (views, model).
	 */
	public void init() {

		model.addObserver(view.getOptionsView()); // To update selected files once model updated.
		model.addObserver(view.getProgressView()); // To update progress.
		model.addObserver(view.getResultView()); // To update result.
		model.getKnn().addObserver(model); // To update model once KNN changes (progress bar).

		view.getMenuView().getMenuItemExit().addActionListener(e -> view.exit());
		view.getOptionsView().getBtnStart().addActionListener(e -> model.start());
		view.getOptionsView().getBtnSrc()
				.addActionListener(e -> model.setSourceFiles(view.getOptionsView().getSourceFiles()));
		view.getOptionsView().getBtnTest()
				.addActionListener(e -> model.setTestFiles(view.getOptionsView().getTestFiles()));
		view.getProgressView().getBtnStop().addActionListener(e -> model.stop());
		view.getOptionsView().getInputK()
				.addChangeListener(e -> model.setK((int) view.getOptionsView().getInputK().getModel().getValue()));
		view.getOptionsView().getBtnReset().addActionListener(e -> model.reset());
	}

}
