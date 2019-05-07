package com.ssi.customerreview.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssi.customerreview.model.ProductModel;

public interface ProductDao extends JpaRepository<ProductModel, Long> {
}
