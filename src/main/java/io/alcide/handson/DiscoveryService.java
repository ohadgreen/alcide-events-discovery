package io.alcide.handson;

import io.alcide.handson.model.Event;
import io.alcide.handson.model.Relation;

import java.util.*;

public class DiscoveryService {

    private Set<Relation> relationSet = new HashSet<>();
    private Set<String> mostRecentIpSet = new HashSet<>();
    private Map<String, Integer> ipTrafficCountMap = new HashMap<>();
    private String highestTrafficIp = null;
    private Integer highestIpTrafficCount = 0;

    private void updateRelations(List<Event> eventList) {
        for (Event event : eventList) {
            relationSet.add(new Relation(event.srcIP, event.destIp));
        }
    }

    public Set<Relation> getRelations() {
        return relationSet;
    }

    private void updateIpTrafficCounts(List<Event> eventList) {
        for (Event event : eventList) {
            String ip = event.srcIP;
            if (ipTrafficCountMap.containsKey(ip)) {
                Integer count = ipTrafficCountMap.get(ip) + 1;
                ipTrafficCountMap.put(ip, count);
                if (count > highestIpTrafficCount) {
                    highestIpTrafficCount++;
                    highestTrafficIp = ip;
                }
            }
            else {
                ipTrafficCountMap.put(ip, 1);
            }
        }
    }

    public String getHighestTrafficIp() {
        return highestTrafficIp;
    }

    private void updateRecentIpSet(List<Event> eventList) {
        mostRecentIpSet.clear();
        for (Event event : eventList) {
            mostRecentIpSet.add(event.srcIP);
        }
    }

    public Set<String> getMostRecentIpSet() {
        return mostRecentIpSet;
    }

    public void consume(List<Event> events) {
        updateRelations(events);
        updateIpTrafficCounts(events);
        updateRecentIpSet(events);
    }
}
