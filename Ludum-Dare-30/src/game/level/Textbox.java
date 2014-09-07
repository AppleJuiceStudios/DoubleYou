package game.level;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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

	public Textbox() {
		currentPage = 0;
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

	public List<String[]> getContent() {
		return content;
	}

	public void setContent(List<String[]> content) {
		this.content = content;
		this.pages = content.size();
	}

}