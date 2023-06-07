import java.nio.IntBuffer;

import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

public class GUI_Manager implements GLEventListener{

	JFrame mainWin_obj;
	GLJPanel glPane_obj;
	
	int VS_SINGLE = -1;
	int FS_SINGLE = -1;
	int PRG_SINGLE = -1;
	
	// constructor
	public GUI_Manager()
	{
		//
		System.out.println("GUI_Manager init, GUI creation start.");
		
		// Create JFrame
		mainWin_obj = new JFrame("3DP-DP");
		mainWin_obj.setSize(1000, 650);
		mainWin_obj.setBounds(200, 200, 1000, 650);
		mainWin_obj.setResizable(false);
		mainWin_obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWin_obj.setLayout(null);
		mainWin_obj.setVisible(true);

		// create GLJPanel
		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		glPane_obj = new GLJPanel(caps);
		
		// add event GLEvent Listener
		glPane_obj.addGLEventListener(this);
		//glPane_obj.setAutoSwapBufferMode(true);
		
		// add GLJpanel to main window
		mainWin_obj.getContentPane().add(glPane_obj);
		glPane_obj.setBounds(200, 0, 800, 650);
		
		
		// show window ( if show window later, layout breaks down )
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		System.out.println("GLListener display");
		
		GL4 GL = arg0.getContext().getGL().getGL4();
		
		// set view port
		
		// clear buffer
		GL.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		GL.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		
		// finish
		GL.glFinish();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		System.out.println("GLListener init");
		GL4 GL = arg0.getContext().getGL().getGL4();
		
		this.print_GL_capabilities(GL);
		this.setup_status(GL);
		this.setup_shader(GL);
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
	
	///////////////////////////
	void print_GL_capabilities(GL4 GL)
	{
		System.out.println(GL.glGetString(GL.GL_VERSION));
		System.out.println(GL.glGetString(GL.GL_RENDERER));
		System.out.println(GL.glGetString(GL.GL_SHADING_LANGUAGE_VERSION));
		
		IntBuffer tempBuf = IntBuffer.allocate(2);
		GL.glGetIntegerv(GL4.GL_MAX_VERTEX_ATTRIBS, tempBuf);
		System.out.println("max_vertex_attribs:" + tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_VARYING_VECTORS, tempBuf);
		System.out.println("max_varying_vectors:"+tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_VERTEX_UNIFORM_VECTORS, tempBuf);
		System.out.println("max_vertex_uniform_vectors:"+tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_FRAGMENT_UNIFORM_VECTORS, tempBuf);
		System.out.println("max_fragment_uniform_vectors:"+tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, tempBuf);
		System.out.println("max_vertex_texture_image_units:"+tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_TEXTURE_IMAGE_UNITS, tempBuf);
		System.out.println("max_texture_image_unit:"+tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_TEXTURE_SIZE, tempBuf);
		System.out.println("max_texture_size:"+tempBuf.get(0));
		
		GL.glGetIntegerv(GL4.GL_MAX_VIEWPORT_DIMS, tempBuf);
		System.out.println("max_viewport:"+tempBuf.get(0)+":"+tempBuf.get(1));
	}
	
	void setup_status(GL4 GL)
	{
		// multisample
		GL.glEnable(GL4.GL_MULTISAMPLE);
		GL.glEnable(GL4.GL_SAMPLE_ALPHA_TO_COVERAGE);
		
		// blend
		GL.glEnable(GL4.GL_BLEND);
		GL.glBlendFunc(GL4.GL_SRC_ALPHA, GL4.GL_ONE_MINUS_SRC_ALPHA);
		
		// cullface
		GL.glEnable(GL4.GL_CULL_FACE);
		GL.glCullFace(GL4.GL_BACK);
		
		// depth test
		GL.glEnable(GL4.GL_DEPTH_TEST);
		GL.glDepthFunc(GL4.GL_LESS);
		
		// pointsize
		GL.glEnable(GL4.GL_PROGRAM_POINT_SIZE);
	}
	
	void setup_shader(GL4 GL)
	{
		VS_SINGLE = GL_shaderLoader_Class.loadShaderSource_And_CompileShader(GL, "SHADER/VS_SINGLE.txt", 0);
		FS_SINGLE = GL_shaderLoader_Class.loadShaderSource_And_CompileShader(GL, "SHADER/FS_SINGLE.txt", 2);
		PRG_SINGLE = GL_shaderLoader_Class.createProgram_And_AttachShader(GL, VS_SINGLE, -1, FS_SINGLE);
	}
}
