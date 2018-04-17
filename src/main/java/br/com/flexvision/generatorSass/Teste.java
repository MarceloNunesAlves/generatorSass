package br.com.flexvision.generatorSass;

public class Teste {

	public static void main(String[] args) {
		String hexVal = "3f83a3";
		for(int i=0;i<=100;i=i+10) {
			hexVal = shadeRGBColor(hex2Rgb(hexVal), i);
			System.out.println(hexVal);
			System.out.println(" ===========><============= ");
		}
	}
	
	public static String shadeRGBColor(String color, int percent) {
	    String[] f=color.split(",");
	    double t=percent<0?0:255;
	    double p=percent<0?((double)percent/100)*-1:((double)percent/100);
	    double R=Integer.parseInt(f[0]);//.slice(4)
	    double G=Integer.parseInt(f[1]);
	    double B=Integer.parseInt(f[2]);
	    
	    int rCalc = (int)(Math.round((t-R)*p)+R);
	    int gCalc = (int)(Math.round((t-G)*p)+G);
	    int bCalc = (int)(Math.round((t-B)*p)+B);
	    
	    String hex = String.format("%02x%02x%02x", rCalc, gCalc, bCalc); 
	    return hex;
	}
	
	public static String hex2Rgb(String colorStr) {
	    return Integer.valueOf( colorStr.substring( 0, 2 ), 16 )+","+
	            Integer.valueOf( colorStr.substring( 2, 4 ), 16 )+","+
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16 );
	}

}
