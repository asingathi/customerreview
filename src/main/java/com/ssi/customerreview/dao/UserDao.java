package com.ssi.customerreview.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssi.customerreview.model.UserModel;

public interface UserDao extends JpaRepository<UserModel, Long> {
}
