package vn.example.thrift_server.utils;

import vn.example.thrift_server.service.BlogService;

public class ServiceFactory {
    private static BlogService blogService;

    public static BlogService getBlogService() {
        if (blogService == null) {
            blogService = new BlogService();
        }
        return blogService;
    }
}
