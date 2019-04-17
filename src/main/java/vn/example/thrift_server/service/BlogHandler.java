package vn.example.thrift_server.service;

import org.apache.thrift.TException;
import vn.example.thrift_server.thrift.Blog;
import vn.example.thrift_server.thrift.BlogService;
import vn.example.thrift_server.utils.ServiceFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BlogHandler implements BlogService.Iface {

    private vn.example.thrift_server.service.BlogService blogService = ServiceFactory.getBlogService();

    public List<Blog> getBlogs() throws TException {
        return this.blogService.getBlogs().stream().map(blog -> {
            return mapToBlogThrift(blog);
        }).collect(Collectors.toList());
    }

    public Blog getBlog(long id) throws TException {
        vn.example.thrift_server.entity.Blog blog = this.blogService.getBlog(id);
        return mapToBlogThrift(blog);
    }

    public Blog createBlog(Blog blog) throws TException {
        vn.example.thrift_server.entity.Blog blogEntity = mapToBlogEntity(blog);
        return mapToBlogThrift(this.blogService.createBlog(blogEntity));
    }

    public Blog updateBlog(Blog blog) throws TException {
        vn.example.thrift_server.entity.Blog blogEntity = mapToBlogEntity(blog);
        return mapToBlogThrift(this.blogService.updateBlog(blogEntity));
    }

    public void deleteBlog(long id) throws TException {
        this.blogService.deleteBlog(id);
    }

    private Blog mapToBlogThrift(vn.example.thrift_server.entity.Blog blog) {
        Blog blogThrift = new Blog();
        blogThrift.setId(blog.getId());
        blogThrift.setTitle(blog.getTitle());
        blogThrift.setContent(blog.getContent());
        blogThrift.setStatus(blog.getStatus());
        blogThrift.setCreatedDate(String.valueOf(blog.getCreatedDate().getTime()));
        blogThrift.setUpdatedDate(String.valueOf(blog.getUpdatedDate().getTime()));
        return blogThrift;
    }

    private vn.example.thrift_server.entity.Blog mapToBlogEntity(Blog blog) {
        vn.example.thrift_server.entity.Blog blogEntity = new vn.example.thrift_server.entity.Blog();
        blogEntity.setId(blog.getId());
        blogEntity.setTitle(blog.getTitle());
        blogEntity.setContent(blog.getContent());
        blogEntity.setStatus(blog.isStatus());
        return blogEntity;
    }
}
