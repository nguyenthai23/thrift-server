package vn.example.thrift_server.repository;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import vn.example.thrift_server.kafka.constants.IKafkaConstants;
import vn.example.thrift_server.kafka.producer.ProducerCreator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Repository<T> {
    private final Producer<Long, T> producer = ProducerCreator.createProducer();

    ExecutorService executor = Executors.newFixedThreadPool(10);

    protected void beforCreate(T entity) {

    }

    protected void afterCreate(T entity) {
        executor.execute(() -> {
            try {
                ProducerRecord<Long, T> record = new ProducerRecord<Long, T>(IKafkaConstants.TOPIC_NAME, IKafkaConstants.PARTTITON_AFTER_CREATE, null, entity);
                producer.send(record).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void beforUpdate(T entity) {
        executor.execute(() -> {
            try {
                ProducerRecord<Long, T> record = new ProducerRecord<Long, T>(IKafkaConstants.TOPIC_NAME, IKafkaConstants.PARTTITON_BEFOR_UPDATE, null, entity);
                producer.send(record).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void afterUpdate(T entity) {
        executor.execute(() -> {
            try {
                ProducerRecord<Long, T> record = new ProducerRecord<Long, T>(IKafkaConstants.TOPIC_NAME, IKafkaConstants.PARTTITON_AFTER_UPDATE, null, entity);
                producer.send(record).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void beforDelete() {

    }

    protected void afterDelete(T entity) {
        executor.execute(() -> {
            try {
                ProducerRecord<Long, T> record = new ProducerRecord<Long, T>(IKafkaConstants.TOPIC_NAME, IKafkaConstants.PARTTITON_AFTER_DELETE, null, entity);
                producer.send(record).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
