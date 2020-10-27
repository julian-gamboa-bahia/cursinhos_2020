package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import java.util.Stack;


// Handler value: example.Handler
public class Hello implements RequestHandler<SQSEvent, String>{
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Define-se 03 Stacks 
    Stack<Integer> stack_1 = new Stack<Integer>();         
    Stack<Integer> stack_2 = new Stack<Integer>(); 
    Stack<Integer> stack_3 = new Stack<Integer>();   
    //Saida pre-JSON (Mapa):
    Map<String, String[]> myMap = new HashMap<String, String[]>();
    //Saida: Juntando as jogadas
    String response_jogadas = new String();
    // GANHOU PERDEU
    String resultado="";
    int numero_jogadas=0;

/***********************************************************************
Tentativas de jogar (Tirar Cartas do topo das pilhas):
1) Primeira tentativa de tirar tudo, o que representa um caso fácil 
2) Segunda tentiva: tirar apenas 2 cartas que satisfazem a regra de somar um multiplo de 3
***********************************************************************/
void estudar_3_pilhas()
{   
    visualizar_3_pilhas();
    numero_jogadas++;
//Vemos quantas cartas tem inicialmente:
    int tamanho_inicial=stack_1.size()+stack_2.size()+stack_3.size();
    
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
//System.out.print("\n(1,3)     "+stack_1.peek()+".  "+inerte+" "+stack_3.peek()+".\n");

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
//System.out.print("\n(2,3)     "+inerte+"  "+stack_2.peek()+". "+stack_3.peek()+".\n");
                    stack_2.pop();
                    stack_3.pop();                
                }
                else
                {
//Se não for possível fazer a remoção em pares 1-2 ou 1-3 ou 2-3: 
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
            
            }
//Se não for possível fazer a remoção em pares 1-2 ou 1-3 ou 2-3: 
        }
    }
//Fim dos Estudos das cartas
    int tamanho_final=stack_1.size()+stack_2.size()+stack_3.size();

//System.out.print("tamanho_final "+tamanho_final+"\n");

    if(tamanho_inicial==tamanho_final)
    {
        if(tamanho_final==0)
        {
//System.out.print("-------GANHOU-----------\n");
            resultado="GANHOU";          
        }            
        else
        {
//System.out.print("-------PERDEU-----------\n");
            resultado="PERDEU";
        }
        return;
    }
    //visualizar_3_pilhas();
    estudar_3_pilhas();

}

/***********************************************************************
Visualizando as 3 pilhas
***********************************************************************/
void visualizar_3_pilhas() {   

    myMap = new HashMap<String, String[]>();

  int maximo_comprimento_pilha=stack_1.size();

  if(maximo_comprimento_pilha<stack_2.size())
  {
      maximo_comprimento_pilha=stack_2.size();
  }

  if(maximo_comprimento_pilha<stack_3.size())
  {
      maximo_comprimento_pilha=stack_3.size();
  }        
  
  //int indice_pilha=maximo_comprimento_pilha-1;
  //for(;indice_pilha>=0;indice_pilha--)  
  for(int indice_pilha=0;indice_pilha<maximo_comprimento_pilha;indice_pilha++)  
  {
      String[] vetor_3_pilhas={"@","@","@"};
      try{
          vetor_3_pilhas[0]=stack_1.get(indice_pilha)+"";
      } 
      catch (ArrayIndexOutOfBoundsException e) {                 
        vetor_3_pilhas[0]="@";
       }

      try{
          vetor_3_pilhas[1]=stack_2.get(indice_pilha)+"";         
      } 
      catch (ArrayIndexOutOfBoundsException e) {         
        vetor_3_pilhas[1]="@";
      }

      try{
          vetor_3_pilhas[2]=stack_3.get(indice_pilha)+"";
      } 
      catch (ArrayIndexOutOfBoundsException e) { 
        vetor_3_pilhas[2]="@";
      }      
      myMap.put(indice_pilha+"", vetor_3_pilhas);
  }
//Já com todos os elementos (saindo do FOR), 
  if(response_jogadas.length()==0)
  {
    response_jogadas=gson.toJson(myMap);
  }
  else
  {
    response_jogadas=response_jogadas+","+gson.toJson(myMap);
  }
  
}

/***********************************************************************
Inicializamos o jogo com uma altura N>0
Cada carta tem um valor numérico inteiro de 0 até 9
***********************************************************************/
void esvaziando_pilhas(Stack<Integer> stackESVAZIAR)
{
  while(stackESVAZIAR.size()>0)
  {
    stackESVAZIAR.pop();    
  }
}

void inicializa_3_pilhas(final int N_inicial) 
{
    final int Max_valor_carta = 9;
//Esvaziamos as pilhas
    esvaziando_pilhas(stack_1);
    esvaziando_pilhas(stack_2);
    esvaziando_pilhas(stack_3);
 

    for (int i = 0; i < N_inicial; i++) {
        stack_1.push((int) (Math.random() * Max_valor_carta));
        stack_2.push((int) (Math.random() * Max_valor_carta));
        stack_3.push((int) (Math.random() * Max_valor_carta));
    }
}

  @Override
  public String handleRequest(SQSEvent event, Context context)
  {
    String response = new String();
    
    response_jogadas="";

    int altura_cartas=7;

    numero_jogadas=0;

    inicializa_3_pilhas(altura_cartas);

    //visualizar_3_pilhas();

    estudar_3_pilhas();

    try {
     
      response = "{\"altura_cartas\":\""+altura_cartas+
      "\", \"numero_jogadas\":"+numero_jogadas+",\"resultado\":\""+resultado+"\", \"jogadas\": ["+response_jogadas+"]}";
      
    } catch(Exception e) {
      e.getStackTrace();
    }
    return response;
  }
}