package transformers;

import org.mule.api.MuleMessage;
import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import java.util.HashMap;



@ContainsTransformerMethods
public class GoogleSafeBrowsingToMap extends AbstractMessageTransformer {
	
	public Object transformMessage(MuleMessage message, String outputEncoding)
		throws TransformerException {
			HashMap<String, Object> result = new HashMap<String, Object>();
			HashMap<String, Object> event = new HashMap<String, Object>();
			String response_body = new String();
			Boolean positive = false;
		try {	
			response_body = message.getPayloadAsString();
			if (!"".equals(response_body)) {
				positive = true;
			}
			else
			{
				response_body = "Clean/Unknown site";
			}
			
			result.put("positive",positive);
			result.put("type",response_body);

			event.put("target",message.getInvocationProperty("target"));
			event.put("field",message.getInvocationProperty("field"));
			event.put("source","googlesafebrowsing");
			event.put("results",result);


	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return event ;
	}
}