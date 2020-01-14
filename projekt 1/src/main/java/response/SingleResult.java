package response;

public class SingleResult<TContent> implements IResult<TContent> {
    private boolean success;
    private String message;
    private TContent content;

    public SingleResult() {
    }

    public SingleResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.content = null;
    }

    public SingleResult(boolean success, String message, TContent content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }

    public boolean success() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public TContent result() {
        return this.content;
    }
}
