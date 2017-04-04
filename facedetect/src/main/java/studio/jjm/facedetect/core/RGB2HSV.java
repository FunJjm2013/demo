package studio.jjm.facedetect.core;
/**
 *	RGB颜色空间转换成HSV颜色空间
 * 	@author Jiang Junming
 * 	@version 1.0
 */
public class RGB2HSV {

	public static final float FLT_EPSILON = 1.192092896e-07F;
	private int srccn;
	private int blueIdx;
	float hrange;
	
	public RGB2HSV(int _srccn, int _blueIdx, float _hrange){
		
		this.srccn = _srccn ;
		this.blueIdx = _blueIdx ;
		this.hrange = _hrange;
	}
	 /**
     *  RGB颜色空间转换成HSV颜色空间
     *	@param	src	rgb源图像
     *	@param	dst	hsv目标图像
     *
     *	@param	像素中颜色分量的数目
     */

	public void convert(float[] src, float[] dst, int n){
	       int i, bidx = blueIdx, scn = srccn;
	        float hscale = hrange*(1.f/360.f);
	        int pointer = 0 ;
	        n *= 3;

	        for( i = 0, pointer = 0; i < n; i += 3, pointer += scn )
	        {
	            float b = src[bidx+pointer], g = src[1+pointer], r = src[(bidx^2)+pointer];
	            float h, s, v;

	            float vmin, diff;

	            v = vmin = r;
	            if( v < g ) v = g;
	            if( v < b ) v = b;
	            if( vmin > g ) vmin = g;
	            if( vmin > b ) vmin = b;
	            diff = v - vmin;
	            s = diff/(float)(v + FLT_EPSILON);
	            diff = (float)(60./(diff + FLT_EPSILON));
	            if( v == r )
	                h = (g - b)*diff;
	            else if( v == g )
	                h = (b - r)*diff + 120.f;
	            else
	                h = (r - g)*diff + 240.f;

	            if( h < 0 ) h += 360.f;

	            dst[i] = h*hscale;
	            dst[i+1] = s;
	            dst[i+2] = v;
	        }

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
