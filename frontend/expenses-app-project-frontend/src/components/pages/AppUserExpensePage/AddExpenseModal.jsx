import React from "react";
import { Modal, Button, Form, Alert } from "react-bootstrap";

const expenseTypes = [
    { value: "GROCERIESANDCHEMICALS", label: "Groceries and Chemicals" },
    { value: "SHOESANDCLOTHES", label: "Shoes and Clothes" },
    { value: "ACCESORIES", label: "Accessories" },
    { value: "REGULARPAYMENTS", label: "Regular Payments" },
];

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
                <Form onSubmit={handleAddExpense}>
                    <Form.Group className="mb-3">
                        <Form.Label>Type</Form.Label>
                        <Form.Select name="type" required>
                            <option value="">Select Expense Type</option>
                            {expenseTypes.map((expenseType) => (
                                <option key={expenseType.value} value={expenseType.value}>
                                    {expenseType.label}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control as="textarea" name="description" rows={3} />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Price</Form.Label>
                        <Form.Control type="number" step="0.01" name="price" required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Date</Form.Label>
                        <Form.Control type="date" name="date" required />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Check type="checkbox" label="Important" name="important" />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Add Expense
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
}

export default AddExpenseModal;
