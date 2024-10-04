package com.viraj.eco_project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viraj.eco_project.model.Product;

@Repository
public interface Productrepository extends JpaRepository<Product,Integer> {

	@Query("SELECT p from Product p WHERE " + 
			"LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//			"LOWER(p.`desc`) LIKE LOWER(CONCAT('%', :keyword, '%')) OR" +
			"LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(p.Category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Product> searchProducts(@Param("keyword") String keyword);
}
