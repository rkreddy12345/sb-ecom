package com.rkecom.ui.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rkecom.db.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Include
    private Long userId;

    @NotBlank(message = "user name is required")
    @Size(min = 8, max = 15, message = "user name should have min 8 and max 15 characters")
    private String userName;
    @JsonIgnore
    private String password;
    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;
    private List < Role > roles;

}
