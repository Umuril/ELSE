package com.ELSE.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.ELSE.presenter.Presenter;

class StatusBar {
	static StatusBar newInstance() {
		return new StatusBar();
	}

	private Bar bar;
	private JLabel statusText;
	private JButton add, remove, update, save, load, print;

	private StatusBar() {
		bar = Bar.newInstance();
		statusText = new JLabel();
		bar.getLeft().add(statusText);
		add = Button.newInstance(StatusBar.class.getResource("/add.png"));
		bar.getRight().add(add);
		remove = Button.newInstance(StatusBar.class.getResource("/remove.png"));
		bar.getRight().add(remove);
		update = Button.newInstance(StatusBar.class.getResource("/update.png"));
		bar.getRight().add(update);
		save = Button.newInstance(StatusBar.class.getResource("/save.png"));
		bar.getRight().add(save);
		load = Button.newInstance(StatusBar.class.getResource("/load.png"));
		bar.getRight().add(load);
		print = Button.newInstance(StatusBar.class.getResource("/print.png"));
		bar.getRight().add(print);
	}

	JButton getAddButton() {
		return add;
	}

	Bar getBar() {
		return bar;
	}

	JButton getLoadButton() {
		return load;
	}

	JButton getPrintButton() {
		return print;
	}

	JButton getRemoveButton() {
		return remove;
	}

	JButton getSaveButton() {
		return save;
	}

	JButton getUpdateButton() {
		return update;
	}

	public void needToSave(boolean need) {
		save.setIcon(new ImageIcon(new ImageIcon(StatusBar.class.getResource(need ? "/save_red.png" : "/save.png")).getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
	}

	void setPresenter(Presenter presenter) {
		add.addActionListener(presenter.getStatusBarPresenter().addMainPageButton());
		remove.addActionListener(presenter.getStatusBarPresenter().removeMainPageButton());
		update.addActionListener(presenter.getStatusBarPresenter().updateMainPageButton());
		save.addActionListener(presenter.getStatusBarPresenter().saveMainPageButton());
		load.addActionListener(presenter.getStatusBarPresenter().loadMainPageButton());
		print.addActionListener(presenter.getStatusBarPresenter().printMainPageButton());
	}

	void setStatusText(String s) {
		statusText.setText(s);
	}
}
