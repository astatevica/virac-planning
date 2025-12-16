// src/components/DepartmentList.js
import React, { useState, useEffect } from 'react';
import { departmentService, productService } from '../services/api';
import { Link } from 'react-router-dom';
const DepartmentList = () => {
  const [department, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await departmentService.getAll();
        setDepartment(response.data);
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch products');
        setLoading(false);
        console.error(err);
      }
    };
    fetchDepartments();
  }, []);
  if (loading) return <div>Loading...</div>;
  if (error) return <div className="alert alert-danger">{error}</div>;
  return (
    <div className="container mt-4">
      <h2>Departments</h2>
      <Link to="/departments/new" className="btn btn-primary mb-3">
        Add New Department
      </Link>
      <div className="row">
        {products.length === 0 ? (
          <p>No department found</p>
        ) : (
          products.map(product => (
            <div className="col-md-4 mb-3" key={product.id}>
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">{product.name}</h5>
                  <p className="card-text">{product.description}</p>
                  <p className="card-text">
                    <strong>Price: ${product.price}</strong>
                  </p>
                  <Link to={`/products/${product.id}`} className="btn btn-info mr-2">
                    View Details
                  </Link>
                </div>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
export default DepartmentList;