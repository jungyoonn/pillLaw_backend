package com.eeerrorcode.pilllaw.repository.follow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eeerrorcode.pilllaw.entity.follow.Follow;

@Repository
public interface FollowRepository extends JpaRepository <Follow, Long> {

    // @Query("SELECT f FROM Follow f WHERE f.followerId = :followerId AND f.followingId = :followingId")
    // Follow findFollow(@Param("followerId") long followerId, @Param("followingId") long followingId);


    // 내가 팔로우한 사람 목록 보기
      // List<Follow> findByFollowIdAndSenderMno(Long followId, Long senderMno);

      // // 맞팔로우 목록을 가져오는 메소드
      // List<Follow> findByIsFollowBack(Boolean isFollowBack);
      
      // 나를 팔로우한 사람 목록 보기 (팔로워 목록)
      List<Follow> findByReceiver_Mno(long receiverMno);
      // 내가 팔로우한 사람 목록 보기 (팔로잉 목록)
      List<Follow> findBySender_Mno(long senderMno);

      

      // 맞팔 목록 조회 (isFollowBack이 true인 경우)
      List<Follow> findByReceiver_MnoAndIsFollowBackTrue(long receiverMno);


      boolean existsBySender_MnoAndReceiver_Mno(long senderMno, long receiverMno);
      //맞팔여부
      Optional<Follow> findBySender_MnoAndReceiver_Mno(long senderMno, long receiverMno);
      // Optional<Follow> findBySender_IdAndReceiver_Id(Long senderId, Long receiverId);

      // @Query("SELECT f FROM Follow f JOIN Member m ON f.receiverFollowId = m.mno WHERE m.nickname = :nickname")
      // List<Follow> findByReceiverNickname(@Param("nickname") String nickname);

  }