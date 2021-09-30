package com.sujin.stargram.Service;

import com.sujin.stargram.domain.Subscribe;
import com.sujin.stargram.dto.SubscribeDto;
import com.sujin.stargram.handler.ex.CustomApiException;
import com.sujin.stargram.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.qlrm.mapper.JpaResultMapper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // repository는 entityManager를 구현해서 만들어져 있는 구현체


    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(long principalId, long pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부하면 안됨

        //1.물음표 principalId
        //2.물음표 principalId
        //3.마지막 물음표 pageUserId

        //쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1,principalId)
                .setParameter(2,principalId)
                .setParameter(3,pageUserId);

        //쿼리 실행 (qlrm 라이브러리 필요 = Dto에 DB결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);

        return subscribeDtos;
    }

    @Transactional
    public void subscribe(long fromUserId, long toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void unSubscribe(long fromUserId, long toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }


}
