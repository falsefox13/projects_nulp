def insertion_sort(list_to_sort):
    for i in range(1, len(list_to_sort)):
        current = list_to_sort[i]
        position = i
        while position > 0 and current > list_to_sort[position - 1]:
            list_to_sort[position] = list_to_sort[position - 1]
            position -= 1
        list_to_sort[position] = current


if __name__ == "__main__":
    file = open('discnt_in.txt')
    lines = [line.rstrip('\n') for line in file]
    prices_list = []
    for price in lines[0].split(" "):
        prices_list.append(int(price))
    discount_percent = int(lines[1])

    insertion_sort(prices_list)

    third = int(len(prices_list) / 3)
    final_price = 0
    for pos in range(0, len(prices_list)):
        if pos < third:
            discount = (100 - discount_percent) / 100
            final_price += (prices_list[pos] * discount)
        else:
            final_price += prices_list[pos]

    print("final price: %.2f" % final_price)

    f = open("discnt_out.txt", "w")
    f.write("%.2f" % final_price)
