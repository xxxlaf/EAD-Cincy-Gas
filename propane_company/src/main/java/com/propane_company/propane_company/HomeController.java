package com.propane_company.propane_company;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Transactional
public class HomeController {
    // https://www.geeksforgeeks.org/spring-boot-thymeleaf-with-example/#
    @GetMapping(path="")
    public String home() {
        return "home";
    }

    @PostMapping("/search_orders")
    public String lookupOrder(@RequestParam String phone, @RequestParam String address, Model model) {
        System.out.println(phone);
        System.out.println(address);

        List<Customer> customerList = customerRepository.findByPhoneNumberAndDeliveryAddress(phone, address);
        List<HashMap> tanks = new ArrayList<>();

        for (Customer customer : customerList) {
            System.out.println(customer.getId());

            List<Order> orders = orderRepository.findByCustomerId(customer.getId());

            for (Order order : orders) {
                Integer quantity = order.getQuantity();
                List<PropaneTank> propaneTanks = order.getPropaneTanks();

                for (PropaneTank propaneTank : propaneTanks) {
                    HashMap<String, String> tank = new HashMap<>();
                    tank.put("id", Integer.toString(propaneTank.getPropaneTankId()));
                    tank.put("size", Integer.toString(propaneTank.getTankSize()));
                    tank.put("quantity", Integer.toString(quantity));
                    tank.put("status", propaneTank.getDeliveryStatus());
                    tank.put("order_id", Integer.toString(order.getOrderId()));
                    tank.put("order_date", propaneTank.getDeliveryDate().toString());

                    tanks.add(tank);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("home");

        System.out.println("TANK AMOUNT");
        System.out.println(tanks.size());

        model.addAttribute("hashMapList", tanks);

        return "orderLookup";
    }

    @PostMapping("/create_order")
    public ModelAndView createOrder(@RequestParam String CustomerName, @RequestParam String CompanyName,
                                    @RequestParam String DeliveryAddress, @RequestParam String PhoneNumber,
                                    @RequestParam Integer TankSize, @RequestParam Integer Quantity, @RequestParam String Email,
                                    @RequestParam String DeliveryDate)
    {
        System.out.println(CustomerName);
        System.out.println(CompanyName);
        System.out.println(DeliveryAddress);
        System.out.println(PhoneNumber);
        System.out.println(TankSize);
        System.out.println(Quantity);
        System.out.println(Email);
        System.out.println(DeliveryDate);

        Customer customer = new Customer();

        customer.setEmail(Email);
        customer.setCompanyName(CompanyName);
        customer.setPhoneNumber(PhoneNumber);
        customer.setFullName(CustomerName);
        customer.setDeliveryAddress(DeliveryAddress);

        Customer saved_customer = customerRepository.save(customer);

        Order order = new Order();

        order.setCustomerId(saved_customer.getId());
        order.setQuantity(Quantity);

        Order result_order = orderRepository.save(order);

        List<PropaneTank> propaneTanks = new ArrayList<>();

        for (int i=0; i < Quantity; i++) {
            PropaneTank propaneTank = new PropaneTank();

            // Parse the string into a LocalDate object
            LocalDate localDate = LocalDate.parse(DeliveryDate, DateTimeFormatter.ISO_DATE);
            Date sqlDate = Date.valueOf(localDate);

            propaneTank.setTankSize(TankSize);
            propaneTank.setDeliveryStatus("pending");
            propaneTank.setOrder(result_order);
            propaneTank.setDeliveryDate(sqlDate);

            propaneTanks.add(propaneTank);
        }

        order.setPropaneTanks(propaneTanks);

        orderRepository.save(result_order);

        // https://www.baeldung.com/spring-mvc-model-model-map-model-view
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message", "Order Created");

        return modelAndView;
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/order_lookup")
    public String orderLookup(){


        return "orderLookup";
    }


}
