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

package com.ibm.etools.mft.pattern.fp.rd.httponeway.pattern;

import java.util.Map;
import org.eclipse.osgi.util.NLS;
import com.ibm.etools.mft.pattern.fp.rd.httponeway.plugin.PatternBundle;
import com.ibm.etools.mft.pattern.fp.rd.httponeway.plugin.PatternPlugin;
import com.ibm.etools.patterns.model.base.IPatternBundle;

public class PatternMessages extends PatternBundle implements IPatternBundle {
	private static final String BUNDLE_NAME = "com.ibm.etools.mft.pattern.fp.rd.httponeway.pattern.messages"; //$NON-NLS-1$
	private static final Map<String, String> map;	
	private static final String[] enumerations = {
	 	"5061747465726E73216E6F5F726F7574696E67", //$NON-NLS-1$
	 	"5061747465726E732173706563696669635F726F75746573", //$NON-NLS-1$
	 	"5061747465726E732173616D706C655F6E6F6E65", //$NON-NLS-1$
	 	"5061747465726E73216E6F5F726F7574696E67", //$NON-NLS-1$
	 	"5061747465726E732173706563696669635F726F75746573", //$NON-NLS-1$
	};
	
	public static String getStringStatic(String key) {
		return map.get(key);
	}
	
	public String getString(String key) {
		return map.get(key);
	}

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f5a93b9a887432db7e7e97;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f5a93b9a887432db7e7e97_description;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f7159cc70f5188c86560abc;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f7159cc70f5188c86560abc_description;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f71c2fe44dd7c3e27d37d4b;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f71c2fe44dd7c3e27d37d4b_description;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f9005f0d0ccc2eb9f1a343;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f9005f0d0ccc2eb9f1a343_description;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f91238f10970b86f19b26fe;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_group_Id1443f91238f10970b86f19b26fe_description;		



	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_includesample;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_includesample_watermark;		


	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_inputdir;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_inputdir_watermark;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_filenamepattern;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_filenamepattern_watermark;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_filenamepattern_default;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_useftp;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_useftp_watermark;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_ftpservice;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_ftpservice_watermark;		


	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_routingtype;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_routingtype_watermark;		


	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_noroutingweburl;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_noroutingweburl_watermark;		


	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_specificroutesurl;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_specificroutesurl_watermark;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_specificroutesnamespace;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_specificroutesnamespace_watermark;		

	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_specificroutesrouting;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_root_specificroutesrouting_watermark;		


















	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_Id1443f728c4111bd67117f7c873f_5061747465726E73216E6F5F726F7574696E67;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_Id1443f728c4111bd67117f7c873f_5061747465726E732173706563696669635F726F75746573;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_Id14449bec550642c2b87ae8b123c_5061747465726E732173616D706C655F6E6F6E65;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_Id14449bec550642c2b87ae8b123c_5061747465726E73216E6F5F726F7574696E67;		
	public static String com_ibm_etools_mft_pattern_fp_rd_httponeway_pattern_pov_Id14449bec550642c2b87ae8b123c_5061747465726E732173706563696669635F726F75746573;		

	
	static {
		NLS.initializeMessages(BUNDLE_NAME, PatternMessages.class);
		PatternPlugin.addBundle(PatternMessages.class);
		map = PatternBundle.createMessageMap(PatternMessages.class, enumerations);
	}
}
