package com.alex.vis.voteApp.validation;

import com.alex.vis.voteApp.exception.IllegalRequestDataException;
import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.exception.VoteException;
import com.alex.vis.voteApp.model.HasId;
import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.Vote;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalTime;

public class ValidationUtil {
    public static LocalTime endChangingVote = LocalTime.of(11, 0);
    public static LocalTime updateMenuTime = LocalTime.of(1, 0);

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkRoleAdmin() {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ADMIN)) {
            throw new IllegalRequestDataException("Forbidden operation. You must have Role_ADMIN");
        }
    }

    public static <T> T checkNotFoundWithId(T obj, int id) {
        if (obj == null) {
            throw new NotFoundException("Not found entity wit id=" + id);
        }
        return obj;
    }

    public static void checkTime() {
        if (LocalTime.now().compareTo(endChangingVote) > 0) {
            throw new VoteException("You can change your vote before " + endChangingVote);
        }
    }

    public static void checkVote(Vote vote) {
        if (vote != null) {
            throw new VoteException("You have voted already. You cant vote twice a day.");
        }
    }

    public static void checkDevote(Vote vote, int userId) {
        if (vote == null) {
            throw new VoteException("Today no vote for user=" + userId);
        }
    }
}
