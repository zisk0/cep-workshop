
package transformers;

import org.mule.api.MuleMessage;
import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.transformer.TransformerException;

import java.util.HashMap;

import org.mule.transformer.AbstractMessageTransformer;

@ContainsTransformerMethods
public class RequestWrapper extends AbstractMessageTransformer
{
    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding)
            throws TransformerException {

        HashMap<String, Object> event = new HashMap<String, Object>();
        try {  
            
            event.put("source_request",message.getPayload());
            event.put("source","request");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return event;

    }
}