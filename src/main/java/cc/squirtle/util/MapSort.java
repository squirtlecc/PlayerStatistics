package cc.squirtle.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;



public class MapSort<T>{
    /**
     * sort with value
     * @param map
     * @return
     */

    public MapSort(){

    }
    
    public Map<UUID, T> sortMapByValue(Map<UUID, T> oriMap) {

        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }

        Map<UUID, T> sortedMap = new LinkedHashMap<UUID, T>();

        List<Map.Entry<UUID, T>> entryList = new ArrayList<>(
                oriMap.entrySet());

        Collections.sort(entryList, new MapValueComparator<>());

        Iterator<Entry<UUID, T>> iter  = entryList.iterator();
        Map.Entry<UUID, T> tmpEntry = null;

        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }

        return sortedMap;

    }

}


class MapValueComparator<T> implements Comparator<Map.Entry<UUID, T>> {
    @Override
    public int compare(Entry<UUID, T> me1, Entry<UUID, T> me2) {
        return -((int)me1.getValue()-(int)me2.getValue());
    }
}