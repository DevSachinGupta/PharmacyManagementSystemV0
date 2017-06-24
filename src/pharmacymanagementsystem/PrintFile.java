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

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Sachin Scientist
 */
public class PrintFile {
   
        public PrintFile(JTable table) 
        {
            try {
		File f=new File("Invoice.txt");
		FileWriter fw=new FileWriter(f);
		int row=table.getRowCount();
		int col=table.getColumnCount();
		BufferedWriter bw=new BufferedWriter(fw);
		String str="\n\t\t\t\t\t     ABCD Pharmaceuticals Limited    ";
		String st1="\n\t\t\t\t\t       Available At: XYZ Location     ";
		String st2="\n\t\t\t\t\t           Invoice Generation          ";
		String st3="\n\t----------------------------------------------------------------------------------------------------------------------------------";
		String st4="\n\t NO.\t Product Name \t Manufacturing Company \t Date Of Expiry \t Quantity \t M.R.P. \t Amount";
		bw.write(str);
		bw.newLine();
		bw.write(st1);
		bw.newLine();
		bw.write(st2);
		bw.newLine();
		bw.write(st3);
		bw.newLine();
		bw.write(st4);
		bw.newLine();
		bw.write(st3);
		bw.newLine();
                for(int i=0;i<=row;i++)
                {
                    String temp="\n\t ";
                    bw.write(temp);
                    for(int j=0;j<=col;j++)
                    {
                        String st5=String.valueOf(table.getModel().getValueAt(i,j));
                        bw.write(str+"\t\t");
                    }
                    bw.newLine();
                }
		bw.flush();
		}
		catch(Exception e)
		{
		JOptionPane.showMessageDialog(null,"Caught Exception");
                e.printStackTrace();
		}
        }
}

