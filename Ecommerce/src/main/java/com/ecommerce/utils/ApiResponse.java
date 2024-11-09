package com.ecommerce.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse 
{
	private Boolean status;
	private String message;
	private Object data;
	private String error;
	
	public ApiResponse(Boolean status, String message) {	
		this.status = status;
		this.message = message;
	}
	
	public ApiResponse(Boolean status, String message,Object data) {	
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public ApiResponse(Boolean status, String message,String error) {	
		this.status = status;
		this.message = message;
		this.error = error;
	}
}
