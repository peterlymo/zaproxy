/*
*
* Paros and its related class files.
* 
* Paros is an HTTP/HTTPS proxy for assessing web application security.
* Copyright (C) 2003-2004 Chinotec Technologies Company
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the Clarified Artistic License
* as published by the Free Software Foundation.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* Clarified Artistic License for more details.
* 
* You should have received a copy of the Clarified Artistic License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

package org.parosproxy.paros.extension.option;

import java.util.List;

import org.parosproxy.paros.common.AbstractParam;
import org.zaproxy.zap.utils.LocaleUtils;

/**
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OptionsParamView extends AbstractParam {

	private static final String ROOT = "view";

	private static final String PROCESS_IMAGES = "view.processImages";
	// ZAP: Added support fr selecting the locale
	public static final String LOCALE = "view.locale";
	public static final String LOCALES = "view.locales";
	public static final String DISPLAY_OPTION = "view.displayOption";

	public static final String DEFAULT_LOCALE = "en_GB";
	//private static final String[] DEFAULT_LOCALES = {"en_GB", "de_DE", "es_ES", "pt_BR", "pl_PL"};

	private int processImages = 0;
	private String configLocale = "";
	private String locale = "";
	private int displayOption = 0;
	
    /**
     * @param rootElementName
     */
    public OptionsParamView() {
    }

    /* (non-Javadoc)
     * @see com.proofsecure.paros.common.FileXML#parse()
     */
    protected void parse() {
	    // use temp variable to check.  Exception will be flagged if any error.
	    processImages = getConfig().getInt(PROCESS_IMAGES, 0);
	    configLocale = getConfig().getString(LOCALE);	// No default
	    locale = getConfig().getString(LOCALE, DEFAULT_LOCALE);
	    displayOption = getConfig().getInt(DISPLAY_OPTION, 0);
    }

	/**
	 * @return Returns the skipImage.
	 */
	public int getProcessImages() {
		return processImages;
	}
	
	/**
	 * @param processImages 0 = not to process.  Other = process images
	 * 
	 */
	public void setProcessImages(int processImages) {
		this.processImages = processImages;
		getConfig().setProperty(PROCESS_IMAGES, Integer.toString(processImages));
	}
	
	public boolean isProcessImages() {
		return !(processImages == 0);
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
		getConfig().setProperty(LOCALE, locale);
	}

	public String getConfigLocale() {
		return configLocale;
	}

	public int getDisplayOption() {
		return displayOption;
	}

	public void setDisplayOption(int displayOption) {
		this.displayOption = displayOption;
		getConfig().setProperty(DISPLAY_OPTION, Integer.toString(displayOption));
	}
	
}
