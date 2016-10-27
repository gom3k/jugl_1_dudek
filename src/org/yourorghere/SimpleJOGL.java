package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.*;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



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
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);
        
        
        //warto?ci sk³adowe o?wietlenia i koordynaty ?ród³a ?wiat³a
        float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat³o otaczajšce
        float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//?wiat³o rozproszone
        float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //?wiat³o odbite
        float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };//pozycja ?wiat³a
        //(czwarty parametr okre?la odleg³o?æ ?ród³a:
        //0.0f-nieskoñczona; 1.0f-okre?lona przez pozosta³e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametrów ?ród³a ?wiat³a nr. 0
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat³o otaczajšce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?wiat³o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //?wiat³o odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja ?wiat³a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?ród³a ?wiat³a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolorów
        //kolory bêdš ustalane za pomocš glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasno?ci i odblaskowo?ci obiektów
        float specref[] = { 1.0f, 1.0f, 1.0f, 1.0f }; //parametry odblaskowo?ci
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR,specref,0);
        
        gl.glMateriali(GL.GL_FRONT,GL.GL_SHININESS,128);

        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
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
        glu.gluPerspective(100.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    private float[] WyznaczNormalna(float[] punkty, int ind1, int ind2, int ind3){
        float[] norm = new float[3];
        float[] wektor0 = new float[3];
        float[] wektor1 = new float[3];

        for(int i=0;i<3;i++)
        {
        wektor0[i]=punkty[i+ind1]-punkty[i+ind2];
        wektor1[i]=punkty[i+ind2]-punkty[i+ind3];
        }

        norm[0]=wektor0[1]*wektor1[2]-wektor0[2]*wektor1[1];
        norm[1]=wektor0[2]*wektor1[0]-wektor0[0]*wektor1[2];
        norm[2]=wektor0[0]*wektor1[1]-wektor0[1]*wektor1[0];
        float d=
       (float)Math.sqrt((norm[0]*norm[0])+(norm[1]*norm[1])+ (norm[2]*norm[2]) );
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

        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuniêcie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó³ osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó³ osi Y

//        // --------------------------- 3D ostros³up o podstawie kwadratu
//        
//        //?ciana dolna (czerwony)
//        gl.glBegin(GL.GL_QUADS);
//        gl.glColor3f(1.0f,0.0f,0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glEnd();
//        //?ciana lewa (niebieski)
//        gl.glBegin(GL.GL_TRIANGLES);
//        gl.glColor3f(1.0f,0.0f,0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,1.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(0.0f,0.5f,0.0f);
//        gl.glEnd();
//        //?ciana prawa (zielony)
//        gl.glBegin(GL.GL_TRIANGLES);
//        gl.glColor3f(1.0f,0.0f,0.0f);
//        gl.glVertex3f(1.0f,-1.0f,1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(0.0f,0.5f,0.0f);
//        gl.glEnd();
//        //?ciana przednia (zó?ty)
//        gl.glBegin(GL.GL_TRIANGLES);
//        float[] scianka1={-1.0f, -1.0f, 1.0f, //wpó³rzêdne pierwszego punktu
//                            1.0f, -1.0f, 1.0f, //wspó³rzêdne drugiego punktu
//                            0.0f, 0.5f, 0.0f}; //wspó³rzêdne trzeciego punktu
//        float[] normalna1 = WyznaczNormalna(scianka1,0,3,6);
//        gl.glNormal3fv(normalna1,0);
//        gl.glVertex3fv(scianka1,0); //wspó³rzêdne 1-go punktu zaczynaj¹ siê od indeksu 0
//        gl.glVertex3fv(scianka1,3); //wspó³rzêdne 2-go punktu zaczynaj¹ siê od indeksu 3
//        gl.glVertex3fv(scianka1,6); //wspó³rzêdne 3-go punktu zaczynaj¹ siê od indeksu 6
//        gl.glEnd();
//        //?ciana tylna (zó?ty)
//        gl.glBegin(GL.GL_TRIANGLES);
//        gl.glColor3f(1.0f,0.0f,0.0f);
//        gl.glVertex3f(-1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(1.0f,-1.0f,-1.0f);
//        gl.glVertex3f(0.0f,0.5f,0.0f);
//        gl.glEnd();
        
        // ---------------------------- 3D walec
        //gorna
        float x, y, z, kat;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(1.0f,1.0f,0.0f);
        gl.glVertex3f(0.0f, 2.0f, 0.0f);
        for(kat = 0.0f; kat < (2.0f*Math.PI); kat+=(Math.PI/32.0f)){
                x = 1.5f * (float)Math.sin(kat);
                y = 0.0f * (float)Math.cos(kat) + 2.0f;
                z = 1.5f * (float)Math.cos(kat);
                gl.glVertex3f(x, y, z); //kolejne punkty
        }
        gl.glEnd();
        
        //dolna
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(0.0f, 1.0f,0.0f);
        gl.glVertex3f(0.0f, -2.0f, 0.0f);
        for(kat = 0.0f; kat < (2.0f*Math.PI); kat+=(Math.PI/32.0f)){
                x = 1.5f * (float)Math.sin(kat);
                y = 0.0f * (float)Math.cos(kat) - 2.0f;
                z = 1.5f * (float)Math.cos(kat);
                gl.glVertex3f(x, y, z); //kolejne punkty
        }
        gl.glEnd();
        
        //sciana
        gl.glBegin(GL.GL_QUAD_STRIP);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        for(kat = 0.0f; kat < (2.0f*Math.PI); kat+=(Math.PI/32.0f)){
            x = 1.5f * (float)Math.sin(kat);
            z = 1.5f * (float)Math.cos(kat);
            gl.glVertex3f(x, -2.0f, z); //kolejne punkty
            gl.glVertex3f(x, 2.0f, z);
        }
        gl.glEnd();
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}