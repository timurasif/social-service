package com.demo.social.exceptions;

import com.demo.social.Data.Response;
import com.demo.social.Utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException ex) {
        String errorMessage = ex.getMessage();
        Response response = new Response(Constants.HttpStatusCodes.NOT_FOUND, errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    public static class PostNotFoundException extends RuntimeException {
        public PostNotFoundException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(UpdateForbiddenException.class)
    public ResponseEntity<Object> handleUpdateForbiddenException(UpdateForbiddenException ex) {
        String errorMessage = ex.getMessage();
        Response response = new Response(Constants.HttpStatusCodes.FORBIDDEN, errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    public static class UpdateForbiddenException extends RuntimeException {
        public UpdateForbiddenException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(WrongUserIdException.class)
    public ResponseEntity<Object> handleWrongUserIdException(WrongUserIdException ex) {
        String errorMessage = ex.getMessage();
        Response response = new Response(Constants.HttpStatusCodes.NOT_FOUND, errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    public static class WrongUserIdException extends RuntimeException {
        public WrongUserIdException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFoundException(CommentNotFoundException ex) {
        String errorMessage = ex.getMessage();
        Response response = new Response(Constants.HttpStatusCodes.NOT_FOUND, errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    public static class CommentNotFoundException extends RuntimeException {
        public CommentNotFoundException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(ImageException.class)
    public ResponseEntity<Object> handleImageException(ImageException ex) {
        String errorMessage = ex.getMessage();
        Response response = new Response(Constants.HttpStatusCodes.FORBIDDEN, errorMessage, null);
        return ResponseEntity.badRequest().body(response);
    }

    public static class ImageException extends RuntimeException {
        public ImageException(String message) {
            super(message);
        }
    }
}
