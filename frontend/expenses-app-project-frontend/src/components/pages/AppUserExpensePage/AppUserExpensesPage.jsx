import React, { useEffect, useState } from "react";
import Navbar from "../../common/Navbar";
import AddExpenseModal from "./AddExpenseModal";
import ExpenseDetailsModal from "./ExpenseDetailsModal";
import ExpenseList from "./ExpenseList";
import { getAppUserExpenses } from "../../../api/UserExpenses";
import { deleteExpense } from "../../../api/DeleteExpense";
import { addExpense } from "../../../api/AddExpense";
import { Button } from "react-bootstrap";

function AppUserExpensesPage() {
    const [userExpenses, setUserExpenses] = useState([]);
    const [showAddModal, setShowAddModal] = useState(false);
    const [showDetailsModal, setShowDetailsModal] = useState(false);
    const [selectedExpense, setSelectedExpense] = useState(null);
    const [validationErrors, setValidationErrors] = useState([]);

    useEffect(() => {
        const fetchExpenses = async () => {
            const result = await getAppUserExpenses();
            if (result.success) {
                setUserExpenses(result.data);
            }
        };
        fetchExpenses();
    }, []);

    const handleShowAddModal = () => setShowAddModal(true);
    const handleCloseAddModal = () => {
        setShowAddModal(false);
        setValidationErrors([]); 
    };

    const handleShowDetailsModal = (expense) => {
        setSelectedExpense(expense);
        setShowDetailsModal(true);
    };

    const handleCloseDetailsModal = () => {
        setSelectedExpense(null);
        setShowDetailsModal(false);
    };

    
    const handleDelete = async (expenseId) => {
        const result = await deleteExpense(expenseId);
        if (result.success) {
            setUserExpenses((prev) => prev.filter((expense) => expense.id !== expenseId));
        }
    };

    const handleAddExpense = async (e) => {
        e.preventDefault();

        const newExpense = {
            type: e.target.type.value,
            description: e.target.description.value,
            price:e.target.price.value,
            date: e.target.date.value,
            important: e.target.important.checked,
        };

        const result = await addExpense(newExpense);

        if (result.success) {
            setUserExpenses((prev) => [...prev, newExpense]);
            handleCloseAddModal();
        } else {
            setValidationErrors(result.errors); 
        }
    };

    return (
        <div>
            <Navbar />
            <div className="container mt-4">
                <h2>Expenses</h2>
                <Button variant="success" onClick={handleShowAddModal} className="mb-3">
                    Add New Expense
                </Button>
                <ExpenseList
                    expenses={userExpenses}
                    onShowDetails={handleShowDetailsModal}
                />
            </div>
            <AddExpenseModal
                show={showAddModal}
                handleClose={handleCloseAddModal}
                handleAddExpense={handleAddExpense}
                validationErrors={validationErrors} 
            />
            <ExpenseDetailsModal
                show={showDetailsModal}
                handleClose={handleCloseDetailsModal}
                onDelete = {handleDelete}
                expense={selectedExpense}
            />
        </div>
    );
}

export default AppUserExpensesPage;
