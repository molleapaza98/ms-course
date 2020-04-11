package com.erickmp98.shoppingservice.repository;

import com.erickmp98.shoppingservice.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {
}
