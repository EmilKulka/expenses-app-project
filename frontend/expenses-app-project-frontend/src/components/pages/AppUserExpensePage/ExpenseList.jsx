import React from "react";
import { ListGroup } from "react-bootstrap";
import ExpenseItem from "./ExpenseItem";

function ExpenseList({ expenses, onShowDetails }) {
    return (
        <ListGroup>
            {expenses.map((expense) => (
                <ExpenseItem 
                    expense={expense} 
                    showDetails={onShowDetails} 
                    key={expense.id}
                />
            ))}
        </ListGroup>
    );
}

export default ExpenseList;