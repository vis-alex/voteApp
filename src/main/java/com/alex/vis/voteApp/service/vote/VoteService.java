package com.alex.vis.voteApp.service.vote;

import com.alex.vis.voteApp.model.Vote;

import java.util.List;

public interface VoteService {

    Vote vote(int userId, int restaurantId);

    void devote(int userId, int restaurantId);

    List<Vote> getAll();

    Vote get(int id);

    Vote getUserVote(int userId);
}
