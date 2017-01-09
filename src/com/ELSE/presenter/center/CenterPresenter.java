package com.ELSE.presenter.center;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.MD5Checksum;
import com.ELSE.model.Model;
import com.ELSE.view.View;

public class CenterPresenter {

	private View view;
	private Model model;

	public CenterPresenter(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	public ActionListener clickOnABook(BufferedImage image, BookMetadata book) {
		return new ListenerBookClick(view, image, book);
	}

	public ActionListener backFromBookDetail() {
		return new ListenerBackButton(this, view);
	}

	public ActionListener setBookDetailPageEditable() {
		return new ListenerEditButton(view);
	}

	public ActionListener saveBookDetailPageChanges(BookMetadata book) {
		return new ListenerSaveButton(view, book);
	}

	public void change(Image image, BookMetadata book) {
		view.change(image, book);
	}

	public void loadFromFile(String filename) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File("db.txt")), Charset.defaultCharset()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Path path = Paths.get(line).toRealPath();
				model.getPathbase().add(path.toString());
			}

		} catch (IOException e) {
		}
	}

	public void aggiorna() {
		view.getUpSlider().removeAll();
		// internalPanel.removeAll();

		for (String s : model.getPathbase().getPathsList()) {
			File path = new File(s);
			try {
				if (path.isDirectory())
					Files.walkFileTree(Paths.get(s), new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							if (view.getUpSlider().getComponentCount() >= 14) {
								return FileVisitResult.TERMINATE;
							}
							if (file.toString().endsWith(".pdf")) {
								view.setStatusText("Adding image: " + file.toFile());
								addImage(view.getUpSlider(), file.toFile());
							}
							return FileVisitResult.CONTINUE;
						}
					});
				else if (view.getUpSlider().getParent().getComponentCount() < 14)
					addImage(view.getUpSlider(), path);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// internalPanel.revalidate();
		// internalPanel.repaint();
	}

	private void addImage(JPanel internalPanel, File file) throws IOException {

		String filename = System.getProperty("user.home") + File.separator + ".else" + File.separator
				+ MD5Checksum.getMD5Checksum(file.toString()) + ".jpg";

		File imageFile = new File(filename);

		view.setStatusText("Trying to read: " + file.toPath());

		BufferedImage image = null;

		if (imageFile.exists())
			image = ImageIO.read(imageFile);
		else
			image = saveImage(file.toPath());

		Image img = image.getScaledInstance(-1, 180, Image.SCALE_DEFAULT);
		JButton picLabel = new JButton(new ImageIcon(img));

		String checksum = "";
		try {
			checksum = MD5Checksum.getMD5Checksum(file.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BookMetadata book = model.getLibrary().getDatabase().get(checksum);

		if (book == null) {
			System.out.println("The book is null");
			book = new BookMetadata();
			book.setChecksum(checksum);
		} else {
			System.out.println("The book is not null");
			System.out.println(book);
		}

		view.setStatusText("COMIIIIING");

		picLabel.addActionListener(new ListenerBookClick(view, image, book));
		// picLabel.addActionListener(new
		// ActionListenerWhenClickingOnABook(file));

		picLabel.setBorder(null);

		internalPanel.add(picLabel);

		picLabel.revalidate();
		picLabel.repaint();

	}

	private BufferedImage saveImage(Path file) throws IOException {
		view.setStatusText("Creating image of " + file.toFile());

		String s = System.getProperty("user.home") + File.separator + ".else" + File.separator
				+ MD5Checksum.getMD5Checksum(file.toString()) + ".jpg";

		final File outputfile = new File(s);

		if (outputfile.exists())
			return null;

		PDDocument doc = PDDocument.load(file.toFile());
		PDFRenderer renderer = new PDFRenderer(doc);

		final BufferedImage image = renderer.renderImage(0);

		doc.close();

		// Need an asyncronus way
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					ImageIO.write(image, "jpg", outputfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r).start();

		return image;

	}

}