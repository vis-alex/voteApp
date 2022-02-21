package com.alex.vis.voteApp.validation;

import com.alex.vis.voteApp.exception.IllegalRequestDataException;
import com.alex.vis.voteApp.model.HasId;
import com.alex.vis.voteApp.model.Role;
import com.alex.vis.voteApp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ValidationUtil {
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkRole(UserDetails user) {
        if (!user.getAuthorities().contains(Role.ADMIN)) {
            throw new IllegalRequestDataException("Forbidden operation. You must have Role_ADMIN");
        }
    }

    public static void checkNull(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("User is null");
        }
    }
}
