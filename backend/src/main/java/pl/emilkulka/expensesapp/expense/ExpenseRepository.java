package pl.emilkulka.expensesapp.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    @Query("SELECT e FROM Expense e JOIN FETCH e.user u WHERE u.userName = :userName")
    List<Expense> findExpensesByUserName(@Param("userName") String userName);
}
