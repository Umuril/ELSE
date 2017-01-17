package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;
import com.ELSE.presenter.Presenter;

class BookDetailsPage implements CentralProperties {
	static BookDetailsPage newInstance() {
		return new BookDetailsPage();
	}

	private JButton back, edit, save;
	private BookMetadata book;
	private boolean editable;
	private Image image;
	private MetadataPanel metadataPanel;
	private JPanel parent;
	private Presenter presenter;
	private JPanel up, down;

	private BookDetailsPage() {
		parent = CentralPage.newInstance(this);
		editable = false; // this vs field intanced
	}

	@Override
	public JPanel getContainerPanel() {
		return parent;
	}

	JPanel getDown() {
		return down;
	}

	public MetadataPanel getMetadataPanel() {
		return metadataPanel;
	}

	JPanel getUp() {
		return up;
	}

	@Override
	public JPanel initDown(JPanel container) {
		down = SubSizePanel.newInstance(container);
		back = Button.newInstance(SliderPage.class.getResource("/back.png"));
		down.add(back);
		back.setAlignmentX(Component.LEFT_ALIGNMENT);
		JPanel dcenter = JInvisiblePanel.newInstance(container);
		edit = Button.newInstance(SliderPage.class.getResource("/edit.png"));
		dcenter.add(edit);
		down.add(dcenter);
		dcenter.setAlignmentX(Component.CENTER_ALIGNMENT);
		save = Button.newInstance(SliderPage.class.getResource("/save.png"));
		down.add(save);
		save.setAlignmentX(Component.RIGHT_ALIGNMENT);
		return down;
	}

	@Override
	public JPanel initUp(JPanel container) {
		up = CentralSizePanel.newInstance(container);
		up.setLayout(new BorderLayout());
		up.setBackground(Color.white);
		metadataPanel = MetadataPanel.newInstance(up);
		return up;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		back.addActionListener(presenter.getCenterPresenter().backFromBookDetail());
		edit.addActionListener(presenter.getCenterPresenter().setBookDetailPageEditable());
		// metadataPanel.setPresenter(presenter);
	}

	public void update() {
		if (image != null && book != null)
			metadataPanel.change(image, book, editable);
	}

	void updateUpWith(Image image, BookMetadata book) {
		this.image = image;
		this.book = book;
		metadataPanel.change(image, book, editable);
		// Needs further checks
		metadataPanel.setPresenter(presenter);
		for (ActionListener al : save.getActionListeners())
			save.removeActionListener(al);
		save.addActionListener(presenter.getCenterPresenter().saveBookDetailPageChanges(book));
	}
}
