import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jogamp.opengl.GL4;

public class GL_shaderLoader_Class {
	
	public GL_shaderLoader_Class()
	{
		System.out.println("GL_shaderLoader_Class init");
		
	}
	
	public static int loadShaderSource_And_CompileShader(GL4 ctx4, String fileName, int type)
	{
		int sh_name = -1;
		
		
		// create object
		switch(type)
		{
		case 0:
			sh_name = ctx4.glCreateShader(GL4.GL_VERTEX_SHADER);
			System.out.println("VShader:"+sh_name);
			break;
		case 1:
			sh_name = ctx4.glCreateShader(GL4.GL_GEOMETRY_SHADER);
			System.out.println("GShader:"+sh_name);
			break;
		case 2:
			sh_name = ctx4.glCreateShader(GL4.GL_FRAGMENT_SHADER);
			System.out.println("FShader:"+sh_name);
			break;
		case 3:
			sh_name = ctx4.glCreateShader(GL4.GL_COMPUTE_SHADER);
			System.out.println("CShader:"+sh_name);
			break;
		default:
			System.out.println("Shader type is invalid...");
			return -1;
		}
		
		
		// supply shader source ******************************
		try {

			// get shader string
			String shaderSource = new String(Files.readAllBytes(Paths.get(fileName)));
		
			// supply shader source as String Array[1]
			ctx4.glShaderSource(sh_name, 1, new String[] {shaderSource}, null);
			
			// compileShader
			ctx4.glCompileShader(sh_name);
			
			// error check
			IntBuffer logLength = IntBuffer.allocate(1);
			ctx4.glGetShaderiv(sh_name, GL4.GL_INFO_LOG_LENGTH, logLength);
			
			if(logLength.get(0) > 1)
			{
				// alloc buffer
				ByteBuffer logBuf = ByteBuffer.allocate(logLength.get(0));
			
				// get error log
				ctx4.glGetShaderInfoLog(sh_name, logLength.get(0), null, logBuf);
			
				// get log into array
				byte[] log = new byte[logLength.get(0)];
				logBuf.get(log);
				
				// convert to string
				String logStr = new String(log, StandardCharsets.UTF_8);
			
				// print
				System.out.println(logStr);
				
				// free
				logBuf.clear();
				logBuf = null;
			}
			else
			{
				System.out.println("compile SUCCESS!");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
				
		return sh_name;
	}
	
	
	public static int createProgram_And_AttachShader(GL4 ctx4, int vs, int gs, int fs)
	{
		int PRG = ctx4.glCreateProgram();
		
		if(vs != -1)
		{
			ctx4.glAttachShader(PRG, vs);
		}
		if(gs != -1)
		{
			ctx4.glAttachShader(PRG, gs);
		}
		if(fs != -1)
		{
			ctx4.glAttachShader(PRG, fs);
		}
		
		// link program
		ctx4.glLinkProgram(PRG);
		
		// check error
		IntBuffer status = IntBuffer.allocate(1);
		ctx4.glGetProgramiv(PRG, GL4.GL_LINK_STATUS, status);
		
		if(status.get(0) == GL4.GL_FALSE)
		{
			System.out.println("Program link ERROR...");
		}
		else if(status.get(0) == GL4.GL_TRUE)
		{
			System.out.println("Program Link SUCCESS!");
		}
		
		return PRG;
	}
	
	
}
