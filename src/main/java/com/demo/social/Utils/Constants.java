package com.demo.social.Utils;

public class Constants {
    public static class HttpStatusCodes {
        public static final int SUCCESS = 200;
        public static final int CREATED = 201;
        public static final int BAD_REQUEST = 400;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
    }

    public static final long MAX_IMAGE_SIZE_ALLOWED = 100 * 1024 * 1024;
    public static final String IMAGE_NOT_FOUND = "Image file not found!";
    public static final String WRONG_IMAGE_FORMAT = "Only images in these formats are allowed: .png, .jpg, .bmp.";
    public static final String WRONG_IMAGE_SIZE = "Image size should be lesser than " + MAX_IMAGE_SIZE_ALLOWED;
    public static final String POST_CREATED = "Post created successfully!";
    public static final String POST_UPDATED = "Post updated successfully!";
    public static final String POST_FETCHED = "Post fetched successfully!";
    public static final String COMMENT_CREATED = "Comment posted successfully!";
    public static final String COMMENT_DELETED = "Comment deleted successfully!";


}
