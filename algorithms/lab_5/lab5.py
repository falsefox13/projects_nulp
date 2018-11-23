def find_difference(str1, str2):
    str1_counter, str2_counter = 0, 0
    count_diffs = 0
    while str1_counter < len(str1) and str2_counter < len(str2):
        if str1[str1_counter] != str2[str2_counter]:
            count_diffs += 1
            str2_counter -= 1
        str1_counter += 1
        str2_counter += 1
    return count_diffs


def read_file():
    words = []
    for line in open("wchain_in.txt"):
        stripped = line.strip("\n")
        words.append(stripped)
    print "Got " + str(words.pop(0)) + " words in"
    words.sort(key=lambda s: len(s))
    return words


if __name__ == "__main__":
    words_to_use = read_file()
    # add first word by default
    answers = [words_to_use.pop(0)]

    for word in words_to_use:
        prev_difference = 0
        previous_added = answers[-1]

        if len(word) - len(previous_added) == 1:
            prev_difference = find_difference(word, previous_added)
            if prev_difference <= 1:
                answers.append(word)

        # check if found a better option
        elif len(word) == len(previous_added):
            prev_difference = find_difference(word, answers[-2])
            if prev_difference <= 1:
                answers.pop()
                answers.append(word)
    print(answers)
    answer_file = open("wchain_out.txt", 'w')
    answer_file.write(str(len(answers)))