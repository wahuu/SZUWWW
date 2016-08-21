package pl.chrusciel.mariusz.helper;

public enum Status {
	ZGLOSZONE("Zgłoszone"), PRZYPISANE("Przypisane"), WREALIZACJI("W realizacji"), ZAMKNIETE("Zamknięte");

	private final String text;

	private Status(final String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}

}
