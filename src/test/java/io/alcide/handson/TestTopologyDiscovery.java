package io.alcide.handson;

import io.alcide.handson.model.Event;
import io.alcide.handson.model.Relation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

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
        Set<Relation> parsedRelations = discoveryService.getRelations();
        Set<Relation> relationSet = this.relations.stream().collect(Collectors.toSet());

        assertEquals(relationSet, parsedRelations);
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
//        List<Event> events = eventStream
//                .limit(1000)
//                .collect(Collectors.toList());

        /** please implement and test your code to return (efficiently) the *IPS* highest amount of outbound traffic */
        /** OG: when two or more ip has the same number of highest traffic it will choose one of them randomly */
        discoveryService.consume(initTestEventList());
        String highestTrafficIp = discoveryService.findHighestTrafficIp();
        assertEquals("4", highestTrafficIp);
    }

    private List<Event> initTestEventList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event("3", null, 0l, 0l));
        eventList.add(new Event("3", null, 0l, 0l));
        eventList.add(new Event("3", null, 0l, 0l));

        eventList.add(new Event("4", null, 0l, 0l));
        eventList.add(new Event("4", null, 0l, 0l));
        eventList.add(new Event("4", null, 0l, 0l));
        eventList.add(new Event("4", null, 0l, 0l));

        eventList.add(new Event("1", null, 0l, 0l));

        return eventList;
    }
}
