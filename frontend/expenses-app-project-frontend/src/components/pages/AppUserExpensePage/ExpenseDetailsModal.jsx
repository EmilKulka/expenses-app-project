import React, { useState } from "react";
import { Modal, Button, Alert, ProgressBar } from "react-bootstrap";

const ExpenseDetailsModal = ({ show, handleClose, expense, onDelete }) => {
    const [showSuccessAlert, setShowSuccessAlert] = useState(false);
    const [progress, setProgress] = useState(0);
    const [deleteCancelled, setDeleteCancelled] = useState(false);
    const [intervalRef, setIntervalRef] = useState(null); // Store interval reference

    const handleDeleteWithAlert = (expenseId) => {
        
        setShowSuccessAlert(true);
        setDeleteCancelled(false); 
        setProgress(0); 

        
        const interval = setInterval(() => {
            setProgress((prevProgress) => {
                if (deleteCancelled) {
                    clearInterval(interval); 
                    setShowSuccessAlert(false); 
                    setProgress(0); 
                    return prevProgress; 
                }

                const newProgress = prevProgress + 1; 
                if (newProgress >= 120) {
                    clearInterval(interval); 
                    setShowSuccessAlert(false); 
                    handleClose(); 
                    onDelete(expenseId); 
                    return 100; 
                }
                return newProgress;
            });
        }, 50); 

        setIntervalRef(interval); 
    };

    const handleCancelDelete = () => {
        setDeleteCancelled(true); 
        if (intervalRef) clearInterval(intervalRef); 
        setShowSuccessAlert(false); 
        setProgress(0); 
    };

    if (!expense) return null; 

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Expense Details</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {showSuccessAlert ? (
                    <div>
                        <Alert variant="warning" className="d-flex justify-content-between align-items-center">
                            Expense deletion in progress...
                            <Button 
                                variant="outline-dark" 
                                size="sm" 
                                onClick={handleCancelDelete}
                            >
                                Cancel
                            </Button>
                        </Alert>
                        <ProgressBar now={progress} animated />
                    </div>
                ) : (
                    <>
                        <p><strong>Type:</strong> {expense.type}</p>
                        <p><strong>Description:</strong> {expense.description}</p>
                        <p><strong>Price:</strong> ${expense.price}</p>
                        <p><strong>Date:</strong> {expense.date}</p>
                        <p><strong>Important:</strong> {expense.important ? "Yes" : "No"}</p>
                    </>
                )}
            </Modal.Body>
            <Modal.Footer>
                {!showSuccessAlert && (
                    <>
                        <Button variant="primary" onClick={() => console.log("Edit clicked")}>
                            Edit
                        </Button>
                        <Button
                            variant="danger"
                            onClick={() => handleDeleteWithAlert(expense.id)}
                        >
                            Delete
                        </Button>
                    </>
                )}
            </Modal.Footer>
        </Modal>
    );
};

export default ExpenseDetailsModal;
