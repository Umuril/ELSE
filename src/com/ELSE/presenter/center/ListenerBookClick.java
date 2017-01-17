package com.ELSE.presenter.center;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import com.ELSE.model.BookMetadata;
import com.ELSE.view.View;

public class ListenerBookClick implements ActionListener {
	private BookMetadata book;
	private BufferedImage image;
	private View view;

	public ListenerBookClick(View view, BufferedImage image, BookMetadata book) {
		this.view = view;
		this.image = image;
		this.book = book;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.change(image, book);
	}
}
