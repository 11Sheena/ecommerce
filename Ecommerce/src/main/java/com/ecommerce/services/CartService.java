package com.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.CartItem;
import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.model.AddCartModel;
import com.ecommerce.model.UpdateCartModel;
import com.ecommerce.repo.CartItemRepo;
import com.ecommerce.repo.CartRepo;
import com.ecommerce.repo.CustomerRepo;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.utils.ApiResponse;
@Service
public class CartService {

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartItemRepo cartItemRepo;
	
	public ApiResponse addToCart(User user,List<AddCartModel> model) {
		try {
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
						for(AddCartModel acm : model)
						{
							Optional<Product> pr = productRepo.findById(acm.getProductId());
							if(pr.isPresent())
							{
								Product product = pr.get();
								Optional<CartItem> ci = cartItemRepo.findByProduct(product);
								if(ci.isPresent())
								{
									CartItem cartItem = ci.get();
									cartItem.setQuantity(acm.getQuantity());
									cartItemRepo.save(cartItem);
								}
								else
								{
									CartItem cartItem = new CartItem(cart,product,acm.getQuantity()); 
									cartItemRepo.save(cartItem);
								}
							}
							else
							{
								return new ApiResponse(false, "Product not found");
							}
						}
						return new ApiResponse(true, "Products added to cart", cartItemRepo.findByCart(cart).get());
						
					}
					else
					{
						Cart cart = new Cart(customer,true);
						cartRepo.save(cart);
						for(AddCartModel acm : model)
						{
							Optional<Product> pr = productRepo.findById(acm.getProductId());
							if(pr.isPresent())
							{
								Product product = pr.get();
								CartItem cartItem = new CartItem(cart,product,acm.getQuantity()); 
								cartItemRepo.save(cartItem);
							}
							else {
								return new ApiResponse(false, "Product not found");
							}
						}
						return new ApiResponse(true, "Products added to cart", cartItemRepo.findByCart(cart).get());
					}
				}
				else
				{
					return new ApiResponse(false, "Customer not found");
				}
			}
			else
			{
				return new ApiResponse(false, "User not found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred : ", e.getMessage());
		}
	}

	public ApiResponse deletefromCart(User user, Integer productId) {
		try {
			Optional<Product> proObj = productRepo.findById(productId);
			if(proObj.isPresent()) {
				Product product =  proObj.get();
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
							Optional<CartItem> cartObj = cartItemRepo.findByCartAndProduct(cart,product);
							if(cartObj.isPresent()) {
								cartItemRepo.delete(cartObj.get());
								
								return new ApiResponse(true, "Product Deleted from Cart", cartItemRepo.findByCart(cart).get());
							}
							else {
								return new ApiResponse(false,"Product not available in the cart");
							}
						}
						else
						{
							return new ApiResponse(false,"Cart not available for the customer");
						}
					}
					else
					{
						return new ApiResponse(false,"Customer not Found");
					}
				}
				else
				{
					return new ApiResponse(false,"User not found");
				}
			}
			else {
				return new ApiResponse(false,"Product not found");
			}
			
		}catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred : ", e.getMessage());
		}
	}

	public ApiResponse getCart(User user) {
		try {
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
						Optional<List<CartItem>> list = cartItemRepo.findByCart(cart);
						if(list.isPresent())
						{
							return new ApiResponse(true, "Cart Details of the customer", list.get());
						}
						else
						{
							return new ApiResponse(false, "Cart is empty"); 
						}
					}
					else
					{
						return new ApiResponse(false, "Cart is not present");
					}
				}
				else
				{
					return new ApiResponse(false, "Customer not found");
				}
			}
			else
			{
				return new ApiResponse(false, "User not found");
			}
		}catch (Exception e) {
			return new ApiResponse(false, "Exception ocurres : ",e.getMessage());
		}	
	}

	public ApiResponse updateCart(User user, UpdateCartModel model) {
		try {
			Optional<Product> proObj = productRepo.findById(model.getProductId());
			if(proObj.isPresent()) {
				Product product =  proObj.get();
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
							Optional<CartItem> itemobj = cartItemRepo.findByCartAndProduct(cart, product);
							if(itemobj.isPresent()){
								CartItem cartItem = itemobj.get();
								cartItem.setQuantity(model.getQuantity());
								cartItemRepo.save(cartItem);
								return new ApiResponse(true, "Updated Cart Details", cartItemRepo.findByCart(cart).get());
							}
							else {
								return new ApiResponse(false, "Product not found in Cart");
							}
						}
						else {
							return new ApiResponse(false, "Cart not found");
						}
					}
					else {
						return new ApiResponse(false, "Customer not found");
					}
				}
				else {
					return new ApiResponse(false, "User not found");
				}
			}
			else {
				return new ApiResponse(false, "Product not found");
			}
		}catch (Exception e) {
			return new ApiResponse(false, "Exception ocurres : ",e.getMessage());
		}	
	}

}
