package name.richardson.james.bukkit.timedrestore.region;

public class MissingComponentException extends Exception {

	private final String message;

	private static final long serialVersionUID = 5879319844421134103L;

	public MissingComponentException(final String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
