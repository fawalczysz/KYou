package fr.isima.kyou.enums;

public enum Score {
	TRES_BON("Très bon"), BON("Bon"), MOYEN("Moyen"), MAUVAIS("Mauvais"), TRES_MAUVAIS("Très mauvais");

	String value;

	Score(String value) {

	}

	public String getValue() {
		return value;
	}
}
