package com.alex.vis.voteApp.service.vote;

import com.alex.vis.voteApp.model.Vote;
import com.alex.vis.voteApp.repository.RestaurantRepository;
import com.alex.vis.voteApp.repository.VoteRepository;
import com.alex.vis.voteApp.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @Override
    public void vote(int userId, int restaurantId) {
        ValidationUtil.checkNotFoundWithId(restaurantRepository.getById(restaurantId), restaurantId);
        ValidationUtil.checkVote(getUserVote(userId));
        voteRepository.save(new Vote(userId, restaurantId, LocalDateTime.now()));
    }
    //TODO check  user vote today

    @Transactional
    @Override
    public void devote(int userId, int restaurantId) {
        ValidationUtil.checkTime();
        ValidationUtil.checkNotFoundWithId(restaurantRepository.getById(restaurantId), restaurantId);
        Vote vote = getUserVote(userId);
        ValidationUtil.checkDevote(vote, userId);
        voteRepository.delete(vote);
    }

    @Override
    public List<Vote> getAll() {
        ValidationUtil.checkRoleAdmin();
        return voteRepository.findAll();
    }

    @Override
    public Vote get(int id) {
        ValidationUtil.checkRoleAdmin();
        return ValidationUtil.checkNotFoundWithId(voteRepository.getById(id), id);
    }

    @Override
    public Vote getUserVote(int userId) {
        LocalDate nowDate = LocalDate.now();
        LocalTime zeroTime = LocalTime.MIDNIGHT;
        LocalDateTime today = LocalDateTime.of(nowDate, zeroTime);
        return voteRepository.getVoteByUserId(userId, today);
    }

}
