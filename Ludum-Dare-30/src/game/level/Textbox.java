package game.level;

import java.util.ArrayList;
import java.util.List;

public class Textbox {

	private List<String[]> content;

	private int pages;
	private int currentPage;

	public Textbox(String... lines) {
		content = new ArrayList<String[]>();
		currentPage = 0;

		for (String string : lines) {
			content.add(string.split("\n"));
		}
		pages = content.size();
	}

	public boolean hasNextPage() {
		if (currentPage < pages) {
			return true;
		} else {
			return false;
		}
	}

	public void nextPage() {
		currentPage++;
	}

	public String[] getPage() {
		String[] str;
		if (hasNextPage()) {
			str = content.get(currentPage);
		} else {
			str = new String[] { "" };
		}
		return str;
	}
}