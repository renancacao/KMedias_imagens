//import java.io.BufferedReader;  

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;  

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.lang.NumberFormatException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class kmedias {

static int[][] Matriz; //cores + grupo
static int C[][]; 
static String datanome="";



static int largura,altura;

public static void main(String[] args) throws IOException, NumberFormatException
{

try
{
int qtdTestes = 0;
int numCentroides=0;

Calendar now = GregorianCalendar.getInstance();
datanome  = now.get(Calendar.YEAR) + "_" + now.get(Calendar.MONTH) + "_" + now.get(Calendar.DAY_OF_MONTH) + "_" + now.get(Calendar.HOUR_OF_DAY) + "_" + now.get(Calendar.MINUTE) ;

while ((numCentroides<2)||(numCentroides>16))
{
//define numero de clusters (4 a 16)
numCentroides = msg("Digite a quantidade de cores (mín:2 - máx:16):", 1);
}




while ((qtdTestes<1)||(qtdTestes>10))
{
//define a quantidade de iterações desejadas
qtdTestes = msg("Digite a quantidade de testes para o algoritmo com " + numCentroides + " cores (mín:1 - máx:10):", 1);
}


//leio os dados
System.out.println("Lendo a imagem...");


if (!leImagem())
{
msg("Imagem não encontrada!", 0);
return;
}


//cria o gerardor de randons
Random numero = new Random();

//variavel que pega o valor aleatorio
int vRand=0;
int oldRnd[] = new int[numCentroides];
//inicializo as variaveis 
for (int ini=0; ini < oldRnd.length;ini++)
{
oldRnd[ini]=-1;
}



//aqui ocorre todo o procsso de iteracao
for(int i =0;i<qtdTestes;i++)
{



//crio centroides
C = new int[numCentroides][3];
String[] cores = new String[numCentroides];

for (int cc = 0;cc<numCentroides;cc++)
{
//defino os centroides verificando se eles ja nao foram escolhidos

do
{

vRand = numero.nextInt(Matriz.length-1);

//oldRnd[cc] = vRand; // variavel pra impedir dos centróides de  cairem no mesmo ponto  

//inicializo os centroides
C[cc][0] = Matriz[vRand][0];
C[cc][1] = Matriz[vRand][1];
C[cc][2] = Matriz[vRand][2];

}
while (Arrays.asList(cores).contains(Matriz[vRand][0]+";"+Matriz[vRand][1]+";"+Matriz[vRand][2]));

cores[cc]=Matriz[vRand][0]+";"+Matriz[vRand][1]+";"+Matriz[vRand][2];


//verificose ascores não sao repetidas


}

//aqui o algoritmo faz o trabalho sujo

//executa o algoritmo para definir os clusters enquanto for preciso
int cont=0;
do
{

cont++;

//gera clusters
executak(numCentroides);


}
while (calculaCentroides()); //recalcula os centroides



double somas[]=  new double[numCentroides];

int id;

//exibir os pontos e seus centroides
for( int j =0; j< Matriz.length;j++)
{
id = (int) Matriz[j][3];
somas[id]+= Math.pow( Math.sqrt( Math.pow((C[id][0] - Matriz[j][0]),2)+ Math.pow((C[id][1] - Matriz[j][1]),2))+ Math.pow((C[id][2] - Matriz[j][2]),2),2) ;
//System.out.println(somac1);
}


double seq=0;
for (int s=0;s<somas.length;s++)
{
seq+=somas[s];
}


//adiciono o SEQ no arquivo

gravaArquivo(numCentroides,i+1,seq);


}

System.out.println("---FIM---");




}
catch(Exception e)
{
msg("Erro ao ler um valor!",0);
return;
}

}

private static int msg(String texto,int tipo)
{

JFrame f = new JFrame();
f.setVisible(true);
f.setSize(0, 0);
f.toFront();

JTextArea area = new JTextArea(texto);
area.setRows(30);
area.setColumns(50);
area.setLineWrap(true);
area.setEditable(false);
JScrollPane pane = new JScrollPane(area);

String val = "-1";

if (tipo==0)
{
JOptionPane.showMessageDialog(null, pane);
}
else
{
val = JOptionPane.showInputDialog(null,texto);
}



f.setVisible(false);

return Integer.parseInt(val);


}

private static void msgImg(String img, String texto)
{


JFrame f = new JFrame();
f.setVisible(true);
f.setSize(0, 0);
f.toFront();

JTextArea area = new JTextArea(img + "\n\rSEQ: " + texto);
area.setRows(10);
area.setColumns(30);
area.setLineWrap(true);
area.setEditable(false);
JScrollPane pane = new JScrollPane(area);


ImageIcon i = new ImageIcon(img);

JOptionPane.showMessageDialog(null, pane,"Resultado",0,i);




f.setVisible(false);



}

private static void gravaArquivo(int clusters, int teste, double SEQ) throws IOException {


BufferedImage saida =  new BufferedImage(largura, altura,BufferedImage.TYPE_INT_RGB);
Graphics2D g2 = saida.createGraphics();

int cont = 0;
Color c;
int id =-1;

//percorro imagem
for (int i =0;i<largura;i++)
{
for (int j =0;j<altura;j++)
{
id =  Matriz[cont][3];

c = new Color(C[id][0],C[id][1],C[id][2]);
g2.setColor(c);
g2.fillRect(i, j, 1, 1);
cont++;
}
}

File file = new File(datanome + "_c" + clusters + "_t" + teste + ".png");

ImageIO.write(saida, "png",file );

//msg("SEQ = " + String.valueOf(SEQ),0);
//System.out.println("SEQ = " + String.valueOf(SEQ));


msgImg(datanome + "_c" + clusters + "_t" + teste + ".png",String.valueOf(SEQ) );
}

private static boolean calculaCentroides() 
{
boolean mudou = false;


int novosC[][] = new int[C.length][3];

int elementos[] = new int [C.length];


for (int i=0; i<Matriz.length; i++)
{
novosC[(int) Matriz[i][3]][0] += Matriz[i][0];
novosC[(int) Matriz[i][3]][1] += Matriz[i][1];
novosC[(int) Matriz[i][3]][2] += Matriz[i][2];
elementos[(int) Matriz[i][3]] ++;

}


for (int i=0; i<C.length; i++)
{

novosC[i][0] = novosC[i][0] /  elementos[i];
novosC[i][1] = novosC[i][1] /  elementos[i];
novosC[i][2] = novosC[i][2] /  elementos[i];


if (novosC[i][0]!=C[i][0] || novosC[i][1]!=C[i][1] || novosC[i][2]!=C[i][2])
{

mudou = true;
}


}


C= novosC.clone();

return mudou;

}

private static void executak(int nCentroides) {

double menorDistancia;
double distancia = 0;

for (int i=0; i<Matriz.length; i++)
{

menorDistancia = 99999999;

for (int j=0; j<C.length; j++)
{
distancia = Math.sqrt(Math.pow((C[j][0] - Matriz[i][0]),2)+ Math.pow((C[j][1] - Matriz[i][1]),2)+ Math.pow((C[j][2] - Matriz[i][2]),2));

if (distancia <= menorDistancia)
{
menorDistancia = distancia;
Matriz[i][3]= j;
}

}
}

}

static boolean leImagem() throws IOException
{



//pego a imagem
File arquivo = new File("imagem.png");  
        if(!arquivo.exists())  
        {  
          return false;
        } 
        
BufferedImage bimg = ImageIO.read(arquivo);
largura = bimg.getWidth();
altura = bimg.getHeight();


//defino novo tamanhp da matriz
Matriz = new int[largura*altura][4];

int[] rgb;

int cont =0;

//percorro imagem
for (int i =0;i<largura;i++)
{
for (int j =0;j<altura;j++)
{
rgb = getCores(bimg,i,j);

Matriz[cont][0]=rgb[0];
Matriz[cont][1]=rgb[1];
Matriz[cont][2]=rgb[2];
Matriz[cont][3]=-1;

cont++;
}
}


return true;
          
    }  

static  int[] getCores(BufferedImage img,int x, int y)
{
int argb = img.getRGB(x, y);
int rgb[] = new int[] {
(argb >> 16) & 0xff,
(argb >> 8) & 0xff,
(argb) & 0xff};


return rgb;

}


}