import { ListGroup, Badge } from "react-bootstrap";

function ExpenseItem({expense, showDetails}) {
    return (
        <ListGroup.Item
                    as="li"
                    className="d-flex justify-content-between align-items-start"
                    style={{ cursor: "pointer" }}
                    onClick={() => showDetails(expense)}
                >
                    <div className="ms-2 me-auto">
                        <div className="fw-bold d-flex align-items-center">
                            {expense.type}
                        </div>
                    </div>
                    <div className="d-flex align-items-center">
                        <div
                            style={{
                                minWidth: "80px",
                                textAlign: "right",
                                marginRight: "15px",
                            }}
                        >
                            ${expense.price}
                        </div>
                        <Badge bg="primary" pill className="me-2">
                            {expense.date}
                        </Badge>
                    </div>
                </ListGroup.Item>
    )
}
export default ExpenseItem;
