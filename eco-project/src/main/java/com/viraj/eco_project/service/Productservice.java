package com.viraj.eco_project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.viraj.eco_project.Repository.Productrepository;
import com.viraj.eco_project.model.Product;

@Service
public class Productservice {
	
	@Autowired
	private Productrepository repo;
	
	public List<Product> getallproducts(){
		return repo.findAll();
	}

	public Product getproductbyid(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(new Product());
	}

	public Product addproduct(Product product, MultipartFile imagefile) throws IOException {
		product.setImagename(imagefile.getOriginalFilename());
		product.setImagetype(imagefile.getContentType());
		product.setImagedate(imagefile.getBytes());
		return repo.save(product);
	}

	
	public Product updateproduct(int id, Product product, MultipartFile imagefile) throws IOException {
		product.setImagedate(imagefile.getBytes());
		product.setImagename(imagefile.getOriginalFilename());
		product.setImagetype(imagefile.getContentType());
		return repo.save(product);
	}

	public void deleteproduct(int id) {
		repo.deleteById(id);
	}

	public List<Product> searchproduct(String keyword) {
		return repo.searchProducts(keyword);
	}

	
}
