package com.ssi.customerreview.service;

import java.util.List;

import com.ssi.customerreview.model.CustomerReviewModel;
import com.ssi.customerreview.model.LanguageModel;
import com.ssi.customerreview.model.ProductModel;
import com.ssi.customerreview.model.UserModel;


public interface CustomerReviewService
{
	CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, ProductModel product, UserModel user);

	void updateCustomerReview(CustomerReviewModel customer, UserModel user, ProductModel product);

	List<CustomerReviewModel> getReviewsForProduct(ProductModel product);

	Double getAverageRating(ProductModel product);

	Integer getNumberOfReviews(ProductModel product);

	List<CustomerReviewModel> getReviewsForProductAndLanguage(ProductModel product, LanguageModel language);

	void deleteCustomerReview(Long id);
}
