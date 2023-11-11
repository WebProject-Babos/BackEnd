package hub.babos.pkuweb.message.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.message.dto.MessagesResponse;
import hub.babos.pkuweb.message.dto.NewMessageRequest;

public interface MessageService {

    MessagesResponse findMyMessages(AuthInfo authInfo);
    Long send(NewMessageRequest newMessageRequest, AuthInfo authInfo);
}
