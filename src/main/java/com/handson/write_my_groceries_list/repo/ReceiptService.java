package com.handson.write_my_groceries_list.repo;

import com.handson.write_my_groceries_list.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;


    public Iterable<Receipt> all() {
        return receiptRepository.findAll();
    }

    public Optional<Receipt> findById(UUID id){
        return receiptRepository.findById(id);
    }

    public Iterable<Receipt> findReceiptsByUserId(Long userId){
        return receiptRepository.findAllByUserId(userId);
    }

}
