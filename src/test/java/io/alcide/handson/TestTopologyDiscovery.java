package io.alcide.handson;

import com.google.common.collect.Lists;
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

import static java.util.stream.Collectors.groupingBy;
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
        /** please implement and test your code to return (efficiently) the *IPS* with most recent events */

        List<List<Event>> eventListToChunks = splitEventListToChunks(initTestEventList(), 4);
        for (List<Event> eventList : eventListToChunks) {
            discoveryService.consume(eventList);
        }

        Set<String> mostRecentIpSet = discoveryService.getMostRecentIpSet();
        assertTrue(mostRecentIpSet.contains("6") && mostRecentIpSet.contains("1") && mostRecentIpSet.contains("2"));
    }

    @Test
    public void testIpsByHighestOutboundTraffic() {
        /** please implement and test your code to return (efficiently) the *IPS* highest amount of outbound traffic */
        /** OG: when two or more ip has the same number of highest traffic it will choose one of them randomly */
        discoveryService.consume(initTestEventList());
        String highestTrafficIp = discoveryService.getHighestTrafficIp();
        assertEquals("6", highestTrafficIp);
    }

    private List<Event> initTestEventList() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event("3", "33", 0l, 0l));
        eventList.add(new Event("3", "33", 0l, 0l));
        eventList.add(new Event("3", "33", 0l, 0l));

        eventList.add(new Event("6", "66", 0l, 0l));
        eventList.add(new Event("6", "66", 0l, 0l));
        eventList.add(new Event("6", "66", 0l, 0l));
        eventList.add(new Event("6", "66", 0l, 0l));
        eventList.add(new Event("6", "66", 0l, 0l));
        eventList.add(new Event("6", "66", 0l, 0l));

        eventList.add(new Event("1", "11", 0l, 0l));
        eventList.add(new Event("2", "22", 0l, 0l));
        eventList.add(new Event("2", "22", 0l, 0l));

        return eventList;
    }

    private List<List<Event>> splitEventListToChunks(List<Event> events, int chunkSize) {
        return Lists.partition(events, chunkSize);
    }
}
