import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapTest {
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("a", "1");
		map.put("b", "2");
		map.put("c", "3");
		map.put("d", "4");
		
		String key;
		Object value;

//		Iterator<String> iter = map.keySet().iterator();
//		while (iter.hasNext()) {
//			key = iter.next();
//			value = map.get(key);
//		}

//		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
//		Entry<String, String> entry;
//		while (iter.hasNext()) {
//			entry = iterator.next();
//			key = entry.getKey();
//			value = entry.getValue();
//		}
		
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry)iterator.next();
			key = entry.getKey().toString();
			value = entry.getValue();
		}
	}
}