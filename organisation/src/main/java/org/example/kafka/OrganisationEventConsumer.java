package org.example.kafka;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.kafka.KafkaProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.example.service.OrganisationService;

@ApplicationScoped
public class OrganisationEventConsumer {
    @Inject
    OrganisationService organisationService;

    @Incoming("employee-events")
    @Blocking
    public void consumeEmployeeEvent(String event) {
        String[] parts = event.split(",");
        String eventType = parts[0];
        Long organisationId = Long.parseLong(parts[1]);

        if ("EMPLOYEE_ADDED".equals(eventType)) {
            organisationService.incrementEmployeeCount(organisationId);
        } else if ("EMPLOYEE_REMOVED".equals(eventType)) {
            organisationService.decrementEmployeeCount(organisationId);
        }
    }
}
