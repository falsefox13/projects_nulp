class QuickSort:
    list_to_sort = []
    swaps = 0
    comparisons = 0

    def __init__(self, list):
        self.list_to_sort = list

    def quick_sort(self):

        def partition(start, end):
            if end - start < 1:
                return

            left = start + 1
            right = end
            pivot = self.list_to_sort[start].price
            while True:
                while self.list_to_sort[left].price < pivot and left < end:
                    left += 1
                    self.comparisons += 1

                while not self.list_to_sort[right].price < pivot and right > start:
                    right -= 1
                    self.comparisons += 1

                if left >= right:
                    break

                self.list_to_sort[left], self.list_to_sort[right] = self.list_to_sort[right], self.list_to_sort[left]
                self.swaps += 1
            self.list_to_sort[start], self.list_to_sort[right] = self.list_to_sort[right], self.list_to_sort[start]
            self.swaps += 1
            partition(start, right - 1)
            partition(right + 1, end)

        partition(0, len(self.list_to_sort) - 1)
        print("Comparisons: " + str(self.comparisons))
        print("Swaps: " + str(self.swaps))
