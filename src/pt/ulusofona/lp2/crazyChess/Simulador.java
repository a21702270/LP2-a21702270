package pt.ulusofona.lp2.crazyChess;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static java.lang.Math.*;

public class Simulador {
    int turnos;
    int jogadasPretas;
    int jogadasBrancas;
    int jogadasInvalidasPretas;
    int jogadasInvalidasBrancas;
    int jogadasValidasPretas;
    int jogadasValidasBrancas;
    int comidasPretas;
    int comidasBrancas;
    int pecasPretas;
    int pecasBrancas;
    int tamanhoDoTabuleiro;
    int quantasPecas;
    int secoes;
    int count;
    int tabuleiro[][];
    int jogadasSeguidas;
    int xO, yO, xD, Yd;
    List<CrazyPiece> utilizadores;

    public String vencedor(int comidasBrancas ,int comidasPretas,int pecasBrancas, int pecasPretas){
        if(comidasPretas==pecasPretas){
            return "VENCERAM AS BRANCAS";
        }else if(comidasBrancas==pecasBrancas){
            return "VENCERAM AS PRETAS";
        }
        return "EMPATE";
    }

    public void setUtilizadores(List<CrazyPiece> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public Simulador() {
    }

    public boolean iniciaJogo(File ficheiroInicial) {
        try {
            Scanner leitorFicheiro = new Scanner(ficheiroInicial);
            int linhasDaMatriz=0;
            pecasPretas=0;
            pecasBrancas=0;
            count=0;
            turnos=0;
            jogadasPretas=0;
            jogadasBrancas=0;
            jogadasInvalidasPretas=0;
            jogadasInvalidasBrancas=0;
            jogadasValidasPretas=0;
            jogadasValidasBrancas=0;
            secoes=1;
            pecasPretas=0;
            pecasBrancas=0;
            comidasPretas=0;
            comidasBrancas=0;
            jogadasSeguidas=0;
            utilizadores = new ArrayList<>();
            while (leitorFicheiro.hasNextLine()) {
                String linha = leitorFicheiro.nextLine();
                if (secoes == 1) {
                    tamanhoDoTabuleiro = Integer.parseInt(linha);
                    tabuleiro= new int[tamanhoDoTabuleiro][tamanhoDoTabuleiro];
                    secoes = 2;
                } else if (secoes == 2) {
                     quantasPecas = Integer.parseInt(linha);
                     secoes = 3;
                } else if (secoes == 3) {
                    CrazyPiece peca = new CrazyPiece();
                    String dados[] = linha.split(":");
                    peca.id= Integer.parseInt(dados[0]);
                    peca.idTipoDePeca = Integer.parseInt(dados[1]);
                    peca.idEquipa = Integer.parseInt(dados[2]);
                    peca.alcunha = dados[3];
                    utilizadores.add(peca);
                    if(peca.idEquipa==1){
                        pecasPretas++;
                    }else{
                        pecasBrancas++;
                    }
                    count++;
                    if (count == quantasPecas) {
                        secoes = 4;
                    }
                } else if (secoes == 4) {
                    CrazyPiece piece;
                    for(int i=0;i<tamanhoDoTabuleiro;i++){
                        String dados[] = linha.split(":");
                        tabuleiro[i][linhasDaMatriz] = Integer.parseInt(dados[i]);
                        for(int g=0; g<quantasPecas;g++){
                            if(utilizadores.get(g).id==Integer.parseInt(dados[i])){
                                piece=utilizadores.get(g);
                                piece.setX(i);
                                piece.setY(linhasDaMatriz);
                            }
                        }
                    }linhasDaMatriz++;
                }
            }
            leitorFicheiro.close();
            return true;
        } catch (FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + ficheiroInicial + "nao foi encontrado.";
            System.out.println(mensagem);
            return false;
        }
    }

    public int getTamanhoTabuleiro() {
        return tamanhoDoTabuleiro;
    }

    public boolean processaJogada(int xO, int yO, int xD, int Yd){
        for(int c=0;c<utilizadores.size();c++){
            if(utilizadores.get(c).getX()==xO && utilizadores.get(c).getY()==yO){
                for(int i=0; i<utilizadores.size();i++){
                if(utilizadores.get(i).getX()==xD && utilizadores.get(i).getY()==Yd ){
                    if( utilizadores.get(c).getIdEquipa()==utilizadores.get(i).getIdEquipa()){
                        if(utilizadores.get(c).getIdEquipa()==1){
                            jogadasInvalidasPretas++;
                        }else{
                            jogadasInvalidasBrancas++;
                        }
                        return false;
                    }
                }
            }
                if ((abs(xD - xO) > 1 || abs(Yd - yO) > 1) || utilizadores.get(c).getIdEquipa()!=turnos || (xD < 0 && xD < tamanhoDoTabuleiro - 1) || (Yd < 0 && Yd < tamanhoDoTabuleiro - 1)) {
                    if(turnos==0){
                        jogadasInvalidasPretas++;
                    }else{
                        jogadasInvalidasBrancas++;
                    }
                    return false;
                } else{
                    for(int g=0; g<utilizadores.size();g++){
                        if(utilizadores.get(g).getX()==xD && utilizadores.get(g).getY()==Yd){
                            utilizadores.get(g).setX(-1);
                            utilizadores.get(g).setY(-1);
                        if(utilizadores.get(g).getIdEquipa()==0){
                            comidasPretas++;
                        }else{
                            comidasBrancas++;
                        }
                        jogadasSeguidas=0;
                    }
                        if(utilizadores.get(g).getX()==xO && utilizadores.get(g).y==yO){
                            tabuleiro[xD][Yd]=tabuleiro[xO][yO];
                            tabuleiro[xO][yO]=0;
                            utilizadores.get(g).setX(xD);
                            utilizadores.get(g).setY(Yd);
                            jogadasSeguidas++;
                        }
                    }
                    if(turnos==0){
                        turnos=1;
                        jogadasPretas++;
                    }else{
                        jogadasBrancas++;
                        turnos=0;
                    }
                    if(utilizadores.get(c).getIdEquipa()==0){
                        jogadasValidasPretas++;
                    }else{
                        jogadasValidasBrancas++;
                    }

            }
          }

        }
        // verefica se o que se esta a tentar mover tem la alguma peça
        if(tabuleiro[xO][yO]==0) {
            if(turnos==1){
                jogadasInvalidasBrancas++;
            }else{
                jogadasInvalidasPretas++;
            }
            return false;
        }
        return true;
    }

    public boolean jogoTerminado() {
        if(comidasPretas==pecasPretas || comidasBrancas==pecasBrancas || jogadasSeguidas==10 || (pecasPretas-comidasPretas==1 && pecasBrancas-comidasBrancas==1)){
            return true;
        }
        return false;
    }

    public List<String> getAutores() {
        List<String> autores = new ArrayList<>();
        autores.add("Diogo Agostinho");
        return autores;

    }

    public List<CrazyPiece> getPecasMalucas() {
        return utilizadores;
    }

    public List<String> getResultados() {
            List<String> autores2 = new ArrayList<>();
            autores2.add("JOGO DE CRAZY CHESS");
            autores2.add("Resultado:"+ vencedor( comidasBrancas ,comidasPretas,pecasBrancas, pecasPretas));
            autores2.add("Equipa Preta:");
            autores2.add(Integer.toString(comidasBrancas));
            autores2.add(Integer.toString(jogadasValidasPretas));
            autores2.add(Integer.toString(jogadasInvalidasPretas));
            autores2.add("Equipa Branca:");
            autores2.add(Integer.toString(comidasPretas));
            autores2.add(Integer.toString(jogadasValidasBrancas));
            autores2.add(Integer.toString(jogadasInvalidasBrancas));
            return autores2;

    }

    public int getIDPeca(int x, int y) {
        for (int i=0; i<utilizadores.size();i++) {
            if (utilizadores.get(i).getX()== x && utilizadores.get(i).getY()== y) {
                return utilizadores.get(i).getId();
            }
        }return 0;
    }

    public int getIDEquipaAJogar(){
        return turnos;
    }

}

