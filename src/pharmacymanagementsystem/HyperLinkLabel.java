/*
 * The MIT License
 *
 * Copyright 2014 Shiv Ji.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package pharmacymanagementsystem;


import java.awt.Color;  
 import java.awt.Cursor;  
 import java.awt.Desktop;  
 import java.awt.event.MouseAdapter;  
 import java.awt.event.MouseEvent;  
 import java.io.IOException;  
 import java.net.URI;  
 import java.net.URISyntaxException;  
 import java.util.logging.Level;  
 import java.util.logging.Logger;  
 import javax.swing.JLabel; 
import javax.swing.*;

 public class HyperLinkLabel extends JLabel {  
   public HyperLinkLabel(final main url, final String anchor) {  
     this.setText(anchor);  
     this.setForeground(Color.BLUE);  
     this.setCursor(new Cursor(Cursor.HAND_CURSOR));  
     this.addMouseListener(new MouseAdapter() {  
       @Override  
       public void mouseReleased(MouseEvent e) { 
           Object d=new Object();
         if(url.equals(d)){
         try {  
          url.setVisible(true);
         } catch (Exception ex) {  
            ex.getStackTrace();
         }  
       }
              
       }  
       @Override  
       public void mouseEntered(MouseEvent e) {  
         setText("<html><u>" + anchor + "</u></html>");  
       }  
       @Override  
       public void mouseExited(MouseEvent e) {  
         setText(anchor);  
       }  
     });  
   }  
 }  