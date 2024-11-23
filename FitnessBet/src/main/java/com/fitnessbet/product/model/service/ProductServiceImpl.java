package com.fitnessbet.product.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitnessbet.product.model.dao.ProductDao;
import com.fitnessbet.product.model.dto.DateFilter;
import com.fitnessbet.product.model.dto.Product;
import com.fitnessbet.user.model.dto.PointHistory;
import com.fitnessbet.user.model.service.UserService;

@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductDao dao;
	private final UserService userService;
	
	public ProductServiceImpl(ProductDao dao, UserService userService) {
		this.dao = dao;
		this.userService = userService;
	}

	@Override
	public List<Product> getAllProduct() {
		return dao.selectAllProduct();
	}

	@Override
	@Transactional
	public boolean registProduct(Product product) {
		return dao.insertProduct(product) > 0;
	}

	@Override
	@Transactional
	public boolean modifyProduct(Product product) {
		Product origin = dao.selectOneProductById(product.getId());
		origin.setImg(product.getImg());
		origin.setName(product.getName());
		origin.setPoint(product.getPoint());
		origin.setPrice(product.getPrice());
		origin.setDelYn(product.getDelYn());
		
		return dao.updateProduct(origin);
	}

	@Override
	@Transactional
	public boolean exchangeProduct(PointHistory info) {
		return userService.exchangeProduct(info);
	}

	@Override
	public List<PointHistory> getAllExchangList(DateFilter date) {
		return dao.selectAllExchangeList(date);
	}
	



}
