/*
 * Copyrights     : CNRS
 * Author         : Oleg Lodygensky
 * Acknowledgment : XtremWeb-HEP is based on XtremWeb 1.8.0 by inria : http://www.xtremweb.net/
 * Web            : http://www.xtremweb-hep.org
 * 
 *      This file is part of XtremWeb-HEP.
 *
 *    XtremWeb-HEP is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    XtremWeb-HEP is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with XtremWeb-HEP.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package xtremweb.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This defines XtremWeb application type.
 * 
 * Created: 16 novembre 2011
 * 
 * @author <a href="mailto: lodygens at lal in2p3 fr">Oleg Lodygensky</a>
 * @since 8.0.0
 */

public enum AppTypeEnum {

	NONE,
	/**
	 * This denotes an application where workers must download executable This
	 * denotes application as known until XWHEP 7
	 */
	DEPLOYABLE,
	/**
	 * This denotes an application where workers don't download binary because
	 * the application is supposed to be pre-installed on workers. All following
	 * values in this enum represent shared application.
	 */
	SHARED,
	/**
	 * On Dec 2nd, 2011, this denotes our 1st shared application. This denotes
	 * VirtualBox as shared application
	 */
	VIRTUALBOX;

	public static final AppTypeEnum LAST = VIRTUALBOX;
	public static final int SIZE = LAST.ordinal() + 1;

	/**
	 * This retrieves an OS from its ordinal value
	 * 
	 * @exception IllegalArgumentException
	 *                is thrown if v is not a valid ordinal value
	 */
	public static AppTypeEnum fromInt(int v) throws IllegalArgumentException {
		for (final AppTypeEnum i : AppTypeEnum.values()) {
			if (i.ordinal() == v) {
				return i;
			}
		}
		throw new IllegalArgumentException("unvalid XWOSes value " + v);
	}

	/**
	 * This retrieves this string representation
	 * 
	 * @return a array containing this string representation
	 */
	public static String[] getLabels() {
		final String[] labels = new String[SIZE];
		for (final AppTypeEnum c : AppTypeEnum.values()) {
			labels[c.ordinal()] = c.toString();
		}
		return labels;
	}

	/**
	 * This array stores default VirtualBox pathnames (one entry per OS). Each
	 * entry is a semicolon separated paths list Defaults are Oracle VirtualBox
	 * paths
	 * 
	 * @since 8.0.0 (FG)
	 */
	private static String[] virtualboxpaths = {
			null, // NONE
			"/usr/bin/VBoxHeadless", // LINUX
			"c:\\Program Files\\Oracle\\VirtualBox\\VBoxHeadless.exe;c:\\Program Files\\VirtualBox\\VBoxHeadless.exe;c:\\Program Files (x86)\\Oracle\\VirtualBox\\VBoxHeadless.exe;c:\\Program Files (x86)\\VirtualBox\\VBoxHeadless.exe", // WIN32
			"/Applications/VirtualBox.app/Contents/MacOS/VBoxHeadless", // MACOSX
			null, // OSF1
			null, // SOLARIS
			null // JAVA
	};

	/**
	 * This retrieves application default pathname
	 * 
	 * @param t
	 *            is the application type to retrieve path for
	 * @return application binary path if available
	 * @throws FileNotFoundException
	 *             if no binary path available for the provided application type
	 * @see xtremweb.common.OSEnum#getOs(String)
	 * @since 8.0.0 (FG)
	 */
	public String getPathName()  {
		return virtualboxpaths[OSEnum.getOs().ordinal()];
	}

	/**
	 * This retrieves application default pathname
	 * 
	 * @return application binary path for the current OS
	 * @throws FileNotFoundException
	 *             if no application binary path found for the current OS
	 * @see xtremweb.common.OSEnum#getOs(String)
	 * @since 8.0.0 (FG)
	 */
	public File getPath() throws FileNotFoundException {
		final String filePath = virtualboxpaths[OSEnum.getOs().ordinal()];
		if (filePath == null) {
			throw new FileNotFoundException("no binary path for " + this);
		}
		final File f = new File(filePath);
		if (f.exists() == true) {
			return f;
		}
		throw new FileNotFoundException("no binary path for " + this);
	}
	/**
	 * This dumps path
	 * 
	 * @see xtremweb.common.OSEnum#getOs(String)
	 * @since 8.0.0 (FG)
	 */
	static public void dumpPath() {
		for (final AppTypeEnum a : AppTypeEnum.values()) {
			try {
				System.out.println(a.getPath());
			} catch (Exception e) {
			}
		}
	}

	/**
	 * This checks if the provided application is available
	 * 
	 * @return true if getPath() != null; false otherwise
	 * @throws FileNotFoundException
	 *             if application path is not valid
	 * @see #getPath()
	 * @since 8.0.0 (FG)
	 */
	public boolean available() throws FileNotFoundException {
		return (getPath() != null);
	}

	/**
	 * This is for debug purposes
	 * 
	 * @param argv
	 */
	public static void main(String[] argv) {
		for (final AppTypeEnum i : AppTypeEnum.values()) {
			System.out.println(i.toString());
		}
	}

}
