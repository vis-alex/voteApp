package com.alex.vis.voteApp.to;

import com.alex.vis.voteApp.model.HasId;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTo implements Serializable, HasId {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;
}
