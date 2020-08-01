package com.example.demo.repositories;


import com.example.demo.entities.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, String> {

}
