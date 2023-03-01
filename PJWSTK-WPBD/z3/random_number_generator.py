import random
import os

file = open('random_numbers.txt', 'a')

for i in range(15000):
	number = random.randrange(0, 1000000)
	file.write(f'{str(number)} ')
file.write('7')
