package pl.emilkulka.expensesapp.expense;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T15:44:04+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
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
