
#Text to binary
def cypher(message):
    cypher_words = []
    for letter in message:
        cypher_letter = format(ord(letter), 'b')
        cypher_words.append(cypher_letter)
    return ' '.join(cypher_words)

#Text to binary
def decipher(message):
    binary_base = [1, 2, 4, 8, 16, 32, 64, 128]
    words = message.split(' ')
    decipher_message = []
    for word in words:
        word = str(word)
        sumatory = 0
        for value, letter in enumerate(word[::-1]):
            if int(letter) == 1:
                sumatory += binary_base[value]
        decipher_letter = chr(sumatory)
        decipher_message.append(decipher_letter)
    return "".join(decipher_message)