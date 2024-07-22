package com.handson.write_my_groceries_list.repo;

import com.handson.write_my_groceries_list.model.Receipt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ReceiptRepository extends CrudRepository<Receipt, UUID> {

    List<Receipt> findAllByUserId(Long userId);

}
