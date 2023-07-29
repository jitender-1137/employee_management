package com.universal.em.exception;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.universal.em.dto.response.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto<?>> handle(ServiceException ex) {
        String errorCode = ex.getErrorCode();
        String errorMessage = null;
        ErrorResponseDto<?> errorResponseDto = null;
        HttpStatus httpStatus = ex.getHttpStatus();
        errorMessage = messageSource.getMessage(errorCode, null, null);
        if (StringUtils.hasLength(errorMessage)) {
            errorResponseDto = new ErrorResponseDto<>(errorCode, ex.getData(), errorMessage);
        } else {
            errorMessage = errorCode;
            errorResponseDto = new ErrorResponseDto<>(errorMessage);
        }
        log.error("Service Exception | errorCode : {} | errorMessage : {} | httpStatus : {}", errorCode, errorMessage,
                httpStatus);
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<ErrorResponseDto> handleAuthenticationException(AuthenticationException ex,
//			HttpServletResponse response) {
//		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
////		errorResponseDto.setMessage(ex.getMessage());
//		response.setStatus(HttpStatus.UNAUTHORIZED.value());
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
//	}

//	@ExceptionHandler( AuthenticationException.class )
//	public ResponseEntity<ErrorResponseDto> handleSecurityAccessDeniedException(AuthenticationException exception,
//			WebRequest request) {
//		log.error("Access Denied for request: {}", request, exception);
//		String errorCode = "UM_SC_024";
//		ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode,
//				messageSource.getMessage(errorCode, null, null));
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
//	}

//	@ExceptionHandler({ AccessDeniedException.class })
//	public ResponseEntity<ErrorResponseDto> handleSecurityAccessDeniedException(AccessDeniedException exception,
//			WebRequest request) {
//		log.error("Access Denied for request: {}", request, exception);
//		String errorCode = "UM_SC_024";
//		ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode,
//				messageSource.getMessage(errorCode, null, null));
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
//	}

//	@ExceptionHandler(Throwable.class) // Exception does not cover all scenarios
//	public ResponseEntity<ErrorResponseDto> handle(Exception ex) {
//		log.error("Something went wrong. Exception : ", ex);
//		String errorCode = "GE_01";
//		String errorMessage = ApplicationConstants.GENERIC_EXCEPTION_STR;
//		ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode, errorMessage);
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
//	}

//	@ExceptionHandler(ServletException.class) // Exception does not cover all scenarios
//	public ResponseEntity<ErrorResponseDto> handle(ServletException ex) {
//		ServiceException serviceException = new ServiceException(ex.getMessage());
//		return handle(serviceException);
//	}

//	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		log.error("Request Validation failed");
//		List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
//				.map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
//				.collect(Collectors.toList());
//		log.error("Validation error list : " + validationList);
//		String errorCode = "VE_101";
//		List<String> errorMessage = CollectionUtils.isEmpty(validationList)
//				? Collections.singletonList("Error in validating fields")
//				: validationList;
//		ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode, errorMessage);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
//	}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Request Validation failed");
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        log.error("Validation error list : " + validationList);
        String errorCode = "VE_101";
        List<String> errorMessage = CollectionUtils.isEmpty(validationList)
                ? Collections.singletonList("Error in validating fields")
                : validationList;
        ErrorResponseDto<?> errorResponseDto = new ErrorResponseDto<>(errorCode, errorMessage);
        log.error("Service Exception | errorCode : {} | errorMessage : {} ", errorCode, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

//	@Override
//	public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		log.error("Required headers missing : ", ex);
//		String errorCode = "HE_101";
//		String errorMessage = "Required Header not valid";
//		ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorCode, errorMessage);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
//
//	}

}