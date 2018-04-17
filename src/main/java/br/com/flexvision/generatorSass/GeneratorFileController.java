package br.com.flexvision.generatorSass;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/sass")
public class GeneratorFileController {

	@RequestMapping(value="/", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON)
	public @ResponseBody Boolean save(@RequestBody Parameter p) {
		StringBuilder colorPrimary = generateStrColor(p.getColorPrimary(), "$mat-flex-primary: (");
		StringBuilder colorAccent = generateStrColor(p.getColorSecundary(), "$mat-flex-accent: (");
		
		Path path = Paths.get("flex-var.scss");

		//Use try-with-resource to get auto-closeable writer instance
		try (BufferedWriter writer = Files.newBufferedWriter(path)){
		    writer.write(colorPrimary.toString()+"\n\n"+colorAccent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	private StringBuilder generateStrColor(Color c, String init) {
		StringBuilder color = new StringBuilder(init); 
		String hexVal = c.getHex();
		color.append(" 50: #"+hexVal+",");
		for(int i=100;i<=900;i=i+100) {
			hexVal = shadeRGBColor(hex2Rgb(hexVal), (i/35));
			color.append(" "+i+": #"+hexVal+",\n");
		}
		if(c.getDark()) {
			color.append("	contrast: (\n");
			color.append("		50:  $dark-primary-text,\n");
			color.append("		100: $dark-primary-text,\n");
			color.append("		200: $dark-primary-text,\n");
			color.append("		300: $dark-primary-text,\n");
			color.append("		400: $dark-primary-text,\n");
			color.append("		500: $light-primary-text,\n");
			color.append("		600: $light-primary-text,\n");
			color.append("		700: $light-primary-text,\n");
			color.append("		800: $light-primary-text,\n");
			color.append("		900: $light-primary-text,\n");
			color.append("		A100: $light-primary-text,\n");
			color.append("		A200: $dark-primary-text,\n");
			color.append("		A400: $dark-primary-text,\n");
			color.append("		A700: $dark-primary-text,\n");
			color.append("	)\n");
			color.append(");");
		}else {
			color.append("	contrast: (\n");
			color.append("		50:  $light-primary-text,\n");
			color.append("		100: $light-primary-text,\n");
			color.append("		200: $light-primary-text,\n");
			color.append("		300: $light-primary-text,\n");
			color.append("		400: $light-primary-text,\n");
			color.append("		500: $dark-primary-text,\n");
			color.append("		600: $dark-primary-text,\n");
			color.append("		700: $dark-primary-text,\n");
			color.append("		800: $dark-primary-text,\n");
			color.append("		900: $dark-primary-text,\n");
			color.append("		A100: $dark-primary-text,\n");
			color.append("		A200: $light-primary-text,\n");
			color.append("		A400: $light-primary-text,\n");
			color.append("		A700: $light-primary-text,\n");
			color.append("	)\n");
			color.append(");");

		}
		return color;
	}
	
	public String shadeRGBColor(String color, int percent) {
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
	
	public String hex2Rgb(String colorStr) {
	    return Integer.valueOf( colorStr.substring( 0, 2 ), 16 )+","+
	            Integer.valueOf( colorStr.substring( 2, 4 ), 16 )+","+
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16 );
	}

}
