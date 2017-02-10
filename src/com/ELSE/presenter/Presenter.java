package com.ELSE.presenter;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.presenter.reader.EbookReader;
import com.ELSE.view.View;

public class Presenter implements WindowListener {
	private final CenterPresenter centerPresenter;
	private final MenuBarPresenter menuBarPresenter;
	private final Model model;
	private final StatusBarPresenter statusBarPresenter;
	private final View view;
	
	public Presenter(final View view, final Model model) {
		this.view = view;
		this.model = model;
		centerPresenter = new CenterPresenter(view, model, this);
		menuBarPresenter = new MenuBarPresenter(view, model, centerPresenter);
		statusBarPresenter = new StatusBarPresenter(view, model, centerPresenter);
		updateAllColors();
	}
	
	public CenterPresenter getCenterPresenter() {
		return centerPresenter;
	}
	
	BufferedImage getCover(final Path path) {
		try {
			return EbookReader.newInstance(path).getCover();
		} catch (final IOException ex) {
			view.setStatusText("Errore nella lettura del libro");
			return null;
		}
	}
	
	public MenuBarPresenter getMenuBarPresenter() {
		return menuBarPresenter;
	}
	
	void getReader(final Path path) {
		Utils.log(Utils.Debug.DEBUG, path);
		try {
			EbookReader.newInstance(path).getFrame();
		} catch (final IOException ex) {
			view.setStatusText("Errore nella lettura del libro");
		}
	}
	
	public StatusBarPresenter getStatusBarPresenter() {
		return statusBarPresenter;
	}
	
	private void updateAllColors() {
		view.updateColor(new Color(Integer.parseInt(Utils.getPreferences("Color1"))));
		final Color color2 = new Color(Integer.parseInt(Utils.getPreferences("Color2")));
		view.getAdvanceSearch().updateColor(color2);
		view.getSettings().updateColor(color2);
		view.getMenuBar().getParent().updateColor(color2);
		view.getStatusBar().getBar().updateColor(color2);
		final Color backcolor = new Color(Integer.parseInt(Utils.getPreferences("BackColor")));
		view.getBookDetailsPage().updateColor(backcolor);
		view.getSliderPage().updateColor(backcolor);
	}
	
	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void windowClosing(final WindowEvent e) {
		if (!view.needSave()) {
			view.getFrame().dispose();
			System.exit(0);
		} else if (Boolean.parseBoolean(Utils.getPreferences("Save")))
			try {
				model.getPathTree().createPathTreeFile(Paths.get(Utils.getPreferences("PathTree")));
				model.getLibrary().createLibraryFile();
				view.getFrame().dispose();
				System.exit(0);
			} catch (final IOException ex) {
				// TODO Need checks
				view.setStatusText("Errore salvataggio prima di chiudere");
			}
		else {
			if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire senza salvare?", "Chiudi senza salvare", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				view.getFrame().dispose();
				System.exit(0);
			}
		}
	}
	
	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub
	}
}
