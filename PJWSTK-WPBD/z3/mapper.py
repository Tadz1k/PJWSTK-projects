import sys

for line in sys.stdin:
	line = line.split()
	for number in line:
		print('{0}\t{1}'.format(number, 1))
