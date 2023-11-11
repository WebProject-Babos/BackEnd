package hub.babos.pkuweb.message.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.exception.MemberNotFoundException;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.message.domain.Message;
import hub.babos.pkuweb.message.dto.MessagesElementResponse;
import hub.babos.pkuweb.message.dto.MessagesResponse;
import hub.babos.pkuweb.message.dto.NewMessageRequest;
import hub.babos.pkuweb.message.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;


    public MessageServiceImpl(MessageRepository messageRepository, MemberRepository memberRepository) {
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public MessagesResponse findMyReceivedMessages(AuthInfo authInfo) {
        Member member = findMember(authInfo);

        List<MessagesElementResponse> messages = messageRepository.findMessagesByReceiver(member)
                .stream().map(MessagesElementResponse::from).toList();

        return new MessagesResponse(messages);
    }

    @Override
    public MessagesResponse findMySentMessages(AuthInfo authInfo) {
        Member member = findMember(authInfo);

        List<MessagesElementResponse> messages = messageRepository.findMessagesBySender(member)
                .stream().map(MessagesElementResponse::from).toList();

        return new MessagesResponse(messages);
    }

    @Override
    public Long send(NewMessageRequest newMessageRequest, AuthInfo authInfo) {
        Member receiver = findMember(newMessageRequest.getReceiverId());
        Member sender = findMember(authInfo);

        Message message = createMessage(newMessageRequest, receiver, sender);
        Message savedMessage = messageRepository.save(message);

        return savedMessage.getId();
    }

    private Member findMember(AuthInfo authInfo) {
        return findMember(authInfo.getId());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private Message createMessage(NewMessageRequest newMessageRequest, Member receiver, Member sender) {
        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(newMessageRequest.getContent())
                .build();
    }
}
