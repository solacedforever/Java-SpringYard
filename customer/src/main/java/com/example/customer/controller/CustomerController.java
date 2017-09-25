package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class CustomerController {
  @Autowired
  private CustomerService customerService;

  @RequestMapping("/customers")
  String getCustomers(Model model) {
    List<Customer> customers = customerService.get();
    model.addAttribute("listOfCustomers", customers);
    return "view_customers";
  }

  @GetMapping("/customer")
  String getCustomer(Model model) {
    return "new_customer";
  }

  @PostMapping("/customer")
  String addCustomer(Customer customer, Model model) {
    customerService.add(customer);
    List<Customer> customers = customerService.get();
    model.addAttribute("listOfCustomers", customers);
    return "view_customers";
  }

  @ExceptionHandler(value = Exception.class)
  public String handleDefaultErrors(final Exception exception, Model model) {
    System.out.println(exception);
    model.addAttribute("message", exception.getMessage());
    return "error_message";
  }

  @GetMapping("/login")
  String login() {
    return "login";
  }
}