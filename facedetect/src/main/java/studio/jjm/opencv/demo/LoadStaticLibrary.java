package studio.jjm.opencv.demo;

import org.opencv.core.Core;

public abstract class LoadStaticLibrary {
	
	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME) ;        
	}
}
