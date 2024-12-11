import React, { useState, useEffect } from "react";
import { Modal, Button, Alert } from "react-bootstrap";
import EditExpense from "./EditExpense";

const ExpenseDetailsModal = ({ show, expense, onDelete, close, onEdit }) => {
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editedExpense, setEditedExpense] = useState(expense || {});

    useEffect(() => {
        setEditedExpense(expense);
    }, [expense]);

    const handleEditChange = (e) => {
        const { name, value, type, checked } = e.target;
        setEditedExpense((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSaveEdit = () => {
        if (expense && onEdit) {
            onEdit(expense.id, editedExpense);
            setIsEditing(false);
        }
    };

    const handleDeleteClick = () => {
        setShowConfirmation(true);
    };

    const confirmDelete = (expenseId) => {
        onDelete(expenseId); 
        close(); 
    };

    const cancelDelete = () => {
        setShowConfirmation(false); 
    };

    if (!expense) return null;

    return (
        <Modal show={show} onHide={close}>
            <Modal.Header closeButton>
                <Modal.Title>Expense Details</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {showConfirmation ? (
                    <Alert variant="danger">
                        <p>Are you sure you want to delete this expense?</p>
                        <div className="d-flex justify-content-between">
                            <Button
                                variant="danger"
                                onClick={() => confirmDelete(expense.id)}
                            >
                                Yes
                            </Button>
                            <Button variant="secondary" onClick={cancelDelete}>
                                No
                            </Button>
                        </div>
                    </Alert>
                ) : isEditing ? (
                    <EditExpense editedExpense={editedExpense} handleEditChange={handleEditChange}></EditExpense>
                ) : (
                    <>
                        <p><strong>Type:</strong> {expense.type}</p>
                        <p><strong>Description:</strong> {expense.description}</p>
                        <p><strong>Price:</strong> ${expense.price}</p>
                        <p><strong>Date:</strong> {expense.date}</p>
                        <p><strong>Important:</strong> {expense.important ? "Yes" : "No"}</p>
                    </>
                )
                }
            </Modal.Body>
            <Modal.Footer>
            {!showConfirmation && (
                    <>
                        {isEditing ? (
                            <>
                                <Button variant="success" onClick={handleSaveEdit}>
                                    Save
                                </Button>
                                <Button variant="secondary" onClick={() => setIsEditing(false)}>
                                    Cancel
                                </Button>
                            </>
                        ) : (
                            <>
                                <Button variant="primary" onClick={() => setIsEditing(true)}>
                                    Edit
                                </Button>
                                <Button variant="danger" onClick={handleDeleteClick}>
                                    Delete
                                </Button>
                            </>
                        )}
                    </>
                )}
            </Modal.Footer>
        </Modal>
    );
};

export default ExpenseDetailsModal;
