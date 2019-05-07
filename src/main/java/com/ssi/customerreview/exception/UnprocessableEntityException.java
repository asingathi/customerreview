package com.ssi.customerreview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Weichen Zhou
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException
{
	public UnprocessableEntityException(final Long productId, final String message)
	{
		super( message +" for Product "+productId );
	}
}
