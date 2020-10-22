import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;



public class CoracaoCartas
{

    //Define-se 03 Stacks 
    static Stack<Integer> stack_1 = new Stack<Integer>();         
    static Stack<Integer> stack_2 = new Stack<Integer>(); 
    static Stack<Integer> stack_3 = new Stack<Integer>(); 

/***********************************************************************
Tentativas de jogar (Tirar Cartas do topo das pilhas):
1) Primeira tentativa de tirar tudo, o que representa um caso fácil 

EmptyStackException
***********************************************************************/
    static void estudar_3_pilhas()
    {   
//Vemos quantas cartas tem inicialmente:
        int tamanho_inicial=stack_1.size()+stack_2.size()+stack_3.size();
        //System.out.print("tamanho_inicial "+tamanho_inicial+"\n");

        boolean pular_soma_3 = false;
        int soma_3=0;
//Se por acaso uma das columnas estiver vazia, nem adianta tentar somar as 3 columnas
        if(
            stack_1.isEmpty() ||
            stack_2.isEmpty() ||
            stack_3.isEmpty() 
        )
        {
            pular_soma_3=true;
        }
        else{
            soma_3=stack_1.peek()+stack_2.peek()+stack_3.peek();        
        }
// Estudando colunas: 1,2, 3 

        if(((soma_3 % 3)==0) && (soma_3 !=0) && !pular_soma_3)
        {
            System.out.print("Tirando todas cartas do topo: "+stack_1.peek()+"  "+stack_2.peek()+"  "+stack_3.peek()+"\n");
            stack_1.pop();
            stack_2.pop();
            stack_3.pop();
        }
        else
        {
//Pŕevio 1 e 2 :             
            boolean pular_soma_1_com_2 = false;
            int soma_1_com_2=0;
            //Se por acaso uma das columnas estiver vazia, nem adianta tentar somar as 2 columnas
            if(
                stack_1.isEmpty() ||
                stack_2.isEmpty()
                )
            {
                pular_soma_1_com_2=true;
            }
            else{
                soma_1_com_2=stack_1.peek()+stack_2.peek();        
            }
//1 e 2 :             
            String inerte="@";
            if(!stack_3.empty()) { inerte=stack_3.peek().toString(); }


            if(((soma_1_com_2 % 3)==0)&&(soma_1_com_2 !=0) && ! pular_soma_1_com_2) 
            {
                System.out.print("\n(1,2)     "+stack_1.peek()+".  "+stack_2.peek()+". "+inerte+"\n");
                stack_1.pop();
                stack_2.pop();                
            }
            else
            {
//Pŕevio 1 e 3  :             
                boolean pular_soma_1_com_3 = false;
                int soma_1_com_3=0;
                //Se por acaso uma das columnas estiver vazia, nem adianta tentar somar as 2 columnas
                if(
                    stack_1.isEmpty() ||
                    stack_3.isEmpty()
                    )
                {
                    pular_soma_1_com_3=true;
                }
                else{
                    soma_1_com_3=stack_1.peek()+stack_3.peek();        
                }
//1 e 3  :              
                inerte="@";
                if(!stack_2.empty()) { inerte=stack_2.peek().toString(); }

                if(((soma_1_com_3 % 3)==0)&&(soma_1_com_3 !=0)&& ! pular_soma_1_com_3)
                {
                    System.out.print("\n(1,3)     "+stack_1.peek()+".  "+inerte+" "+stack_3.peek()+".\n");

                    stack_1.pop();
                    stack_3.pop();                
                }
                else
                {
//Pŕevio 2 e 3  :             
                        boolean pular_soma_2_com_3 = false;
                        int soma_2_com_3=0;
                        //Se por acaso uma das columnas estiver vazia, nem adianta tentar somar as 2 columnas
                        if(
                            stack_2.isEmpty() ||
                            stack_3.isEmpty()
                            )
                        {
                            pular_soma_2_com_3=true;
                        }
                        else{
                            soma_2_com_3=stack_2.peek()+stack_3.peek();        
                        }
//2 e 3  :              
                    if(((soma_2_com_3 % 3)==0)&&(soma_2_com_3 !=0) && ! pular_soma_2_com_3) 
                    {
                        
                        inerte="@";
                        if(!stack_1.empty()) { inerte=stack_1.peek().toString(); }
                        System.out.print("\n(2,3)     "+inerte+"  "+stack_2.peek()+". "+stack_3.peek()+".\n");
                        stack_2.pop();
                        stack_3.pop();                
                    }
                
                }
            }
//Quando não for possível tirar por grupos, 
//ESTUDAMOS elemento por elemento
            for(int i=0;i<3;i++)
            {
                int elemento=0;

                switch(i) {
                    case 0:
                        if(!stack_1.empty())
                            elemento=stack_1.peek();
                      break;
                    case 1:
                        if(!stack_2.empty())
                            elemento=stack_2.peek();
                      break;
                    case 2:
                        if(!stack_3.empty())
                            elemento=stack_3.peek();
                }

                if(
                    ((elemento % 3)==0)
                    &&
                    (elemento!=0)
                ) 
                {
                    System.out.print("Tirando as cartas da columa "+(i+1)+"   "+elemento+"\n");                                 
                    switch(i) {
                        case 0:
                            stack_1.pop();
                          break;
                        case 1:
                            stack_2.pop();
                          break;
                        case 2:
                            stack_3.pop();
                      }
                }
            }


        }
//Fim dos Estudos das cartas
        int tamanho_final=stack_1.size()+stack_2.size()+stack_3.size();

        //System.out.print("tamanho_final "+tamanho_final+"\n");

        if(tamanho_inicial==tamanho_final)
        {
            if(tamanho_final==0)
            {
                System.out.print("-------GANHOU-----------\n");
            }            
            else
            {
                System.out.print("-------PERDEU-----------\n");
            }
            return;
        }
        visualizar_3_pilhas();
        estudar_3_pilhas();

    }

///////
    static void estudar_3_pilhasANTIGO()
    {
    }
/***********************************************************************
Inicializamos o jogo com uma altura N>0

Cada carta tem um valor numérico inteiro de 0 até 9
***********************************************************************/
    static void inicializa_3_pilhas(final int N_inicial) 
    {
        final int Max_valor_carta = 9;
        for (int i = 0; i < N_inicial; i++) {
            stack_1.push((int) (Math.random() * Max_valor_carta));
            stack_2.push((int) (Math.random() * Max_valor_carta));
            stack_3.push((int) (Math.random() * Max_valor_carta));
        }
        
    }
/***********************************************************************
Visualizando as 3 pilhas
***********************************************************************/
    static void visualizar_3_pilhas() {        
        
        int maximo_comprimento_pilha=stack_1.size();

        if(maximo_comprimento_pilha<stack_2.size())
        {
            maximo_comprimento_pilha=stack_2.size();
        }

        if(maximo_comprimento_pilha<stack_3.size())
        {
            maximo_comprimento_pilha=stack_3.size();
        }        
        
        int indice_pilha=maximo_comprimento_pilha-1;

        System.out.print("----------------------------\n");

        for(;indice_pilha>=0;indice_pilha--)
        {
            try{
                if(stack_1.size()==(indice_pilha+1))  {System.out.print(".");}
                System.out.print(stack_1.get(indice_pilha)+" ");
            } 
            catch (ArrayIndexOutOfBoundsException e) {                 System.out.print("@ ");
             }

            try{
                if(stack_2.size()==(indice_pilha+1))  {System.out.print(".");}
                System.out.print(stack_2.get(indice_pilha)+" ");
            } 
            catch (ArrayIndexOutOfBoundsException e) { System.out.print("@ "); }

            try{
                if(stack_3.size()==(indice_pilha+1))  {System.out.print(".");}
                System.out.print(stack_3.get(indice_pilha)+" ");
            } 
            catch (ArrayIndexOutOfBoundsException e) { System.out.print("@ "); }

            System.out.print(" \n");    
        }
        
    }

public static void main(final String[] args) throws IOException {

    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("Por favor indique um  numero interiro N (0 ≤ N ≤ 100): \n ");

    try {
        final int N_entrada = Integer.parseInt(br.readLine());

        if((N_entrada>0) && (N_entrada<=100))
        {
            inicializa_3_pilhas(N_entrada);
            visualizar_3_pilhas();
            System.out.println("-------------------------------------------");
            estudar_3_pilhas();
        }
        else
        {
            System.err.println("Cada instância é iniciada por um inteiro N (0 ≤ N ≤ 100)");
            if(N_entrada==0)
                System.out.println("com a entra nula (N=0) não pode-se iniciar nenhum jogo");
            else
                System.out.println("com um valor de entrada exagerada (N>100) não pode-se iniciar nenhum jogo");
        }

    } catch (final NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }
    }
//////////////////////////////////////////
}
