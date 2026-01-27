package lv.venta.virac.token;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.virac.user.User;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idToken")
	@Setter(value = AccessLevel.NONE)
    private int id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiryDate", nullable = false)
    private Instant expiryDate;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }
    
    //TODO: getters & setters

}
