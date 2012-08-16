package hex2bin;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Hex2bin
{
    public static int getHexDigit(char c) throws IOException
    {
        if(c >= '0' && c <= '9')
            return c - '0';
        if(c >= 'A' && c <= 'F')
            return c - 'A'+10;
        throw new IOException("Invalid hex digit: "+c);
    }
    
    public static int getByte(String s, int ind) throws IOException
    {
        return (getHexDigit(s.charAt(ind)) << 4) | 
               (getHexDigit(s.charAt(ind+1)));
    }
    
    public static void convert(File hexfile) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader(hexfile));
        OutputStream out = new FileOutputStream(hexfile.getAbsolutePath()+".bin");
        
        String line;
        
        while((line = in.readLine()) != null)
            for(int i = 0; i < 16; i++)
                if(line.length() > i*3+9)
                    out.write(getByte(line, i*3+9));
        
        in.close();
        out.close();
    }
    public static void main(String[] args)
    {
        JFileChooser fc = new JFileChooser();
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        fc.showDialog(null, "Convert");
        File hexfile = fc.getSelectedFile();
        
        ConvertingFrame f = new ConvertingFrame();
        f.setVisible(true);
        boolean fail = false;
        try
        {
            convert(hexfile);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, 
                    "An error occured while converting: "+ex.getMessage(), 
                    "hex2bin 1.0 by Dirbaio", JOptionPane.ERROR_MESSAGE);
            fail = true;
            System.exit(1);
        }
        f.setVisible(false);
        if(!fail)
            JOptionPane.showMessageDialog(null, 
                    "Conversion finished awesomely!", 
                    "hex2bin 1.0 by Dirbaio", JOptionPane.INFORMATION_MESSAGE);        
        System.exit(0);
    }
}
