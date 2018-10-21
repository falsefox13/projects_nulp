from collections import defaultdict


class Graph:
    def __init__(self, graph):
        self.graph = graph
        self.marked = {}
        self.stack = []
        for v in graph.keys():
            if v not in self.marked:
                self.dfs(v)

    def dfs(self, vertex):
        self.marked[vertex] = True
        for v in self.graph[vertex]:
            if v not in self.marked.keys():
                self.dfs(v)
        self.stack.append(vertex)


if __name__ == "__main__":
    needed_docs = defaultdict(list)
    for line in open("govern_in.txt"):
        vertexes = line.split()
        if len(vertexes) != 2:
            break
        needed_docs[vertexes[0]].append(vertexes[1])
    result_queue = Graph(needed_docs).stack
    print(result_queue)
    govern_out = open("govern_out.txt", 'w')
    for res in result_queue:
        govern_out.write(res + "\n")
