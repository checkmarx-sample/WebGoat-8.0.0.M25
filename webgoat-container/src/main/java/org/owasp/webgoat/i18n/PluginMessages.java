/*
 * This file is part of WebGoat, an Open Web Application Security Project utility. For details,
 * please see http://www.owasp.org/
 * <p>
 * Copyright (c) 2002 - 2017 Bruce Mayhew
 * <p>
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 * <p>
 * Getting Source ==============
 * <p>
 * Source for this application is maintained at https://github.com/WebGoat/WebGoat, a repository for free software
 * projects.
 * <p>
 */

package org.owasp.webgoat.i18n;

import lombok.SneakyThrows;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

//-------FIX
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
//-------FIX


/**
 * Message resource bundle for plugins.
 *
 * @author nbaars
 * @date 2/4/17
 */
public class PluginMessages extends ReloadableResourceBundleMessageSource {
    private static final String PROPERTIES_SUFFIX = ".properties";

    private Language language;

    public PluginMessages(Messages messages, Language language) {
        this.language = language;
        this.setParentMessageSource(messages);
        this.setBasename("WebGoatLabels");
    }

    @Override
    @SneakyThrows
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        Properties properties = new Properties();
        long lastModified = System.currentTimeMillis();

        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(filename + PROPERTIES_SUFFIX);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String sourcePath = resource.toURI().toString().replace(PROPERTIES_SUFFIX, "");
            PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
            properties.putAll(holder.getProperties());
        }
        return new PropertiesHolder(properties, lastModified);
    }


    public Properties getMessages() {
        return getMergedProperties(language.getLocale()).getProperties();
    }

    public String getMessage(String code, Object... args) {
        return getMessage(code, args, language.getLocale());
    }

    public String getMessage(String code, String defaultValue, Object... args) {
//----VULNERABLE		
        return super.getMessage(code, args, defaultValue, language.getLocale());
//----VULNERABLE		
//		Encoder encoder = ESAPI.encoder();
//		String encodedMessage;
//		encodedMessage = super.getMessage(code, args, defaultValue, language.getLocale());
//		encodedMessage = encoder.encodeForJavaScript(encodedMessage);	
//		return encodedMessage;
		
//-------FIX

//-------FIX

    }
}
