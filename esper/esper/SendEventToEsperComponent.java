package esper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mule.api.annotations.param.Payload;
//import org.mule.api.MuleMessage;
/**
 * @author Juan Boubeta-Puig <juan.boubeta@uca.es>
 *
 */
public class SendEventToEsperComponent {

	public synchronized void sendEventToEsper(@Payload Map<String, Object> eventMap) { 
		
		System.out.println("===eventMap: " + eventMap.keySet().toArray());

		// We take the event type from the one and only first key.
		//String eventTypeName = (String) eventMap.keySet().toArray()[0];
		String eventTypeName = (String) eventMap.get("source");
		System.out.println("===eventType: " + eventTypeName);
		// Then we take the payload
		Map<String, Object> eventPayload = new HashMap<String, Object>();
		eventPayload = (Map<String, Object>) eventMap;
		System.out.println("===eventPayload Passed: " + eventPayload);

		
		Map<String, Object> eventPayloadTypeMap = new HashMap<String, Object>();
		eventPayloadTypeMap = getEventType(eventPayload);
		
		System.out.println("===eventPayloadTypeMap: " + eventPayloadTypeMap);

		System.out.println("===eventTypeName: " + eventTypeName);

		if (!EsperUtils.eventTypeExists(eventTypeName)) {
			EsperUtils.addNewEventType(eventTypeName, eventPayloadTypeMap);
		}
								
		EsperUtils.sendEvent(eventPayload, eventTypeName);
		System.out.println("\nSending event '" + eventTypeName + "' to Esper: " + eventPayload + "\n");
	}

	
	private Map<String, Object> getEventType(Map<String, Object> eventMap) {
		   
		// LinkedHashMap will iterate in the order in which the entries were put into the map
		Map<String, Object> eventTypeMap = new LinkedHashMap<String, Object>();

		for (String key : eventMap.keySet()) {
/*			System.out.println("=== Trying to get class from key:" + key);
*/			Object value = eventMap.get(key);
               
			if (value instanceof Map) {               
				@SuppressWarnings("unchecked")
				Map<String, Object> nestedEventProperty = getEventType((Map<String, Object>) value);
				
				eventTypeMap.put(key, nestedEventProperty);
			} else if (value != null) {
				eventTypeMap.put(key, value.getClass());
/*			    System.out.println("=== Key:" + key + "  Class: " + value.getClass());
*/
			}
		}

		return eventTypeMap;
	}
}