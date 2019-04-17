package vn.example.thrift_server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.example.thrift_server.service.BlogHandler;

public class ThriftServer {
    private static final Logger logger = LoggerFactory.getLogger(ThriftServer.class);

    public static void main(String... args) {

        try {
            BlogHandler blogHandler = new BlogHandler();
            vn.example.thrift_server.thrift.BlogService.Processor processor = new vn.example.thrift_server.thrift.BlogService.Processor(blogHandler);

            Runnable runnable = new Runnable() {
                public void run() {
                    start(processor);
                }
            };
            new Thread(runnable).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void start(vn.example.thrift_server.thrift.BlogService.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            logger.info("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
