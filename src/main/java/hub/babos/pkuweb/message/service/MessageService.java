package hub.babos.pkuweb.message.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.message.dto.MessagesResponse;

public interface MessageService {

    MessagesResponse findMyMessages(AuthInfo authInfo);
}
