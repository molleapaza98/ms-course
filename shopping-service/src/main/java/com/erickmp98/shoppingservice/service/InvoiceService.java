package com.erickmp98.shoppingservice.service;

import com.erickmp98.shoppingservice.entity.Invoice;

import java.util.List;

public interface InvoiceService {

     List<Invoice> findInvoiceAll();

     Invoice createInvoice(Invoice invoice);
     Invoice updateInvoice(Invoice invoice);
     Invoice deleteInvoice(Invoice invoice);

     Invoice getInvoice(Long id);

}
