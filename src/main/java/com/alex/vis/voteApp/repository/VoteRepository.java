package com.alex.vis.voteApp.repository;

import com.alex.vis.voteApp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurantId = ?1")
    int getVoteCount(int id);

    @Query("SELECT v FROM Vote v WHERE v.userId = ?1 AND v.dateTime >= ?2")
    Vote getVoteByUserId(int userId, LocalDateTime today);

}
