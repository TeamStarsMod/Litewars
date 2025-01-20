package xyz.litewars.litewars.api.data;

import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.team.Team;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSet<K, MK, V> {
    private final Map<K, Map<MK, V>> values = new ConcurrentHashMap<>();

    public Map<MK, V> newDataMap (K name) {
        values.put(name, new ConcurrentHashMap<>());
        return values.get(name);
    }

    public void deleteDataMap (K name) {
        values.remove(name);
    }

    public void clearDataMap (K name) {
        values.get(name).clear();
    }

    public void replaceDataMap (K name, Map<MK, V> newValue) {
        values.replace(name, newValue);
    }

    public V getValue (K name, MK key) {
        return values.get(name).get(key);
    }

    public Map<MK, V> getDataMap (K name) {
        return values.get(name);
    }

    public Map<MK, Arena> getArenaMap (K name) {
        try {
            return (Map<MK, Arena>) values.get(name);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Map<MK, Team> getTeamMap (K name) {
        try {
            return (Map<MK, Team>) values.get(name);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Map<MK, Boolean> getBooleanMap (K name) {
        try {
            return (Map<MK, Boolean>) values.get(name);
        } catch (ClassCastException e) {
            return null;
        }
    }
}
