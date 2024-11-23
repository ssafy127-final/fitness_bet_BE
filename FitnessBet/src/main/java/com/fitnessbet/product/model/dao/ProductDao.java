package com.fitnessbet.product.model.dao;

import java.util.List;

import com.fitnessbet.product.model.dto.Product;
import com.fitnessbet.user.model.dto.PointHistory;

public interface ProductDao {

	List<Product> selectAllProduct();

	int insertProduct(Product product);

	Product selectOneProductById(int id);

	boolean updateProduct(Product origin);

	List<PointHistory> selectAllExchangeList();


}
