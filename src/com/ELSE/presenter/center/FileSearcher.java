package com.ELSE.presenter.center;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;

import com.ELSE.model.Pathbase;
import com.ELSE.view.View;

public class FileSearcher extends Thread {
	private static final int perPage = 14;
	private Object lock = new Object();
	private Pathbase pathbase;
	private CenterPresenter centerPresenter;
	private int found, page;
	private int needToSkip;
	private View view;

	public FileSearcher(View view, CenterPresenter centerPresenter, Pathbase pathbase, int page) {
		this.pathbase = pathbase;
		this.centerPresenter = centerPresenter;
		found = 0;
		this.page = page;
		this.view = view;
	}

	public void findNext() {
		synchronized (lock) {
			page++;
			found = 0;
			lock.notifyAll(); // only notify() ?
		}
	}

	public int getFound() {
		return found;
	}

	public int getPage() {
		return page;
	}

	@Override
	public void run() {
		needToSkip = page * perPage;
		for (String s : pathbase.getPathsList()) {
			Path path = Paths.get(s);
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						if (file.toString().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
							if (needToSkip > 0) {
								needToSkip--;
								System.out.println(needToSkip + " Skipping book: " + file);
								return FileVisitResult.CONTINUE;
							}
							if (found >= perPage) {
								// Here there are another books but i still
								// don't add it
								view.enableNextButton(true);
								try {
									synchronized (lock) {
										while (found >= perPage)
											lock.wait();
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							System.out.println("Aggiungendo al centro: " + file);
							centerPresenter.addImage(file.toFile());
							found++; // ++found?
						}
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		view.enableNextButton(false);
	}

	public void setFound(int found) {
		this.found = found;
	}
}