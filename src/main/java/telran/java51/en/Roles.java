package telran.java51.en;

public enum Roles {

	ADMINISTRATOR, MODERATOR, USER;

	@Override
	public String toString() {
		switch (this) {
		case ADMINISTRATOR: {
			return "ADMINISTRATOR";
		}
		case MODERATOR: {
			return "MODERATOR";
		}
		case USER: {
			return "USER";
		}
		default:
			throw new IllegalArgumentException("Unexpected role: " + this);
		}
	}
}
