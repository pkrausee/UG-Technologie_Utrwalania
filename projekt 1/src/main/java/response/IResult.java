package response;

public interface IResult<TContent> {
    boolean success();

    String getMessage();

    TContent result();
}
