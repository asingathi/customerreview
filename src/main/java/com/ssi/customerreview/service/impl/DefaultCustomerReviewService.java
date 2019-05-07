package com.ssi.customerreview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssi.customerreview.dao.CustomerReviewDao;
import com.ssi.customerreview.model.CustomerReviewModel;
import com.ssi.customerreview.model.LanguageModel;
import com.ssi.customerreview.model.ProductModel;
import com.ssi.customerreview.model.UserModel;
import com.ssi.customerreview.service.CustomerReviewService;
import com.ssi.customerreview.util.ServicesUtil;


@Component
public class DefaultCustomerReviewService implements CustomerReviewService
{
	private CustomerReviewDao customerReviewDao;

	@Autowired
	public DefaultCustomerReviewService(CustomerReviewDao customerReviewDao)
	{
		this.customerReviewDao = customerReviewDao;
	}

	@Override
	public CustomerReviewModel createCustomerReview(final Double rating, final String headline, final String comment,
			final ProductModel product, final UserModel user)
	{
		final CustomerReviewModel review = new CustomerReviewModel();
		review.setRating(rating);
		review.setHeadline(headline);
		review.setComment(comment);
		review.setProduct(product);
		review.setUser(user);
		customerReviewDao.save(review);
		return review;
	}

	@Override
	public void updateCustomerReview(final CustomerReviewModel review, UserModel user, ProductModel product)
	{
		customerReviewDao.save(review);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProduct(final ProductModel product)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		return customerReviewDao.getAllReviews(product);
	}

	@Override
	public Double getAverageRating(final ProductModel product)
	{
		return customerReviewDao.getAverageRating(product);
	}

	@Override
	public Integer getNumberOfReviews(final ProductModel product)
	{
		return customerReviewDao.getNumberOfReviews(product);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProductAndLanguage(final ProductModel product, final LanguageModel language)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		ServicesUtil.validateParameterNotNullStandardMessage("language", language);
		return customerReviewDao.getReviewsForProductAndLanguage(product, language);
	}

	@Override
	public void deleteCustomerReview(final Long id)
	{
		customerReviewDao.deleteReview(id);
	}
}
