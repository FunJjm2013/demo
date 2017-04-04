package studio.jjm.facedetect.util;

import java.io.File;
import java.io.FilenameFilter;
/**
 *	¹ýÂËÍ¼ÏñÎÄ¼þ 
 * 	@author Jiang Junming
 * 	@version 1.0
 * 
 */
public class ImageFileFilter implements FilenameFilter{
    private String[] extension = new String[]{".jpg", ".JPG", ".gif", ".GIF", ".png", ".PNG", ".jpeg", ".JPEG", ".bmp", ".BMP"}; 

    @Override
    public boolean accept(File dir, String name) {
        // TODO Auto-generated method stub
        for(String s : extension){
            if(name.endsWith(s)){
                return true;    
            }
        }   
        return  false; 
    }

}
