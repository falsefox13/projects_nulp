import re


def calc_size_of_css():
    file = open('apache_logs')
    pattern = "May/2015:((10:[0-5][0-9]|11:[0-1][0-7])|(03:2[0-9]|0[4-9]:[0-5][0-9])).*GET.*\.css.*200\s\d{4}"
    total_size_of_css = 0
    for line in file:
        match = re.search(pattern, line)
        if match:
            current_size = int(match.group()[-4:])
            total_size_of_css += current_size
    print("The total size of .css files that was returned is: " + str(total_size_of_css))
    file.close()


if __name__ == "__main__":
    calc_size_of_css()
