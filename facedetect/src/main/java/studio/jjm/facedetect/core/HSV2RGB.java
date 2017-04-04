package studio.jjm.facedetect.core;
/**
 *	HSV颜色空间转换成RGB颜色空间
 * 	@author Jiang Junming
 * 	@version 1.0
 */

public class HSV2RGB {

	int dstcn, blueIdx;
	float hscale;
	public HSV2RGB(int _dstcn, int _blueIdx, float _hscale){
		this.dstcn = _dstcn;
		this.blueIdx = _blueIdx;
		this.hscale = _hscale;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Math.floor(0.4999f));
		System.out.println(Math.rint(0.4999f));
		System.out.println(Math.round(0.4999f));
		System.out.println(Math.ceil(0.4999f));
		System.out.println("\n"+Math.floor(0.9f));
		System.out.println(Math.rint(0.5f));
		System.out.println(Math.round(0.5));
		System.out.println(Math.ceil(0.9));
	}
/*	public int xianxiaquzheng(float a, float b){
		int i = 0 ;
		float f = 0.5f;
		float e = a / b ;
		int i1 = (int)e ;
		int i2 = (int)(e + 0.5f) ;
		
	}
*/	
	 /**
     *  HSV颜色空间转换成RGB颜色空间
     *	@param	src	rgb源图像
     *	@param	dst	hsv目标图像
     *
     *	@param	像素中颜色分量的数目
     */

	public void transfer(float[] src, float[] dst, int n){
		   int bidx = blueIdx, dcn = dstcn;
		    float _hscale = hscale;
		    float alpha = 1.0f;
		    n *= 3;
		    
		    for(int i = 0, pointer = 0; i < n; i += 3)
		    {
		        float h = src[i+pointer], s = src[i+1+pointer], v = src[(i^2)+pointer];
		        float b, g, r;

		        if( s == 0 )
		            b = g = r = v;
		        else
		        {
		            int sector_data[][]={{1,3,0}, {1,0,2}, {3,0,1}, {0,2,1}, {0,1,3}, {2,1,0}};
		            float tab[] = new float[4];
		            int sector;
		            h *= _hscale;			//  1/60
		            if( h < 0 )
		                do h += 6; while( h < 0 );
		            else if( h >= 6 )
		                do h -= 6; while( h >= 6 );
		            sector = (int)Math.floor(h);
		            h -= sector;

		            tab[0] = v;
		            tab[1] = v*(1.0f - s);		//p
		            tab[2] = v*(1.0f - s*h);		//q
		            tab[3] = v*(1.0f - s*(1.0f - h));		//t

		            b = tab[sector_data[sector][0]];
		            g = tab[sector_data[sector][1]];
		            r = tab[sector_data[sector][2]];
		        }

		        dst[bidx] = b;
		        dst[1] = g;
		        dst[bidx^2] = r;
		        if( dcn == 4 )
		            dst[3] = alpha;
		        pointer += dcn;
		    }

	}
	
}


