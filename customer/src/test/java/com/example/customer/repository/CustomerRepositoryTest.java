package com.example.customer.repository;

import com.example.customer.model.Customer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
package com.example.aggregate.respository;

import com.example.aggregate.domain.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

  @Autowired
  CustomerRepository customerRepository;

  @Test
  public void testAddGet() {
    // Get unique names every time this test runs
    String firstName = Long.toString(System.currentTimeMillis());
    String lastName = Long.toString(System.currentTimeMillis());

    Customer customer1 = new Customer();
    customer1.setFirstName(firstName);
    customer1.setLastName(lastName);
    customerRepository.add(customer1);

    List<Customer> customer = customerRepository.get();

    Customer customer2 = findInList(customer, firstName, lastName);
    Assert.assertNotNull(customer2);

    Customer customer3 = customerRepository.getById(customer2.getId());
    Assert.assertNotNull(customer3);
    Assert.assertEquals(firstName, customer3.getFirstName());
    Assert.assertEquals(lastName, customer3.getLastName());
  }

  @Test
  public void testUpdate() {
    Customer customer1 = createTestCustomer();
    customerRepository.add(customer1);

    List<Customer> customer = customerRepository.get();

    Customer customer2 = findInList(customer, customer1.getFirstName(), customer1.getLastName());
    Assert.assertNotNull(customer2);

    String updateFirstName = Long.toString(System.currentTimeMillis());
    String updateLastName = Long.toString(System.currentTimeMillis());

    customer2.setFirstName(updateFirstName);
    customer2.setLastName(updateLastName);
    customerRepository.update(customer2);

    customer = customerRepository.get();

    Customer customer3 = findInList(customer, updateFirstName, updateLastName);
    Assert.assertNotNull(customer3);
    Assert.assertEquals(customer2.getId(), customer3.getId());
  }

  @Test
  public void testDelete() {
    Customer customer1 = createTestCustomer();
    customerRepository.add(customer1);

    List<Customer> customer = customerRepository.get();

    Customer customer2 = findInList(customer, customer1.getFirstName(), customer1.getLastName());
    Assert.assertNotNull(customer2);

    customerRepository.delete(customer2.getId());

    customer = customerRepository.get();
    Customer customer3 = findInList(customer, customer1.getFirstName(), customer1.getLastName());
    Assert.assertNull(customer3);
  }

  private Customer createTestCustomer() {
    // Get unique names every time this test runs
    String firstName = Long.toString(System.currentTimeMillis());
    String lastName = Long.toString(System.currentTimeMillis());

    Customer customer = new Customer();
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    return customer;
  }


  private Customer findInList(List<Customer> customer, String first, String last) {
    // Find the new customer in the list
    for (Customer customer : customer) {
      if (customer.getFirstName().equals(first) && customer.getLastName().equals(last)) {
        return customer;
      }
    }
    return null;
  }
}
