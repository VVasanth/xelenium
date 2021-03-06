package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;

import java.net.URLConnection;

import net.sourceforge.htmlunit.corejs.javascript.Context;

/**
 * Interface for URL connection based security domain providers. Used by 
 * {@link UrlModuleSourceProvider} to create Rhino security domain objects for 
 * newly loaded scripts (see {@link Context#compileReader(java.io.Reader, 
 * String, int, Object)}) based on the properties obtainable through their URL 
 * connection.
 * @author Attila Szegedi
 * @version $Id: UrlConnectionSecurityDomainProvider.java 6395 2011-05-05 17:00:20Z mguillem $
 */
public interface UrlConnectionSecurityDomainProvider
{
    /**
     * Create a new security domain object for a script source identified by 
     * its URL connection.
     * @param urlConnection the URL connection of the script source
     * @return the security domain object for the script source. Can be null if
     * no security domain object can be created, although it is advisable for 
     * the implementations to be able to create a security domain object for 
     * any URL connection.
     */
    public Object getSecurityDomain(URLConnection urlConnection);
}