import React, { useEffect, useState } from "react";
import Navbar from "../../common/Navbar";
import AddExpenseModal from "./AddExpenseModal";
import ExpenseDetailsModal from "./ExpenseDetailsModal";
import ExpenseList from "./ExpenseList";
import { getAppUserExpenses } from "../../../api/UserExpenses";
import { deleteExpense } from "../../../api/DeleteExpense";
import { addExpense } from "../../../api/AddExpense";
import { editExpense } from "../../../api/EditExpense";

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
            handleCloseDetailsModal();
        }
    };

    const handleAddExpense = async (e) => {
        e.preventDefault();

        const newExpense = {
            type: e.target.type.value,
            description: e.target.description.value,
            price: e.target.price.value,
            date: e.target.date.value,
            important: e.target.important.checked,
        };

        const result = await addExpense(newExpense);

        if (result.success) {
            const updatedExpenses = await getAppUserExpenses();
            if (updatedExpenses.success) {
                setUserExpenses(updatedExpenses.data);
            }
            handleCloseAddModal();
        } else {
            setValidationErrors(result.errors);
        }
    };

    const handleEditExpense = async (expenseId, updatedExpense) => {
        const result = await editExpense(expenseId, updatedExpense);
        if (result.success) {
            const updatedExpenses = await getAppUserExpenses();
            if (updatedExpenses.success) {
                setUserExpenses(updatedExpenses.data);
            }
            handleCloseDetailsModal();
        }
    };

    return (
        <div>
            <Navbar />
            <div className="container mt-4">
                <h2>Recent expenses</h2>
                <ExpenseList
                    expenses={userExpenses}
                    onShowDetails={handleShowDetailsModal}
                />
                <div className="d-flex justify-content-center mt-4">
                    <button
                        className="btn btn-light rounded-circle shadow-lg "
                        style={{
                            width: "60px",
                            height: "60px",
                            fontSize: "24px",
                            fontWeight: "bold",
                        }}
                        onClick={() => setShowAddModal(true)}
                    >
                        +
                    </button>
                </div>
            </div>
            <AddExpenseModal
                show={showAddModal}
                handleClose={handleCloseAddModal}
                handleAddExpense={handleAddExpense}
                validationErrors={validationErrors}
            />
            <ExpenseDetailsModal
                show={showDetailsModal}
                onClose={handleCloseDetailsModal}
                onDelete={handleDelete}
                expense={selectedExpense}
                onEdit={handleEditExpense}
            />
        </div>
    );
}

export default AppUserExpensesPage;
