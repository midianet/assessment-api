package meta.metarch.infra.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meta.metarch.infra.helper.I18NHelper;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe responsável por interceptar as Exceções da aplicação, tratar e retornar uma mensagem amigável no padrão.
 *
 * @see ErrorResponse
 * @see ResponseEntityExceptionHandler
 * @see I18NHelper
 */
@Log4j2
@Component
@RestControllerAdvice
@RequiredArgsConstructor
public class AdviceExceptionHandler extends ResponseEntityExceptionHandler {

    private final I18NHelper i18NHelper;

    //400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.trace(e.getMessage(), e);
        return handleExceptionInternal(e, buildErrorResponse(e,
                        i18NHelper.getMessage("smart.message.error.validation")),
                headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.trace(e.getMessage(), e);
        return handleExceptionInternal(e,
                buildErrorResponse(i18NHelper.getMessage("smart.message.error.conversion"),
                        buildErrorResponseDetail(((MethodArgumentConversionNotSupportedException) e).getName(),
                                String.valueOf(e.getValue()))),
                headers, status, request
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.trace(e.getMessage(), e);
        final var message = i18NHelper.getMessage("smart.message.error.messageunreadable");
        if (e.getCause() instanceof final InvalidFormatException cause) {
            return handleExceptionInternal(e,
                    buildErrorResponse(message, cause.getPath().stream()
                            .map(violation -> buildErrorResponseDetail(violation.getFieldName()))
                            .toList()), headers, status, request);
        } else {
            return handleExceptionInternal(e, buildErrorResponse(message), headers, status, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status,
                                                        WebRequest request) {
        log.trace(e.getMessage(), e);
        return handleExceptionInternal(e,
                buildErrorResponse(i18NHelper.getMessage("smart.message.error.typemismatch"),
                        buildErrorResponseDetail(
                                Optional.ofNullable(e.getPropertyName()).orElse(""),
                                String.format("%s : %s", e.getValue(), e.getRequiredType()))),
                headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException e,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {
        log.trace(e.getMessage(), e);
        return handleExceptionInternal(e,
                buildErrorResponse(
                        i18NHelper.getMessage("smart.message.error.missingrequestpart"),
                        buildErrorResponseDetail(e.getRequestPartName())
                ), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request) {
        log.trace(e.getMessage(), e);
        return handleExceptionInternal(e,
                buildErrorResponse(
                        i18NHelper.getMessage("smart.message.error.missingrequestparam"),
                        buildErrorResponseDetail(e.getParameterName())
                ), headers, status, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ErrorResponse handleMethodArgumentTypeMismatch(@NonNull final MethodArgumentTypeMismatchException e) {
        log.trace(e.getMessage(), e);
        return buildErrorResponse(
                i18NHelper.getMessage("smart.message.error.argumenttypemsimatch"),
                buildErrorResponseDetail(e.getName(),
                        Optional.ofNullable(e.getRequiredType())
                                .map(Class::getComponentType)
                                .map(Class::getName)
                                .orElse("")));
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse handleConstraintViolation(@NonNull final ConstraintViolationException e) {
        log.trace(e.getMessage(), e);
        return buildErrorResponse(i18NHelper.getMessage("smart.message.error.constraintviloation"),
                e.getConstraintViolations().stream()
                        .map(violation -> buildErrorResponseDetail(
                                String.valueOf(violation.getPropertyPath()),
                                violation.getMessage()))
                        .toList());
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {
        log.trace(e.getMessage(), e);
        return new ResponseEntity<>(buildErrorResponse(String.format("%s %s %s",
                i18NHelper.getMessage("smart.message.error.nohandlerfound"),
                e.getHttpMethod(), e.getRequestURL())), headers, status);
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         WebRequest request) {
        log.trace(e.getMessage(), e);
        return new ResponseEntity<>(buildErrorResponse(
                i18NHelper.getMessage("smart.message.error.methodnotsupported", e.getMethod(),
                        Optional.ofNullable(e.getSupportedHttpMethods())
                                .map(httpMethods -> httpMethods.stream()
                                        .map(HttpMethod::toString)
                                        .collect(Collectors.joining(","))).orElse(""))),
                headers, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {
        log.trace(e.getMessage(), e);
        return new ResponseEntity<>(buildErrorResponse(
                i18NHelper.getMessage("smart.message.error.mediatypenotsupported", e.getContentType(),
                        Optional.of(e.getSupportedMediaTypes())
                                .map(types -> types.stream()
                                        .map(MediaType::toString)
                                        .collect(Collectors.joining(","))).orElse(""))),
                new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    //400
    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class, RestClientException.class}) //ver
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePrecondition(final Exception e) {
        log.trace(e.getMessage(), e);
        return buildErrorResponse(e.getLocalizedMessage());
    }

    // 404
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final Exception e) {
        log.trace(e.getMessage(), e);
        return buildErrorResponse(e.getLocalizedMessage());
    }

//    @ExceptionHandler({HystrixRuntimeException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleCircuitBreaker(final Exception e) {
//        log.trace(e.getMessage(), e);
//        return buildErrorResponse("Serviço indisponível");
//    }

//    @ExceptionHandler({AccessDeniedException.class})
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorResponse handleAccessDeniedException(final AccessDeniedException e) {
//        log.warn(e.getMessage(), e);
//        return buildErrorResponse(e.getLocalizedMessage());
//    }

    // 500
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAll(final Exception e) {
        log.fatal(e.getMessage(), e);
        return buildErrorResponse(e.getLocalizedMessage());
    }

    private ErrorResponse buildErrorResponse(
            @NonNull final BindException e,
            @NonNull final String message
    ) {
        final var error = ErrorResponse.builder().message(message);
        e
                .getBindingResult()
                .getFieldErrors()
                .forEach(err ->
                        error.detail(
                                buildErrorResponseDetail(err.getField(), err.getDefaultMessage())
                        )
                );
        e
                .getBindingResult()
                .getGlobalErrors()
                .forEach(err ->
                        error.detail(
                                buildErrorResponseDetail(err.getObjectName(), err.getDefaultMessage())
                        )
                );
        return error.build();
    }

    private ErrorResponse buildErrorResponse(@NonNull final String message) {
        return ErrorResponse.builder().message(message).build();
    }

    private ErrorResponse buildErrorResponse(@NonNull final String message,
                                             @NonNull final ErrorResponse.Detail detail) {
        return ErrorResponse.builder().message(message).detail(detail).build();
    }

    private ErrorResponse buildErrorResponse(@NonNull final String message,
                                             @NonNull List<ErrorResponse.Detail> details) {
        return ErrorResponse.builder().message(message).details(details).build();
    }

    private ErrorResponse.Detail buildErrorResponseDetail(@NonNull final String name,
                                                          @Nullable final String message) {
        return ErrorResponse.Detail.builder().name(name).message(message).build();
    }

    private ErrorResponse.Detail buildErrorResponseDetail(@NonNull final String name) {
        return ErrorResponse.Detail.builder().name(name).build();
    }
}
