package hub.babos.pkuweb.message.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.message.dto.MessagesResponse;
import hub.babos.pkuweb.message.dto.NewMessageRequest;
import hub.babos.pkuweb.message.service.MessageService;
import hub.babos.pkuweb.support.token.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/me/receive")
    public ResponseEntity<MessagesResponse> findMyMessages(@Login AuthInfo authInfo) {
        MessagesResponse messagesResponse = messageService.findMyMessages(authInfo);
        return ResponseEntity.ok(messagesResponse);
    }

    @PostMapping("/send")
    public ResponseEntity<Long> send(@RequestBody NewMessageRequest newMessageRequest, @Login AuthInfo authInfo) {
        Long messageId = messageService.send(newMessageRequest, authInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageId);
    }
}
