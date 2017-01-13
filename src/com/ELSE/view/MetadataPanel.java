package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ELSE.model.BookMetadata;
import com.ELSE.presenter.Presenter;

class MetadataPanel {

	private JPanel parent;
	private JLabel bookPreview;
	private JTextField titolo, autore, anno, pagine;
	private BookMetadata book;

	JTextField getTitolo() {
		return titolo;
	}

	JTextField getAutore() {
		return autore;
	}

	JTextField getAnno() {
		return anno;
	}

	JTextField getPagine() {
		return pagine;
	}

	private MetadataPanel(JPanel parent) {
		this.parent = parent;
		// I don't like it but seems to work
		bookPreview = new JLabel();
	}

	static MetadataPanel newInstance(JPanel parent) {
		return new MetadataPanel(parent);
	}

	void change(Image image, BookMetadata book, boolean editable) {

		System.out.println("CHANGING TO " + book + " - " + editable);

		this.book = book;

		parent.removeAll();
		bookPreview.setIcon(new ImageIcon(new ImageIcon(image).getImage()
				.getScaledInstance(-1, 300, Image.SCALE_DEFAULT)));
		parent.add(bookPreview, BorderLayout.WEST);
		JPanel things = JInvisiblePanel.newInstance(parent);
		things.setLayout(new FlowLayout());
		JPanel panel = JInvisiblePanel.newInstance(things);
		panel.setLayout(new GridLayout(0, 2));
		if (editable) {

			JLabel ltitolo = new JLabel("Titolo: ");
			panel.add(ltitolo);
			titolo = new JTextField(15);
			titolo.setText(book.getTitolo());
			panel.add(titolo);
			JLabel lautore = new JLabel("Autore: ");
			panel.add(lautore);
			autore = new JTextField(15);
			autore.setText(book.getAutore());
			panel.add(autore);
			JLabel lanno = new JLabel("Anno: ");
			panel.add(lanno);
			anno = new JTextField(15);
			anno.setText(book.getAnno() != null ? book.getAnno().toString()
					: "");
			panel.add(anno);
			JLabel lpagine = new JLabel("Pagine: ");
			panel.add(lpagine);
			pagine = new JTextField(15);
			pagine.setText(Integer.toString(book.getNpagine()));
			panel.add(pagine);

		} else {

			JLabel ltitolo = new JLabel("Titolo: ");
			panel.add(ltitolo);
			JLabel titolo = new JLabel(book.getTitolo());
			panel.add(titolo);

			JLabel lautore = new JLabel("Autore: ");
			panel.add(lautore);
			JLabel autore = new JLabel(book.getAutore());
			panel.add(autore);

			JLabel lanno = new JLabel("Anno: ");
			panel.add(lanno);
			JLabel anno = new JLabel(book.getAnno() != null ? book.getAnno()
					.toString() : "");
			panel.add(anno);

			JLabel lpagine = new JLabel("Pagine: ");
			panel.add(lpagine);
			JLabel pagine = new JLabel(Integer.toString(book.getNpagine()));
			panel.add(pagine);

		}
		things.add(panel);
		parent.add(things, BorderLayout.CENTER);
		parent.revalidate();
		parent.repaint();
	}

	void setPresenter(Presenter presenter) {
		for (MouseListener ml : bookPreview.getMouseListeners())
			bookPreview.removeMouseListener(ml);
		bookPreview.addMouseListener(presenter.getCenterPresenter().openBook(
				book));
	}
}
