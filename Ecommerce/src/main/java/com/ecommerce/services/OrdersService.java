package com.ecommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.entities.Customer;
import com.ecommerce.entities.CustomerAddress;
import com.ecommerce.entities.OrderItems;
import com.ecommerce.entities.Orders;
import com.ecommerce.entities.User;
import com.ecommerce.model.AddressModel;
import com.ecommerce.model.AllOrderResponse;
import com.ecommerce.repo.AddressRepo;
import com.ecommerce.repo.CartItemRepo;
import com.ecommerce.repo.CartRepo;
import com.ecommerce.repo.CustomerRepo;
import com.ecommerce.repo.OrderItemRepo;
import com.ecommerce.repo.OrderRepo;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.utils.ApiResponse;

@Service
public class OrdersService {

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartItemRepo cartItemRepo;
	
	@Autowired
	private OrderRepo orderrepo;
	
	@Autowired
	private AddressRepo addRepo;
	
	@Autowired
	private OrderItemRepo oiRepo;
	
	public ApiResponse placeOrder(User user, AddressModel model) {
		try
		{
			if(user!=null)
			{
				Optional<Customer> ob = customerRepo.findByUser(user);
				if(ob.isPresent())
				{
					Customer customer = ob.get();
					Optional<Cart> obj = cartRepo.findByCustomer(customer);
					if(obj.isPresent())
					{
						Cart cart = obj.get();
						CustomerAddress add = new CustomerAddress(customer, model.getHouseNumber(), model.getArea(), model.getCity(), model.getState(), model.getType());
						add = addRepo.save(add);
						LocalDate date = LocalDate.now();
						Orders order = new Orders(customer,date,add,0.00,"Pending") ;
						order = orderrepo.save(order);
						
						Double total = 0.00;
						
						Optional<List<CartItem>> ci = cartItemRepo.findByCart(cart);
						if(ci.isPresent())
						{
							for(CartItem item : ci.get()) 
							{
								OrderItems oi = new OrderItems(order, item.getProduct(), item.getQuantity());
								oi = oiRepo.save(oi);
								cartItemRepo.delete(item);
								 
								total += oi.getProduct().getPrice() * oi.getQuantity();
							}
							order.setTotalAmount(total);
							order.setStatus("Successfull");
							orderrepo.save(order);
							return new ApiResponse(true, "Order placed successfully", order);
						}
						else
						{
							return new ApiResponse(false, "Cart is empty");
						}
					}
					else
					{
						return new ApiResponse(false,"Cart not found");
					}
				}
				else
				{
					return new ApiResponse(false,"Customer not found");
				}
			}
			else
			{
				return new ApiResponse(false,"User not found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred : ",e.getMessage());
		}
	}

	public ApiResponse getAllOrders() {
		try
		{
			List<AllOrderResponse> responseObj = new ArrayList<>();
			List<Orders> obj = orderrepo.findAll();
			for(Orders o : obj) {
				List<OrderItems> itemList = oiRepo.findByOrder(o);
				AllOrderResponse res = new AllOrderResponse(o.getCustomer(), o, itemList);
				responseObj.add(res);
			}
			if(responseObj.isEmpty()) {
				return new ApiResponse(false, "No Orders");
			}
			else {
				return new ApiResponse(true, "All Orders", responseObj);
			}
			
		}catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred : ",e.getMessage());
		}
	}

	public ApiResponse orderByCustomerId(Integer cId) {
		try {
			Optional<Customer> obj = customerRepo.findById(cId);
			if(obj.isPresent()) {
				Customer customer = obj.get();
				Optional<List<Orders>> ob = orderrepo.findByCustomer(customer);
				if(ob.isPresent()) {
					return new ApiResponse(true, "Customer Orders", ob.get());
				}
				else {
					return new ApiResponse(false, "Order not present");
				}
			}
			else
			{
				return new ApiResponse(false, "Customer not found");
			}
		}catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred : ",e.getMessage());
		}
	}

}
