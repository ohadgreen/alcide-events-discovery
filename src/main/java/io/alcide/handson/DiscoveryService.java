package io.alcide.handson;

import io.alcide.handson.model.Event;
import io.alcide.handson.model.Relation;

import java.util.*;
import java.util.stream.Collectors;

public class DiscoveryService {

    private List<Event> events = new ArrayList<>();
    public Set<Relation> getRelations() {

        Set<Relation> relationSet = new HashSet<>();
        for (Event event : events) {
            relationSet.add(new Relation(event.srcIP, event.destIp));
        }

        return relationSet;
    }

    public String findHighestTrafficIp() {
        Map<String, Integer> ipTrafficCountMap = new HashMap<>();
        String highestIp = null;
        Integer highestCount = 0;
        for (Event event : events) {
            String ip = event.srcIP;
            if (ipTrafficCountMap.containsKey(ip)) {
                Integer count = ipTrafficCountMap.get(ip) + 1;
                ipTrafficCountMap.put(ip, count);
                if (count > highestCount) {
                    highestCount++;
                    highestIp = ip;
                }
            }
            else {
                ipTrafficCountMap.put(ip, 1);
            }
        }

        return highestIp;
    }

    public void consume(List<Event> events) {
        this.events = events;
    }
}
