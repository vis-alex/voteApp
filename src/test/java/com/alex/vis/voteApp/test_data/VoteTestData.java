package com.alex.vis.voteApp.test_data;

import com.alex.vis.voteApp.MatcherFactory;
import com.alex.vis.voteApp.model.Vote;

import java.time.LocalDateTime;

import static com.alex.vis.voteApp.test_data.UserTestData.*;
import static com.alex.vis.voteApp.test_data.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_IGNORE_DATE_TIME_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "dateTime");
    public static final MatcherFactory.Matcher<Vote> VOTE_EQUALS_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int FIRST_VOTE_ID= 100010;
    public static final int SECOND_VOTE_ID = 100011;

    public static final LocalDateTime TEST_TIME = LocalDateTime.of(9999, 12, 31, 0, 0, 0);

    public static final Vote firstVote = new Vote(FIRST_VOTE_ID, USER_ID, FIRST_RESTAURANT_ID);
    public static final Vote secondVote = new Vote(SECOND_VOTE_ID, ADMIN_ID, SECOND_RESTAURANT_ID);

    static {
        firstVote.setDateTime(TEST_TIME);
        secondVote.setDateTime(TEST_TIME);
    }

}
