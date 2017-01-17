package com.ELSE.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pathbase {
	private static class PathNode {
		private Map<String, PathNode> children = new HashMap<>();
		private final String name;

		private PathNode(String name) {
			this.name = name;
		}

		private void add(List<String> path, int i) {
			String childName = path.get(i);
			PathNode child = children.get(childName);
			if (child != null) {
				if (path.size() - i <= 1)
					child.children.clear();
				else
					child.add(path, i + 1);
			} else if (!isLeaf() || isRoot()) {
				PathNode node = this;
				for (; i < path.size(); i++) {
					String key = path.get(i);
					node.children.put(key, new PathNode(key));
					node = node.children.get(key);
				}
			}
		}

		public void clear() {
			for (PathNode child : children.values()) {
				if (!child.isLeaf()) {
					child.clear();
					child.children.clear();
				}
			}
		}

		public boolean isLeaf() {
			return children.size() == 0;
		}

		public boolean isRoot() {
			return name.isEmpty();
		}

		private void listPaths(ArrayList<String> list, String prefix) {
			for (PathNode child : children.values()) {
				if (child.isLeaf())
					list.add(prefix + child.name);
				else
					child.listPaths(list, prefix + child.name + File.separator);
			}
		}

		private boolean remove(List<String> path, int i) {
			PathNode child = children.get(path.get(i));
			if (!child.isLeaf() && i + 1 < path.size()) {
				if (child.children.get(path.get(i + 1)).isLeaf()) {
					child.children.remove(path.get(i + 1));
					System.out.println("true");
					return true;
				} else {
					return child.remove(path, i + 1);
				}
			}
			return false;
		}
	}

	private PathNode root = new PathNode("");

	public void add(String p) {
		root.add(Arrays.asList(p.split("\\\\|/")), 0);
	}

	public void clear() {
		root.clear();
	}

	public List<String> getPathsList() {
		ArrayList<String> list = new ArrayList<>();
		root.listPaths(list, "");
		return list;
	}

	public void remove(String p) {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(p.split("\\\\|/")));
		while (root.remove(list, 0))
			list.remove(list.size() - 1);
	}
}