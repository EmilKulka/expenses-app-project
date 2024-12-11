function EditExpense({editedExpense, handleEditChange}) {
    return (
        <>
            <div className="mb-3">
                            <label>Type</label>
                            <input
                                className="form-control"
                                name="type"
                                value={editedExpense.type || ""}
                                onChange={handleEditChange}
                            />
                        </div>
                        <div className="mb-3">
                            <label>Description</label>
                            <input
                                className="form-control"
                                name="description"
                                value={editedExpense.description || ""}
                                onChange={handleEditChange}
                            />
                        </div>
                        <div className="mb-3">
                            <label>Price</label>
                            <input
                                className="form-control"
                                name="price"
                                type="number"
                                value={editedExpense.price || ""}
                                onChange={handleEditChange}
                            />
                        </div>
                        <div className="mb-3">
                            <label>Date</label>
                            <input
                                className="form-control"
                                name="date"
                                type="date"
                                value={editedExpense.date || ""}
                                onChange={handleEditChange}
                            />
                        </div>
                        <div className="mb-3 form-check">
                            <input
                                className="form-check-input"
                                name="important"
                                type="checkbox"
                                checked={editedExpense.important || false}
                                onChange={handleEditChange}
                            />
                            <label className="form-check-label">Important</label>
                        </div>
        </>
    ) 
}

export default EditExpense;