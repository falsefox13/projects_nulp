import math


def reverse_graph(graph, n):
    processed_graph = [[0 for i in range(n)] for i in range(n)]
    for i in range(n):
        for j in range(n):
            processed_graph[i][j] = graph[j][i]
    return processed_graph


def dfs_helper(graph, start, start_weight, difference):
    edges = {start_weight}

    def dfs(start):
        used.add(start)
        for index, weight in enumerate(graph[start]):
            if index not in used and index != start:
                edges.add(weight)
                if (max(edges) - min(edges)) <= difference:
                    dfs(index)
                else:
                    edges.remove(weight)
    used = set()
    dfs(start)
    return used


def find_implications(graph, reversed_graph, n, max_difference):
    answer_found = False
    answers = set()
    for difference in range(max_difference):
        copy_graph = graph[0].copy()[1:]
        for start_weight in copy_graph:
            answer = dfs_helper(graph, 0, start_weight, difference)
            if len(answer) == n:
                reverse_answer = dfs_helper(reversed_graph, 0, start_weight, difference)
                if len(reverse_answer) == n:
                    answers.add(difference)
                    answer_found = True
            if answer_found:
                break
        if answer_found:
            break
    print("Result: " + str(answers))
    answers_file = open("answer.txt", 'w')
    for answ in answers:
        answers_file.write(str(answ) + "\n")


if __name__ == "__main__":
    for line in open("profes.txt"):
        values_from_file = line.split(",")
    converted_to_int = []
    for v in values_from_file:
        converted_to_int.append(int(v))
    n = int(math.sqrt(len(values_from_file)))

    graph = [[0 for i in range(n)] for i in range(n)]
    for i in range(n):
        for j in range(n):
            graph[i][j] = converted_to_int[i * n + j]
    print("Input graph is: " + str(graph))

    set_values = set(converted_to_int)
    n = len(graph)
    reversed_graph = reverse_graph(graph, n)

    max_edge = max(set_values)
    min_edge = min(set_values)

    max_difference_between_edge = max_edge - min_edge + 1
    find_implications(graph, reversed_graph, n, max_difference_between_edge)

