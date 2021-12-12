package com.joaogsrocha._SpringREST.model.user;

import com.joaogsrocha._SpringREST.model.voucheruser.VoucherUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user", schema = "avarum_users")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String screenName;
    @Column(name = "discordid")
    private String discordID;
    @Column(name = "gender")
    private byte gender;
    @Column(name = "password")
    private String pw;
    @Column(name = "email")
    private String email;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private VoucherUser voucherUser;

    @Column(name = "enabled", columnDefinition = "int2")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean enabled;

    @Column(name = "account_locked", columnDefinition = "int2")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean accountNonLocked;

    @Column(name = "account_expired", columnDefinition = "int2")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean accountNonExpired;

    @Column(name = "credentials_expired", columnDefinition = "int2")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean credentialsNonExpired;
}
