from Rally import Rally
import time
from Quick_sort import QuickSort


def insertion_sort(list_to_sort):
    comparisons = 0
    swaps = 0
    for i in range(1, len(list_to_sort)):
        current = list_to_sort[i]
        position = i
        while position > 0 and current.duration > list_to_sort[position - 1].duration:
            comparisons += 1
            swaps += 1
            list_to_sort[position] = list_to_sort[position - 1]
            position -= 1
        list_to_sort[position] = current
    print("Comparisons: " + str(comparisons))
    print("Swaps: " + str(swaps))


if __name__ == "__main__":
    rally_list = []
    country_pos = 0
    duration_pos = 1
    price_pos = 2
    file = open('rally_list.txt')
    for line in file:
        values = line.split(',')
        rally = Rally(values[country_pos], int(values[duration_pos]), int(values[price_pos][:-1]))
        rally_list.append(rally)

    print("INSERTION SORT")
    start_time = time.clock()
    insertion_sort(rally_list)
    print("Time: " + str(time.clock() - start_time))
    for rally in rally_list:
        print(rally)

    print("QUICK SORT")
    quick_sort = QuickSort(rally_list)
    start_time = time.clock()
    quick_sort.quick_sort()
    print("Time: " + str(time.clock() - start_time))
    for rally in rally_list:
        print(rally)









