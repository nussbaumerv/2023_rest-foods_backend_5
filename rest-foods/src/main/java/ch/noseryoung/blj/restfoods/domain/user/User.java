package ch.noseryoung.blj.restfoods.domain.user;

import ch.noseryoung.blj.restfoods.domain.role.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "useremail")
    private String useremail;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_role", referencedColumnName = "role_id")
    private Role role;
}
