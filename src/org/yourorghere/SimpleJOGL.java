package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.*;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


/**
 * SimpleJOGL.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class SimpleJOGL implements GLEventListener {
    private static float xrot = 0.0f, yrot = 0.0f;
    private static float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat³o otaczajšce
    private static float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//?wiat³o rozproszone
    private static float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //?wiat³o odbite
    private static float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };//pozycja ?wiat³a
    static BufferedImage image1 = null, image2 = null, image3 = null;
    static Texture t1 = null, t2 = null, t3 = null;
    static Koparka koparka;
    static Scena scena;
    static float x, z, c, v;
    int i = 0;
    
    static void przesun(float d){
        x-=d*Math.sin(yrot*(3.14f/180.0f));
        z+=d*Math.cos(yrot*(3.14f/180.0f));
    }
    
    static void przesunKoparki(float d){
        c-=d*Math.sin(yrot*(3.14f/180.0f));
        v+=d*Math.cos(yrot*(3.14f/180.0f));
    }
    
        
    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();
        



        canvas.addGLEventListener(new SimpleJOGL());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        
        //Obs³uga klawiszy strza³ek
        frame.addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                xrot -= 5.0f;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                xrot += 5.0f;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                yrot += 5.0f;
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                yrot -= 5.0f;
                
                if(e.getKeyChar() == 'q')
                    ambientLight = new float[] {ambientLight[0]-0.1f, ambientLight[0]-0.1f, ambientLight[0]-0.1f};
                if(e.getKeyChar() == 'w')
                    ambientLight = new float[] {ambientLight[0]+0.1f, ambientLight[0]+0.1f, ambientLight[0]+0.1f};
                if(e.getKeyChar() == 'a')
                    diffuseLight = new float[] {diffuseLight[0]-0.1f, diffuseLight[0]-0.1f, diffuseLight[0]-0.1f};
                if(e.getKeyChar() == 's')
                    diffuseLight = new float[] {diffuseLight[0]+0.1f, diffuseLight[0]+0.1f, diffuseLight[0]+0.1f};
                if(e.getKeyChar() == 'z')
                    specular = new float[] {specular[0]-0.1f, specular[0]-0.1f, specular[0]-0.1f,1};
                if(e.getKeyChar() == 'x')
                    specular = new float[] {specular[0]+0.1f, specular[0]+0.1f, specular[0]+0.1f,1};
                if(e.getKeyChar() == 'n')
                    lightPos[3] = 0;
                if(e.getKeyChar() == 'm')
                    lightPos[3] = 1;
                
                //koparka
                if(e.getKeyCode() == KeyEvent.VK_1 && koparka.kat1 <= 30.0f)
                    koparka.ZmienKat1(1.0f);
                if(e.getKeyCode() == KeyEvent.VK_2 && koparka.kat1 >= -45.0f)
                    koparka.ZmienKat1(-1.0f);
                if(e.getKeyCode() == KeyEvent.VK_3 && koparka.kat2 <= 80.0f)
                    koparka.ZmienKat2(1.0f);
                if(e.getKeyCode() == KeyEvent.VK_4 && koparka.kat2 >= -65.0f)
                    koparka.ZmienKat2(-1.0f);
                if(e.getKeyCode() == KeyEvent.VK_5 && koparka.kat3 <= 60.0f)
                    koparka.ZmienKat3(1.0f);
                if(e.getKeyCode() == KeyEvent.VK_6 && koparka.kat3 >= -50.0f)
                    koparka.ZmienKat3(-1.0f);
                if(e.getKeyCode() == KeyEvent.VK_7)
                    koparka.ZmienKat4(1.0f);
                if(e.getKeyCode() == KeyEvent.VK_8)
                    koparka.ZmienKat4(-1.0f);
                
                //przesunac kamery po scenie
                if(e.getKeyChar() == 'y' && z <= 95.0f && z >= -95.0f && x <= 95.0f && x >= -95.0f)
                    przesun(1.0f);
                if(e.getKeyChar() == 'h')
                    przesun(-1.0f); 
                
                if(e.getKeyChar() == 'u')
                    przesunKoparki(-1.0f); 
                if(e.getKeyChar() == 'j')
                    przesunKoparki(1.0f); 
            }
            public void keyReleased(KeyEvent e){}
            public void keyTyped(KeyEvent e){}
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        gl.setSwapInterval(1);

        //wartoœci sk³adowe oœwietlenia i koordynaty Ÿród³a œwiat³a
        float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat³o otaczaj¹ce
        float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//œwiat³o rozproszone
        float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //œwiat³o odbite
        float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };//pozycja œwiat³a
        //(czwarty parametr okreœla odleg³oœæ Ÿród³a:
        //0.0f-nieskoñczona; 1.0f-okreœlona przez pozosta³e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie oœwietlenia
        //ustawienie parametrów Ÿród³a œwiat³a nr. 0
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat³o otaczaj¹ce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //œwiat³o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //œwiat³o odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja œwiat³a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie Ÿród³a œwiat³a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie œledzenia kolorów
        //kolory bêd¹ ustalane za pomoc¹ glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
        
        try{
            image1 = ImageIO.read(getClass().getResourceAsStream("/bok.jpg"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/trawa.jpg"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/niebo.jpg"));
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, exc.toString());
            return;
        }

        t1 = TextureIO.newTexture(image1, false);
        t2 = TextureIO.newTexture(image2, false);
        t3 = TextureIO.newTexture(image3, false);

        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND | GL.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        scena = new Scena();
        koparka = new Koparka();
   }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        
        if (height <= 0) { // avoid a divide by zero error!
            height = 1;
        }
        
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(100.0f, h, 0.1f, 300.0f); //Przy koparce perspektywa
//        float ilor;
//        if(width<=height){
//            ilor = height/width;
//            gl.glOrtho(-10.0f,10.0f,-10.0f*ilor,10.0f*ilor,-10.0f,10.0f);
//        }else{
//            ilor = width/height;
//            gl.glOrtho(-10.0f*ilor,10.0f*ilor,-10.0f,10.0f,-10.0f,10.0f);
//        }
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    private float[] WyznaczNormalna(float[] punkty, int ind1, int ind2, int ind3){
        float[] norm = new float[3];
        float[] wektor0 = new float[3];
        float[] wektor1 = new float[3];

        for(int i=0;i<3;i++){
            wektor0[i]=punkty[i+ind1]-punkty[i+ind2];
            wektor1[i]=punkty[i+ind2]-punkty[i+ind3];
        }
        
        norm[0]=wektor0[1]*wektor1[2]-wektor0[2]*wektor1[1];
        norm[1]=wektor0[2]*wektor1[0]-wektor0[0]*wektor1[2];
        norm[2]=wektor0[0]*wektor1[1]-wektor0[1]*wektor1[0];
        float d = (float)Math.sqrt((norm[0]*norm[0])+(norm[1]*norm[1])+ (norm[2]*norm[2]));
        if(d==0.0f)
        d=1.0f;
        norm[0]/=d;
        norm[1]/=d;
        norm[2]/=d;

        return norm;
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat³o otaczajšce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?wiat³o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //?wiat³o odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja ?wiat³a
        
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó³ osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó³ osi Y
        gl.glTranslatef(0.0f, 94.0f, 1.0f); //przesuniêcie o 6 jednostek
        
        gl.glTranslatef(x, 0, z);
        gl.glTranslatef(c, 0, v);
        scena.Rysuj(gl,t1,t2,t3);
        
        gl.glTranslatef(-x, -95.0f, -z);
        koparka.Rysuj(gl);
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    void glOrtho(double left, double right, double bottom, double top, double zNear, double zFar){
        
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}