/**
 * Pattern program for use with IBM WebSphere Message Broker.
 *
 * COPYRIGHT NOTICE AND LICENSE
 * © Copyright International Business Machines Corporation 2009, 2011
 * Licensed Materials - Property of IBM
 *
 * On condition that the user is also then a licensed user of the specific 
 * version of the IBM product named above, this pattern program may be   
 * used, executed, copied and modified without obligation to make any  
 * royalty payment to IBM, as follows:
 *
 * (a) for the user's own instruction and study; and
 *
 * (b) in order to develop one or more applications designed to run with an IBM
 *     WebSphere Message Broker software product, either (i) for the licensed user's
 *     own internal use or (ii) for redistribution by the licensed user, as part of  
 *     such an application and in the licensed user's own product or products.
 *
 * No other rights under copyright are granted without prior written permission
 * of International Business Machines Corporation.
 *
 * In all other respects, the licensing terms and conditions associated with
 * the above-named IBM product continue to apply without modification.
 *
 * NO WARRANTY 
 * These materials and this sample program illustrate programming techniques. 
 * They have not been thoroughly tested under all conditions. 
 *
 * IBM therefore cannot and does not in any way guarantee, warrant represent 
 * or imply the reliability, serviceability, or function of this sample program. 
 * 
 * To the fullest extent permitted by applicable law, this program is provided by  
 * IBM "As Is", without warranty of any kind (express or implied), including without  
 * limitation any implied warranty of merchantability (satisfactory quality) or fitness 
 * for any particular purpose.
 */
package com.ibm.etools.mft.pattern.fp.rd.httponeway.plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.osgi.util.NLS;

public class PatternBundle extends NLS {
	private static final String PERIOD = "."; //$NON-NLS-1$
	private static final String UNDERSCORE = "_"; //$NON-NLS-1$
	public String getString(String key) { return null; }
	public static String getSString(String key) { return null; }
	
	public String getMessage(String key, Object[] parameters) {
		String message, baseMessageFormat;
		baseMessageFormat = getString(key);
		if (!baseMessageFormat.equals(key)) {
			message = NLS.bind(baseMessageFormat, parameters);
			return message;
		}
		return key;
	}

	/**
	 * Creates the message map for JET summary and field names.
	 * 
	 * @param messageClass
	 *            The pattern message NLS class.
	 * @param enumerations
	 *            A list of known enumeration names.
	 */
	public static Map<String, String> createMessageMap(Class<?> messageClass, String[] enumerations) {
		Map<String, String> map = new HashMap<String, String>();	
		for (Field currentField : messageClass.getFields()) {
			String fieldName = currentField.getName();
			String enumerationName = "";
			boolean isEnumeration = false;
			
			// Enumerations keep any underscore parts!
			for (String currentName : enumerations) {
				if (fieldName.endsWith(currentName) == true) {
					fieldName = fieldName.replaceAll(currentName, "");
					enumerationName = currentName;
					isEnumeration = true; break;
				}
			}
			
			try {
				Object fieldValue = currentField.get(null);

				// Replace underscores with periods for later lookup
				String keyName = fieldName.replace(UNDERSCORE, PERIOD);
				if (isEnumeration == true) {
					keyName += enumerationName;
				}
				
				map.put(keyName, fieldValue.toString());
				
			} catch (IllegalAccessException exception) { 
				// This should never happen..!
				exception.printStackTrace();
			}
		}		
		return map;
	}
}
