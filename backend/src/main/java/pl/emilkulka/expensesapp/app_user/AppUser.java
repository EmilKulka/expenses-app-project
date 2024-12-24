package pl.emilkulka.expensesapp.app_user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.emilkulka.expensesapp.expense.Expense;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(exclude = "expenseList")
@ToString(exclude = "expenseList")
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email"),
            @UniqueConstraint(columnNames = "userName")})
public class AppUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private AppUserRole userRole;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Expense> expenseList;

    public AppUser(String userName, String password, String email, AppUserRole userRole) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

}

