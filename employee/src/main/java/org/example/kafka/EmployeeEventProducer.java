package org.example.kafka;

import io.smallrye.reactive.messaging.kafka.KafkaProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.kafka.clients.producer.ProducerRecord;

@ApplicationScoped
public class EmployeeEventProducer {
    @Inject
    KafkaProducer<String, String> kafkaProducer;

    public void publishEmployeeAdded(Long organisationId) {
        String message = "EMPLOYEE_ADDED," + organisationId;
        kafkaProducer.send(new ProducerRecord<>("employee-events", message));
    }


    public void publishEmployeeRemoved(Long organisationId) {
        String message = "EMPLOYEE_REMOVED," + organisationId;
        kafkaProducer.send(new ProducerRecord<>("employee-events", message));
    }
}
