package com.fitnessbet.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.product.model.dto.Product;
import com.fitnessbet.product.model.service.ProductService;
import com.fitnessbet.user.model.dto.PointHistory;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {
	
	private final ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllProduct(){
		List<Product> list =  service.getAllProduct();
		if(list.size()>0 && list != null) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
			
		}
		return new ResponseEntity<String>("상품이 존재하지 않습니다.", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("")
	public ResponseEntity<String> registProduct(@RequestBody Product product){
		if(service.registProduct(product)) {
			return new ResponseEntity<String>("등록 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("")
	public ResponseEntity<?> modifyProduct(@RequestBody Product product){
		if(service.modifyProduct(product)) {
			return new ResponseEntity<String>("수정 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/change")
	public ResponseEntity<?> exchangeProduct(@RequestBody PointHistory info){
		if(service.exchangeProduct(info)) {
			return new ResponseEntity<String>("교환 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("교환 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
