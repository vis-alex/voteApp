package com.alex.vis.voteApp.validation;

import com.alex.vis.voteApp.exception.IllegalRequestDataException;
import com.alex.vis.voteApp.exception.NotFoundException;
import com.alex.vis.voteApp.model.HasId;
import com.alex.vis.voteApp.model.Role;
import org.springframework.security.core.context.SecurityContextHolder;

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
}
