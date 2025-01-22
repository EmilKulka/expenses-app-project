package pl.emilkulka.expensesapp.expense;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-22T14:59:53+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class ExpenseMapperImpl implements ExpenseMapper {

    @Override
    public Expense toExpense(ExpenseDto expenseDto) {
        if ( expenseDto == null ) {
            return null;
        }

        Expense expense = new Expense();

        expense.setType( expenseDto.getType() );
        expense.setDescription( expenseDto.getDescription() );
        expense.setPrice( expenseDto.getPrice() );
        expense.setDate( expenseDto.getDate() );
        expense.setImportant( expenseDto.isImportant() );

        return expense;
    }
}
