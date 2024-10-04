package com.viraj.eco_project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viraj.eco_project.model.Product;
import com.viraj.eco_project.service.Productservice;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Productcontroller {
	
	@Autowired
	private Productservice service;
	
	@RequestMapping("/")
	public String greet() {
		return "Hello Viraj";
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getallproducts(){
		return new ResponseEntity<>(service.getallproducts(),HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public Product getproductbyid(@PathVariable int id) {
		return service.getproductbyid(id);
	}
	
	
	@PostMapping("/product")
	public ResponseEntity<?> addproduct(@RequestPart Product product,
											@RequestPart MultipartFile imagefile){
		try {
			Product product1 = service.addproduct(product,imagefile);
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping("/product/{productid}/image")
	public ResponseEntity<byte[]> getimagebyproductid(@PathVariable int productid){
		Product product = service.getproductbyid(productid);
		byte[] imagefile = product.getImagedate();
		
		return ResponseEntity.ok()
				.contentType(MediaType.valueOf(product.getImagetype()))
				.body(imagefile);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateproduct(@PathVariable int id,
													@RequestPart Product product,
													@RequestPart MultipartFile imagefile){
		Product product1 = null;
		
		try {
			product1 = service.updateproduct(id,product,imagefile);
		} catch (IOException e) {
			return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
		}
		
		if(product1!=null) {
			return new ResponseEntity<>("Updated",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteproduct(@PathVariable int id){
		Product product1 = service.getproductbyid(id);
		
		if(product1!=null) {
			service.deleteproduct(id);
			return new ResponseEntity<>("Deleted",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchproduct(@RequestParam String keyword){
		List<Product> products = service.searchproduct(keyword);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}

}
