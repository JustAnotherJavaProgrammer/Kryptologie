package caesar;

public class SanitizeResult {
	public final boolean changed;
	public final String result;
	
	public SanitizeResult(boolean changed, String result) {
		this.changed = changed;
		this.result = result;
	}
}
