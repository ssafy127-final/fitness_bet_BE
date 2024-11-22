package com.fitnessbet.product.model.service;

import java.util.List;

import com.fitnessbet.product.model.dto.Product;
import com.fitnessbet.user.model.dto.PointHistory;

public interface ProductService {

	List<Product> getAllProduct();

	boolean registProduct(Product product);

	boolean modifyProduct(Product product);

	boolean exchangeProduct(PointHistory info);

}
