package com.sujin.stargram.repository;

import com.sujin.stargram.domain.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;


public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {


    @Modifying // insert, delete, update를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(long fromUserId, long toUserId);

    @Modifying // insert, delete, update를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(long fromUserId, long toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int mSubscribeState(long principalId, long pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId ", nativeQuery = true)
    int mSubscribeCount(long pageUserId);


}
