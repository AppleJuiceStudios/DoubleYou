package game.level;

import java.util.ArrayList;
import java.util.List;

public class Textbox {

	private List<String> content;

	private int pages;
	private int currentPage;

	public Textbox(String... lines) {
		content = new ArrayList<String>();
		currentPage = 0;

		for (String string : lines) {
			content.add(string);
		}
		pages = content.size();
	}

	public boolean hasNextPage() {
		if (currentPage <= pages) {
			return false;
		} else {
			return true;
		}
	}

	public String getNextPage() {
		currentPage++;
		return content.get(currentPage - 1);
	}
}