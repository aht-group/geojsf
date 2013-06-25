package org.geojsf.exception;

public class UnconsistentConfgurationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	 public UnconsistentConfgurationException ()
     {
     }

	 public UnconsistentConfgurationException (String message)
     {
		 super (message);
     }

	 public UnconsistentConfgurationException (Throwable cause)
     {
		 super (cause);
     }

	 public UnconsistentConfgurationException (String message, Throwable cause)
     {
		 super (message, cause);
     }
}
