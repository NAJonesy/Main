#Graphs
G = {0:{1:6, 3:1},
     1:{0:6, 2:5},
     2:{1:5, 3:8, 4:4},
     3:{0:1, 2:8,  4:7},
     4:{2:4, 3:7}}

G1 = {0:{1:1, 4:8, 5:6},
      1:{0:1, 2:2, 3:2},
      2:{1:2, 3:9, 5:3},
      3:{1:2, 2:9, 4:4},
      4:{0:8, 3:4, 5:5},
      5:{0:6, 2:3, 4:5, 6:8},
      6:{5:8}}

G2 = {0:{4:6, 3:2},
      1:{5:8},
      2:{5:2},
      3:{0:2, 5:1},
      4:{0:6, 5:6},
      5:{1:8, 2:2, 3:1, 4:6}}
      
def findParent(U, start): #finding the parent of an edge takes maximum O(m) time.
    if U[start] != start:
        return findParent(U,U[start])
    else:
        return U[start]

def Kruskal(G):
    U = {}
    edges = []
    for i in G: #this loop runs in O(m) time. m being the number of edges
        U.update({i:i})
        for j in G[i]:
            edges.append([i,j,G[i][j]])
            del G[j][i]
    edges.sort(key=lambda x:x[2]) #this sorts the list of edges based on weight taking O(m lg m) time
    for i in edges: #this adds the edges if they fit the criteria. going through all of the edges takes O(m) time
        x = findParent(U,i[0])
        y = findParent(U,i[1])
        if x != y:
            if i[0] == x and i[1] != y:
                U[i[0]] = i[1]
            elif i[1] == y and i[0] != x:
                U[i[1]] = i[0]
            else:         
                if i[0] > i[1]:
                    U[i[0]] = i[1]
                else:
                    U[i[1]] = i[0]      
    return U

#total I believe this algorithm should run in O(m + m lg m + m) time
    
Kruskal(G)
Kruskal(G1)
Kruskal(G2)