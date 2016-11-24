package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;

public class Scena {
    public void Rysuj(GL gl, Texture t1, Texture t2, Texture t3){
        //szescian
        gl.glColor3f(1.0f,1.0f,1.0f);
        //za³adowanie tekstury wczytanej wczeœniej z pliku krajobraz.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t1.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
        //œciana przednia
        gl.glNormal3f(0.0f,0.0f,-1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,100.0f);
        //œciana tylnia
        gl.glNormal3f(0.0f,0.0f,1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,-100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,-100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,-100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,-100.0f);
        //œciana lewa
        gl.glNormal3f(1.0f,0.0f,0.0f);
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,-100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(-100.0f,100.0f,100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,100.0f);
        gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-100.0f,-100.0f,-100.0f);
        //œciana prawa
        gl.glNormal3f(-1.0f,0.0f,0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,-100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(100.0f,-100.0f,100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,-100.0f);
        gl.glEnd();

        //œciana dolna
        //za³adowanie tekstury wczytanej wczeœniej z pliku niebo.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
        //ustawienia aby tekstura siê powiela³a
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f,1.0f,0.0f);
        //koordynaty ustawienia 16 x 16 kwadratów powielonej tekstury na œcianie dolnej
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,-100.0f,100.0f);
        gl.glTexCoord2f(0.0f, 16.0f);gl.glVertex3f(100.0f,-100.0f,-100.0f);
        gl.glTexCoord2f(16.0f, 16.0f);gl.glVertex3f(-100.0f,-100.0f,-100.0f);
        gl.glTexCoord2f(16.0f, 0.0f);gl.glVertex3f(-100.0f,-100.0f,100.0f);
        gl.glEnd();

        //œciana gorna
        //za³adowanie tekstury wczytanej wczeœniej z pliku trawa.bmp
        gl.glBindTexture(GL.GL_TEXTURE_2D, t3.getTextureObject());
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f,-1.0f,0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);gl.glVertex3f(-100.0f,100.0f,100.0f);
        gl.glTexCoord2f(1.0f, 1.0f);gl.glVertex3f(-100.0f,100.0f,-100.0f);
        gl.glTexCoord2f(1.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,-100.0f);
        gl.glTexCoord2f(0.0f, 0.0f);gl.glVertex3f(100.0f,100.0f,100.0f);
        gl.glEnd();
    }
}
