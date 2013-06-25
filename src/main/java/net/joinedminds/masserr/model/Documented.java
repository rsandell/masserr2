package net.joinedminds.masserr.model;

/**
 * Interface describing a modelobject that has some documentation at a specific URL.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public interface Documented {
    /**
     * The URL to the documentation.
     *
     * @return the URL.
     */
    String getDocUrl();
}
