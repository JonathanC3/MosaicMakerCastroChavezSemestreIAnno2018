/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import domain.Mosaic;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author faubricioch
 */
public class MosaicFile {
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;//cantidad de registros en el archivo
    private int regSize;//tamanno del registro
    private String myFilePath;//ruta

    public MosaicFile(File file) throws IOException{
        //almaceno la ruta
        myFilePath = file.getPath();
        
        //indico el tamanno m'aximo
        this.regSize = 300;
        
        //una validaci'on sencilla
        if(file.exists() && !file.isFile()){
            throw new IOException(file.getName() + " is an invalid file");
        }else{
            //crear la nueva instancia de RAF
            randomAccessFile = new RandomAccessFile(file, "rw");
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = 
                    (int)Math.ceil((double)randomAccessFile.length() / (double)regSize);
        }
    }//fin constructor
    
    public void close() throws IOException{
        randomAccessFile.close();
    }
    
    //indicar la cantidad de registros de nuestro archivo
    public int fileSize(){
        return this.regsQuantity;
    }
    
    //insertar un nuevo registro en una posici'on espec'ifica
    public boolean putValue(int position, Mosaic mosaic) throws IOException{
        //primero: verificar que sea v'alida la inserci'on
        if(position < 0 && position > this.regsQuantity){
            System.err.println("1001 - Record position is out of bounds");
            return false;
        }else{
            if(mosaic.sizeInBytes() > this.regSize){
                System.err.println("1002 - Record size is out of bounds");
                return false;
            }else{
                //BINGO
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(mosaic.getName());
                randomAccessFile.writeLong(mosaic.getPixels());
                randomAccessFile.writeUTF(mosaic.getPathImage());
                randomAccessFile.writeUTF(mosaic.getPathMosaic());
                return true;
            }
        }
    }//end method
    
    //insertar al final del archivo
    public boolean addEndRecord(Mosaic mosaic) throws IOException{
        boolean success = putValue(this.regsQuantity, mosaic);
        if(success){
            ++this.regsQuantity;
        }
        return success;
    }
    
    //obtener un mosaico
    public Mosaic getMosaic(int position) throws IOException{
        //validar la posici'on
        if(position >= 0 && position <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            
            //llevamos a cabo la lectura
            Mosaic mosaicTemp = new Mosaic();
            mosaicTemp.setName(randomAccessFile.readUTF());
            mosaicTemp.setPixels(randomAccessFile.readLong());
            mosaicTemp.setPathImage(randomAccessFile.readUTF());
            mosaicTemp.setPathMosaic(randomAccessFile.readUTF());
            
            if(mosaicTemp.getName().equalsIgnoreCase("deleted")){
                return null;
            }else{
                return mosaicTemp;
            }
        }else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    
}
