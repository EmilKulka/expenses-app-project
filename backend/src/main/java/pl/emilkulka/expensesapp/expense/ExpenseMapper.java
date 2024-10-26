package pl.emilkulka.expensesapp.expense;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ExpenseMapper {
    ExpenseMapper INSTANCE  = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "type", target = "type")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "important", target = "important")
    Expense toExpense(ExpenseDto expenseDto);
}
