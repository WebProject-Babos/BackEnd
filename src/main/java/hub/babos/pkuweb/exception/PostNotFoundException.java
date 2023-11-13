package hub.babos.pkuweb.exception;

public class PostNotFoundException extends NotFoundException {

    private static final String MESSAGE = "게시물을 찾을 수 없습니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }
}
