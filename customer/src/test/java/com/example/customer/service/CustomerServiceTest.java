package com.example.customer.service;

import com.example.customer.model.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

  @Autowired
  CustomerService customerService;

  @Test
  public void testTransactional() {
    Customer customer1 = createTestCustomer();
    Customer customer2 = createTestCustomer();
    List<Customer> customer = new ArrayList<>();
    customer.add(customer1);
    customer.add(customer2);

    // Cause an error
    customer2.setFirstName(null);
    try {
      customerService.add(customer);
      Assert.assertFalse("Expected an exception to be thrown");
    } catch(DataIntegrityViolationException e) {
      System.out.println("Received an exception as expected.");
    }

    customer = customerService.get();
    Customer customer1Retrieved =
            findInList(customer, customer1.getFirstName(), customer1.getLastName());
    Assert.assertNull("The first customer created should have rolled back",
            customer1Retrieved);
  }
}
