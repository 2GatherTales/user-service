package com.twogathertales.userservice.model.roleuser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="role_user", schema = "avarum_users")
@Getter
@Setter
public class RoleUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "role_id")
    private Long roleid;
    @Column(name = "user_id")
    private Long userid;
}
