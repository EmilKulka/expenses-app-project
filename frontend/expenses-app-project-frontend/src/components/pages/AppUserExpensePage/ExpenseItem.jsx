import React from 'react';
import { ListGroup } from "react-bootstrap";

function ExpenseItem({expense, showDetails}) {
    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', {
            day: '2-digit',
            month: 'short',
            year: '2-digit'
        });
    };

    const getIcon = (type) => {
        const icons = {
            'GROCERIESANDCHEMICALS': '👑',
            'SHOESANDCLOTHES': '👕',
            'ACCESORIES': '💍',
            'REGULARPAYMENTS': '🏢',
            'default': '🛒'
        };
        return icons[type] || icons.default;
    };

    const formatExpenseType = (type) => {
        return type
            .replace(/([A-Z])/g, ' $1')
            .split('AND')
            .join(' & ')
            .toLowerCase()
            .replace(/^\w/, c => c.toUpperCase());
    };

    return (
        <ListGroup.Item
            action
            className="bg-dark text-white border-0 rounded mb-2"
            style={{ backgroundColor: '#1c1c1e !important' }}
            onClick={() => showDetails(expense)}
        >
            <div className="d-flex align-items-center py-2">
                {/* Icon container */}
                <div className="rounded p-2 me-3" style={{ backgroundColor: '#2c2c2e' }}>
                    <span style={{ fontSize: '1.2rem' }}>
                        {getIcon(expense.type)}
                    </span>
                </div>

                {/* Content */}
                <div className="flex-grow-1">
                    <div className="d-flex justify-content-between align-items-center">
                        {/* Left side with expense type and amount */}
                        <div>
                            <p className="mb-0 text-secondary" style={{ fontSize: '0.9rem' }}>
                                {formatExpenseType(expense.type)}
                            </p>
                            <p className="mb-0 text-white fw-bold">
                                ${Number(expense.price).toFixed(2)}
                            </p>
                        </div>

                        {/* Right side with date */}
                        <div className="text-secondary" style={{ fontSize: '0.8rem' }}>
                            {formatDate(expense.date)}
                        </div>
                    </div>
                </div>
            </div>
        </ListGroup.Item>
    );
}

export default ExpenseItem;