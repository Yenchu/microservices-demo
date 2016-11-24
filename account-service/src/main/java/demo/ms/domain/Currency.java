package demo.ms.domain;

public enum Currency {

	NT, RMB, USD, EUR;

	public static Currency getDefault() {
		return NT;
	}
}
