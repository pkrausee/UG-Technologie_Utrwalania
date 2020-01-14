package response;

import java.util.List;

public class ManyResults<TContent> implements IResult<List<TContent>> {
    private boolean success;
    private String message;
    private List<TContent> content;

    public ManyResults() {
    }

    public ManyResults(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.content = null;
    }

    public ManyResults(boolean success, String message, List<TContent> content) {
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

    public List<TContent> result() {
        return this.content;
    }
}

