package com.alex.vis.voteApp.to;

import com.sun.istack.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String password;
}
