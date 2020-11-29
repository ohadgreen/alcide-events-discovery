package io.alcide.handson;

import io.alcide.handson.model.Event;
import io.alcide.handson.model.Relation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTopologyDiscovery {

    Generator generator;
    List<Relation> relations;
    Stream<Event> eventStream;

    DiscoveryService discoveryService;

    @Before
    public void before() {
        generator = new Generator();
        List<String> ips = generator.generateIps(10);
        relations = generator.generateRelations(ips);
        eventStream = generator.createEventStream(relations);

        discoveryService = new DiscoveryService();
    }

    @After
    public void after() throws InterruptedException {
        generator.stop();
    }

    @Test
    public void discovery() {
        List<Event> events = eventStream
                .limit(1000)
                .collect(Collectors.toList());


        discoveryService.consume(events);

        assertEquals(relations, discoveryService.getRelations());
    }

    @Test
    public void testIpsByRecentEvents() {
        //simulate events
        eventStream
                .limit(1000)
                .collect(Collectors.toList());

        /** please implement and test your code to return (efficiently) the *IPS* with most recent events */
        assertTrue(false);
    }


    @Test
    public void testIpsByHighestOutboundTraffic() {
        //simulate events
        eventStream
                .limit(1000)
                .collect(Collectors.toList());

        /** please implement and test your code to return (efficiently) the *IPS* highest amount of outbound traffic */
        assertTrue(false);
    }
}
