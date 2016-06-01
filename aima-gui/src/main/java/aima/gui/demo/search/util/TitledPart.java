package aima.gui.demo.search.util;

public class TitledPart<T> {
	
	private String title;
	private T part;
	
	public TitledPart(String title, T part) {
		this.title = title;
		this.part = part;
	}
	
	public String getTitle() {
		return title;
	}
	
	public T getPart() {
		return part;
	}

}
