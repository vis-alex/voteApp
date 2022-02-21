package com.alex.vis.voteApp.validation;

import com.alex.vis.voteApp.exception.IllegalRequestDataException;
import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.model.HasId;
import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ValidationUtil {
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

    public static void checkRole() {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Role.ADMIN)) {
            throw new IllegalRequestDataException("Forbidden operation. You must have Role_ADMIN");
        }
    }

    public static void checkNull(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("User is null");
        }
    }

    public static <T> T checkNotFoundWithId(T obj, int id) {
        if (obj == null) {
            throw new NotFoundException("Not found entity wit id=" + id);
        }
        return obj;
    }
}
