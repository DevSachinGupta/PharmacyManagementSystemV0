/*
 * The MIT License
 *
 * Copyright 2014 Sachin Scientist.
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

import java.awt.*;  
import java.awt.print.*;  
import java.io.*;
/**
 *
 * @author Sachin Scientist
 */
public class PrintingList {
    public static void main(String[] args)  
  {      
    // Get a PrinterJob 
    PrinterJob job = PrinterJob.getPrinterJob(); 
    // Ask user for page format (e.g., portrait/landscape) 
    PageFormat pf = job.pageDialog(job.defaultPage()); 
    // Specify the Printable is an instance of 
    // PrintListingPainter; also provide given PageFormat 
    job.setPrintable(new PrintListingPainter("Invoice.txt")); 
    // Print 1 copy    
    job.setCopies(1);      
    // Put up the dialog box      
    if (job.printDialog())  
    { 
      // Print the job if the user didn't cancel printing 
      try { job.print(); } 
      catch (Exception e) { /* handle exception */ }      
    }      
    System.exit(0);    
  }  
} 
 
class PrintListingPainter implements Printable  
{ 
  private RandomAccessFile raf;    
  private String fileName;    
  private Font fnt = new Font("Helvetica", Font.PLAIN, 10); 
  private int rememberedPageIndex = -1;    
  private long rememberedFilePointer = -1;    
  private boolean rememberedEOF = false; 
   
  public PrintListingPainter(String file)  
  {  
    fileName = file;      
    try 
    {  
      // Open file       
      raf = new RandomAccessFile(file, "r");      
    }  
    catch (Exception e) { rememberedEOF = true; }    
  } 
 
  public int print(Graphics g, PageFormat pf, int pageIndex) 
  throws PrinterException  
  { 
  try  
  {  
    // For catching IOException      
    if (pageIndex != rememberedPageIndex)  
    {  
      // First time we've visited this page 
      rememberedPageIndex = pageIndex;   
      // If encountered EOF on previous page, done  
      if (rememberedEOF) return Printable.NO_SUCH_PAGE; 
      // Save current position in input file 
      rememberedFilePointer = raf.getFilePointer(); 
    }  
    else raf.seek(rememberedFilePointer); 
    g.setColor(Color.black);      
    g.setFont(fnt);  
        int x = (int) pf.getImageableX() + 10; 
        int y = (int) pf.getImageableY() + 12;     
    // Title line      
    g.drawString("File: " + fileName + ", page: " +                                          
(pageIndex+1),  x, y); 
    // Generate as many lines as will fit in imageable area 
    y += 36; 
    while (y + 12 < pf.getImageableY()+pf.getImageableHeight()) 
    { 
      String line = raf.readLine(); 
      if (line == null) 
      {  
        rememberedEOF = true; 
        break;  
                } 
        g.drawString(line, x, y);  
        y += 12;      
      } 
      return Printable.PAGE_EXISTS;     
    }  
    catch (Exception e) { return Printable.NO_SUCH_PAGE;} 
  }
}
