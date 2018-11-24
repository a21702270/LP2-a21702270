package pt.ulusofona.lp2.crazyChess;

import java.io.File;

public class CrazyPiece {
 int id;
 int idTipoDePeca;
 int idEquipa;
 String alcunha;
 int x;
 int y;
 String filename="pecaDoJogo.png";
 String filename1="pecaDoJogo1.png";

    public CrazyPiece(){
     }

    public int getIdEquipa() {
        return idEquipa;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public int getIdTipoDePeca() {
        return idTipoDePeca;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
         return id;
     }

    public String getImagePNG(){
         if(idEquipa==1){
            return filename ;
         }else{
             return  filename1;
         }
    }

    public String toString(){
        if(x==-1 && y==-1){
            return id + "|" + idTipoDePeca + "|" + idEquipa + "|" + alcunha + "@ (" +  x + "," + y + ")";
        }else{
            return id + "|" + idTipoDePeca + "|" + idEquipa + "|" + alcunha + "@ (n/a)";
        }
    }

 }
