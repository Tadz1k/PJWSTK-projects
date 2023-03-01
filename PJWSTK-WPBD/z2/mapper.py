import sys

for line in sys.stdin:
	line = line.strip()
	line = line.replace(' ', '')
	for letter in line:
		print('{0}\t{1}'.format(letter, 1))
