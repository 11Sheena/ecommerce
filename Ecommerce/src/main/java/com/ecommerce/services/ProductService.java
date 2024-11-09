package com.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entities.Product;
import com.ecommerce.model.AddProductModel;
import com.ecommerce.repo.ProductRepo;
import com.ecommerce.utils.ApiResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public ApiResponse addProduct(AddProductModel model) {
		try
		{
			Product product = new Product(model.getProductName(), model.getCategory(), model.getDescription(), model.getPrice(), true);
			product = productRepo.save(product);
			return new ApiResponse(true, "Product added successfully", product);
		}
		catch (Exception e) {
			return new ApiResponse(false, "Exception Occured" , e.getMessage());
		}
	}

	public ApiResponse deleteProduct(Integer productId) {
		try
		{
			Optional<Product> ob = productRepo.findById(productId);
			if(ob.isPresent())
			{
				Product product = ob.get();
				product.setStatus(false);
				productRepo.save(product);
				return new ApiResponse(true, "Product deleted successfully");
			}
			else 
			{
				return new ApiResponse(false, "Product not found corresponding to the product id");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred: ",e.getMessage());
		}
	}

	public ApiResponse updateProduct(Integer productId, AddProductModel model) {
		try
		{
			Optional<Product> ob = productRepo.findById(productId);
			if(ob.isPresent())
			{
				Product product = ob.get();
				product.setProductName(model.getProductName());
				product.setCategory(model.getCategory());
				product.setDescription(model.getDescription());
				product.setPrice(model.getPrice());
				productRepo.save(product);
				
				return new ApiResponse(true, "Product updated successfully", product);
			}
			else 
			{
				return new ApiResponse(false, "Product not found corresponding to the product id");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred: ",e.getMessage());
		}
	}

	public ApiResponse allProducts() {
		try
		{
			Optional<List<Product>> oblist = productRepo.getByStatus(true);
			if(oblist.isPresent())
			{
				List<Product> products = oblist.get();
				return new ApiResponse(true,"All products list", products);
			}
			else 
			{
				return new ApiResponse(false, "No product found");
			}
		}
		catch (Exception e) {
			return new ApiResponse(false, "Exception ocurred: ",e.getMessage());
		}
	}
}
