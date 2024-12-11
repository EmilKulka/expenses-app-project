import React from "react";
import { Modal, Alert } from "react-bootstrap";
import AddExpenseForm from "../../forms/AddExpenseForm";

function AddExpenseModal({ show, handleClose, handleAddExpense, validationErrors }) {
    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add New Expense</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {validationErrors.length > 0 && (
                    <Alert variant="danger">
                        <ul className="mb-0">
                            {validationErrors.map((error, index) => (
                                <li key={index}>{error}</li>
                            ))}
                        </ul>
                    </Alert>
                )}
                <AddExpenseForm handleAddExpense={handleAddExpense}></AddExpenseForm>
            </Modal.Body>
        </Modal>
    );
}

export default AddExpenseModal;
