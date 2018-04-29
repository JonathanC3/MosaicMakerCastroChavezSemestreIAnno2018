/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;
import domain.Mosaic;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class ProjectData {
    private String path;
    
    public ProjectData() {
        this.path = "Proyects.dat";
    }
    
    public void saveProyect(Mosaic project) throws IOException, ClassNotFoundException{
        File file= new File(this.path);
        List<Mosaic> objetoList= new ArrayList<Mosaic>();
        
        if(file.exists()){
            ObjectInputStream objectInputStream= new ObjectInputStream(new FileInputStream(file));
            Object aux= objectInputStream.readObject();
            objetoList=(List<domain.Mosaic>) aux;
            objectInputStream.close();
        }
        
        objetoList.add(project);
        ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream(file));
        output.writeUnshared(objetoList);
        output.close();
    }
    
     public ArrayList<Mosaic> loadProyect() throws IOException, ClassNotFoundException{
        File myFile =new File(this.path);
        ArrayList<Mosaic> objetoList= new ArrayList<Mosaic>();
        if(myFile.exists()){
            ObjectInputStream ObjectinputStream=new ObjectInputStream(new FileInputStream(myFile));
            Object aux= ObjectinputStream.readObject();
            objetoList=(ArrayList<domain.Mosaic>) aux;
            ObjectinputStream.close();
        }//If       
           
        return objetoList;
    }//obtieneObjeto
}
