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
    private static float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat�o otaczaj�ce
    private static float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//?wiat�o rozproszone
    private static float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //?wiat�o odbite
    private static float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };//pozycja ?wiat�a
    
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
        
                //Obs�uga klawiszy strza�ek
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
        
        
        //warto?ci sk�adowe o?wietlenia i koordynaty ?r�d�a ?wiat�a
        float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };//swiat�o otaczaj�ce
        float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };//?wiat�o rozproszone
        float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f}; //?wiat�o odbite
        float lightPos[] = { 0.0f, 150.0f, 150.0f, 1.0f };//pozycja ?wiat�a
        //(czwarty parametr okre?la odleg�o?� ?r�d�a:
        //0.0f-niesko�czona; 1.0f-okre?lona przez pozosta�e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametr�w ?r�d�a ?wiat�a nr. 0
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat�o otaczaj�ce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?wiat�o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //?wiat�o odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja ?wiat�a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?r�d�a ?wiat�a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolor�w
        //kolory b�d� ustalane za pomoc� glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasno?ci i odblaskowo?ci obiekt�w
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
        
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,ambientLight,0); //swiat�o otaczaj�ce
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,diffuseLight,0); //?wiat�o rozproszone
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR,specular,0); //?wiat�o odbite
        gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION,lightPos,0); //pozycja ?wiat�a
        
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuni�cie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wok� osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wok� osi Y
        
        
//        walec(gl);
//        gl.glTranslatef(0.0f, 0.0f, -1.0f);
//        stozek(gl);
//        gl.glTranslatef(0.0f, 0.0f, -1.4f);
//        gl.glScalef(0.5f, 0.5f, 0.5f);
//        stozek(gl);
//        gl.glTranslatef(0.0f, 0.0f, -1.6f);
//        gl.glScalef(0.5f, 0.5f, 0.5f);
//        stozek(gl);
        
        for(int i = 1; i <= 3; i++){
            gl.glPushMatrix();
            for(int j = 1; j <= 10; j++){
                gl.glTranslatef(0.0f, 2.0f, 0.0f);
                drzewo(gl);
            }
            gl.glPopMatrix();
            gl.glTranslatef(-2.0f, 0.0f, 0.0f);
        }
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    void walec(GL gl){
        //wywo�ujemy automatyczne normalizowanie normalnych
        //bo operacja skalowania je zniekszta�ci
        gl.glEnable(GL.GL_NORMALIZE);
        gl.glColor3f(150.0f/255,75.0f/255,0.0f/255);
        float x,y,kat;
        gl.glBegin(GL.GL_QUAD_STRIP);
        for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f)){
        x = 0.5f*(float)Math.sin(kat);
        y = 0.5f*(float)Math.cos(kat);
        gl.glNormal3f((float)Math.sin(kat),(float)Math.cos(kat),0.0f);
        gl.glVertex3f(x, y, -1.0f);
        gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        
        gl.glNormal3f(0.0f,0.0f,-1.0f);
        x=y=kat=0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -1.0f); //srodek kola
        for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f)){
        x = 0.5f*(float)Math.sin(kat);
        y = 0.5f*(float)Math.cos(kat);
        gl.glVertex3f(x, y, -1.0f);
        }
        gl.glEnd();
        
        gl.glNormal3f(0.0f,0.0f,1.0f);
        x=y=kat=0.0f;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for(kat = 2.0f*(float)Math.PI; kat > 0.0f ; kat -= (Math.PI/32.0f)){
        x = 0.5f*(float)Math.sin(kat);
        y = 0.5f*(float)Math.cos(kat);
        gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }
        
    void stozek(GL gl){
        //wywo�ujemy automatyczne normalizowanie normalnych
        gl.glEnable(GL.GL_NORMALIZE);
        gl.glColor3f(0.0f/255,102.0f/255,51.0f/255);
        float x,y,kat;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(0.0f, 0.0f, -2.0f); //wierzcholek stozka
        for(kat = 0.0f; kat < (2.0f*Math.PI); kat += (Math.PI/32.0f)){
            x = (float)Math.sin(kat);
            y = (float)Math.cos(kat);
            gl.glNormal3f((float)Math.sin(kat),(float)Math.cos(kat), -2.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f,0.0f,1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //srodek kola
        for(kat = 2.0f*(float)Math.PI; kat > 0.0f; kat -= (Math.PI/32.0f)){
            x = (float)Math.sin(kat);
            y = (float)Math.cos(kat);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
    }
    
    void drzewo(GL gl){
        gl.glPushMatrix();
        walec(gl);
        
        gl.glTranslatef(0.0f, 0.0f, -1.0f);
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, -1.4f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        
        stozek(gl);
        gl.glTranslatef(0.0f, 0.0f, -1.6f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        
        stozek(gl);
        gl.glPopMatrix();
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}