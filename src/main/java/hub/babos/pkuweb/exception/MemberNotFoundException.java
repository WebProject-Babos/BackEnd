package hub.babos.pkuweb.exception;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = "멤버가 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
