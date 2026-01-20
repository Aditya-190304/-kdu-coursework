package com.railway.repo;

import com.railway.entity.Paymenthistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Paymentrepo extends JpaRepository<Paymenthistory, String> {
}