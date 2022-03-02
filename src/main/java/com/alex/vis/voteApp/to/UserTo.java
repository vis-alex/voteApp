package com.alex.vis.voteApp.to;

import com.alex.vis.voteApp.model.HasId;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTo implements Serializable, HasId {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 5, max = 32)
    private String password;
}
