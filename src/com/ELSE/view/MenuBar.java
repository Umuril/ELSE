package com.ELSE.view;

import javax.swing.JButton;

import com.ELSE.presenter.Presenter;

class MenuBar {
	static MenuBar newInstance() {
		return new MenuBar();
	}

	private JButton advSearch;
	private Bar parent;
	private SearchBar searchBar;
	private JButton settings;

	private MenuBar() {
		parent = Bar.newInstance();
		settings = Button.newInstance(MenuBar.class.getResource("/settings_gray.png"));
		parent.getLeft().add(settings);
		searchBar = SearchBar.newInstance();
		parent.getRight().add(searchBar.getPanel());
		advSearch = Button.newInstance(MenuBar.class.getResource("/advsearch.png"));
		parent.getRight().add(advSearch);
	}

	JButton getAdvSearch() {
		return advSearch;
	}

	Bar getParent() {
		return parent;
	}

	SearchBar getSearchBar() {
		return searchBar;
	}

	JButton getSettings() {
		return settings;
	}

	void setPresenter(Presenter presenter) {
		settings.addActionListener(presenter.getMenuBarPresenter().settings());
		searchBar.getTesto().addActionListener(presenter.getMenuBarPresenter().search(searchBar.getTesto()));
		searchBar.getIcona().addActionListener(presenter.getMenuBarPresenter().search(searchBar.getTesto()));
		advSearch.addActionListener(presenter.getMenuBarPresenter().advSearch());
	}
}
