package pl.emilkulka.expensesapp.expense;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-26T18:59:18+0200",
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
