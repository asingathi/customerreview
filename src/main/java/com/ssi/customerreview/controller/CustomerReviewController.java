package com.ssi.customerreview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssi.customerreview.dao.ProductDao;
import com.ssi.customerreview.dao.UserDao;
import com.ssi.customerreview.exception.ProductNotFoundException;
import com.ssi.customerreview.exception.UnprocessableEntityException;
import com.ssi.customerreview.exception.UserNotFoundException;
import com.ssi.customerreview.forms.CustomerReviewForm;
import com.ssi.customerreview.model.CustomerReviewModel;
import com.ssi.customerreview.model.ProductModel;
import com.ssi.customerreview.model.UserModel;
import com.ssi.customerreview.service.CustomerReviewService;
import com.ssi.customerreview.util.CurseWords;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CustomerReviewController
{
	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CustomerReviewService customerReviewService;

	@GetMapping({ "products/{productId:\\d+}/reviews" })
	public List<CustomerReviewModel> getReviews(@PathVariable final Long productId,
			@RequestParam(required = false) final Double ratingFrom, @RequestParam(required = false) final Double ratingTo)
	{
		
		
		
		final ProductModel product = productDao.findOne(productId);
		if (product == null)
		{
			throw new ProductNotFoundException(productId);
		}
		
		List<CustomerReviewModel> list = customerReviewService.getReviewsForProduct(product);
		if( ratingFrom != null && ratingTo != null ) {
			double tempfrom = ratingFrom;
			double tempto = ratingTo;
			if( ratingFrom > ratingTo ) {
				tempfrom = ratingTo;
				tempto = ratingFrom;
			}
			final double rangeFrom = tempfrom;
			final double rangeTo = tempto;
			list = list.stream().filter( model -> model.getRating() >= rangeFrom && model.getRating() <= rangeTo ).collect( Collectors.toList() );
		}
		
		return list;
	}

	@PostMapping({ "products/{productId:\\d+}/users/{userId:\\d+}/reviews" })
	public CustomerReviewModel createReview(@PathVariable final Long userId, @PathVariable final Long productId,
			@RequestBody final CustomerReviewForm customerReviewForm )
	{
		final ProductModel product = productDao.findOne(productId);
		if (product == null)
		{
			throw new ProductNotFoundException(productId);
		}

		final UserModel user = userDao.findOne(userId);
		if (user == null)
		{
			throw new UserNotFoundException(userId);
		}
        

	        if( customerReviewForm.getRating() < 0 ) {
	            throw new UnprocessableEntityException(productId, "Negative Rating not allowed :: "+ customerReviewForm.getRating());
	        }
	        
	        if( CurseWords.getCurseWords().containsKey(customerReviewForm.getHeadline().toLowerCase()) ) {
	            throw new UnprocessableEntityException(productId, "Customer's Headline contain curse words :: "+ customerReviewForm.getHeadline());
	        }
	        if( CurseWords.getCurseWords().containsKey(customerReviewForm.getComment().toLowerCase()) )  {
	            throw new UnprocessableEntityException(productId, "Customer's Comments contain curse words :: "+ customerReviewForm.getComment());
	        }

                
		return customerReviewService
				.createCustomerReview(customerReviewForm.getRating(), customerReviewForm.getHeadline(),
						customerReviewForm.getComment(), product, user);
	}
	
	@PostMapping({ "products" })
	public ProductModel createProduct()
	{
		ProductModel product = new ProductModel();
		product.setName( "dress" );
		productDao.save(product);
		product = new ProductModel();
		product.setName( "shoes" );
		productDao.save(product);
		product = new ProductModel();
		product.setName( "dress" );
		productDao.save(product);
		
		List<ProductModel> list = productDao.findAll();
		for( ProductModel aa : list ) {
			System.err.println( aa.getId() +" "+ aa.getName() );
		}
		
		return product;
	}

	@PostMapping({ "users" })
	public UserModel createUser()
	{
		UserModel user = new UserModel();
		user.setName( "ajay" );
		userDao.save(user);
		user = new UserModel();
		user.setName( "raj" );
		userDao.save(user);
		
		List<UserModel> list = userDao.findAll();
		for( UserModel aa : list ) {
			System.err.println( aa.getId() +" "+ aa.getName() );
		}
		
		return user;
	}

	@DeleteMapping({ "reviews/{reviewId:\\d+}" })
	public void deleteReview(@PathVariable final Long reviewId)
	{
		customerReviewService.deleteCustomerReview(reviewId);
	}
}
