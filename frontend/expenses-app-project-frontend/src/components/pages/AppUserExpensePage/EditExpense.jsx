import React from 'react';
import { Form } from "react-bootstrap";

const expenseTypes = [
    { value: "GROCERIESANDCHEMICALS", label: "Groceries and Chemicals" },
    { value: "SHOESANDCLOTHES", label: "Shoes and Clothes" },
    { value: "ACCESORIES", label: "Accessories" },
    { value: "REGULARPAYMENTS", label: "Regular Payments" },
];

function EditExpense({ editedExpense = {}, handleEditChange }) {   
    return (
        <>
            <Form.Group className="mb-3">
                <Form.Label>Type</Form.Label>
                <Form.Select
                    name="type"
                    value={editedExpense?.type || ''}
                    onChange={handleEditChange}
                >
                    {expenseTypes.map((expenseType) => (
                        <option key={expenseType.value} value={expenseType.value}>
                            {expenseType.label}
                        </option>
                    ))}
                </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Description</Form.Label>
                <Form.Control 
                    as="textarea" 
                    name="description" 
                    rows={3} 
                    value={editedExpense?.description || ''} 
                    onChange={handleEditChange} 
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Price</Form.Label>
                <Form.Control 
                    type="number" 
                    step="0.01" 
                    name="price" 
                    value={editedExpense?.price || ''} 
                    onChange={handleEditChange}
                    required 
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Date</Form.Label>
                <Form.Control 
                    type="date" 
                    name="date" 
                    max={new Date().toISOString().split('T')[0]}
                    value={editedExpense?.date || ''} 
                    onChange={handleEditChange}
                    required 
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Check
                    type="checkbox" 
                    label="Important" 
                    name="important" 
                    checked={editedExpense?.important || false}
                    onChange={handleEditChange}
                />
            </Form.Group>
        </>
    );
}

export default EditExpense;