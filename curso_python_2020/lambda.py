import json
import sys 

'''
Algoritmo de PRIM, 
adaptado de:

https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/

'''
class Graph():
	
	arvore_minima=[]

	def __init__(self, vertices):
		self.V = vertices
		self.graph = [[0 for column in range(vertices)] 
					for row in range(vertices)]

# Saída no formato JSON
	def imprimirSAIDA(self, parent):
		saida="["
		for i in range(1, self.V):			
			saida=saida+"{\"inicio\":"+str(parent[i])+","\
			+"\"fim:\":"+str(i)+","\
			+"\"peso:\":"+str(self.graph[i][ parent[i]])+"},"
		saida=saida+"]"

	# A utility function to find the vertex with 
	# minimum distance value, from the set of vertices 
	# not yet included in shortest path tree
	def minKey(self, key, mstSet):

		# Initilaize min value
		min = sys.maxsize

		for v in range(self.V):
			if key[v] < min and mstSet[v] == False:
				min = key[v]
				min_index = v

		return min_index

	# Function to construct and print MST for a graph 
	# represented using adjacency matrix representation
	def primMST(self):

		# Key values used to pick minimum weight edge in cut
		key = [sys.maxsize] * self.V
		parent = [None] * self.V # Array to store constructed MST
		# Make key 0 so that this vertex is picked as first vertex
		key[0] = 0
		mstSet = [False] * self.V

		parent[0] = -1 # First node is always the root of

		for cout in range(self.V):

			# Pick the minimum distance vertex from 
			# the set of vertices not yet processed. 
			# u is always equal to src in first iteration
			u = self.minKey(key, mstSet)

			# Put the minimum distance vertex in 
			# the shortest path tree
			mstSet[u] = True

			# Update dist value of the adjacent vertices 
			# of the picked vertex only if the current 
			# distance is greater than new distance and
			# the vertex in not in the shotest path tree
			for v in range(self.V):

				# graph[u][v] is non zero only for adjacent vertices of m
				# mstSet[v] is false for vertices not yet included in MST
				# Update the key only if graph[u][v] is smaller than key[v]
				if self.graph[u][v] > 0 and mstSet[v] == False and key[v] > self.graph[u][v]:
						key[v] = self.graph[u][v]
						parent[v] = u

		self.arvore_minima=[]
		for i in range(1, self.V):			
			jsonTEMP={
				"inicio":parent[i],
				"fim":i,
				"peso":self.graph[i][parent[i]]
			}
			self.arvore_minima.append(jsonTEMP)
		return self.arvore_minima

'''
Manipulação da entrada 
'''

def lambda_handler(event, context):
    
    
    # entradas:    
    
    numero_vertices=int(event["queryStringParameters"]['numero_vertices'])

    conexoes_vertices=event["queryStringParameters"]['conexoes_vertices']
    
    separando_linhas=conexoes_vertices.split(";")

#verificamos que tive-se recebendo informação completa e coesa

    if(len(separando_linhas)!=numero_vertices):
        return {
            "statusCode": 200,
            "headers": {},
            "body": json.dumps({"erro":"errado"})
        }
    
# no caso de ter uma entrada coesa:
    linha_entrada=[]
    grafo_inicial=[]
    for linha in separando_linhas:
        linha_entrada=linha.split(",")
        #transforma-se cada entrada num valor INT
        linha_entrada_numerica=[int(i) for i in linha_entrada]
        grafo_inicial.append(linha_entrada_numerica)
        
# Invocando o algoritmo de PRIM
    g = Graph(numero_vertices)
    g.graph =grafo_inicial
    arvoreSAIDA=g.primMST()
    
# Corpo da saida

    saida={ 
    #"event":grafo_inicial,
    #"numero_vertices":numero_vertices,
    "arvoreSAIDA":arvoreSAIDA
    }
# Saida completa
    response = {
        "statusCode": 200,
        "headers": {},
        "body": json.dumps(saida)
    }
    return response
