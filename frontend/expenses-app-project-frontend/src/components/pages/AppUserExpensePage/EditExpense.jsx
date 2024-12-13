import {Form} from "react-bootstrap";

const expenseTypes = [
    { value: "GROCERIESANDCHEMICALS", label: "Groceries and Chemicals" },
    { value: "SHOESANDCLOTHES", label: "Shoes and Clothes" },
    { value: "ACCESORIES", label: "Accessories" },
    { value: "REGULARPAYMENTS", label: "Regular Payments" },
];


function EditExpense({editedExpense, handleEditChange}) {
    return (
        <>
            <Form.Group className="mb-3">
                <Form.Label>Type</Form.Label>
                <Form.Select
                    name="type"
                    onChange={handleEditChange}
                >
                    <option value="">
                        {editedExpense.type}
                    </option>
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
                    value={editedExpense.description} 
                    onChange={handleEditChange} 
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Price</Form.Label>
                <Form.Control 
                    type="number" 
                    step="0.01" 
                    name="price" 
                    value={editedExpense.price}
                    onChange={handleEditChange}
                    required 
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Date</Form.Label>
                <Form.Control 
                    type="date" 
                    name="date" 
                    value={editedExpense.date}
                    onChange={handleEditChange}
                    required 
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Check
                    type="checkbox" 
                    label="Important" 
                    name="important" 
                    checked={editedExpense.important}
                    onChange={handleEditChange}
                />
            </Form.Group>
        </>
    ) 
}

export default EditExpense;