package com.ELSE.presenter.statusBar;

import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import com.ELSE.model.Model;
import com.ELSE.presenter.Presenter;
import com.ELSE.view.View;

public class StatusBarPresenter {

	private View view;
	private Model model;
	private Presenter presenter;

	public StatusBarPresenter(View view, Model model, Presenter presenter) {
		this.view = view;
		this.model = model;
		this.presenter = presenter;
	}

	public ActionListener addMainPageButton() {
		return new AddMainPageButtonListener(view, model.getPathbase());
	}

	public ActionListener loadMainPageButton() {
		return new LoadMainPageButtonListener();
	}

	public ActionListener saveMainPageButton() {
		return new SaveMainPageButtonListener(this);
	}

	public ActionListener updateMainPageButton() {
		return new UpdateMainPageButtonListener(presenter.getCenterPresenter());
	}

	public ActionListener printMainPageButton() {
		return new PrintMainPageButtonListener();
	}

	public void aggiungiFile() {
		view.setStatusText("aggiungiFile");
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = jfc.showOpenDialog(view.getFrame());
		if (result == JFileChooser.APPROVE_OPTION)
			model.getPathbase().add(jfc.getSelectedFile().getAbsolutePath());
	}

	public void createMetadataFile() {
		model.getLibrary().createFile();
	}

}