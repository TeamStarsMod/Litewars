package xyz.litewars.litewars.api.data;

import xyz.litewars.litewars.api.arena.Arena;
import xyz.litewars.litewars.api.arena.team.Team;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Deprecated
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

    public <T> T getValue (K name, MK mapKey, Class<? extends T> class1) {
        try {
            return (T) values.get(name).get(mapKey);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public <T> Map<MK, T> getKeyMap (K name, Class<? extends T> class1) {
        try {
            return (Map<MK, T>) values.get(name);
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

    public <T> KeyMap<T> getMap (K name, Class<? extends T> class1) {
        return new KeyMap<>(name, class1);
    }

    public Map<MK, Boolean> getBooleanMap (K name) {
        try {
            return (Map<MK, Boolean>) values.get(name);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public class KeyMap<KV> {
        private final K name;
        private final Class<? extends KV> class1;
        public KeyMap(K name, Class<? extends KV> class1) {
            this.name = name;
            this.class1 = class1;
        }

        public Map<MK, KV> getMap () {
            return getKeyMap(name, class1);
        }

        public KV get(MK key) {
            return getValue(name, key, class1);
        }
    }
}
