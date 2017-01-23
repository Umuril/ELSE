package com.ELSE.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmptyPage implements CentralProperties {
	static EmptyPage newInstance() {
		return new EmptyPage();
	}

	private JPanel parent;

	private EmptyPage() {
		parent = CentralPage.newInstance(this);
	}

	@Override
	public JPanel getContainerPanel() {
		return parent;
	}

	@Override
	public JPanel initDown(JPanel parent) {
		return SubSizePanel.newInstance(parent);
	}

	@Override
	public JPanel initUp(JPanel parent) {
		JPanel up = JInvisiblePanel.newInstance(parent);
		up.add(new JLabel("Attualmente non ci sono libri, si prega di usare il tasto più per aggiungere cartelle o singoli file"));
		up.add(new JLabel("I formati supportati sono EPUB, HTML e PDF"));
		return up;
	}
}