package hub.babos.pkuweb.message.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.exception.MemberNotFoundException;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.message.dto.MessagesElementResponse;
import hub.babos.pkuweb.message.dto.MessagesResponse;
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
    public MessagesResponse findMyMessages(AuthInfo authInfo) {
        Member member = findMember(authInfo);

        List<MessagesElementResponse> messages = messageRepository.findMessagesByReceiver(member)
                .stream().map(MessagesElementResponse::from).toList();

        return new MessagesResponse(messages);
    }

    private Member findMember(AuthInfo authInfo) {
        return memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);
    }
}